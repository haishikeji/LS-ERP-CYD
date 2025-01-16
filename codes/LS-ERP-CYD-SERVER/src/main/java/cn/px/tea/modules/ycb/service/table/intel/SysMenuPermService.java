package cn.px.tea.modules.ycb.service.table.intel;

import cn.px.tea.modules.ycb.model.SysMenuPerm;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysMenuPermService extends IService<SysMenuPerm> {

    int countByPermId(Long permId);

    List<SysMenuPerm> listByMenuId(Long menuId);

    List<SysMenuPerm> listByMenuId(List<Long> listMenuId);
}
