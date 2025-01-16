package cn.px.tea.modules.ycb.controller.shop;

import cn.px.tea.common.domain.AjaxResult;
import cn.px.tea.common.domain.TableDataInfo;
import cn.px.tea.modules.ycb.model.SysGiveaway;
import cn.px.tea.modules.ycb.model.SysGiveawayRecord;
import cn.px.tea.modules.ycb.pojo.rf.giveaway.AddGiveawayRf;
import cn.px.tea.modules.ycb.pojo.rf.giveaway.EditGiveawayRf;
import cn.px.tea.modules.ycb.pojo.rf.giveaway.PageGiveawayRf;
import cn.px.tea.modules.ycb.pojo.rf.com.PrimaryRf;
import cn.px.tea.modules.ycb.service.business.inter.GiveawayService;
import cn.px.tea.security.aspect.Log;
import cn.px.tea.security.define.SystemDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 门店-商品赠送
 */
@RestController
@RequestMapping("/web/giveaway")
public class WebGiveawayController {

    @Autowired
    private GiveawayService giveawayService;

    /**
     * 赠送商品列表
     * @param rf
     */
    @GetMapping("pageGiveaway")
    @Log(modelTitle = "门店-商品赠送-赠送商品列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public TableDataInfo<SysGiveaway> pageGiveaway(@Validated PageGiveawayRf rf) {
        return giveawayService.pageGiveaway(rf.getPageNum(), rf.getPageSize(), rf.getGiveawayDate(), rf.getMembersId(), rf.getOperator(), rf.getStatus());
    }

    /**
     * 赠送商品详情
     * @param rf
     */
    @GetMapping("getGiveawayInfo")
    @Log(modelTitle = "门店-商品赠送-赠送商品详情" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<SysGiveaway> getGiveawayInfo(@Validated PrimaryRf rf) {
        return AjaxResult.success(giveawayService.getGiveawayInfo(rf.getPrimaryId()));
    }

    /**
     * 根据关联主键（如订单号）查询赠送商品明细列表
     * @param rf
     */
    @GetMapping("listRecordByRelevanceId")
    @Log(modelTitle = "门店-商品赠送-根据关联主键（如订单号）查询赠送商品明细列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<SysGiveawayRecord>> listRecordByRelevanceId(@Validated PrimaryRf rf) {
        return AjaxResult.success(giveawayService.listRecordByRelevanceId(rf.getPrimaryId()));
    }

    /**
     * 根据商品主单查询赠送商品明细列表
     * @param rf
     */
    @GetMapping("listRecordByGiveawayId")
    @Log(modelTitle = "门店-商品赠送-根据商品主单查询赠送商品明细列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<SysGiveawayRecord>> listRecordByGiveawayId(@Validated PrimaryRf rf) {
        return AjaxResult.success(giveawayService.listRecordByGiveawayId(rf.getPrimaryId()));
    }

    /**
     * 赠送商品明细列表
     * @param rf
     */
    @GetMapping("pageGiveawayRecord")
    @Log(modelTitle = "门店-商品赠送-赠送商品明细列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public TableDataInfo<SysGiveawayRecord> pageGiveawayRecord(@Validated PageGiveawayRf rf) {
        return giveawayService.pageGiveawayRecord(rf.getPageNum(), rf.getPageSize(), rf.getGiveawayDate(), rf.getMembersId(), rf.getOperator(), rf.getStatus());
    }

    /**
     * 赠送商品明细详情
     * @param rf
     */
    @GetMapping("getGiveawayRecordInfo")
    @Log(modelTitle = "门店-商品赠送-赠送商品明细详情" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<SysGiveawayRecord> getGiveawayRecordInfo(@Validated PrimaryRf rf) {
        return AjaxResult.success(giveawayService.getGiveawayRecordInfo(rf.getPrimaryId()));
    }

    /**
     * 新增赠送商品
     * @param rf
     */
    @PostMapping("addGiveaway")
    @Log(modelTitle = "门店-商品赠送-新增赠送商品" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysGiveaway> addGiveaway(@Validated @RequestBody AddGiveawayRf rf) {
        return AjaxResult.success(giveawayService.addGiveaway(rf.getMembersId(), rf.getListGoods(), rf.getRemark()));
    }

    /**
     * 编辑赠送商品
     * @param rf
     */
    @PostMapping("editGiveaway")
    @Log(modelTitle = "门店-商品赠送-编辑赠送商品" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysGiveaway> editGiveaway(@Validated @RequestBody EditGiveawayRf rf) {
        return AjaxResult.success(giveawayService.editGiveaway(rf.getGiveawayId(), rf.getMembersId(), rf.getListGoods(), rf.getRemark()));
    }

    /**
     * 赠送
     * @param rf
     */
    @PostMapping("giveaway")
    @Log(modelTitle = "门店-商品赠送-赠送" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysGiveaway> giveaway(@Validated @RequestBody PrimaryRf rf) {
        return AjaxResult.success(giveawayService.giveaway(rf.getPrimaryId()));
    }

    /**
     * 作废
     * @param rf
     */
    @PostMapping("cancelGiveaway")
    @Log(modelTitle = "门店-商品赠送-作废" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysGiveaway> cancelGiveaway(@Validated @RequestBody PrimaryRf rf) {
        return AjaxResult.success(giveawayService.cancelGiveaway(rf.getPrimaryId()));
    }

    /**
     * 撤销
     * @param rf
     */
    @PostMapping("rebackrollGiveaway")
    @Log(modelTitle = "门店-商品赠送-撤销" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysGiveaway> rebackrollGiveaway(@Validated @RequestBody PrimaryRf rf) {
        return AjaxResult.success(giveawayService.rebackrollGiveaway(rf.getPrimaryId()));
    }

}
