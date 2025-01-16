package cn.px.tea.modules.ycb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

/**
 * 菜单
 */
@TableName("sys_menu")
public class SysMenu {
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单key
     */
    private String menuKey;

    /**
     * 父级id
     */
    private Long parentId;


//    /**
//     * 菜单分类ID
//     */
//    private Long menuCategoryId;

    /**
     * 菜单URL
     */
    private String menuUrl;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单类型 1-底部菜单 2-导航
     */
    private Integer menuType;

    /**
     * 显示状态  0-隐藏 1-显示
     */
    private Integer showStatus;

    /**
     * 状态 0-无效 1-有效
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    public SysMenu() {
    }

    public SysMenu(Long menuId, String menuName, String menuKey, Long parentId/*, Long menuCategoryId*/, String menuUrl, String menuIcon, Integer menuType, Integer showStatus, Integer status, Integer sort, Timestamp timestamp) {
        this.id = 0L;
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuKey = menuKey;
        this.parentId = parentId;
//        this.menuCategoryId = menuCategoryId;
        this.menuUrl = menuUrl;
        this.menuIcon = menuIcon;
        this.menuType = menuType;
        this.showStatus = showStatus;
        this.status = status;
        this.sort = sort;
        this.createTime = timestamp;
        this.updateTime = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

//    public Integer getMenuAliasId() {
//        return menuAliasId;
//    }
//
//    public void setMenuAliasId(Integer menuAliasId) {
//        this.menuAliasId = menuAliasId;
//    }
//
//    public String getMenuAliasName() {
//        return menuAliasName;
//    }
//
//    public void setMenuAliasName(String menuAliasName) {
//        this.menuAliasName = menuAliasName;
//    }
//
//    public Long getMenuCategoryId() {
//        return menuCategoryId;
//    }
//
//    public void setMenuCategoryId(Long menuCategoryId) {
//        this.menuCategoryId = menuCategoryId;
//    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
