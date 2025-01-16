package cn.px.tea.modules.ycb.service.table.intel;

import cn.px.tea.modules.ycb.model.SysPermCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface SysPermCategoryService extends IService<SysPermCategory> {

    SysPermCategory getByCategoryId(Long categoryId);

    int countByCategoryName(String name);

    List<SysPermCategory> listByName(String categoryName);
}
