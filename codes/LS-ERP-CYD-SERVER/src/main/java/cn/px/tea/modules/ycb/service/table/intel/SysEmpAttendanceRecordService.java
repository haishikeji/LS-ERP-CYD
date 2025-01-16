package cn.px.tea.modules.ycb.service.table.intel;

import cn.px.tea.modules.ycb.model.SysEmpAttendanceRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 */
public interface SysEmpAttendanceRecordService extends IService<SysEmpAttendanceRecord> {

    List<SysEmpAttendanceRecord> listByCondition(Long shopId, Long empId, Timestamp startTime, Timestamp endTime);
}
