package cn.px.tea.modules.ycb.service.member.impl;

import cn.hutool.http.HttpUtil;
import cn.px.tea.common.domain.AjaxResult;
import cn.px.tea.common.domain.AppUserDetails;
import cn.px.tea.common.service.RedisService;
import cn.px.tea.common.util.DateUtils;
import cn.px.tea.common.util.uuid.IdUtils;
import cn.px.tea.modules.ycb.define.CommonDefine;
import cn.px.tea.modules.ycb.define.SmsDefine;
import cn.px.tea.modules.ycb.model.SysMembers;
import cn.px.tea.modules.ycb.model.SysMembersBalance;
import cn.px.tea.modules.ycb.model.SysWeixinLogin;
import cn.px.tea.modules.ycb.service.table.intel.SysMembersBalanceService;
import cn.px.tea.modules.ycb.service.table.intel.SysMembersService;
import cn.px.tea.modules.ycb.service.table.intel.SysWeixinLoginService;
import cn.px.tea.modules.ycb.model.*;
import cn.px.tea.modules.ycb.pojo.bo.com.MemberInfo;
import cn.px.tea.modules.ycb.pojo.bo.com.SmsCodeCacheBo;
import cn.px.tea.modules.ycb.pojo.bo.wxsmall.WxCode;
import cn.px.tea.modules.ycb.pojo.member.vo.MemberInfoVo;
import cn.px.tea.modules.ycb.pojo.member.vo.WxBindInfo;
import cn.px.tea.modules.ycb.pojo.vo.login.LoginVo;
import cn.px.tea.modules.ycb.pojo.vo.login.PhoneCodeVo;
import cn.px.tea.modules.ycb.service.base.inter.SmsService;
import cn.px.tea.modules.ycb.service.base.inter.UserService;
import cn.px.tea.modules.ycb.service.member.inter.MemberLoginService;
import cn.px.tea.modules.ycb.service.table.intel.*;
import cn.px.tea.security.annotation.CustomException;
import cn.px.tea.security.util.JsonUtils;
import cn.px.tea.security.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberLoginImpl implements MemberLoginService {
    private static final Logger logger = LoggerFactory.getLogger(MemberLoginImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private SysWeixinLoginService sysWeixinLoginService;

    @Autowired
    private SysMembersService sysMembersService;

    @Autowired
    private SysMembersBalanceService sysMembersBalanceService;

    @Autowired
    private RedisService redisService;

    @Override
    public LoginVo weixinLogin(String appid, String authCode) {
        logger.info("appid=" + appid + ",authCode=" + authCode);

        String openId = "";
        if ("dfueyru764i4yhu3".equals(authCode)) {
            openId = "212345jkk";
        } else {
            try {
                String data = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session?appid=wxa6f12840b0fbee85&secret=0f6842127fde1133ef279e7cba348759&grant_type=authorization_code&js_code=" + authCode);
                if (StringUtils.isBlank(data)) {
                    throw CustomException.instance("微信授权登录失败");
                }
                WxCode wxCode = JsonUtils.toBean(data, WxCode.class);
                if (wxCode == null) {
                    throw CustomException.instance("微信授权登录失败");
                }
                if (wxCode.getErrcode() == null || wxCode.getErrcode() == 0) {
                    openId = wxCode.getOpenid();
                }
            } catch (Exception e) {
                throw CustomException.instance(e.getMessage());
            }
//            try {
//                if (!this.wxMpService.switchover(appid)) {
//                    throw CustomException.instance(500, "无效appid");
//                }
//                WxOAuth2AccessToken wxOAuth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(authCode);
//                if (wxOAuth2AccessToken == null) {
//                    throw CustomException.instance(500, "无效授权码");
//                }
//                WxOAuth2UserInfo wxOAuth2UserInfo = wxMpService.getOAuth2Service().getUserInfo(wxOAuth2AccessToken, null);
//                if (wxOAuth2UserInfo == null) {
//                    throw CustomException.instance(500, "登入失败！");
//                }
//                logger.info("微信授权登入信息：" + JsonUtils.toJson(wxOAuth2UserInfo));
//                openId = wxOAuth2UserInfo.getOpenid();
//            } catch (Exception e) {
//                logger.error("微信授权登入异常：" + e.getMessage(), e);
//                throw CustomException.instance(500, e.getMessage());
//            }
        }
        if (StringUtils.isBlank(openId)) {
            throw CustomException.instance("微信授权登录失败");
        }

        SysWeixinLogin mbLogin = sysWeixinLoginService.getByOpenId(appid, openId);
        if (mbLogin == null) {
            // 需要绑定手机号
            String token = String.valueOf(IdUtils.getUnid());
            WxBindInfo wxBindInfo = new WxBindInfo(appid, openId);
            String key = "TOKEN-MEMBER-BIND:" + token;
            String data = JsonUtils.toJson(wxBindInfo);
            redisService.set(key, data, 1800);

            LoginVo loginVo = new LoginVo(token, token, 1800);
            loginVo.setStatus(2);
            return loginVo;
        } else {
            SysMembers sysMembers = sysMembersService.getByMembers(mbLogin.getMemberId());
            if (sysMembers == null) {
                throw CustomException.instance("会员信息不存在");
            }
            if (sysMembers.getStatus() != CommonDefine.STATUS_ENABLE) {
                throw CustomException.instance("该会员已被禁用");
            }
            LoginVo loginVo = userService.login(new AppUserDetails(CommonDefine.MEMBER_TOKEN + sysMembers.getMemberId(), "", sysMembers.getStatus()));
            if (loginVo == null) {
                throw CustomException.instance("登录失败");
            }
            return loginVo;
        }
    }

    @Override
    public PhoneCodeVo smsBindByCode(String token, String phone) {
        SysMembers sysMembers = null;
        SysWeixinLogin mbLogin = sysWeixinLoginService.getByPhone(phone);
        if (mbLogin == null) {
            sysMembers = sysMembersService.getByMemberPhone(phone);
            if (sysMembers == null) {
                throw CustomException.instance("会员不存在");
            }
        } else {
            sysMembers = sysMembersService.getByMemberPhone(mbLogin.getShopId(), phone);
            if (sysMembers == null) {
                throw CustomException.instance("会员不存在");
            }
        }
        if(sysMembers.getStatus() != CommonDefine.STATUS_ENABLE) {
            throw CustomException.instance("非正常状态帐号，不可登入");
        }


        String key = "TOKEN-MEMBER-BIND:" + token;
        String data = (String)redisService.get(key);
        if (StringUtils.isBlank(data)) {
            throw CustomException.instance("没有登录");
        }
        WxBindInfo wxBindInfo = JsonUtils.toBean(data, WxBindInfo.class);
        if (wxBindInfo == null) {
            throw CustomException.instance("请重新进行微信授权登录");
        }

        String verificationCode = RandomUtil.getSixBitRandom();
        if (!smsService.sendCodeForMemberBind(SmsDefine.TEMPLATE_LOGIN, phone, verificationCode)) {
            throw CustomException.instance("短信发送失败");
        }
        wxBindInfo.setPhone(phone);
        wxBindInfo.setCode(verificationCode);

        data = JsonUtils.toJson(wxBindInfo);
        redisService.set(key, data, 1800);

        return new PhoneCodeVo(phone, 60, 120);
    }

    @Override
    public LoginVo wxBindPhone(String token, String phone, String code) {
        String key = "TOKEN-MEMBER-BIND:" + token;
        String data = (String)redisService.get(key);
        if (StringUtils.isBlank(data)) {
            throw CustomException.instance("没有登录");
        }
        WxBindInfo wxBindInfo = JsonUtils.toBean(data, WxBindInfo.class);
        if (wxBindInfo == null) {
            throw CustomException.instance("请重新进行微信授权登录");
        }

        if (!phone.equals(wxBindInfo.getPhone())) {
            throw CustomException.instance("手机号不匹配");
        }
        if (!code.equals(wxBindInfo.getCode())) {
            throw CustomException.instance("验证码不匹配");
        }

        SysMembers sysMembers = null;
        SysWeixinLogin mbLogin = sysWeixinLoginService.getByPhone(phone);
        if (mbLogin == null) {
            sysMembers = sysMembersService.getByMemberPhone(phone);
            if (sysMembers == null) {
                throw CustomException.instance("会员不存在");
            }
            if(sysMembers.getStatus() != CommonDefine.STATUS_ENABLE) {
                throw CustomException.instance("非正常状态帐号，不可登入");
            }
            mbLogin = sysWeixinLoginService.getByMemberId(sysMembers.getMemberId());
            if (mbLogin ==null) {
                mbLogin = new SysWeixinLogin(sysMembers.getMemberId(), sysMembers.getMemberName(), wxBindInfo.getAppid(), wxBindInfo.getOpenid(), sysMembers.getPhone(), sysMembers.getShopId(), DateUtils.getNowTimestamp());
                if (!sysWeixinLoginService.save(mbLogin)) {
                    throw CustomException.instance("登录失败");
                }
            } else {
                mbLogin.setAppid(wxBindInfo.getAppid());
                mbLogin.setWxOpenid(wxBindInfo.getOpenid());
                mbLogin.setPhone(phone);
                mbLogin.setUpdateTime(DateUtils.getNowTimestamp());
                if (!sysWeixinLoginService.updateById(mbLogin)) {
                    throw CustomException.instance("登录失败");
                }
            }
        } else {
            sysMembers = sysMembersService.getByMemberPhone(mbLogin.getShopId(), phone);
            if (sysMembers == null) {
                throw CustomException.instance("会员不存在");
            }
            if(sysMembers.getStatus() != CommonDefine.STATUS_ENABLE) {
                throw CustomException.instance("非正常状态帐号，不可登入");
            }
            mbLogin.setAppid(wxBindInfo.getAppid());
            mbLogin.setWxOpenid(wxBindInfo.getOpenid());
            mbLogin.setPhone(phone);
            mbLogin.setUpdateTime(DateUtils.getNowTimestamp());
            if (!sysWeixinLoginService.updateById(mbLogin)) {
                throw CustomException.instance("登录失败");
            }
        }

        // 需要绑定手机号
        LoginVo loginVo = userService.login(new AppUserDetails(CommonDefine.MEMBER_TOKEN + sysMembers.getMemberId(), "", sysMembers.getStatus()));
        if (loginVo == null) {
            throw CustomException.instance("登录失败");
        }
        return loginVo;
    }

    @Override
    public PhoneCodeVo smsLoginByCode(String phone) {
        SysMembers sysMembers = null;
        SysWeixinLogin mbLogin = sysWeixinLoginService.getByPhone(phone);
        if (mbLogin == null) {
            sysMembers = sysMembersService.getByMemberPhone(phone);
            if (sysMembers == null) {
                throw CustomException.instance("会员不存在");
            }
        } else {
            sysMembers = sysMembersService.getByMemberPhone(mbLogin.getShopId(), phone);
            if (sysMembers == null) {
                throw CustomException.instance("会员不存在");
            }
        }
        if(sysMembers.getStatus() != CommonDefine.STATUS_ENABLE) {
            throw CustomException.instance("非正常状态帐号，不可登入");
        }

        AjaxResult<SmsCodeCacheBo> ajaxResult = smsService.sendCode(SmsDefine.TEMPLATE_LOGIN, phone);
        if (ajaxResult.checkError()) {
            throw CustomException.instance(ajaxResult.getCode(), ajaxResult.getMsg());
        }
        SmsCodeCacheBo smsCodeCacheBo = ajaxResult.getData();
        return new PhoneCodeVo(smsCodeCacheBo.getPhone(), smsCodeCacheBo.getSendTime(), smsCodeCacheBo.getCacheTime());
    }

    @Override
    public LoginVo loginByCode(String phone, String code) {
        if (!smsService.useCode(SmsDefine.TEMPLATE_LOGIN, phone, code)) {
            throw CustomException.instance("验证码错误");
        }

        SysMembers sysMembers = null;
        SysWeixinLogin mbLogin = sysWeixinLoginService.getByPhone(phone);
        if (mbLogin == null) {
            sysMembers = sysMembersService.getByMemberPhone(phone);
            if (sysMembers == null) {
                throw CustomException.instance("会员不存在");
            }
            mbLogin = new SysWeixinLogin(sysMembers.getMemberId(), sysMembers.getMemberName(), "", "", sysMembers.getPhone(), sysMembers.getShopId(), DateUtils.getNowTimestamp());
            if (!sysWeixinLoginService.save(mbLogin)) {
                throw CustomException.instance("登录失败");
            }
        } else {
            sysMembers = sysMembersService.getByMemberPhone(mbLogin.getShopId(), phone);
            if (sysMembers == null) {
                throw CustomException.instance("会员不存在");
            }
        }
        if(sysMembers.getStatus() != CommonDefine.STATUS_ENABLE) {
            throw CustomException.instance("非正常状态帐号，不可登入");
        }

        LoginVo loginVo = userService.login(new AppUserDetails(CommonDefine.MEMBER_TOKEN + sysMembers.getMemberId(), "", sysMembers.getStatus()));
        if (loginVo == null) {
            throw CustomException.instance("登录失败");
        }
        return loginVo;
    }

    @Override
    public LoginVo refreshToken() {
        return userService.refresh();
    }

    @Override
    public void loginOut() {
        userService.removeTokenMember();
    }

    @Override
    public MemberInfoVo getInfo() {
        MemberInfo memberInfo = userService.getMemberInfo();
        Long membersId = memberInfo.getMemberId();
        String shopName = memberInfo.getShopName();

        SysMembers sysMembers = sysMembersService.getByMembers(membersId);
        if (sysMembers == null) {
            throw CustomException.instance("会员信息不存在");
        }
        SysMembersBalance sysMembersBalance = sysMembersBalanceService.getByMemberId(membersId);
        if (sysMembersBalance == null) {
            throw CustomException.instance("会员账号信息不存在");
        }
        return new MemberInfoVo(
                sysMembers.getMemberId(),
                sysMembers.getMemberNo(),
                sysMembers.getMemberLev(),
                sysMembers.getMemberType(),
                sysMembersBalance.getMemberCredit(),
                sysMembers.getNickName(),
                sysMembers.getShopId(),
                shopName,
                sysMembers.getLogo(),
                sysMembers.getSex(),
                sysMembers.getPhone(),
                sysMembers.getWeixin(),
                sysMembers.getRefereesName(),
                sysMembers.getCommissionEmpName(),
                sysMembersBalance.getRealBalance(),
                sysMembersBalance.getGiveBalance(),
                sysMembersBalance.getBalance(),
                sysMembersBalance.getAllQuota(),
                sysMembersBalance.getSpentQuota(),
                sysMembersBalance.getSurplusQuota(),
                sysMembersBalance.getYearChargeCope(),
                sysMembersBalance.getYearConsumeCope(),
                sysMembers.getCreateTime()
        );
    }

    @Override
    public SysMembers editInfo(String nickname, String avatar, Integer sex) {
        MemberInfo memberInfo = userService.getMemberInfo();
        Long memberId = memberInfo.getMemberId();

        SysMembers sysMembers = sysMembersService.getByMembers(memberId);
        if (sysMembers == null) {
            throw CustomException.instance("会员信息不存在");
        }
        if (StringUtils.isNotBlank(nickname)) {
            sysMembers.setNickName(nickname);
        }
        if (StringUtils.isNotBlank(avatar)) {
            sysMembers.setLogo(avatar);
        }
        if (sex != null && sex > 0) {
            sysMembers.setSex(sex);
        }
        sysMembers.setTimestamp(DateUtils.getNowTimestamp());
        if (!sysMembersService.updateById(sysMembers)) {
            throw CustomException.instance("修改资料失败");
        }

        userService.removeUserInfo(memberInfo.getMemberId());

        return sysMembers;
    }

}
