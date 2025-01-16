package cn.px.tea.modules.ycb.service.table.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.px.tea.modules.ycb.mapper.SysDeliverGoodsDetailMapper;
import cn.px.tea.modules.ycb.model.SysDeliverGoodsDetail;
import cn.px.tea.modules.ycb.service.table.intel.SysDeliverGoodsDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDeliverGoodsDetailImpl extends ServiceImpl<SysDeliverGoodsDetailMapper, SysDeliverGoodsDetail>
        implements SysDeliverGoodsDetailService {
    @Override
    public List<SysDeliverGoodsDetail> getByDeliverOrderId(Long deliverOrderId) {
        LambdaQueryWrapper<SysDeliverGoodsDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDeliverGoodsDetail::getDeliverOrderId, deliverOrderId);
        return list(queryWrapper);
    }
}
