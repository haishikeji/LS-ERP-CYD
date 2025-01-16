package cn.px.tea.modules.ycb.service.table.intel;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.px.tea.modules.ycb.model.SysTeaStorageInventory;

import java.sql.Date;
import java.util.List;


public interface SysTeaStorageInventoryService extends IService<SysTeaStorageInventory> {

    SysTeaStorageInventory getByInventoryId(Long inventoryId);
    List<SysTeaStorageInventory> listByCondition(Long shopId, Long membersId, String membersName, Integer status, Date startDate, Date endDate);
}
