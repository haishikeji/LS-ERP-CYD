package cn.px.tea.modules.ycb.service.table.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.px.tea.modules.ycb.mapper.SysClockEmpRuleMapper;
import cn.px.tea.modules.ycb.model.SysClockEmpRule;
import cn.px.tea.modules.ycb.service.table.intel.SysClockEmpRuleService;
import org.springframework.stereotype.Service;

/**
 * 员工-打卡考勤关系(SysClockEmpRule)表服务实现类
 *
 * @author 品讯科技
 * @since 2022-11-25 09:48:29
 */
@Service("sysClockEmpRuleService")
public class SysClockEmpRuleServiceImpl extends ServiceImpl<SysClockEmpRuleMapper, SysClockEmpRule> implements SysClockEmpRuleService {

}

