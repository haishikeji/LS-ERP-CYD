package cn.px.tea.modules.ycb.service.table.intel;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.px.tea.modules.ycb.model.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuService extends IService<SysRoleMenu> {

    List<SysRoleMenu> listByRoleId(Long roleId);

    int countByMenuId(Long menuId);

    List<SysRoleMenu> listByMenuId(List<Long> listMenuId);
}
