package cn.px.tea.modules.ycb.service.agent.inter;

import cn.px.tea.common.domain.TableDataInfo;
import cn.px.tea.modules.ycb.model.SysRecycleGoods;
import cn.px.tea.modules.ycb.model.SysRecycleGoodsDetail;
import cn.px.tea.modules.ycb.pojo.bo.agent.PlanGoodsNode;

import java.util.List;

public interface RecycleGoodsService {
    TableDataInfo<SysRecycleGoods> pageRecycleGoods(Integer pageNum, Integer pageSize, Integer type, String date, Integer status);

    SysRecycleGoods getRecycleGoods(Long recycleOrderId);

    List<SysRecycleGoodsDetail> getRecycleGoodsDetail(Long recycleOrderId);

    SysRecycleGoods addRecycleGoods(Integer type, Long shopId, List<PlanGoodsNode> listDetail, String remark);

    SysRecycleGoods editRecycleGoods(Long recycleOrderId, List<PlanGoodsNode> listDetail, String remark);

    SysRecycleGoods submitRecycleGoods(Long recycleOrderId, String remark);
}
