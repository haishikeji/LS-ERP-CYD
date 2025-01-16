package cn.px.tea.modules.ycb.model;


import lombok.Builder;
import lombok.Data;

/**
 * 员工-打卡考勤关系(SysClockEmpRule)表实体类
 *
 * @author 品讯科技
 * @since 2022-11-25 09:34:03
 */
@Data
@Builder
public class SysClockEmpRule {
    //自增ID
    private Long id;
    //员工ID
    private Integer empId;
    //考勤组ID
    private Long ruleId;
    //逻辑删除字段
    private Integer deleteAt;


}

