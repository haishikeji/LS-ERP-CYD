package cn.px.tea.modules.ycb.service.table.intel;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.px.tea.modules.ycb.model.SysRecycleGoodsDetail;

import java.util.List;

public interface SysRecycleGoodsDetailService extends IService<SysRecycleGoodsDetail> {
    List<SysRecycleGoodsDetail> getByRecycleOrderId(Long recycleOrderId);
}
