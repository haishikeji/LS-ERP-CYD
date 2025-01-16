package cn.px.tea.modules.ycb.service.base.impl;

import cn.px.tea.modules.ycb.model.SysMenu;
import cn.px.tea.modules.ycb.model.SysPost;
import cn.px.tea.modules.ycb.model.SysRole;
import cn.px.tea.modules.ycb.model.SysRoleMenu;
import cn.px.tea.modules.ycb.service.table.intel.*;
import com.github.pagehelper.PageHelper;
import cn.px.tea.common.domain.TableDataInfo;
import cn.px.tea.common.util.DateUtils;
import cn.px.tea.common.util.uuid.IdUtils;
import cn.px.tea.modules.ycb.model.*;
import cn.px.tea.modules.ycb.pojo.bo.com.UserInfo;
import cn.px.tea.modules.ycb.service.base.inter.UserService;
import cn.px.tea.modules.ycb.service.table.intel.*;
import cn.px.tea.modules.ycb.service.base.inter.WebRoleService;
import cn.px.tea.security.annotation.CustomException;
import cn.px.tea.security.define.SystemDefine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebRoleImpl implements WebRoleService {

    @Autowired
    private UserService userService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPostService sysPostService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysEmpService sysEmpService;

    @Autowired
    private SysShopEmpService sysShopEmpService;


   @Override
    public List<Long> listMenuByRole(Long roleId) {
       List<SysRoleMenu> listRoleMenu = sysRoleMenuService.listByRoleId(roleId);
       if (listRoleMenu == null || listRoleMenu.size() == 0) {
           return new ArrayList<>();
       }
       List<Long> listMenuId = listRoleMenu.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        return listMenuId;
    }

    @Override
    public List<Long> setRoleMenu(Long roleId, List<Long> listMenuId) {
        SysRole sysRole = sysRoleService.getByRoleId(roleId);
        if (sysRole == null) {
            throw CustomException.instance("角色信息不存在");
        }

        // 已有的角色菜单
        List<SysRoleMenu> listRoleMenu = sysRoleMenuService.listByRoleId(roleId);
        if (listRoleMenu != null && listRoleMenu.size() > 0) {
            List<Long> _listRmid = listRoleMenu.stream().map(SysRoleMenu::getId).collect(Collectors.toList());
            if (!sysRoleMenuService.removeByIds(_listRmid)) {
                throw CustomException.instance("设置角色菜单失败");
            }
        }

        if (listMenuId.size() > 0) {
            List<SysMenu> listMenu = sysMenuService.listByMenuId(listMenuId, 1);
            if (listMenu != null && listMenu.size() > 0) {
                listMenuId = listMenu.stream().map(SysMenu::getMenuId).collect(Collectors.toList());

                Timestamp curTime = DateUtils.getNowTimestamp();
                listRoleMenu = new ArrayList<>();
                for (SysMenu sysMenu : listMenu) {
                    listRoleMenu.add(new SysRoleMenu(roleId, sysMenu.getMenuId(), "", curTime));
                }

                if (!sysRoleMenuService.saveBatch(listRoleMenu)) {
                    throw CustomException.instance("设置角色菜单失败");
                }
            }
        }

        return listMenuId;
    }

    @Override
    public TableDataInfo<SysRole> pageRole(Integer pageNum, Integer pageSize, String roleKey, String roleName, Integer status) {
       UserInfo userInfo = userService.getUserInfo();
       Integer group = userInfo.getGroup();
       if(SystemDefine.isRoleAdmin(userInfo.getRoleKey())) {
           group = null;
       }

        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> listRole = sysRoleService.listByCondition(group, roleKey, roleName, status);
        if (listRole == null || listRole.size() == 0) {
            return TableDataInfo.success();
        }
        return TableDataInfo.success(pageNum, pageSize, listRole);
    }

    @Override
    public SysRole getRoleInfo(Long roleId) {
        SysRole sysRole = sysRoleService.getByRoleId(roleId);
        if (sysRole == null) {
            throw CustomException.instance("角色信息不存在");
        }
        return sysRole;
    }

    @Override
    public SysRole addRole(String roleKey, String menuKey, String roleName, Integer group, String remark) {
        if (StringUtils.isBlank(remark)) {
            remark = "";
        }
        if (StringUtils.isBlank(menuKey)) {
            menuKey = "";
        }
        if (group == null || group < 1 || group > 2) {
            group = 1;
        }
        UserInfo userInfo = userService.getUserInfo();
        if (!SystemDefine.isRoleAdmin(userInfo.getRoleKey())) {
            throw CustomException.instance("您没有权限操作");
        }

       SysRole sysRole = sysRoleService.getByRoleKey(roleKey);
       if (sysRole != null) {
           throw CustomException.instance("角色KEY已存在，不可重复添加");
       }

       sysRole = new SysRole(IdUtils.getUnid(), roleName, roleKey, group, menuKey, 1, remark, DateUtils.getNowTimestamp());
       if (!sysRoleService.save(sysRole)) {
           throw CustomException.instance("添加角色失败");
       }
        return sysRole;
    }

    @Override
    public SysRole editRole(Long roleId, String menuKey, String roleKey, String roleName, String remark) {
        UserInfo userInfo = userService.getUserInfo();
        if (!SystemDefine.isRoleAdmin(userInfo.getRoleKey())) {
            throw CustomException.instance("超级管理员不可编辑");
        }

        SysRole sysRole = sysRoleService.getByRoleId(roleId);
        if (sysRole == null) {
            throw CustomException.instance("角色信息不存在");
        }

        if (StringUtils.isNotBlank(roleKey) && !roleKey.equals(sysRole.getRoleKey())) {
            SysRole _sysRole = sysRoleService.getByRoleKey(roleKey);
            if (_sysRole != null) {
                throw CustomException.instance("角色KEY已存在，不可重复添加");
            }
            sysRole.setRoleKey(roleKey);
        }
        if (StringUtils.isNotBlank(menuKey) && !menuKey.equals(sysRole.getMenuKey())) {
            sysRole.setMenuKey(menuKey);
        }
        if (StringUtils.isNotBlank(roleName) && !roleName.equals(sysRole.getRoleName())) {
            sysRole.setRoleName(roleName);
        }
        if (StringUtils.isNotBlank(remark) && !remark.equals(sysRole.getRemark())) {
            sysRole.setRemark(remark);
        }
        sysRole.setUpdateTime(DateUtils.getNowTimestamp());
        if (!sysRoleService.updateById(sysRole)) {
            throw CustomException.instance("编辑角色失败");
        }
        return sysRole;
    }

    @Override
    public SysRole changeStatusRole(Long roleId, Integer status) {
       if (status < 0 || status > 1) {
           status = 0;
       }
        SysRole sysRole = sysRoleService.getByRoleId(roleId);
        if (sysRole == null) {
            throw CustomException.instance("角色信息不存在");
        }

        if (status.equals(sysRole.getStatus())) {
            return sysRole;
        }

        sysRole.setStatus(status);
        sysRole.setUpdateTime(DateUtils.getNowTimestamp());
        if (!sysRoleService.updateById(sysRole)) {
            throw CustomException.instance("设置角色状态失败");
        }

        return sysRole;
    }

    @Transactional
    @Override
    public SysRole deleteRole(Long roleId) {
        SysRole sysRole = sysRoleService.getByRoleId(roleId);
        if (sysRole == null) {
            throw CustomException.instance("角色信息不存在");
        }

        if (sysShopEmpService.countByRoleId(roleId) > 0L) {
            throw CustomException.instance("角色已被使用，不可删除");
        }

        List<SysRoleMenu> listRoleMenu = sysRoleMenuService.listByRoleId(roleId);
        if (listRoleMenu != null && listRoleMenu.size() > 0) {
            List<Long> listRoleMenuId = listRoleMenu.stream().map(SysRoleMenu::getId).collect(Collectors.toList());
            if (!sysRoleMenuService.removeByIds(listRoleMenuId)) {
                throw CustomException.instance("删除角色失败");
            }
        }

        if (!sysRoleService.removeById(sysRole.getId())) {
            throw CustomException.instance("删除角色失败");
        }
        return sysRole;
    }

    @Override
    public TableDataInfo<SysPost> pagePost(Integer pageNum, Integer pageSize, String postKey, String postName, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysPost> listPost = sysPostService.listByCondition(postKey, postName, status);
        if (listPost == null || listPost.size() == 0) {
            return TableDataInfo.success();
        }
        return TableDataInfo.success(pageNum, pageSize, listPost);
    }

    @Override
    public SysPost getPostInfo(Long postId) {
        SysPost sysPost = sysPostService.getByPostId(postId);
        if (sysPost == null) {
            throw CustomException.instance("岗位信息不存在");
        }
        return sysPost;
    }

    @Override
    public SysPost addPost(String postKey, String postName, String remark) {
        if (StringUtils.isBlank(remark)) {
            remark = "";
        }
        SysPost sysPost = sysPostService.getByPostKey(postKey);
        if (sysPost != null) {
            throw CustomException.instance("岗位KEY已存在，不可重复添加");
        }

        sysPost = new SysPost(IdUtils.getUnid(), postName, postKey, 1, remark, DateUtils.getNowTimestamp());
        if (!sysPostService.save(sysPost)) {
            throw CustomException.instance("添加岗位失败");
        }
        return sysPost;
    }

    @Override
    public SysPost editPost(Long postId, String postKey, String postName, String remark) {
        UserInfo userInfo = userService.getUserInfo();
        if (!SystemDefine.isRoleAdmin(userInfo.getRoleKey())) {
            throw CustomException.instance("超级管理员不可编辑");
        }

        SysPost sysPost = sysPostService.getByPostId(postId);
        if (sysPost == null) {
            throw CustomException.instance("岗位信息不存在");
        }

        if (StringUtils.isNotBlank(postKey) && !postKey.equals(sysPost.getPostKey())) {
            SysPost _sysPost = sysPostService.getByPostKey(postKey);
            if (_sysPost != null) {
                throw CustomException.instance("岗位KEY已存在，不可重复添加");
            }
            sysPost.setPostKey(postKey);
        }
        if (StringUtils.isNotBlank(postName) && !postName.equals(sysPost.getPostName())) {
            sysPost.setPostName(postName);
        }
        if (StringUtils.isNotBlank(remark) && !remark.equals(sysPost.getRemark())) {
            sysPost.setRemark(remark);
        }
        sysPost.setUpdateTime(DateUtils.getNowTimestamp());
        if (!sysPostService.updateById(sysPost)) {
            throw CustomException.instance("编辑岗位失败");
        }
        return sysPost;
    }

    @Override
    public SysPost changeStatusPost(Long postId, Integer status) {
        if (status < 0 || status > 1) {
            status = 0;
        }
        SysPost sysPost = sysPostService.getByPostId(postId);
        if (sysPost == null) {
            throw CustomException.instance("岗位信息不存在");
        }

        if (status.equals(sysPost.getStatus())) {
            return sysPost;
        }

        sysPost.setStatus(status);
        sysPost.setUpdateTime(DateUtils.getNowTimestamp());
        if (!sysPostService.updateById(sysPost)) {
            throw CustomException.instance("设置岗位状态失败");
        }

        return sysPost;
    }

    @Override
    public SysPost deletePost(Long postId) {
        SysPost sysPost = sysPostService.getByPostId(postId);
        if (sysPost == null) {
            throw CustomException.instance("岗位信息不存在");
        }

        if (sysShopEmpService.countByPostId(postId) > 0L) {
            throw CustomException.instance("岗位已被使用，不可删除");
        }

        if (!sysPostService.removeById(sysPost.getId())) {
            throw CustomException.instance("删除岗位失败");
        }
        return sysPost;
    }
}
