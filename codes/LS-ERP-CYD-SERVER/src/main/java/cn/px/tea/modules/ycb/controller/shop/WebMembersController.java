package cn.px.tea.modules.ycb.controller.shop;

import cn.px.tea.common.domain.AjaxResult;
import cn.px.tea.common.domain.TableDataInfo;
import cn.px.tea.modules.ycb.model.*;
import cn.px.tea.modules.ycb.pojo.rf.members.*;
import cn.px.tea.modules.ycb.model.*;
import cn.px.tea.modules.ycb.pojo.bo.base.MemberVo;
import cn.px.tea.modules.ycb.pojo.rf.login.PhoneRf;
import cn.px.tea.modules.ycb.pojo.rf.members.*;
import cn.px.tea.modules.ycb.pojo.vo.members.MembersVo;
import cn.px.tea.modules.ycb.service.base.inter.MembersService;
import cn.px.tea.security.aspect.Log;
import cn.px.tea.security.define.SystemDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 门店-会员
 */
@RestController
@RequestMapping("/web/members")
public class WebMembersController {

    @Autowired
    private MembersService membersService;

    /**
     * 获取会员列表
     * @param rf
     */
    @GetMapping("pageMembers")
    @Log(modelTitle = "门店-会员-获取会员列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public TableDataInfo<MemberVo> pageMembers(@Validated PageMembersRf rf) {
        return membersService.pageMembers(rf.getPageNum(), rf.getPageSize(), rf.getMemberLev(), rf.getSelect(), rf.getStatus());
    }

    /**
     * 获取会员详细信息
     * @param rf
     */
    @GetMapping("getMembersInfo")
    @Log(modelTitle = "门店-会员-获取会员详细信息" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<MemberVo> getMembersInfo(@Validated MembersRf rf) {
        return AjaxResult.success(membersService.getMembersInfo(rf.getMembersId()));
    }

    /**
     * 根据手机号获取会员详细信息
     * @param rf
     */
    @GetMapping("getMembersInfoByPhone")
    @Log(modelTitle = "门店-会员-根据手机号获取会员详细信息" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<MembersVo> getMembersInfoByPhone(@Validated PhoneRf rf) {
        MembersVo vo = membersService.getMembersInfoByPhone(rf.getPhone());
        if (vo == null) {
            return AjaxResult.success();
        } else {
            return AjaxResult.success(vo);
        }
    }

    /**
     * 获取会员代金券信息
     * @param rf
     */
    @GetMapping("getMembersVoucher")
    @Log(modelTitle = "门店-会员-获取会员代金券信息" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<SysMembersVoucher>> getMembersVoucher(@Validated MembersRf rf) {
        return AjaxResult.success(membersService.getMembersVoucher(rf.getMembersId()));
    }

    /**
     * 会员注册
     * @param rf
     */
    @PostMapping("/addMembers")
    @Log(modelTitle = "门店-会员-会员注册" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<MembersVo> addMembers(@Validated @RequestBody AddMembersRf rf) {
        return AjaxResult.success(membersService.addMembers(rf.getMemberNo(), rf.getMemberName(), rf.getSex(), rf.getPhone(), rf.getWeixin(), rf.getAddr(), rf.getAvatar(), rf.getEmail(), rf.getBirth(), rf.getExpireDate(), rf.getRefereesNo(), rf.getCommissionEmpNo(), rf.getRemark()));
    }

    /**
     * 编辑会员基础资料
     * @param rf
     */
    @PostMapping("/editMembers")
    @Log(modelTitle = "门店-会员-编辑会员基础资料" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<MembersVo> editMembers(@Validated @RequestBody EditMembersRf rf) {
        return AjaxResult.success(membersService.editMembers(rf.getMemberId(), rf.getMemberName(), rf.getWeixin(), rf.getAddr(), rf.getAvatar(), rf.getEmail(), rf.getSex(), rf.getBirth(), rf.getRemark()));
    }

    /**
     * 设置会员状态（可启用、禁用）
     * @param rf
     */
    @PostMapping("/statusMembers")
    @Log(modelTitle = "门店-会员-设置会员状态（可启用、禁用）" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<MembersVo> statusMembers(@Validated @RequestBody MembersEmpRf rf) {
        return AjaxResult.success(membersService.statusMembers(rf.getMembersId(), rf.getStatus()));
    }

    /**
     * 更换会员手机号码(需短信验证码验证）
     * @param rf
     */
    @PostMapping("/changePhone")
    @Log(modelTitle = "门店-会员-更换会员手机号码(需短信验证码验证）" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<MembersVo> changePhone(@Validated @RequestBody ChangePhoneRf rf) {
        return AjaxResult.success(membersService.changePhone(rf.getMembersId(), rf.getPhone(), rf.getRemark()));
    }

    /**
     * 会员充值优惠信息
     */
    @GetMapping("getRechargeVoucher")
    @Log(modelTitle = "门店-会员-会员充值优惠信息" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<SysGiftRechargeVoucher>> getRechargeVoucher() {
        return AjaxResult.success(membersService.getRechargeVoucher());
    }

    /**
     * 会员等级设置
     * @param rf
     */
    @PostMapping("/upgradeMembers")
    @Log(modelTitle = "门店-会员-会员等级设置" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysMembers> upgradeMembers(@Validated @RequestBody UpgradeMembersRf rf) {
        return AjaxResult.success(membersService.upgradeMembers(rf.getMembersId(), rf.getMemberLev(), rf.getRemark()));
    }

    /**
     * 会员类型设置
     * @param rf
     */
    @PostMapping("/setMembersType")
    @Log(modelTitle = "门店-会员-会员类型设置" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysMembers> setMembersType(@Validated @RequestBody SetMembersTypeRf rf) {
        return AjaxResult.success(membersService.setMembersType(rf.getMembersId(), rf.getMemberType(), rf.getRemark()));
    }

    /**
     * 会员支付透支额度设置
     * @param rf
     */
    @PostMapping("/setPayQuota")
    @Log(modelTitle = "门店-会员-会员支付透支额度设置" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysMembers> setPayQuota(@Validated @RequestBody SetMembersPayQuotaRf rf) {
        return AjaxResult.success(membersService.setPayQuota(rf.getMembersId(), rf.getPayQuota(), rf.getRemark()));
    }

    /**
     * 获取会员充值消费详情
     * @param rf
     */
    @GetMapping("pageByChargeRecord")
    @Log(modelTitle = "门店-会员-获取会员充值消费详情" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public TableDataInfo<SysMembersChargeRecord> pageByChargeRecord(@Validated PageByChargeRecordRf rf) {
        return membersService.pageByChargeRecord(rf.getPageNum(), rf.getPageSize(), rf.getType(), rf.getMembersId(), rf.getStartTime(), rf.getEndTime());
    }

    /**
     * 获取会员积分详情
     * @param rf
     */
    @GetMapping("pageByintegralRecord")
    @Log(modelTitle = "门店-会员-获取会员积分详情" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public TableDataInfo<SysMembersIntegralRecord> pageByintegralRecord(@Validated PageByIntegralRecordRf rf) {
        return membersService.pageByintegralRecord(rf.getPageNum(), rf.getPageSize(), rf.getType(), rf.getMembersId(), rf.getStartTime(), rf.getEndTime());
    }
}
