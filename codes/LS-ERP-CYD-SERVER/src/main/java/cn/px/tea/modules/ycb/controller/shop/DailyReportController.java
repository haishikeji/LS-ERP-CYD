package cn.px.tea.modules.ycb.controller.shop;

import cn.px.tea.common.domain.AjaxResult;
import cn.px.tea.common.domain.TableDataInfo;
import cn.px.tea.modules.ycb.model.SysShiftDailyReport;
import cn.px.tea.modules.ycb.pojo.bo.classes.ShiftClassesStatistics;
import cn.px.tea.modules.ycb.pojo.rf.com.PrimaryRf;
import cn.px.tea.modules.ycb.pojo.rf.shiftdailyreport.AddDailyReportRf;
import cn.px.tea.modules.ycb.pojo.rf.shiftdailyreport.DailyReportStatisticsRf;
import cn.px.tea.modules.ycb.pojo.rf.shiftdailyreport.EditDailyReportRf;
import cn.px.tea.modules.ycb.pojo.rf.shiftdailyreport.PageDailyReportRf;
import cn.px.tea.modules.ycb.service.business.inter.ShiftDailyReportService;
import cn.px.tea.security.aspect.Log;
import cn.px.tea.security.define.SystemDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 门店-日报
 */
@RestController
@RequestMapping("/web/dailyReport")
public class DailyReportController {

    @Autowired
    private ShiftDailyReportService shiftDailyReportService;

    /**
     * 员工日报列表(分页)
     * @param rf
     */
    @GetMapping("pageDailyReport")
    @Log(modelTitle = "门店-日报-员工日报列表(分页)" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public TableDataInfo<SysShiftDailyReport> pageDailyReport(@Validated PageDailyReportRf rf) {
        return shiftDailyReportService.pageDailyReport(rf.getPageNum(), rf.getPageSize(), rf.getType(), rf.getEmpId(), rf.getEmpName(), rf.getRoleId(), rf.getRoleName(), rf.getStatus(), rf.getStartDate(), rf.getEndDate());
    }

    /**
     * 获取日报详情
     * @param rf
     */
    @GetMapping("getDailyReportInfo")
    @Log(modelTitle = "门店-日报-获取日报详情" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<SysShiftDailyReport> getDailyReportInfo(@Validated PrimaryRf rf) {
        return AjaxResult.success(shiftDailyReportService.getDailyReportInfo(rf.getPrimaryId()));
    }

    /**
     * 添加日报
     * @param rf
     */
    @PostMapping("addDailyReport")
    @Log(modelTitle = "门店-日报-添加日报" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysShiftDailyReport> addDailyReport(@Validated @RequestBody AddDailyReportRf rf) {
        return AjaxResult.success(shiftDailyReportService.addDailyReport(rf.getStartTime(), rf.getEndTime(), rf.getStatiticsRemark(), rf.getRemark()));
    }

    /**
     * 编辑日报
     * @param rf
     */
    @PostMapping("editDailyReport")
    @Log(modelTitle = "门店-日报-编辑日报" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysShiftDailyReport> editDailyReport(@Validated @RequestBody EditDailyReportRf rf) {
        return AjaxResult.success(shiftDailyReportService.editDailyReport(rf.getDailyReportId(), rf.getStartTime(), rf.getEndTime(), rf.getStatiticsRemark(), rf.getRemark()));
    }

    /**
     * 提交日报
     * @param rf
     */
    @PostMapping("submitDailyReport")
    @Log(modelTitle = "门店-日报-提交日报" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysShiftDailyReport> submitDailyReport(@Validated @RequestBody PrimaryRf rf) {
        return AjaxResult.success(shiftDailyReportService.submitDailyReport(rf.getPrimaryId()));
    }

    /**
     * 撤销日报
     * @param rf
     */
    @PostMapping("cancelDailyReport")
    @Log(modelTitle = "门店-日报-撤销日报" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysShiftDailyReport> cancelDailyReport(@Validated @RequestBody PrimaryRf rf) {
        return AjaxResult.success(shiftDailyReportService.cancelDailyReport(rf.getPrimaryId()));
    }


    /**
     * 获取班次统计信息
     * @param rf
     */
    @GetMapping("getDailyReportStatistics")
    @Log(modelTitle = "门店-日报-获取班次统计信息" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<ShiftClassesStatistics> getStatistics(@Validated DailyReportStatisticsRf rf) {
        return AjaxResult.success(shiftDailyReportService.getStatistics(rf.getStartTime(), rf.getEndTime()));
    }

}
