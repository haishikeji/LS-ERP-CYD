package cn.px.tea.modules.ycb.service.table.intel;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.px.tea.modules.ycb.model.SysRoomUse;
import cn.px.tea.modules.ycb.pojo.model.IntIntCopeBo;
import cn.px.tea.modules.ycb.pojo.model.TowIntBo;

import java.sql.Timestamp;
import java.util.List;

public interface SysRoomUseService extends IService<SysRoomUse> {
    SysRoomUse getByRoomUserId(Long roomUserId);

    List<SysRoomUse> listByCondition(Long shopId, Integer roomUseStatus, Timestamp startTime, Timestamp endTime);

    List<SysRoomUse> listByCondition(Long shopId, Long membersId);

    TowIntBo statistics(Long shopId, int status, Timestamp startTime, Timestamp endTime);

    List<IntIntCopeBo> statisticsForNum(Long shopId, int status, Timestamp startTime, Timestamp endTime);
}
