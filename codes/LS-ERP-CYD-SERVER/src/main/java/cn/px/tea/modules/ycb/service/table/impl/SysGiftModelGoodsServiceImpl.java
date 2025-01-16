package cn.px.tea.modules.ycb.service.table.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.px.tea.modules.ycb.model.SysGiftModelGoods;
import cn.px.tea.modules.ycb.service.table.intel.SysGiftModelGoodsService;
import cn.px.tea.modules.ycb.mapper.SysGiftModelGoodsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SysGiftModelGoodsServiceImpl extends ServiceImpl<SysGiftModelGoodsMapper, SysGiftModelGoods>
    implements SysGiftModelGoodsService{

    @Override
    public List<SysGiftModelGoods> listByGiftModelId(List<Long> listModelId, Integer status) {
        LambdaQueryWrapper<SysGiftModelGoods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysGiftModelGoods::getGiftModelId, listModelId);
        if (status != null) {
            queryWrapper.eq(SysGiftModelGoods::getStatus, status);
        }
        return list(queryWrapper);
    }

    @Override
    public List<SysGiftModelGoods> listByGiftModelId(Long giftModelId) {
        LambdaQueryWrapper<SysGiftModelGoods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysGiftModelGoods::getGiftModelId, giftModelId);
        return list(queryWrapper);
    }
}




