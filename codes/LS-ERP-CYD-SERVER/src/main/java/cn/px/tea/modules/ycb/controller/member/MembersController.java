package cn.px.tea.modules.ycb.controller.member;

import cn.px.tea.common.domain.AjaxResult;
import cn.px.tea.common.domain.TableDataInfo;
import cn.px.tea.modules.ycb.model.SysMembersChargeRecord;
import cn.px.tea.modules.ycb.pojo.rf.members.PageByChargeRecordRf;
import cn.px.tea.modules.ycb.model.*;
import cn.px.tea.modules.ycb.pojo.bo.base.MemberVo;
import cn.px.tea.modules.ycb.pojo.member.rf.MemberChangePhoneRf;
import cn.px.tea.modules.ycb.pojo.rf.login.PhoneRf;
import cn.px.tea.modules.ycb.pojo.rf.members.*;
import cn.px.tea.modules.ycb.pojo.vo.login.PhoneCodeVo;
import cn.px.tea.modules.ycb.service.member.inter.MemberService;
import cn.px.tea.security.aspect.MLog;
import cn.px.tea.security.define.SystemDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 会员-资料
 */
@RestController
@RequestMapping("/member/info")
public class MembersController {

    @Autowired
    private MemberService memberService;


    /**
     * 获取会员详细信息
     */
    @GetMapping("getMemberInfo")
    @MLog(modelTitle = "会员-资料-获取会员详细信息" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<MemberVo> getMemberInfo() {
        return AjaxResult.success(memberService.getMemberInfo());
    }

    /**
     * 短信验证码更换手机之发送短信
     * @param rf
     */
    @PostMapping("smsChangeByCode")
    @MLog(modelTitle = "会员-资料-短信验证码更换手机之发送短信" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<PhoneCodeVo> smsChangeByCode(@RequestBody @Validated PhoneRf rf) {
        return AjaxResult.success(memberService.smsChangeByCode(rf.getPhone()));
    }


    /**
     * 更换会员手机号码(需短信验证码验证）
     * @param rf
     */
    @PostMapping("/changePhone")
    @MLog(modelTitle = "会员-资料-更换会员手机号码(需短信验证码验证）" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<MemberVo> changePhone(@Validated @RequestBody MemberChangePhoneRf rf) {
        return AjaxResult.success(memberService.changePhone(rf.getPhone(), rf.getCode()));
    }

    /**
     * 获取会员充值消费详情
     * @param rf
     */
    @GetMapping("pageByChargeRecord")
    @MLog(modelTitle = "会员-资料-获取会员充值消费详情" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public TableDataInfo<SysMembersChargeRecord> pageByChargeRecord(@Validated PageByChargeRecordRf rf) {
        return memberService.pageByChargeRecord(rf.getPageNum(), rf.getPageSize(), rf.getType(), rf.getStartTime(), rf.getEndTime());
    }
}
