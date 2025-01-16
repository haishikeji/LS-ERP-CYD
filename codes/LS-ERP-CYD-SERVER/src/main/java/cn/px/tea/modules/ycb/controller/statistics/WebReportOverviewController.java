package cn.px.tea.modules.ycb.controller.statistics;

import cn.px.tea.common.domain.AjaxResult;
import cn.px.tea.modules.ycb.pojo.bo.report.ReportTypeBo;
import cn.px.tea.modules.ycb.pojo.rf.statistics.reports.ReportStatisticsRf;
import cn.px.tea.modules.ycb.pojo.rf.statistics.reports.ReportStatisticsTypeRf;
import cn.px.tea.modules.ycb.pojo.vo.reports.statistics.ReportConsumeVo;
import cn.px.tea.modules.ycb.pojo.vo.reports.statistics.ReportRechargeVo;
import cn.px.tea.modules.ycb.pojo.vo.reports.statistics.ReportStatisticsVo;
import cn.px.tea.modules.ycb.service.statistics.intel.ReportStatisticsService;
import cn.px.tea.security.aspect.Log;
import cn.px.tea.security.define.SystemDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 报表-概览
 */
@RestController
@RequestMapping("/web/report/overview")
public class WebReportOverviewController {

    @Autowired
    private ReportStatisticsService reportStatisticsService;

    /**
     * 营业额统计
     * @param rf
     */
    @GetMapping("reportConsume")
    @Log(modelTitle = "报表-概览-营业额统计" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<ReportConsumeVo> reportConsume(@Validated ReportStatisticsRf rf) {
        return AjaxResult.success(reportStatisticsService.reportConsume(rf.getMode(), rf.getSelectDate()));
    }

    /**
     * 按营业类型统计
     * @param rf
     */
    @GetMapping("reportBySaleType")
    @Log(modelTitle = "报表-概览-按营业类型统计" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<ReportTypeBo>> reportBySaleType(@Validated ReportStatisticsRf rf) {
        return AjaxResult.success(reportStatisticsService.reportBySaleType(rf.getMode(), rf.getSelectDate()));
    }

    /**
     * 按账单分布统计
     * @param rf
     */
    @GetMapping("reportByOrderSite")
    @Log(modelTitle = "报表-概览-按账单分布统计" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<ReportTypeBo>> reportByOrderSite(@Validated ReportStatisticsTypeRf rf) {
        return AjaxResult.success(reportStatisticsService.reportByOrderSite(rf.getMode(), rf.getSelectDate(), rf.getStatus()));
    }

    /**
     * 按消费人数统计
     * @param rf
     */
    @GetMapping("reportByConsumeCount")
    @Log(modelTitle = "报表-概览-按消费人数统计" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<ReportTypeBo>> reportByConsumeCount(@Validated ReportStatisticsRf rf) {
        return AjaxResult.success(reportStatisticsService.reportByConsumeCount(rf.getMode(), rf.getSelectDate()));
    }

    /**
     * 营业趋势统计
     * @param rf
     */
    @GetMapping("reportByConsumeTrend")
    @Log(modelTitle = "报表-概览-营业趋势统计" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<ReportTypeBo>> reportByConsumeTrend(@Validated ReportStatisticsRf rf) {
        return AjaxResult.success(reportStatisticsService.reportByConsumeTrend(rf.getMode(), rf.getSelectDate()));
    }

    /**
     * 充值统计
     * @param rf
     */
    @GetMapping("reportRecharge")
    @Log(modelTitle = "报表-概览-充值统计" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<ReportRechargeVo> reportRecharge(@Validated ReportStatisticsRf rf) {
        return AjaxResult.success(reportStatisticsService.reportRecharge(rf.getMode(), rf.getSelectDate()));
    }

    /**
     * 每日/每月 充值/营业 金额分布
     * @param rf
     */
    @GetMapping("reportRechargeDetail")
    @Log(modelTitle = "报表-概览-每日/每月 充值/营业 金额分布" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<ReportStatisticsVo> reportRechargeDetail(@Validated ReportStatisticsTypeRf rf) {
        return AjaxResult.success(reportStatisticsService.reportRechargeDetail(rf.getMode(), rf.getSelectDate(), rf.getType()));
    }

}
