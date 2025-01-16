package cn.px.tea.modules.ycb.service.base.impl;

import cn.px.tea.modules.ycb.model.SysMenu;
import cn.px.tea.modules.ycb.model.SysMenuPerm;
import cn.px.tea.modules.ycb.model.SysPerm;
import cn.px.tea.modules.ycb.model.SysPermCategory;
import cn.px.tea.modules.ycb.service.table.intel.*;
import com.github.pagehelper.PageHelper;
import cn.px.tea.common.domain.TableDataInfo;
import cn.px.tea.common.util.DateUtils;
import cn.px.tea.common.util.uuid.IdUtils;
import cn.px.tea.modules.ycb.model.*;
import cn.px.tea.modules.ycb.pojo.bo.com.MenuVo;
import cn.px.tea.modules.ycb.pojo.bo.com.PermNode;
import cn.px.tea.modules.ycb.pojo.bo.com.PermVo;
import cn.px.tea.modules.ycb.pojo.bo.com.UserInfo;
import cn.px.tea.modules.ycb.service.base.inter.UserService;
import cn.px.tea.modules.ycb.service.table.intel.*;
import cn.px.tea.modules.ycb.service.base.inter.WebMenuService;
import cn.px.tea.security.annotation.CustomException;
import cn.px.tea.security.define.SystemDefine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WebMenuImpl implements WebMenuService {

    @Autowired
    private UserService userService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysPermCategoryService sysPermCategoryService;

    @Autowired
    private SysPermService sysPermService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysMenuPermService sysMenuPermService;


    @Override
    public List<MenuVo> treeMenu() {
        List<SysMenu> listMenu = sysMenuService.listByStatus(1);
        if (listMenu == null || listMenu.size() == 0) {
            return new ArrayList<>();
        }

//        List<SysMenuCategory> listMenuCategory = sysMenuCategoryService.list();
//        Map<Long, String> mapMenuCategory = new HashMap<>();
//        if (listMenuCategory != null && listMenuCategory.size() > 0) {
//            for (SysMenuCategory sysMenuCategory : listMenuCategory) {
//                mapMenuCategory.put(sysMenuCategory.getMenuCategoryId(), sysMenuCategory.getName());
//            }
//        }

        List<MenuVo> listVo = new ArrayList<>();
        Map<Long, List<MenuVo>> mapVo = new HashMap<>();
        for (SysMenu sysMenu : listMenu) {
            MenuVo vo = new MenuVo(sysMenu.getMenuId(), sysMenu.getMenuName(), sysMenu.getMenuKey(), sysMenu.getParentId(), 0L/*sysMenu.getMenuCategoryId()*/, sysMenu.getMenuUrl(), sysMenu.getMenuIcon(), sysMenu.getMenuType(), sysMenu.getShowStatus(), sysMenu.getStatus(), sysMenu.getSort());

//            String menuCategoryName = mapMenuCategory.get(vo.getMenuCategoryId());
//            if (StringUtils.isBlank(menuCategoryName)) {
//                menuCategoryName = "";
//            }
            String menuCategoryName = "";
            vo.setMenuCategoryName(menuCategoryName);

            Long parentId = vo.getParentId();
            if (parentId == 0L) {
                listVo.add(vo);
            } else {
                List<MenuVo> _list = mapVo.get(parentId);
                if (_list == null) {
                    _list = new ArrayList<>();
                    mapVo.put(parentId, _list);
                }
                _list.add(vo);
            }
        }

        for (MenuVo vo : listVo) {
            menuRecursive(vo, mapVo);
        }
        return listVo;
    }

    private void menuRecursive(MenuVo vo, Map<Long, List<MenuVo>> mapVo) {
        Long menuId = vo.getMenuId();
        List<MenuVo> _list = mapVo.get(menuId);
        if (_list != null && _list.size() > 0) {
            vo.setChildren(_list);
            for (MenuVo child : _list) {
                menuRecursive(child, mapVo);
            }
        }
    }

    @Override
    public List<PermVo> treePerm() {
        List<SysPermCategory> listPermCategory = sysPermCategoryService.list();
        Map<Long, String> mapPermCategory = new HashMap<>();
        if (listPermCategory != null && listPermCategory.size() > 0) {
            for (SysPermCategory sysPermCategory : listPermCategory) {
                mapPermCategory.put(sysPermCategory.getPermCategoryId(), sysPermCategory.getName());
            }
        }

        List<SysPerm> listPerm = sysPermService.listBySort();
        if (listPerm == null || listPerm.size() == 0) {
            return new ArrayList<>();
        }

        Map<Long, PermVo> mapVo = new HashMap<>();
        List<PermVo> listVo = new ArrayList<>();
        for (SysPerm sysPerm : listPerm) {
            Long categoryId = sysPerm.getPermCategoryId();
            PermVo vo = mapVo.get(categoryId);
            if (vo == null) {
                String categoryName = mapPermCategory.get(categoryId);
                if (StringUtils.isBlank(categoryName)) {
                    categoryName = "";
                }
                vo = new PermVo(categoryId, categoryName, new ArrayList<>());
                listVo.add(vo);
                mapVo.put(categoryId, vo);
            }
            vo.addNode(new PermNode(sysPerm.getPermId(), sysPerm.getPermName(), sysPerm.getPermUrl(), sysPerm.getRemark()));
        }

        return listVo;
    }

    @Transactional
    @Override
    public void menuPerm(Long menuId, List<Long> listPermId) {
        SysMenu sysMenu = sysMenuService.getByMenuId(menuId);
        if (sysMenu == null) {
            throw CustomException.instance("菜单信息不存在");
        }

        List<SysMenuPerm> listMenuPerm = sysMenuPermService.listByMenuId(menuId);
        if (listMenuPerm != null && listMenuPerm.size() > 0) {
            List<Long> listMpid = listMenuPerm.stream().map(SysMenuPerm::getId).collect(Collectors.toList());
            if (!sysMenuPermService.removeByIds(listMpid)) {
                throw CustomException.instance("菜单权限映射配置失败");
            }
        }

        if (listPermId != null && listPermId.size() > 0) {
            List<SysPerm> listPerm = sysPermService.listByPermId(listPermId);
            if (listPerm != null && listPerm.size() > 0) {
                Timestamp curTime = DateUtils.getNowTimestamp();
                listMenuPerm = new ArrayList<>();
                for (SysPerm sysPerm : listPerm) {
                    listMenuPerm.add(new SysMenuPerm(menuId, sysPerm.getPermId(), "", curTime));
                }
                if (!sysMenuPermService.saveBatch(listMenuPerm)) {
                    throw CustomException.instance("菜单权限映射配置失败");
                }
            }
        }
    }

    @Override
    public List<Long> listMenuPerm(Long menuId) {
        List<SysMenuPerm> list = sysMenuPermService.listByMenuId(menuId);
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        return list.stream().map(SysMenuPerm::getPermId).collect(Collectors.toList());
    }


    @Override
    public List<MenuVo> listMenu(Long parentId, Integer menuType) {
        List<SysMenu> listMenu = sysMenuService.listByParentId(parentId, menuType);
        if (listMenu == null || listMenu.size() == 0) {
            return new ArrayList<>();
        }

        List<MenuVo> listVo = new ArrayList<>();
        for (SysMenu sysMenu : listMenu) {
            String categoryName = "";

            MenuVo vo = changeMenuVo(sysMenu);
            vo.setMenuCategoryName(categoryName);
            listVo.add(vo);
        }
        return listVo;
    }

    @Override
    public MenuVo getMenuInfo(Long menuId) {
        SysMenu sysMenu = sysMenuService.getByMenuId(menuId);
        if (sysMenu == null) {
            throw CustomException.instance("菜单不存在");
        }
        String categoryName = "";
        MenuVo menuVo = changeMenuVo(sysMenu);
        menuVo.setMenuCategoryName(categoryName);
        return menuVo;
    }

    private static MenuVo changeMenuVo(SysMenu sysMenu) {
        return new MenuVo(sysMenu.getMenuId(), sysMenu.getMenuName(), sysMenu.getMenuKey(), sysMenu.getParentId(), 0L/*sysMenu.getMenuCategoryId()*/, sysMenu.getMenuUrl(), sysMenu.getMenuIcon(), sysMenu.getMenuType(), sysMenu.getShowStatus(), sysMenu.getStatus(), sysMenu.getSort());
    }

    @Override
    public SysMenu addMenu(String menuName, String menuKey, Long parentId, Long menuCategoryId, String menuUrl, String menuIcon, Integer menuType, Integer showStatus, Integer sort) {
        if (parentId == null) {
            parentId = 0L;
        }
        if (menuCategoryId == null) {
            menuCategoryId = 0L;
        }
        if (StringUtils.isBlank(menuKey)) {
            throw CustomException.instance("菜单KEY不能为空");
        }
        if (StringUtils.isBlank(menuUrl)) {
            menuUrl = "";
        }
        if (StringUtils.isBlank(menuIcon)) {
            menuIcon = "";
        }
        if (menuType < 1 || menuType > 4) {
            menuType = 2;
        }
        if (showStatus < 0 || showStatus > 1) {
            showStatus = 0;
        }
        if (sort == null || sort < 0) {
            sort = 0;
        }

        SysMenu sysMenu = sysMenuService.getByMenuKey(menuKey);
        if (sysMenu != null) {
            throw CustomException.instance("菜单KEY已存在");
        }

        if (parentId != 0L) {
            sysMenu = sysMenuService.getByMenuId(parentId);
            if (sysMenu == null) {
                throw CustomException.instance("父级菜单不存在");
            }
        }
//        if (menuCategoryId != 0L) {
//            SysMenuCategory sysMenuCategory = sysMenuCategoryService.getByCategoryId(menuCategoryId);
//            if (sysMenuCategory == null) {
//                throw CustomException.instance("菜单组不存在");
//            }
//        }

        sysMenu = new SysMenu(
                IdUtils.getUnid(),
                menuName,
                menuKey,
                parentId,
//                menuCategoryId,
                menuUrl,
                menuIcon,
                menuType,
                showStatus,
                1,
                sort,
                DateUtils.getNowTimestamp());
        if (!sysMenuService.save(sysMenu)) {
            throw CustomException.instance("新增菜单失败");
        }

        return sysMenu;
    }

    @Override
    public SysMenu editMenu(Long menuId, String menuName, String menuKey, Long parentId, Long menuCategoryId, String menuUrl, String menuIcon, Integer menuType, Integer showStatus, Integer sort) {
        UserInfo userInfo = userService.getUserInfo();
        if (!SystemDefine.isRoleAdmin(userInfo.getRoleKey())) {
            throw CustomException.instance("超级管理员不可编辑");
        }

        if (menuType != null && (menuType < 1 || menuType > 4)) {
            menuType = 2;
        }
        if (showStatus != null && (showStatus < 0 || showStatus > 1)) {
            showStatus = 0;
        }

        SysMenu sysMenu = sysMenuService.getByMenuId(menuId);
        if (sysMenu == null) {
            throw CustomException.instance("菜单不存在");
        }

        if (StringUtils.isNotBlank(menuName) && !menuName.equals(sysMenu.getMenuName())) {
            sysMenu.setMenuName(menuName);
        }
        if (StringUtils.isNotBlank(menuKey) && !menuKey.equals(sysMenu.getMenuKey())) {
            SysMenu _sysMenu = sysMenuService.getByMenuKey(menuKey);
            if (_sysMenu != null) {
                throw CustomException.instance("菜单KEY已存在");
            }
            sysMenu.setMenuKey(menuKey);
        }
        if (parentId != null && !parentId.equals(sysMenu.getParentId())) {
            if (parentId != 0L) {
                SysMenu _sysMenu = sysMenuService.getByMenuId(parentId);
                if (_sysMenu == null) {
                    throw CustomException.instance("父级菜单不存在");
                }
            }
            sysMenu.setParentId(parentId);
        }
        if (menuUrl != null && !menuUrl.equals(sysMenu.getMenuUrl())) {
            sysMenu.setMenuUrl(menuUrl);
        }
        if (menuIcon != null && !menuIcon.equals(sysMenu.getMenuIcon())) {
            sysMenu.setMenuIcon(menuIcon);
        }
        if (menuType != null && !menuType.equals(sysMenu.getMenuType())) {
            sysMenu.setMenuType(menuType);
        }
        if (showStatus != null && !showStatus.equals(sysMenu.getShowStatus())) {
            sysMenu.setShowStatus(showStatus);
        }
        if (sort != null && !sort.equals(sysMenu.getSort())) {
            sysMenu.setSort(sort);
        }
        sysMenu.setUpdateTime(DateUtils.getNowTimestamp());
        if (!sysMenuService.updateById(sysMenu)) {
            throw CustomException.instance("编辑菜单失败");
        }

        return sysMenu;
    }

    @Override
    public SysMenu changeStatus(Long menuId, Integer status) {
        if (status == null || (status < 0 && status > 1)) {
            status = 0;
        }
        SysMenu sysMenu = sysMenuService.getByMenuId(menuId);
        if (sysMenu == null) {
            throw CustomException.instance("菜单不存在");
        }
        if (status.equals(sysMenu.getStatus())) {
            return sysMenu;
        }
        sysMenu.setStatus(status);
        sysMenu.setUpdateTime(DateUtils.getNowTimestamp());
        if (!sysMenuService.updateById(sysMenu)) {
            throw CustomException.instance("更改菜单状态失败");
        }

        return sysMenu;
//        boolean bool = sysMenuService.updateStatus(rf.getMenuId(), rf.getStatus());
    }

    @Override
    public SysMenu changeShowStatus(Long menuId, Integer showStatus) {
        if (showStatus == null || (showStatus < 0 && showStatus > 1)) {
            showStatus = 0;
        }
        SysMenu sysMenu = sysMenuService.getByMenuId(menuId);
        if (sysMenu == null) {
            throw CustomException.instance("菜单不存在");
        }
        if (showStatus.equals(sysMenu.getShowStatus())) {
            return sysMenu;
        }
        sysMenu.setShowStatus(showStatus);
        sysMenu.setUpdateTime(DateUtils.getNowTimestamp());
        if (!sysMenuService.updateById(sysMenu)) {
            throw CustomException.instance("更改菜单显示状态失败");
        }

        return sysMenu;
    }

    @Override
    public SysMenu deleteMenu(Long menuId) {
        SysMenu sysMenu = sysMenuService.getByMenuId(menuId);
        if (sysMenu == null) {
            throw CustomException.instance("菜单不存在");
        }

        if (sysRoleMenuService.countByMenuId(sysMenu.getMenuId()) > 0) {
            throw CustomException.instance("菜单已被使用，不可删除");
        }

        if (!sysMenuService.removeById(sysMenu.getId())) {
            throw CustomException.instance("菜单删除失败");
        }

        return sysMenu;
    }



    @Override
    public TableDataInfo<SysPermCategory> listPermCategory(Integer pageNum, Integer pageSize, String categoryName) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysPermCategory> list = sysPermCategoryService.listByName(categoryName);
        if (list == null || list.size() == 0) {
            return TableDataInfo.success();
        }
        return TableDataInfo.success(pageNum, pageSize, list);
    }

    @Override
    public SysPermCategory getPermCategoryInfo(Long categoryId) {
        return sysPermCategoryService.getByCategoryId(categoryId);
    }

    @Override
    public SysPermCategory addPermCategory(String name, Integer sort) {
        if (sort == null || sort < 0) {
            sort = 0;
        }

        if (sysPermCategoryService.countByCategoryName(name) > 0) {
            throw CustomException.instance("名称已经存在");
        }

        SysPermCategory sysPermCategory = new SysPermCategory(IdUtils.getUnid(), name, sort, DateUtils.getNowTimestamp());
        if (!sysPermCategoryService.save(sysPermCategory)) {
            throw CustomException.instance("添加权限组失败");
        }
        return sysPermCategory;
    }

    @Override
    public SysPermCategory editPermCategory(Long categoryId, String name, Integer sort) {
        SysPermCategory sysPermCategory = sysPermCategoryService.getByCategoryId(categoryId);
        if (sysPermCategory == null) {
            throw CustomException.instance("权限组不存在");
        }

        if (StringUtils.isNotBlank(name) && !name.equals(sysPermCategory.getName())) {
            if (sysPermCategoryService.countByCategoryName(name) > 0) {
                throw CustomException.instance("名称已经存在");
            }
            sysPermCategory.setName(name);
        }

        if (sort != null && sort >= 0 && !sort.equals(sysPermCategory.getSort())) {
            sysPermCategory.setSort(sort);
        }
        sysPermCategory.setUpdateTime(DateUtils.getNowTimestamp());

        if (!sysPermCategoryService.updateById(sysPermCategory)) {
            throw CustomException.instance("编辑权限组失败");
        }
        return sysPermCategory;
    }

    @Override
    public SysPermCategory deletePermCategory(Long menuCategoryId) {
        SysPermCategory sysPermCategory = sysPermCategoryService.getByCategoryId(menuCategoryId);
        if (sysPermCategory == null) {
            throw CustomException.instance("权限组不存在");
        }

        if (sysPermService.countByCategoryId(menuCategoryId) > 0) {
            throw CustomException.instance("权限组已被使用，不可删除");
        }

        if (!sysPermCategoryService.removeById(sysPermCategory.getId())) {
            throw CustomException.instance("权限组删除失败");
        }
        return sysPermCategory;
    }

    @Override
    public TableDataInfo<SysPerm> listPerm(Integer pageNum, Integer pageSize, Long categoryId, String permName, String permUrl) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysPerm> listPerm = sysPermService.listByCategoryId(categoryId, permName, permUrl);
        if (listPerm == null || listPerm.size() == 0) {
            return TableDataInfo.success();
        }
        return TableDataInfo.success(pageNum, pageSize, listPerm);
    }

    @Override
    public SysPerm getPermInfo(Long permId) {
        SysPerm sysPerm = sysPermService.getByPermId(permId);
        if (sysPerm == null) {
            throw CustomException.instance("权限资源不存在");
        }
        return sysPerm;
    }

    @Override
    public SysPerm addPerm(Long permCategoryId, String permName, String permUrl, String remark) {
        if (StringUtils.isBlank(remark)) {
            remark = "";
        }
        SysPermCategory sysPermCategory = sysPermCategoryService.getByCategoryId(permCategoryId);
        if (sysPermCategory == null) {
            throw CustomException.instance("权限分组不存在");
        }
        SysPerm sysPerm = sysPermService.getByPermUrl(permUrl);
        if (sysPerm != null) {
            throw CustomException.instance("该权限的URL已存在，不可重复配置");
        }

        sysPerm = new SysPerm(IdUtils.getUnid(), permCategoryId, permName, permUrl, remark, DateUtils.getNowTimestamp());
        if (!sysPermService.save(sysPerm)) {
            throw CustomException.instance("添加权限资源失败");
        }

        return sysPerm;
    }

    @Override
    public SysPerm editPerm(Long permId, Long permCategoryId, String permName, String permUrl, String remark) {
        SysPerm sysPerm = sysPermService.getByPermId(permId);
        if (sysPerm == null) {
            throw CustomException.instance("权限资源不存在");
        }

        if (permCategoryId != null && !permCategoryId.equals(sysPerm.getPermCategoryId())) {
            SysPermCategory sysPermCategory = sysPermCategoryService.getByCategoryId(permCategoryId);
            if (sysPermCategory == null) {
                throw CustomException.instance("权限分组不存在");
            }
            sysPerm.setPermCategoryId(permCategoryId);
        }

        if (StringUtils.isNotBlank(permName) && !permName.equals(sysPerm.getPermName())) {
            sysPerm.setPermName(permName);
        }

        if (StringUtils.isNotBlank(permUrl) && !permUrl.equals(sysPerm.getPermUrl())) {
            SysPerm _sysPerm = sysPermService.getByPermUrl(permUrl);
            if (_sysPerm != null) {
                throw CustomException.instance("URL已存在，不可重复配置");
            }
            sysPerm.setPermUrl(permUrl);
        }

        if (StringUtils.isNotBlank(remark) && !remark.equals(sysPerm.getRemark())) {
            sysPerm.setRemark(remark);
        }
        sysPerm.setUpdateTime(DateUtils.getNowTimestamp());

        if (!sysPermService.updateById(sysPerm)) {
            throw CustomException.instance("编辑权限资源失败");
        }

        return sysPerm;
    }

    @Override
    public SysPerm deletePerm(Long permId) {
        SysPerm sysPerm = sysPermService.getByPermId(permId);
        if (sysPerm == null) {
            throw CustomException.instance("权限不存在");
        }

        if (sysMenuPermService.countByPermId(sysPerm.getPermId()) > 0) {
            throw CustomException.instance("权限已被使用，不可删除");
        }

        if (!sysPermService.removeById(sysPerm.getId())) {
            throw CustomException.instance("权限删除失败");
        }

        return sysPerm;
    }


}
