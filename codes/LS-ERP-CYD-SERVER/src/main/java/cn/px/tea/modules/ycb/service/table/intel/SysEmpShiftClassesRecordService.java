package cn.px.tea.modules.ycb.service.table.intel;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.px.tea.modules.ycb.model.SysEmpShiftClassesRecord;

import java.sql.Timestamp;
import java.util.List;

public interface SysEmpShiftClassesRecordService extends IService<SysEmpShiftClassesRecord> {
    List<SysEmpShiftClassesRecord> listByCondition(Long shopId, Long empId, Timestamp startTime, Timestamp endTime);

    SysEmpShiftClassesRecord getByShiftClassesId(Long shiftClassesId);
}
