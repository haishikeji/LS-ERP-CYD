package cn.px.tea.modules.ycb.controller.member;


import cn.px.tea.common.domain.AjaxResult;
import cn.px.tea.common.domain.TableDataInfo;
import cn.px.tea.modules.ycb.model.SysMembersBalance;
import cn.px.tea.modules.ycb.model.SysMembersCredit;
import cn.px.tea.modules.ycb.model.SysMembersCreditLog;
import cn.px.tea.modules.ycb.pojo.bo.com.BaseInfo;
import cn.px.tea.modules.ycb.pojo.rf.com.PrimaryRf;
import cn.px.tea.modules.ycb.pojo.rf.credit.*;
import cn.px.tea.modules.ycb.pojo.rf.credit.*;
import cn.px.tea.modules.ycb.pojo.model.CreditMemberVo;
import cn.px.tea.modules.ycb.pojo.vo.credit.StatisticCreditMemberVo;
import cn.px.tea.modules.ycb.service.base.inter.CreditService;
import cn.px.tea.modules.ycb.service.base.inter.UserService;
import cn.px.tea.security.aspect.MLog;
import cn.px.tea.security.define.SystemDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员-赊账
 */
@RestController
@RequestMapping("/member/credit")
public class CreditController {

    @Autowired
    private UserService userService;

    @Autowired
    private CreditService creditService;

    /**
     * 获取赊账会员列表
     * @param rf
     */
    @GetMapping("pageCreditMember")
    @MLog(modelTitle = "会员-赊账-获取赊账会员列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public TableDataInfo<CreditMemberVo> pageCreditMember(@Validated PageCreditMemberRf rf) {
        BaseInfo baseInfo = BaseInfo.createNew(userService.getMemberInfo());
        return creditService.pageCreditMember(
                baseInfo,
                rf.getPageNum(),
                rf.getPageSize(),
                rf.getSelect(),
                rf.getTag());
    }

    /**
     * 获取赊账会员统计数据
     * @param rf
     */
    @GetMapping("getStatisticCreditMember")
    @MLog(modelTitle = "会员-赊账-获取赊账会员统计数据" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<StatisticCreditMemberVo> getStatisticCreditMember(@Validated GetStatisticCreditMemberRf rf) {
        BaseInfo baseInfo = BaseInfo.createNew(userService.getMemberInfo());
        return AjaxResult.success(creditService.getStatisticCreditMember(baseInfo, rf.getCreditMonth()));
    }

    /**
     * 获取赊账记录列表
     * @param rf
     */
    @GetMapping("pageCreditRecord")
    @MLog(modelTitle = "会员-赊账-获取赊账记录列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public TableDataInfo<SysMembersCredit> pageCreditRecord(@Validated ListCreditRecordRf rf) {
        BaseInfo baseInfo = BaseInfo.createNew(userService.getMemberInfo());
        return creditService.pageCreditRecord(
                baseInfo,
                rf.getPageNum(),
                rf.getPageSize(),
                rf.getMemberName(),
                rf.getMemberPhone(),
                rf.getWriteoffMemberId(),
                rf.getWriteoffMemberName(),
                rf.getWriteoffMemberPhone(),
                rf.getChargeRecordId(),
                rf.getPayId(),
                rf.getCreditMonth(),
                rf.getStatus(),
                rf.getOperator());
    }

    /**
     * 获取附卡赊账记录列表
     * @param rf
     */
    @GetMapping("pageAttachedCreditRecord")
    @MLog(modelTitle = "会员-赊账-获取附卡赊账记录列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public TableDataInfo<SysMembersCredit> pageAttachedCreditRecord(@Validated PageMemberRf rf) {
        BaseInfo baseInfo = BaseInfo.createNew(userService.getMemberInfo());
        return creditService.pageAttachedCreditRecord(
                baseInfo,
                rf.getPageNum(),
                rf.getPageSize(),
                rf.getMemberId(),
                rf.getCreditMonth());
    }

    /**
     * 获取赊账记录详情
     * @param rf
     */
    @GetMapping("getCreditRecord")
    @MLog(modelTitle = "会员-赊账-获取赊账记录详情" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<SysMembersCredit> getCreditRecord(@Validated PrimaryRf rf) {
        BaseInfo baseInfo = BaseInfo.createNew(userService.getMemberInfo());
        return AjaxResult.success(creditService.getCreditRecord(baseInfo, rf.getPrimaryId()));
    }

    /**
     * 获取赊账日志列表
     * @param rf
     */
    @GetMapping("pageCreditLog")
    @MLog(modelTitle = "会员-赊账-获取挂账日志列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public TableDataInfo<SysMembersCreditLog> pageCreditLog(@Validated ListCreditLogRf rf) {
        BaseInfo baseInfo = BaseInfo.createNew(userService.getMemberInfo());
        return creditService.pageCreditLog(
                baseInfo,
                rf.getPageNum(),
                rf.getPageSize(),
                rf.getMemberName(),
                rf.getMemberPhone(),
                rf.getType(),
                rf.getStartDate(),
                rf.getEndDate(),
                rf.getOperator());
    }

    /**
     * 获取赊账日志详情
     * @param rf
     */
    @GetMapping("getCreditLog")
    @MLog(modelTitle = "会员-赊账-获取赊账日志详情" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<SysMembersCreditLog> getCreditLog(@Validated PrimaryRf rf) {
        BaseInfo baseInfo = BaseInfo.createNew(userService.getMemberInfo());
        return AjaxResult.success(creditService.getCreditLog(baseInfo, rf.getPrimaryId()));
    }

    /**
     * 获取自己已分配额度的共享会员
     */
    @GetMapping("listHasShareMember")
    @MLog(modelTitle = "会员-赊账-获取自己已分配额度的共享会员" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<CreditMemberVo>> listHasShareMember() {
        BaseInfo baseInfo = BaseInfo.createNew(userService.getMemberInfo());
        return AjaxResult.success(creditService.listHasShareMember(baseInfo));
    }

    /**
     * 设置共享额度
     * @param rf
     */
    @PostMapping("setShareMember")
    @MLog(modelTitle = "会员-赊账-设置共享额度" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<SysMembersBalance> setShareMember(@Validated @RequestBody SetShareMemberRf rf) {
        BaseInfo baseInfo = BaseInfo.createNew(userService.getMemberInfo());
        return AjaxResult.success(creditService.setShareMember(baseInfo, rf.getMemberId(), rf.getShareQuota()));
    }

    /**
     * 删除共享额度
     * @param rf
     */
    @PostMapping("delShareMember")
    @MLog(modelTitle = "会员-赊账-删除共享额度" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<SysMembersBalance> delShareMember(@Validated @RequestBody PrimaryRf rf) {
        BaseInfo baseInfo = BaseInfo.createNew(userService.getMemberInfo());
        return AjaxResult.success(creditService.delShareMember(baseInfo, rf.getPrimaryId()));
    }

}
