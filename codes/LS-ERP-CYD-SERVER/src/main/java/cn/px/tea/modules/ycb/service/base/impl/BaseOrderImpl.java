package cn.px.tea.modules.ycb.service.base.impl;

import cn.px.tea.common.service.RedisService;
import cn.px.tea.common.util.DateUtils;
import cn.px.tea.common.util.uuid.IdUtils;
import cn.px.tea.modules.ycb.define.*;
import cn.px.tea.modules.ycb.model.*;
import cn.px.tea.modules.ycb.pojo.bo.base.ShopConsumeInfo;
import cn.px.tea.modules.ycb.pojo.bo.base.ShopConsumeRoomInfo;
import cn.px.tea.modules.ycb.pojo.bo.base.ShopRechargeInfo;
import cn.px.tea.modules.ycb.service.table.intel.*;
import cn.px.tea.modules.ycb.define.*;
import cn.px.tea.modules.ycb.model.*;
import cn.px.tea.modules.ycb.pojo.bo.com.DiscountBo;
import cn.px.tea.modules.ycb.pojo.bo.com.OrderDiscountBo;
import cn.px.tea.modules.ycb.pojo.bo.com.VoucherUseInfo;
import cn.px.tea.modules.ycb.pojo.bo.base.*;
import cn.px.tea.modules.ycb.pojo.bo.businessstatistics.WaterInfoBo;
import cn.px.tea.modules.ycb.pojo.bo.order.OrderBo;
import cn.px.tea.modules.ycb.pojo.bo.order.OrderGoodsDetailsBo;
import cn.px.tea.modules.ycb.pojo.rf.store.StoreInout;
import cn.px.tea.modules.ycb.service.base.inter.BaseOrderService;
import cn.px.tea.modules.ycb.service.base.inter.BaseStoreService;
import cn.px.tea.modules.ycb.service.base.inter.MembersMarketService;
import cn.px.tea.modules.ycb.service.base.inter.SmsService;
import cn.px.tea.modules.ycb.service.table.intel.*;
import cn.px.tea.security.annotation.CustomException;
import cn.px.tea.security.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class BaseOrderImpl implements BaseOrderService {
    private static Logger logger = LoggerFactory.getLogger(BaseOrderImpl.class);

    @Autowired
    private SysShopService sysShopService;

    @Autowired
    private SysMembersService sysMembersService;

    @Autowired
    private SysMembersBalanceService sysMembersBalanceService;

    @Autowired
    private SysOrderService sysOrderService;

    @Autowired
    private SysOrderGoodsDetailsService sysOrderGoodsDetailsService;

    @Autowired
    private  SysStoreGoodsService sysStoreGoodsService;

    @Autowired
    private SysOrderPayService sysOrderPayService;

    @Autowired
    private SysRoomUseService sysRoomUseService;

    @Autowired
    private SysRoomService sysRoomService;

    @Autowired
    private SysMembersVoucherService sysMembersVoucherService;

    @Autowired
    private MembersMarketService membersMarketService;

    @Autowired
    private SysMembersVoucherRecordService sysMembersVoucherRecordService;

    @Autowired
    private SysMembersChargeRecordService sysMembersChargeRecordService;

    @Autowired
    private SysMembersCreditService sysMembersCreditService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private BaseStoreService baseStoreService;

    @Override
    public VoucherUseInfo getVoucherUseInfo(Long shopId, Long customId, Long voucherId, BigDecimal cope) {
        if (voucherId == null || voucherId == 0L) {
            logger.error("优惠券不存在， voucherId=" + voucherId);
            return null;
        }
        SysMembersVoucher sysMembersVoucher = sysMembersVoucherService.getByVoucherId(voucherId);
        if (sysMembersVoucher == null) {
            logger.error("优惠券不存在， voucherId=" + voucherId);
            return null;
        }
        if (!shopId.equals(sysMembersVoucher.getShopId())) {
            logger.error("该优惠券不属于此门店，不可使用， useShopId=" + shopId + ", voucher shopId=" + sysMembersVoucher.getShopId());
            return null;
        }
        if (!customId.equals(sysMembersVoucher.getMemberId())) {
            logger.error("该优惠券不属于消费人，不可使用， useCustomId=" + customId + ", voucher customId=" + sysMembersVoucher.getMemberId());
            return null;
        }
        if (sysMembersVoucher.getStatus() != OrderDefine.VOUCHERSTATUS_WAIT) {
            logger.error("该优惠券状态非待用状态，不可使用， voucherId=" + voucherId);
            return null;
        }
        // 验证优惠券能不能用
        if (!membersMarketService.checkVoucher(sysMembersVoucher.getStartDate(), sysMembersVoucher.getEndDate(), sysMembersVoucher.getFullAmount(), cope)) {
            logger.error("优惠券不满足使用条件");
            return null;
        }
        BigDecimal voucherCope = sysMembersVoucher.getAmount();

        return new VoucherUseInfo(voucherId, customId, voucherCope);
    }

    @Transactional
    @Override
    public boolean useVoucher(SysOrder sysOrder, Long voucherId, Long operator) {
        if (sysOrder == null || voucherId == null || voucherId == 0L) {
            logger.error("优惠券不存在， voucherId=" + voucherId);
            return false;
        }
        SysMembersVoucher sysMembersVoucher = sysMembersVoucherService.getByVoucherId(voucherId);
        if (sysMembersVoucher == null) {
            logger.error("优惠券不存在， voucherId=" + voucherId);
            return false;
        }

        Timestamp curTime = DateUtils.getNowTimestamp();

        sysMembersVoucher.setRelevanceId(sysOrder.getOrderId());
        sysMembersVoucher.setStatus(OrderDefine.VOUCHERSTATUS_USED);
        sysMembersVoucher.setTimestamp(curTime);
        if (!sysMembersVoucherService.updateById(sysMembersVoucher)) {
            logger.error("优惠券保存失败:" + JsonUtils.toJson(sysMembersVoucher));
            return false;
        }

        SysMembersVoucherRecord record = new SysMembersVoucherRecord(
                IdUtils.getUnid(),
                sysMembersVoucher.getVoucherId(),
                sysMembersVoucher.getShopId(),
                sysMembersVoucher.getMemberId(),
                sysMembersVoucher.getName(),
                sysMembersVoucher.getApplyScope(),
                sysMembersVoucher.getStartDate(),
                sysMembersVoucher.getEndDate(),
                sysMembersVoucher.getAmount(),
                sysMembersVoucher.getFullAmount(),
                OrderDefine.VOUCHERTYPE_USE,
                sysMembersVoucher.getRelevanceId(),
                sysMembersVoucher.getRemark(),
                operator,
                curTime
        );
        if (!sysMembersVoucherRecordService.save(record)) {
            logger.error("优惠券使用失败， voucherId=" + voucherId + ", orderId=" + sysOrder.getOrderId());
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public boolean refundVoucher(Long relevanceId, Long operator) {
        if (relevanceId == null || relevanceId == 0L) {
            logger.error("优惠券不存在， relevanceId=" + relevanceId);
            return false;
        }

        List<SysMembersVoucher> listMembersVoucher = sysMembersVoucherService.listByRelevanceId(relevanceId, OrderDefine.VOUCHERSTATUS_USED);
        if (listMembersVoucher == null || listMembersVoucher.size() == 0) {
            logger.error("优惠券不存在， relevanceId=" + relevanceId);
            return false;
        }

        Timestamp curTime = DateUtils.getNowTimestamp();

        List<SysMembersVoucherRecord> listRecord = new ArrayList<>();
        for (SysMembersVoucher sysMembersVoucher : listMembersVoucher) {
            sysMembersVoucher.setStatus(OrderDefine.VOUCHERSTATUS_WAIT);
            sysMembersVoucher.setTimestamp(curTime);

            listRecord.add(new SysMembersVoucherRecord(
                    IdUtils.getUnid(),
                    sysMembersVoucher.getVoucherId(),
                    sysMembersVoucher.getShopId(),
                    sysMembersVoucher.getMemberId(),
                    sysMembersVoucher.getName(),
                    sysMembersVoucher.getApplyScope(),
                    sysMembersVoucher.getStartDate(),
                    sysMembersVoucher.getEndDate(),
                    sysMembersVoucher.getAmount(),
                    sysMembersVoucher.getFullAmount(),
                    OrderDefine.VOUCHERTYPE_REFUND,
                    sysMembersVoucher.getRelevanceId(),
                    sysMembersVoucher.getRemark(),
                    operator,
                    curTime
            ));
        }
        if (!sysMembersVoucherService.updateBatchById(listMembersVoucher)) {
            logger.error("优惠券更新失败, relevanceId=" + relevanceId);
            return false;
        }
        if (!sysMembersVoucherRecordService.saveBatch(listRecord)) {
            logger.error("优惠券更新失败, relevanceId=" + relevanceId);
            return false;
        }

        return true;
    }

    @Transactional
    @Override
    public SysOrderPay orderPayNoMember(Long orderId, Integer payType, String remark, Long operator, String operatorName) {
        SysOrder sysOrder = sysOrderService.getByOrderId(orderId);
        if (sysOrder == null) {
            logger.error("订单不存在！");
            return null;
        }

        Timestamp curTime = DateUtils.getNowTimestamp();
        Long orderPayId = IdUtils.getUnid();
        SysOrderPay sysOrderPay = new SysOrderPay(orderPayId, sysOrder.getShopId(), OrderDefine.ORDERRC_CONSUME, payType, curTime, sysOrder.getDealCope(), sysOrder.getRealCope(), 0, 0, OrderDefine.ORDERSTATUS_PAY, remark, operator, operatorName, curTime);
        if (!sysOrderPayService.save(sysOrderPay)) {
            logger.error("保存支付单信息失败:" + JsonUtils.toJson(sysOrderPay));
            return null;
        }

        sysOrder.setPayType(payType);
        sysOrder.setOrderPayId(orderPayId);
        sysOrder.setPayTime(curTime);
        sysOrder.setStatus(OrderDefine.ORDERSTATUS_PAY);
        sysOrder.setTimestamp(curTime);
        if (!sysOrderService.updateById(sysOrder)) {
            logger.error("更新订单信息失败，listOrderOrder=" + JsonUtils.toJson(sysOrder));
            return null;
        }

        // 更新订单详细信息状态
        List<SysOrderGoodsDetails> listDetail = sysOrderGoodsDetailsService.listByOrderId(orderId);
        if (listDetail != null && listDetail.size() > 0) {
            for (SysOrderGoodsDetails sysOrderGoodsDetails : listDetail) {
                sysOrderGoodsDetails.setStatus(OrderDefine.ORDERSTATUS_PAY);
                sysOrderGoodsDetails.setTimestamp(curTime);
            }
            if (!sysOrderGoodsDetailsService.updateBatchById(listDetail)) {
                logger.error("更新订单详细信息信息失败：" + orderId);
                return null;
            }
        }

        return sysOrderPay;
    }

    @Transactional
    @Override
    public SysOrderPay orderPay(Long orderId, Integer payType, String remark, Long operator, String operatorName, Long writeoffMemberId, String writeoffMemberName, String writeoffMemberPhone, Timestamp submitTime) {
        List<Long> listOrderId = new ArrayList<>();
        listOrderId.add(orderId);
        return orderPay(listOrderId, payType, remark, operator, operatorName, writeoffMemberId, writeoffMemberName, writeoffMemberPhone, submitTime);
    }

    @Transactional
    @Override
    public SysOrderPay orderPay(List<Long> listOrderId, Integer payType, String remark, Long operator, String operatorName, Long writeoffMemberId, String writeoffMemberName, String writeoffMemberPhone, Timestamp submitTime) {
        if (listOrderId == null || listOrderId.size() == 0 || payType == null || payType <= 0 || operator == null || operator == 0L) {
            logger.error("参数有误");
            return null;
        }
        if (StringUtils.isBlank(remark)) {
            remark = "";
        }

        List<SysOrder> listOrder = sysOrderService.listByOrderId(listOrderId);
        if (listOrder == null || listOrder.size() != listOrderId.size()) {
            logger.error("有无效的订单，listOrderId=" + JsonUtils.toJson(listOrderId));
            return null;
        }
        List<SysOrderGoodsDetails> listDetail = sysOrderGoodsDetailsService.listByOrderId(listOrderId);
        caclOrder(listOrder, listDetail);

        Long shopId = null;
        Integer membersType = 0;
        Long membersId = null;
        String membersName = "";
        Long orderSubmitEmpId = 0L;
        String orderSubmitEmpName = "";
        Integer orderRcType = null;
        BigDecimal dealCope = BigDecimal.ZERO;
        BigDecimal realCope = BigDecimal.ZERO;
        BigDecimal voucherCope = BigDecimal.ZERO;
        for (SysOrder sysOrder : listOrder) {
            if (sysOrder.getStatus() != OrderDefine.ORDERSTATUS_NOPAY) {
                logger.error("订单状态非为待支付状态，orderStatus=" + sysOrder.getStatus());
                return null;
            }

            Integer _orderRcType = OrderDefine.getOrderRcType(sysOrder.getOrderType());
            if (orderRcType == null) {
                orderRcType = _orderRcType;
            }
            if (orderRcType == null || _orderRcType == null || !orderRcType.equals(_orderRcType)) {
                logger.error("多笔订单不能同时存在充值和消费，orderRecordType=" + orderRcType + ", " + _orderRcType);
                return null;
            }

            Long _shopId = sysOrder.getShopId();
            Long _membersId = sysOrder.getCustomId();
            if (shopId == null) {
                shopId = _shopId;
            }
            if (membersId == null) {
                membersId = _membersId;
                membersType = sysOrder.getCustomType();
                membersName = sysOrder.getCustomName();
            }
            if (orderSubmitEmpId == 0L) {
                orderSubmitEmpId = sysOrder.getOperator();
                orderSubmitEmpName = sysOrder.getOperatorName();
            }

            if (shopId == null || shopId == 0L || !shopId.equals(_shopId)) {
                logger.error("订单不属于同一个商店的，listOrderId=" + JsonUtils.toJson(listOrderId));
                return null;
            }
            if (membersId == null || membersId == 0L || !membersId.equals(_membersId)) {
                logger.error("订单不属于同一个客户的，listOrderId=" + JsonUtils.toJson(listOrderId));
                return null;
            }

            dealCope = dealCope.add(sysOrder.getDealCope());
            realCope = realCope.add(sysOrder.getRealCope());
            voucherCope = voucherCope.add(sysOrder.getVoucherCope()).add(sysOrder.getDiscountCope());
        }

        SysShop sysShop = sysShopService.getByShop(shopId);
        if (sysShop == null) {
            logger.error("门店信息不存在，shopId=" + shopId);
            return null;
        }

        if (orderRcType == OrderDefine.ORDERRC_CHARGE && (payType == OrderDefine.PAYTYPE_MEMBERS || payType == OrderDefine.PAYTYPE_CREDIT)) {
            // 如果是充值类的，不可用会员余额或挂账支付
            logger.error("会员充值不可再用会员余额或挂账来支付，listOrderId:" + JsonUtils.toJson(listOrderId));
            return null;
        }
        String orderRecordName = "";
        if (orderRcType == OrderDefine.ORDERRC_CHARGE) {
            orderRecordName = "充值成功";
        } else if (orderRcType == OrderDefine.ORDERRC_CONSUME) {
            orderRecordName = "消费成功";
        }

        SysMembers sysMembers = sysMembersService.getByMembers(membersId);
        if (sysMembers == null) {
            logger.error("会员账号不存在，membersId=" + membersId);
            return null;
        }
        membersType = sysMembers.getMemberLev();
        membersName = sysMembers.getMemberName();

        SysMembersBalance sysMembersBalance = sysMembersBalanceService.getByMemberId(membersId);
        if (sysMembersBalance == null) {
            logger.error("会员余额账号不存在，membersId=" + membersId);
            return null;
        }

        if (payType == OrderDefine.PAYTYPE_MEMBERS) {
            // 如果使用会员余额，检查余额是否充足
            BigDecimal balance = sysMembersBalance.getBalance();
            if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
                balance = BigDecimal.ZERO;
            }
            BigDecimal payQuota = sysMembersBalance.getPayQuota();
            if (payQuota == null || payQuota.compareTo(BigDecimal.ZERO) < 0) {
                payQuota = BigDecimal.ZERO;
            }
            BigDecimal _balance = balance.add(payQuota);
            if (_balance.compareTo(realCope) < 0) {
                logger.error("会员余额不足，membersId=" + membersId);
                return null;
            }
        } else if (payType == OrderDefine.PAYTYPE_CREDIT) {
            // 如果是挂账消费，检查额度是否充足
            BigDecimal surplusQuota = sysMembersBalance.getSurplusQuota();
            if (surplusQuota == null || surplusQuota.compareTo(realCope) < 0) {
                logger.error("会员信用额度不足，membersId=" + membersId);
                return null;
            }
        }

        Timestamp curTime = DateUtils.getNowTimestamp();
        Timestamp payTime = curTime;
        if (submitTime != null) {
            payTime = submitTime;
        }
        Long orderPayId = IdUtils.getUnid();
        SysOrderPay sysOrderPay = new SysOrderPay(orderPayId, shopId, orderRcType, payType, payTime, dealCope, realCope, 0, 0, OrderDefine.ORDERSTATUS_PAY, remark, operator, operatorName, curTime);
        if (!sysOrderPayService.save(sysOrderPay)) {
            logger.error("保存支付单信息失败:" + JsonUtils.toJson(sysOrderPay));
            return null;
        }

        for (SysOrder sysOrder : listOrder) {
            sysOrder.setPayType(payType);
            sysOrder.setOrderPayId(orderPayId);
            sysOrder.setPayTime(payTime);
            sysOrder.setStatus(OrderDefine.ORDERSTATUS_PAY);
            sysOrder.setTimestamp(curTime);
        }
        if (!sysOrderService.updateBatchById(listOrder)) {
            logger.error("更新订单信息失败，listOrderOrder=" + JsonUtils.toJson(listOrderId));
            return null;
        }

        List<StoreInout> listPutoutNode = new ArrayList<>();
        // 更新订单详细信息状态
        if (listDetail != null && listDetail.size() > 0) {
            for (SysOrderGoodsDetails sysOrderGoodsDetails : listDetail) {
                sysOrderGoodsDetails.setPayTime(payTime);
                sysOrderGoodsDetails.setStatus(OrderDefine.ORDERSTATUS_PAY);
                sysOrderGoodsDetails.setTimestamp(curTime);

                listPutoutNode.add(new StoreInout(sysOrderGoodsDetails.getInvGoodsId(), sysOrderGoodsDetails.getOrderDetailId(), sysOrderGoodsDetails.getQuantity(), sysOrderGoodsDetails.getQuantity2()));
            }
            if (!sysOrderGoodsDetailsService.updateBatchById(listDetail)) {
                logger.error("更新订单详细信息信息失败：" + JsonUtils.toJson(listOrderId));
                return null;
            }
        }

        // 库存出库处理
        if (listPutoutNode.size() > 0) {
            baseStoreService.storeOut(shopId, StoreDefine.STORE_CATALOG_TYPE_SHOP_INOUT, StoreDefine.DISCOUNT_OBJECT_PERSON, membersId, membersName, StoreDefine.STORE_OUT_PURCHASE, orderPayId, listPutoutNode, operator, operatorName);
        }

        Long chargeRecordId = IdUtils.getUnid();

        if (orderRcType == OrderDefine.ORDERRC_CHARGE) {
            sysMembersBalance.setRealBalance(sysMembersBalance.getRealBalance().add(realCope));
            sysMembersBalance.setGiveBalance(sysMembersBalance.getGiveBalance().add(voucherCope));
            sysMembersBalance.setBalance(sysMembersBalance.getRealBalance().add(sysMembersBalance.getGiveBalance()));
        } else {
            // 消费类时，只有会员支付才扣余额
            if (payType == OrderDefine.PAYTYPE_MEMBERS) {
                BigDecimal balance = sysMembersBalance.getBalance();
                if (balance.compareTo(realCope) >= 0) {
                    BigDecimal realBalance = sysMembersBalance.getRealBalance();
                    if (realBalance.compareTo(BigDecimal.ZERO) > 0) {
                        if (realBalance.compareTo(realCope) < 0) {
                            sysMembersBalance.setRealBalance(BigDecimal.ZERO);
                            sysMembersBalance.setGiveBalance(sysMembersBalance.getGiveBalance().subtract(realCope.subtract(realBalance)));
                        } else {
                            sysMembersBalance.setRealBalance(realBalance.subtract(realCope));
                        }
                    } else {
                        sysMembersBalance.setGiveBalance(sysMembersBalance.getGiveBalance().subtract(realCope));
                    }
                    sysMembersBalance.setBalance(sysMembersBalance.getRealBalance().add(sysMembersBalance.getGiveBalance()));
                } else {
                    BigDecimal payQuota = sysMembersBalance.getPayQuota();
                    BigDecimal _realCope = realCope.subtract(balance);
                    if (payQuota.compareTo(_realCope) < 0) {
                        throw CustomException.instance("额度不足");
                    }
                    BigDecimal curPayBalance = _realCope.multiply(new BigDecimal("-1"));
                    sysMembersBalance.setRealBalance(curPayBalance);
                    sysMembersBalance.setGiveBalance(BigDecimal.ZERO);
                    sysMembersBalance.setBalance(curPayBalance);
                }
            } else if (payType == OrderDefine.PAYTYPE_CREDIT) {
                BigDecimal allQuota = sysMembersBalance.getAllQuota();  // 信用额度
                BigDecimal oriSurplusQuota = sysMembersBalance.getSurplusQuota();  // 消费前剩余额度
                BigDecimal afterSurplusQuota = oriSurplusQuota.subtract(realCope); // 消费后剩余额度
                BigDecimal oriSpentQuota = sysMembersBalance.getSpentQuota();   // 消费前已用额度
                BigDecimal afterSpentQuota = oriSpentQuota.add(realCope); // 消费后已用额度

                if (oriSurplusQuota.compareTo(realCope) < 0) {
                    logger.error("会员信用额度不足，membersId=" + membersId);
                    return null;
                }
                sysMembersBalance.setSurplusQuota(afterSurplusQuota);
                sysMembersBalance.setSpentQuota(afterSpentQuota);

                if (sysMembersBalance.getMemberCredit() == 2) {
                    // 如果是附卡，处理主卡
                    Long shareMemberId = sysMembersBalance.getShareMemberId();
                    SysMembersBalance shareMembersBalance = sysMembersBalanceService.getByMemberId(shareMemberId);
                    if (shareMembersBalance == null) {
                        throw CustomException.instance("主卡不存在");
                    }
                    BigDecimal _oriSurplusQuota = shareMembersBalance.getSurplusQuota();  // 消费前剩余额度
                    BigDecimal _afterSurplusQuota = _oriSurplusQuota.subtract(realCope); // 消费后剩余额度
                    BigDecimal _oriSpentQuota = shareMembersBalance.getSpentQuota();   // 消费前已用额度
                    BigDecimal _afterSpentQuota = _oriSpentQuota.add(realCope); // 消费后已用额度
                    if (_oriSurplusQuota.compareTo(realCope) < 0) {
                        logger.error("会员信用额度不足，membersId=" + shareMemberId);
                        return null;
                    }

                    shareMembersBalance.setSurplusQuota(_afterSurplusQuota);
                    shareMembersBalance.setSpentQuota(_afterSpentQuota);
                    shareMembersBalance.setUpdateTime(curTime);
                    if (!sysMembersBalanceService.updateById(shareMembersBalance)) {
                        logger.error("更新会员账户失败：" + JsonUtils.toJson(shareMembersBalance));
                        return null;
                    }
                }

                // 记录到赊账表，便于后面的结账（月结）
                if (writeoffMemberId != null && writeoffMemberId != 0L) {
                    SysMembers creditMember = sysMembersService.getByMembers(writeoffMemberId);
                    if (creditMember == null) {
                        logger.error("销账会员不存在，writeoffMemberId=" + writeoffMemberId);
                        return null;
                    }
                    writeoffMemberName = creditMember.getMemberName();
                    writeoffMemberPhone = creditMember.getPhone();
                } else {
                    if (StringUtils.isBlank(writeoffMemberName) || StringUtils.isBlank(writeoffMemberPhone)) {
                        // 当作没传，全部设为挂账本人
                        writeoffMemberId = sysMembers.getMemberId();
                        writeoffMemberName = sysMembers.getMemberName();
                        writeoffMemberPhone = sysMembers.getPhone();
                    } else {
                        // 会员外人员，只记录名称与联系电话
                        writeoffMemberId = 0L;
                    }
                }
                SysMembersCredit sysMembersCredit = new SysMembersCredit(
                        IdUtils.getUnid(),
                        sysMembers.getMemberId(),
                        sysMembers.getMemberId(),
                        sysMembers.getMemberName(),
                        sysMembers.getPhone(),
                        writeoffMemberId,
                        writeoffMemberName,
                        writeoffMemberPhone,
                        realCope,
                        allQuota,
                        oriSurplusQuota,
                        afterSurplusQuota,
                        oriSpentQuota,
                        afterSpentQuota,
                        chargeRecordId,
                        orderPayId,
                        DateUtils.getNowDate(),
                        CommonDefine.CREDITTYPE_CREDIT,
                        operator,
                        operatorName,
                        payTime);
                if (!sysMembersCreditService.save(sysMembersCredit)) {
                    logger.error("保存挂账记录失败：" + JsonUtils.toJson(sysMembersCredit));
                    return null;
                }
            }
        }

        if (orderRcType == OrderDefine.ORDERRC_CHARGE) {
            // 充值
            BigDecimal yearChargeCope = sysMembersBalance.getYearChargeCope();
            yearChargeCope = yearChargeCope.add(realCope);
            sysMembersBalance.setYearChargeCope(yearChargeCope);
        } else if (orderRcType == OrderDefine.ORDERRC_CONSUME) {
            // 消费
            BigDecimal yearConsumeCope = sysMembersBalance.getYearConsumeCope();
            yearConsumeCope = yearConsumeCope.add(realCope);
            sysMembersBalance.setYearConsumeCope(yearConsumeCope);
        }
        sysMembersBalance.setUpdateTime(curTime);
        if (!sysMembersBalanceService.updateById(sysMembersBalance)) {
            logger.error("更新会员账户失败：" + JsonUtils.toJson(sysMembersBalance));
            return null;
        }

        // 消费记录
        BigDecimal curBalance = sysMembersBalance.getBalance();
        if (payType == OrderDefine.PAYTYPE_CREDIT) {
            curBalance = sysMembersBalance.getSurplusQuota();
        }
        SysMembersChargeRecord sysMembersChargeRecord = new SysMembersChargeRecord(chargeRecordId, orderPayId, sysShop.getShopName() + orderRecordName, shopId, membersType, membersId, membersName, orderRcType, payType, realCope, curBalance, String.valueOf(orderPayId), remark, orderSubmitEmpId, orderSubmitEmpName, payTime);
        if (!sysMembersChargeRecordService.save(sysMembersChargeRecord)) {
            logger.error("保存消费记录失败：" + JsonUtils.toJson(sysMembersChargeRecord));
            return null;
        }

        // 异步处理会员等级
        if (sysMembers.getMemberLev() == 1 && sysMembersBalance.getYearConsumeCope().compareTo(new BigDecimal("50000")) >= 0) {
            String redisKey = RedisConsts.getMembersKey(sysMembers.getMemberId());
            redisService.setDelayTime(redisKey, 10L, TimeUnit.SECONDS);
            logger.info("设置定时会员等级处理成功");
        }

        smsService.sendConsume(sysMembers.getPhone(), sysMembers.getMemberNo(), sysShop.getShopName(), payType, orderRcType, realCope, sysMembersBalance.getBalance(), listDetail);

        return sysOrderPay;
    }

    /**
     * 获取票剧信息 - 根据支付订单号
     */
    @Override
    public ShopConsumeInfo getConsumeInfoByOrderPayId(Long orderPayId) {
        SysOrderPay sysOrderPay = sysOrderPayService.getByPayId(orderPayId);
        if (sysOrderPay == null) {
            logger.error("支付单信息不存在,orderPayId=" + orderPayId);
            return null;
        }
        Integer orderType = sysOrderPay.getRcType();
        if (orderType == OrderDefine.ORDERRC_CHARGE) {
            return getRechargeInfoByOrderPayId(sysOrderPay);
        } else {
            return getConsumeInfoByOrderPayId(sysOrderPay);
        }
    }

    /**
     * 获取票剧信息 - 根据房间使用号
     */
    @Override
    public ShopConsumeInfo getConsumeInfoByRoomUseId(Long roomUseId) {
        SysRoomUse sysRoomUse = sysRoomUseService.getByRoomUserId(roomUseId);
        if (sysRoomUse == null) {
            logger.error("房间使用信息不存在,roomUseId=" + roomUseId );
            return null;
        }
        SysRoom sysRoom = sysRoomService.getByRoom(sysRoomUse.getRoomId());
        if (sysRoom == null) {
            logger.error("房间信息不存在,roomUseId=" + roomUseId);
            return null;
        }
        Long shopId = sysRoomUse.getShopId();

        SysShop sysShop = sysShopService.getByShop(shopId);
        if (sysShop == null) {
            logger.error("支付单信息不存在,roomUseId=" + roomUseId + ",shopId=" + shopId);
            return null;
        }
        String shopName = sysShop.getShopName();

        Long operator = sysRoomUse.getBillingId();
        String operatorName = sysRoomUse.getBillingName();

        List<SysOrder> listOrder = sysOrderService.listByRelevanceId(roomUseId);
        if (listOrder == null) {
            logger.error("没有订单信息,roomUseId=" + roomUseId);
            return null;
        }

        List<Long> listOrderId = listOrder.stream().map(SysOrder::getOrderId).collect(Collectors.toList());
        List<SysOrderGoodsDetails> listDetails = sysOrderGoodsDetailsService.listByOrderId(listOrderId);

//        caclOrder(listOrder, listDetails);

        Map<Long, OrderBo> mapOrder = new HashMap<>();
        List<OrderBo> listOrderBo = new ArrayList<>();

        BigDecimal goodsCope = BigDecimal.ZERO;
        BigDecimal voucherCope = BigDecimal.ZERO;
        BigDecimal discountCope = BigDecimal.ZERO;
        BigDecimal dealCope = BigDecimal.ZERO;
        BigDecimal realCope = BigDecimal.ZERO;

        BigDecimal roomCope = BigDecimal.ZERO;
        BigDecimal serviceCope = BigDecimal.ZERO;

        List<Long> listPayId = new ArrayList<>();
        for (SysOrder sysOrder : listOrder) {
            Long orderPayId = sysOrder.getOrderPayId();
            if (orderPayId != null && orderPayId != 0L && !listPayId.contains(orderPayId)) {
                listPayId.add(orderPayId);
            }
            Long orderId = sysOrder.getOrderId();
            Integer orderType = sysOrder.getOrderType();
            if (orderType == OrderDefine.ORDERTYPE_GOODS) {
                goodsCope = goodsCope.add(sysOrder.getRealCope());
            } else if (orderType == OrderDefine.ORDERTYPE_ROOM) {
                roomCope = roomCope.add(sysOrder.getRealCope());
            } else if (orderType == OrderDefine.ORDERTYPE_SERVICE) {
                serviceCope = serviceCope.add(sysOrder.getRealCope());
            }

            voucherCope = voucherCope.add(sysOrder.getVoucherCope());
            discountCope = discountCope.add(sysOrder.getDiscountCope());

            OrderBo orderBo = new OrderBo(
                    sysOrder.getOrderId(),
                    sysOrder.getOrderType(),
                    sysOrder.getSaleType(),
                    sysOrder.getPayType(),
                    sysOrder.getOrderPayId(),
                    sysOrder.getStatus(),
                    sysOrder.getDealCope(),
                    sysOrder.getVoucherCope(),
                    sysOrder.getDiscountCope(),
                    sysOrder.getRealCope(),
                    null,
                    sysOrder.getCustomType(),
                    sysOrder.getCustomId(),
                    sysOrder.getCustomName()
            );
            listOrderBo.add(orderBo);
            mapOrder.put(orderId, orderBo);

            if (operator == null || operator == 0L) {
                operator = sysOrder.getOperator();
                operatorName = sysOrder.getOperatorName();
            }
        }


        List<OrderGoodsDetailsBo> listDetailBo = new ArrayList<>();
        Map<Long, OrderGoodsDetailsBo> mapDetailBo = new HashMap();

//        List<Long> listOrderId = listOrder.stream().map(SysOrder::getOrderId).collect(Collectors.toList());
//        List<SysOrderGoodsDetails> listDetails = sysOrderGoodsDetailsService.listByOrderId(listOrderId);
        if (listDetails != null && listDetails.size() > 0) {
            List<Long> listGoodsId = listDetails.stream().map(SysOrderGoodsDetails::getInvGoodsId).collect(Collectors.toList());
            List<SysStoreGoods> listGoods = sysStoreGoodsService.listByGoodsId(listGoodsId);
            Map<Long, SysStoreGoods> mapGoods = new HashMap<>();
            if (listGoods != null && listGoods.size() > 0) {
                mapGoods = listGoods.stream().collect(Collectors.toMap(SysStoreGoods::getStoreGoodsId, d->d,(key1 , key2)-> key2));
            }
            for (SysOrderGoodsDetails sysOrderGoodsDetails : listDetails) {
                OrderBo orderBo = mapOrder.get(sysOrderGoodsDetails.getOrderId());
                if (orderBo == null) {
                    continue;
                }

                SysStoreGoods sysStoreGoods = mapGoods.get(sysOrderGoodsDetails.getInvGoodsId());
                if (sysStoreGoods == null) {
                    sysStoreGoods = new SysStoreGoods();
                }
                //  Long goodsSpecificationId, String unit, Integer quantity, Integer refundQuantity, BigDecimal salePrice, BigDecimal dealCope, Long goodsSpecificationId2, String unit2, Integer quantity2, Integer refundQuantity2, BigDecimal salePrice2, BigDecimal dealCope2
                OrderGoodsDetailsBo orderGoodsDetailsBo = new OrderGoodsDetailsBo(
                        sysOrderGoodsDetails.getOrderDetailId(),
                        sysOrderGoodsDetails.getInvGoodsId(),
                        sysOrderGoodsDetails.getGoodsName(),
                        sysOrderGoodsDetails.getGoodsLogo(),
                        sysOrderGoodsDetails.getGoodsKindName(),
                        sysOrderGoodsDetails.getGoodsSpecification(),
                        DateUtils.parseDateToStr(sysOrderGoodsDetails.getGoodsPublicDate()),
                        sysOrderGoodsDetails.getGoodsBatch(),
                        sysOrderGoodsDetails.getGoodsSpecificationId(),
                        sysOrderGoodsDetails.getGoodsUnitName(),
                        sysOrderGoodsDetails.getQuantity(),
                        sysOrderGoodsDetails.getRefundQuantity(),
                        sysOrderGoodsDetails.getGoodsSalePrice(),
                        sysOrderGoodsDetails.getDealCope(),
                        sysOrderGoodsDetails.getGoodsSpecificationId2(),
                        sysOrderGoodsDetails.getGoodsUnitName2(),
                        sysOrderGoodsDetails.getQuantity2(),
                        sysOrderGoodsDetails.getRefundQuantity2(),
                        sysOrderGoodsDetails.getGoodsSalePrice2(),
                        sysOrderGoodsDetails.getDealCope2());

                orderBo.addDetail(orderGoodsDetailsBo);

                OrderGoodsDetailsBo old = mapDetailBo.get(orderGoodsDetailsBo.getInvGoodsId());
                if (old == null) {
                    old = orderGoodsDetailsBo;
                    mapDetailBo.put(old.getInvGoodsId(), old);
                    listDetailBo.add(old);
                } else {
                    old.setQuantity(old.getQuantity() + orderGoodsDetailsBo.getQuantity());
                    old.setRefundQuantity(old.getRefundQuantity() + orderGoodsDetailsBo.getRefundQuantity());
                }
            }
        }

        List<WaterInfoBo> listWater = new ArrayList<>();
        if (listPayId != null && listPayId.size() > 0) {
            List<SysOrderPay> listPay = sysOrderPayService.listByPayId(listPayId);
            if (listPay != null && listPay.size() > 0) {
                for (SysOrderPay sysOrderPay : listPay) {
                    dealCope = dealCope.add(sysOrderPay.getDealCope());
                    realCope = realCope.add(sysOrderPay.getRealCope());
                    listWater.add(new WaterInfoBo(OrderDefine.getPayName(sysOrderPay.getPayType()), sysOrderPay.getRealCope(), String.valueOf(sysOrderPay.getPayId())));
                }
            }
        }

        String openTime = DateUtils.parseDateToStr(sysRoomUse.getStartTime());
        ShopConsumeRoomInfo roomInfo = new ShopConsumeRoomInfo(
                sysRoomUse.getRoomUserId(),
                sysRoom.getRoomId(),
                sysRoom.getRoomName(),
                sysRoomUse.getShowTime(),
                RoomDefine.getTimeRuleName(sysRoomUse.getTimeRule()),
                sysRoomUse.getCustomNumber(),
                roomCope,
                serviceCope);

        voucherCope = voucherCope.add(discountCope);
        return new ShopConsumeInfo(
                roomUseId,
                String.valueOf(roomUseId),
                OrderDefine.ORDERSALE_SHIP,
                OrderDefine.getSaleTypeName(OrderDefine.ORDERSALE_SHIP),
                shopId,
                shopName,
                openTime,
                roomInfo,
                listOrderBo,
                listDetailBo,
    null,
                voucherCope,
                dealCope,
                realCope,
                listWater,
                operator,
                operatorName);
    }

    private void caclOrder(List<SysOrder> listOrder, List<SysOrderGoodsDetails> listDetails) {
        if (listOrder == null || listOrder.size() == 0 || listDetails == null || listDetails.size() == 0) {
            return;
        }
        for (SysOrder sysOrder : listOrder) {
            if (sysOrder.getOrderType() != OrderDefine.ORDERTYPE_GOODS) {
                continue;
            }
            Long orderId = sysOrder.getOrderId();

            List<SysOrderGoodsDetails> _list = new ArrayList<>();
            for (SysOrderGoodsDetails sysOrderGoodsDetails : listDetails) {
                if (orderId.equals(sysOrderGoodsDetails.getOrderId())) {
                    _list.add(sysOrderGoodsDetails);
                }
            }
            if (_list.size() == 0) {
                continue;
            }

            Long memberId = sysOrder.getCustomId();
            if (memberId == null || memberId == 0L) {
                continue;
            }
            SysMembers sysMembers = sysMembersService.getByMembers(memberId);
            SysMembersBalance sysMembersBalance = sysMembersBalanceService.getByMemberId(memberId);
            if (sysMembers == null || sysMembersBalance == null) {
                continue;
            }
            DiscountBo discountBo = sysGoodsDiscountService.getDiscountSalePrice(sysMembers.getMemberId(), sysMembers.getMemberType(), sysMembersBalance.getMemberDiscount(), sysMembersBalance.getMemberWhiteDiscount(), sysMembersBalance.getMemberYdfDiscount(), _list);
            if (discountBo != null) {
                sysOrder.setDealCope(discountBo.getDiscountCope());
                sysOrder.setVoucherCope(discountBo.getDiscount());
//                sysOrder.setDiscountCope(BigDecimal.ZERO);
                sysOrder.setRealCope(discountBo.getDiscountCope().subtract(sysOrder.getDiscountCope()));

                Map<Long, OrderDiscountBo> _map = discountBo.getmapOrderDiscount();
                for (SysOrderGoodsDetails sysOrderGoodsDetails : _list) {
                    OrderDiscountBo orderDiscountBo = _map.get(sysOrderGoodsDetails.getOrderDetailId());
                    if (orderDiscountBo != null) {
                        sysOrderGoodsDetails.setDealCope(orderDiscountBo.getDiscountDealCope());
                        sysOrderGoodsDetails.setDealCope2(orderDiscountBo.getDiscountDealCope2());
                    }
                }
            }

        }
    }

    @Autowired
    private SysGoodsDiscountService sysGoodsDiscountService;

    private ShopConsumeInfo getConsumeInfoByOrderPayId(SysOrderPay sysOrderPay) {
        Long orderPayId = sysOrderPay.getPayId();
        Long shopId = sysOrderPay.getShopId();
        String openTime = DateUtils.parseDateToStr(sysOrderPay.getPayTime());

        SysShop sysShop = sysShopService.getByShop(shopId);
        if (sysShop == null) {
            logger.error("支付单信息不存在,orderPayId=" + orderPayId + ",shopId=" + shopId);
            return null;
        }
        String shopName = sysShop.getShopName();


        List<SysOrder> listOrder = sysOrderService.listByOrderPayId(orderPayId);
        if (listOrder == null) {
            logger.error("没有订单信息,orderPayId=" + orderPayId);
            return null;
        }

        Map<Long, OrderBo> mapOrder = new HashMap<>();
        List<OrderBo> listOrderBo = new ArrayList<>();

        BigDecimal goodsCope = BigDecimal.ZERO;
        BigDecimal voucherCope = BigDecimal.ZERO;
        BigDecimal discountCope = BigDecimal.ZERO;
        BigDecimal dealCope = sysOrderPay.getDealCope();
        BigDecimal realCope = sysOrderPay.getRealCope();

        BigDecimal roomCope = BigDecimal.ZERO;
        BigDecimal serviceCope = BigDecimal.ZERO;

        Long operator = 0L;
        String operatorName = "";
        Long roomUseId = null;
        for (SysOrder sysOrder : listOrder) {
            Long orderId = sysOrder.getOrderId();
            Integer saleType = sysOrder.getSaleType();
            if (saleType != null && saleType == OrderDefine.ORDERSALE_SHIP) {
                roomUseId = sysOrder.getRelevanceId();
            }
            Integer orderType = sysOrder.getOrderType();
            if (orderType == OrderDefine.ORDERTYPE_GOODS) {
                goodsCope = goodsCope.add(sysOrder.getRealCope());
            } else if (orderType == OrderDefine.ORDERTYPE_ROOM) {
                roomCope = roomCope.add(sysOrder.getRealCope());
            } else if (orderType == OrderDefine.ORDERTYPE_SERVICE) {
                serviceCope = serviceCope.add(sysOrder.getRealCope());
            }

            voucherCope = voucherCope.add(sysOrder.getVoucherCope());
            discountCope = discountCope.add(sysOrder.getDiscountCope());

            OrderBo orderBo = new OrderBo(
                    sysOrder.getOrderId(),
                    sysOrder.getOrderType(),
                    sysOrder.getSaleType(),
                    sysOrder.getPayType(),
                    sysOrder.getOrderPayId(),
                    sysOrder.getStatus(),
                    sysOrder.getDealCope(),
                    sysOrder.getVoucherCope(),
                    sysOrder.getDiscountCope(),
                    sysOrder.getRealCope(),
                    null,
                    sysOrder.getCustomType(),
                    sysOrder.getCustomId(),
                    sysOrder.getCustomName()
            );
            listOrderBo.add(orderBo);
            mapOrder.put(orderId, orderBo);

            if (operator == null || operator == 0L) {
                operator = sysOrder.getOperator();
                operatorName = sysOrder.getOperatorName();
            }
        }


        List<OrderGoodsDetailsBo> listDetailBo = new ArrayList<>();
        Map<Long, OrderGoodsDetailsBo> mapDetailBo = new HashMap();

        List<Long> listOrderId = listOrder.stream().map(SysOrder::getOrderId).collect(Collectors.toList());
        List<SysOrderGoodsDetails> listDetails = sysOrderGoodsDetailsService.listByOrderId(listOrderId);
        if (listDetails != null && listDetails.size() > 0) {
            List<Long> listGoodsId = listDetails.stream().map(SysOrderGoodsDetails::getInvGoodsId).collect(Collectors.toList());
            List<SysStoreGoods> listGoods = sysStoreGoodsService.listByGoodsId(listGoodsId);
            Map<Long, SysStoreGoods> mapGoods = new HashMap<>();
            if (listGoods != null && listGoods.size() > 0) {
                mapGoods = listGoods.stream().collect(Collectors.toMap(SysStoreGoods::getStoreGoodsId, d->d,(key1 , key2)-> key2));
            }
            for (SysOrderGoodsDetails sysOrderGoodsDetails : listDetails) {
                OrderBo orderBo = mapOrder.get(sysOrderGoodsDetails.getOrderId());
                if (orderBo == null) {
                    continue;
                }

                SysStoreGoods sysStoreGoods = mapGoods.get(sysOrderGoodsDetails.getInvGoodsId());
                if (sysStoreGoods == null) {
                    sysStoreGoods = new SysStoreGoods();
                }
                OrderGoodsDetailsBo orderGoodsDetailsBo = new OrderGoodsDetailsBo(
                        sysOrderGoodsDetails.getOrderDetailId(),
                        sysOrderGoodsDetails.getInvGoodsId(),
                        sysOrderGoodsDetails.getGoodsName(),
                        sysOrderGoodsDetails.getGoodsLogo(),
                        sysOrderGoodsDetails.getGoodsKindName(),
                        sysOrderGoodsDetails.getGoodsSpecification(),
                        DateUtils.parseDateToStr(sysOrderGoodsDetails.getGoodsPublicDate()),
                        sysOrderGoodsDetails.getGoodsBatch(),
                        sysOrderGoodsDetails.getGoodsSpecificationId(),
                        sysOrderGoodsDetails.getGoodsUnitName(),
                        sysOrderGoodsDetails.getQuantity(),
                        sysOrderGoodsDetails.getRefundQuantity(),
                        sysOrderGoodsDetails.getGoodsSalePrice(),
                        sysOrderGoodsDetails.getDealCope(),
                        sysOrderGoodsDetails.getGoodsSpecificationId2(),
                        sysOrderGoodsDetails.getGoodsUnitName2(),
                        sysOrderGoodsDetails.getQuantity2(),
                        sysOrderGoodsDetails.getRefundQuantity2(),
                        sysOrderGoodsDetails.getGoodsSalePrice2(),
                        sysOrderGoodsDetails.getDealCope2()
//                        0L,
//                        sysOrderGoodsDetails.getInvGoodsId(),
//                        sysStoreGoods.getGoodsName(),
//                        sysStoreGoods.getGoodsUnitName(),
//                        sysOrderGoodsDetails.getQuantity(),
//                        sysStoreGoods.getGoodsLogo(),
//                        "",
//                        sysStoreGoods.getGoodsSpecification(),
//                        DateUtils.parseDateToStr(sysStoreGoods.getGoodsPublicDate()),
//                        sysStoreGoods.getGoodsBatch(),
//                        sysOrderGoodsDetails.getRefundQuantity(),
//                        sysOrderGoodsDetails.getSalePrice(),
//                        sysOrderGoodsDetails.getDealCope()
                );

                orderBo.addDetail(orderGoodsDetailsBo);

                OrderGoodsDetailsBo old = mapDetailBo.get(orderGoodsDetailsBo.getInvGoodsId());
                if (old == null) {
                    old = orderGoodsDetailsBo;
                    mapDetailBo.put(old.getInvGoodsId(), old);
                    listDetailBo.add(old);
                } else {
                    old.setQuantity(old.getQuantity() + orderGoodsDetailsBo.getQuantity());
                    old.setRefundQuantity(old.getRefundQuantity() + orderGoodsDetailsBo.getRefundQuantity());
                }
            }
        }

        ShopConsumeRoomInfo roomInfo = null;
        if (roomUseId != null && roomUseId != 0L) {
            SysRoomUse sysRoomUse = sysRoomUseService.getByRoomUserId(roomUseId);
            if (sysRoomUse != null) {
                SysRoom sysRoom = sysRoomService.getByRoom(sysRoomUse.getRoomId());
                if (sysRoom != null) {
                    openTime = DateUtils.parseDateToStr(sysRoomUse.getStartTime());
                    roomInfo = new ShopConsumeRoomInfo(
                            sysRoomUse.getRoomUserId(),
                            sysRoom.getRoomId(),
                            sysRoom.getRoomName(),
//                            DateUtils.parseDateToStr(sysRoomUse.getStartTime()),
                            sysRoomUse.getShowTime(),
                            RoomDefine.getTimeRuleName(sysRoomUse.getTimeRule()),
                            sysRoomUse.getCustomNumber(),
                            roomCope,
                            serviceCope);

                    operator = sysRoomUse.getBillingId();
                    operatorName = sysRoomUse.getBillingName();
                }
            }
        }
        List<WaterInfoBo> listWater = new ArrayList<>();
        listWater.add(new WaterInfoBo(OrderDefine.getPayName(sysOrderPay.getPayType()), sysOrderPay.getRealCope(), String.valueOf(sysOrderPay.getPayId())));

        voucherCope = voucherCope.add(discountCope);
        return new ShopConsumeInfo(
                orderPayId,
                String.valueOf(orderPayId),
                OrderDefine.ORDERSALE_SHIP,
                OrderDefine.getSaleTypeName(OrderDefine.ORDERSALE_SHIP),
                shopId,
                shopName,
                openTime,
                roomInfo,
                listOrderBo,
                listDetailBo,
                null,
//                goodsCope,
                voucherCope,
//                discountCope,
                dealCope,
                realCope,
                listWater,
                operator,
                operatorName);
    }

    private ShopConsumeInfo getRechargeInfoByOrderPayId(SysOrderPay sysOrderPay) {
        Long orderPayId = sysOrderPay.getPayId();
        Long shopId = sysOrderPay.getShopId();
        String openTime = DateUtils.parseDateToStr(sysOrderPay.getPayTime());

        SysShop sysShop = sysShopService.getByShop(shopId);
        if (sysShop == null) {
            logger.error("支付单信息不存在,orderPayId=" + orderPayId + ",shopId=" + shopId);
            return null;
        }
        String shopName = sysShop.getShopName();

        BigDecimal voucherCope = BigDecimal.ZERO;
        BigDecimal discountCope = BigDecimal.ZERO;
        BigDecimal dealCope = sysOrderPay.getDealCope();
        BigDecimal realCope = sysOrderPay.getRealCope();

        Long operator = 0L;
        String operatorName = "";

        ShopRechargeInfo rechargeInfo = new ShopRechargeInfo();
        SysMembersChargeRecord sysMembersChargeRecord = sysMembersChargeRecordService.getByOrderPayId(orderPayId);
        if (sysMembersChargeRecord != null) {
            Long customId = sysMembersChargeRecord.getMemberId();
            SysMembers sysMembers = sysMembersService.getByMembers(customId);
            if (sysMembers != null) {
                rechargeInfo = new ShopRechargeInfo(sysMembers.getMemberId(), sysMembers.getMemberNo(), sysMembers.getMemberName(), sysMembers.getPhone(), sysMembersChargeRecord.getAmount(), sysMembersChargeRecord.getBalance(), sysMembersChargeRecord.getRemark());
                operator = sysMembersChargeRecord.getOperator();
                operatorName = sysMembersChargeRecord.getOperatorName();
            }
        }

        List<WaterInfoBo> listWater = new ArrayList<>();
        listWater.add(new WaterInfoBo(OrderDefine.getPayName(sysOrderPay.getPayType()), sysOrderPay.getRealCope(), String.valueOf(sysOrderPay.getPayId())));

        voucherCope = voucherCope.add(discountCope);
        return new ShopConsumeInfo(
                orderPayId,
                String.valueOf(orderPayId),
                OrderDefine.ORDERRC_CHARGE,
                "会员充值"/*OrderDefine.getSaleTypeName(OrderDefine.ORDERSALE_SHIP)*/,
                shopId,
                shopName,
                openTime,
                null,//roomInfo,
                null,//listOrderBo,
                null,//listDetailBo,
                rechargeInfo,
                voucherCope,
//                discountCope,
                dealCope,
                realCope,
                listWater,
                operator,
                operatorName);
    }
}
