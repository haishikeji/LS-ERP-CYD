package cn.px.tea.modules.ycb.service.table.intel;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.px.tea.modules.ycb.model.SysSupplierGoodsDetail;

import java.util.List;

public interface SysSupplierGoodsDetailService extends IService<SysSupplierGoodsDetail> {
    List<SysSupplierGoodsDetail> getByPlanOrderId(Long planOrderId);
}
