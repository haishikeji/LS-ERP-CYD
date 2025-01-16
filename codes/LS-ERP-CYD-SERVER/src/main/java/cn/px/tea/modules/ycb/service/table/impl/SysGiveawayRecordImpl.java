package cn.px.tea.modules.ycb.service.table.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.px.tea.modules.ycb.mapper.SysGiveawayRecordMapper;
import cn.px.tea.modules.ycb.model.SysGiveawayRecord;
import cn.px.tea.modules.ycb.service.table.intel.SysGiveawayRecordService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class SysGiveawayRecordImpl extends ServiceImpl<SysGiveawayRecordMapper, SysGiveawayRecord> implements SysGiveawayRecordService {
    @Override
    public SysGiveawayRecord getByGiveawayRecordId(Long giveawayRecordId) {
        LambdaQueryWrapper<SysGiveawayRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysGiveawayRecord::getGiveawayRecordId, giveawayRecordId);
        return getOne(queryWrapper);
    }

    @Override
    public List<SysGiveawayRecord> listByGiveawayId(Long giveawayId) {
        LambdaQueryWrapper<SysGiveawayRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysGiveawayRecord::getGiveawayId, giveawayId);
        return list(queryWrapper);
    }

    @Override
    public List<SysGiveawayRecord> listByGiveawayId(List<Long> listGiveawayId) {
        LambdaQueryWrapper<SysGiveawayRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysGiveawayRecord::getGiveawayId, listGiveawayId);
        return list(queryWrapper);
    }

    @Override
    public List<SysGiveawayRecord> listByCondition(Long shopId, Long membersId, Long operator, Integer status, Date startDate, Date endDate) {
        LambdaQueryWrapper<SysGiveawayRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysGiveawayRecord::getShopId, shopId);
        if (membersId != null && membersId != 0L) {
            queryWrapper.eq(SysGiveawayRecord::getMembersId, membersId);
        }
        if (operator != null && operator != 0L) {
            queryWrapper.eq(SysGiveawayRecord::getOperator, operator);
        }
        if (status != null && status != 0) {
            queryWrapper.eq(SysGiveawayRecord::getStatus, status);
        }
        if (startDate != null) {
            queryWrapper.ge(SysGiveawayRecord::getGiveawayDate, startDate);
        }
        if (endDate != null) {
            queryWrapper.le(SysGiveawayRecord::getGiveawayDate, endDate);
        }
        queryWrapper.orderByDesc(SysGiveawayRecord::getId);
        return list(queryWrapper);
    }
}




