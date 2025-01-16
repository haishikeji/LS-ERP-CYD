package cn.px.tea.modules.ycb.service.table.intel;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.px.tea.modules.ycb.model.SysShop;

import java.util.List;


public interface SysShopService extends IService<SysShop> {

    List<SysShop> listByShop(List<Long> listShopId);

    SysShop getByShop(Long shopId);

    SysShop getByShopName(String shopName);

    List<SysShop> listByCondition(List<Long> listShopId, String select);

    /**
     * 筛查加盟店（此接口目前做了特殊处理，非北岩总代的加盟店，筛查掉）
     */
    List<SysShop> listByJoin();
}
