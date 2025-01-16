package cn.px.tea.modules.ycb.service.table.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.px.tea.modules.ycb.mapper.SysPlanGoodsDetailMapper;
import cn.px.tea.modules.ycb.model.SysPlanGoodsDetail;
import cn.px.tea.modules.ycb.service.table.intel.SysPlanGoodsDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPlanGoodsDetailImpl extends ServiceImpl<SysPlanGoodsDetailMapper, SysPlanGoodsDetail>
        implements SysPlanGoodsDetailService {
    @Override
    public List<SysPlanGoodsDetail> getByPlanOrderId(Long planOrderId) {
        LambdaQueryWrapper<SysPlanGoodsDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPlanGoodsDetail::getPlanOrderId, planOrderId);
        return list(queryWrapper);
    }

    @Override
    public List<SysPlanGoodsDetail> listByPlanOrderId(List<Long> listPlanOrderId) {
        LambdaQueryWrapper<SysPlanGoodsDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysPlanGoodsDetail::getPlanOrderId, listPlanOrderId);
        return list(queryWrapper);
    }
}
