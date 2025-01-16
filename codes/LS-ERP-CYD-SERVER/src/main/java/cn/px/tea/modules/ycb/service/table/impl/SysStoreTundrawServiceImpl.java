package cn.px.tea.modules.ycb.service.table.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.px.tea.modules.ycb.model.SysStoreTundraw;
import cn.px.tea.modules.ycb.service.table.intel.SysStoreTundrawService;
import cn.px.tea.modules.ycb.mapper.SysStoreTundrawMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysStoreTundrawServiceImpl extends ServiceImpl<SysStoreTundrawMapper, SysStoreTundraw>
    implements SysStoreTundrawService{

    @Override
    public List<SysStoreTundraw> listByCondition(Long shopId, Integer status) {
        LambdaQueryWrapper<SysStoreTundraw> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysStoreTundraw::getShopId, shopId);
        if (status != null && status != 0) {
            queryWrapper.eq(SysStoreTundraw::getStatus, status);
        }
        queryWrapper.orderByDesc(SysStoreTundraw::getId);
        return list(queryWrapper);
    }

    @Override
    public SysStoreTundraw getByTundrawId(Long tundrawId) {
        LambdaQueryWrapper<SysStoreTundraw> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysStoreTundraw::getTundrawId, tundrawId);
        return getOne(queryWrapper);
    }
}




