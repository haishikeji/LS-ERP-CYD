package cn.px.tea.modules.ycb.service.table.intel;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.px.tea.modules.ycb.model.SysRecycleGoods;

import java.util.List;

public interface SysRecycleGoodsService extends IService<SysRecycleGoods> {
    List<SysRecycleGoods> listByCondition(Long shopId, Integer type, Integer status, Integer startDate, Integer endDate);

    SysRecycleGoods getByRecycleOrderId(Long recycleOrderId);
}
