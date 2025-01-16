package cn.px.tea.modules.ycb.service.table.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.px.tea.modules.ycb.mapper.SysTeaStorageInventoryMapper;
import cn.px.tea.modules.ycb.model.SysTeaStorageInventory;
import cn.px.tea.modules.ycb.service.table.intel.SysTeaStorageInventoryService;
import cn.px.tea.security.util.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class SysTeaStorageInventoryImpl extends ServiceImpl<SysTeaStorageInventoryMapper, SysTeaStorageInventory> implements SysTeaStorageInventoryService {

    @Override
    public SysTeaStorageInventory getByInventoryId(Long inventoryId) {
        LambdaQueryWrapper<SysTeaStorageInventory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysTeaStorageInventory::getInventoryId, inventoryId);
        return getOne(queryWrapper);
    }

    @Override
    public List<SysTeaStorageInventory> listByCondition(Long shopId, Long membersId, String membersName, Integer status, Date startDate, Date endDate) {
        LambdaQueryWrapper<SysTeaStorageInventory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysTeaStorageInventory::getShopId, shopId);
        if (membersId != null && membersId != 0L) {
            queryWrapper.eq(SysTeaStorageInventory::getMembersId, membersId);
        } else if (StringUtils.isNotBlank(membersName)) {
            queryWrapper.like(SysTeaStorageInventory::getMembersName, membersName);
        }
        if (status != null && status != 0) {
            queryWrapper.eq(SysTeaStorageInventory::getStatus, status);
        }
        if (startDate != null) {
            queryWrapper.ge(SysTeaStorageInventory::getInventoryDate, startDate);
        }
        if (endDate != null) {
            queryWrapper.le(SysTeaStorageInventory::getInventoryDate, endDate);
        }
        queryWrapper.orderByDesc(SysTeaStorageInventory::getInventoryTime);
        return list(queryWrapper);
    }
}
