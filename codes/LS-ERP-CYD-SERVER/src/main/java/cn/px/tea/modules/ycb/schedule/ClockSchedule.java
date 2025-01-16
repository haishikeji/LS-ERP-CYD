package cn.px.tea.modules.ycb.schedule;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cn.px.tea.common.util.DateUtils;
import cn.px.tea.modules.ycb.model.SysClockEmpLog;
import cn.px.tea.modules.ycb.service.table.intel.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;


//@Component
public class ClockSchedule {

    @Autowired
    private ClockService clockService;

    /**
     * 五分钟检查一次打卡情况  todo 看看有没有必要做
     */
    @Scheduled(cron = "*/5 * * * *")
    public void checkClock(){
        Date now = new Date();

        // 1.查询出当前月的所有打卡数据
        LambdaQueryWrapper<SysClockEmpLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.apply("date_format(active_time,'%Y-%m') = {0}", DateUtils.parseDateToMonth(now));
        wrapper.orderByAsc(SysClockEmpLog::getActiveTime);
        List<SysClockEmpLog> clockLogs = clockService.list(wrapper);


    }
}
