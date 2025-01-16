package cn.px.tea.modules.ycb.controller.business;

import cn.px.tea.common.domain.AjaxResult;
import cn.px.tea.common.domain.TableDataInfo;
import cn.px.tea.modules.ycb.pojo.bo.businessstatistics.MembersRechargeBo;
import cn.px.tea.modules.ycb.pojo.rf.business.*;
import cn.px.tea.modules.ycb.pojo.rf.business.*;
import cn.px.tea.modules.ycb.pojo.rf.members.MemberOrderPayRf;
import cn.px.tea.modules.ycb.pojo.rf.members.MemberRechargeRf;
import cn.px.tea.modules.ycb.pojo.rf.room.RoomRf;
import cn.px.tea.modules.ycb.pojo.rf.store.PageOrderDetailConditionRf;
import cn.px.tea.modules.ycb.pojo.vo.business.OrderInfoVo;
import cn.px.tea.modules.ycb.pojo.vo.business.OrderPayInfoVo;
import cn.px.tea.modules.ycb.pojo.vo.business.OrderRoomVo;
import cn.px.tea.modules.ycb.pojo.vo.business.OrderVo;
import cn.px.tea.modules.ycb.model.SysOrder;
import cn.px.tea.modules.ycb.model.SysOrderGoodsDetails;
import cn.px.tea.modules.ycb.model.SysOrderPay;
import cn.px.tea.modules.ycb.pojo.bo.order.OrderDetail;
import cn.px.tea.modules.ycb.pojo.bo.order.OrderPay;
import cn.px.tea.modules.ycb.pojo.rf.store.ListOrderPayRf;
import cn.px.tea.modules.ycb.pojo.rf.store.PageOrderConditionRf;
import cn.px.tea.modules.ycb.pojo.rf.com.PrimaryRf;
import cn.px.tea.modules.ycb.pojo.vo.config.GiftRechargeVoucherVo;
import cn.px.tea.modules.ycb.service.base.inter.GiftService;
import cn.px.tea.modules.ycb.service.order.inter.OrderService;
import cn.px.tea.security.aspect.Log;
import cn.px.tea.security.define.SystemDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销售-订单
 */
@RestController
@RequestMapping("/web/order")
public class WebOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GiftService giftService;

    /**
     * 订单列表
     * @param rf
     */
    @GetMapping("listOrder")
    @Log(modelTitle = "销售-订单-订单列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public TableDataInfo<SysOrder> listOrder(@Validated PageOrderConditionRf rf) {
        return orderService.listOrder(rf.getPageNum(), rf.getPageSize(), rf.getOrderId(), rf.getRcType(), rf.getOrderType(), rf.getSaleType(), rf.getRoomType(), rf.getRoomId(), rf.getCustomId(), rf.getCustomName(), rf.getStatus(), rf.getStartDate(), rf.getEndDate());
    }

    /**
     * 订单详情
     * @param rf
     */
    @GetMapping("getOrderInfo")
    @Log(modelTitle = "销售-订单-订单详情" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<OrderRoomVo> getOrderInfo(@Validated PrimaryRf rf) {
        return AjaxResult.success(orderService.getOrderInfo(rf.getPrimaryId()));
    }

    /**
     * 订单明细列表
     * @param rf
     */
    @GetMapping("listOrderGoodsDetails")
    @Log(modelTitle = "销售-订单-订单明细列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<SysOrderGoodsDetails>> listOrderGoodsDetails(@Validated PrimaryRf rf) {
        return AjaxResult.success(orderService.listOrderGoodsDetails(rf.getPrimaryId()));
    }

    /**
     * 订单明细分页
     * @param rf
     */
    @GetMapping("pageOrderGoodsDetails")
    @Log(modelTitle = "销售-订单-订单明细分页" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public TableDataInfo<SysOrderGoodsDetails> pageOrderGoodsDetails(@Validated PageOrderDetailConditionRf rf) {
        return orderService.pageOrderGoodsDetails(rf.getPageNum(), rf.getPageSize(), rf.getOrderId(), rf.getSaleType(), rf.getGoodsId(), rf.getGoodsName(), rf.getCustomId(), rf.getCustomName(), rf.getStatus(), rf.getStartDate(), rf.getEndDate());
    }

    /**
     * 订单明细详情
     * @param rf
     */
    @GetMapping("getOrderGoodsDetailsInfo")
    @Log(modelTitle = "销售-订单-订单明细详情" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<SysOrderGoodsDetails> getOrderGoodsDetailsInfo(@Validated PrimaryRf rf) {
        return AjaxResult.success(orderService.getOrderGoodsDetailsInfo(rf.getPrimaryId()));
    }

    /**
     * 会员充值详情（根据订单号）
     * @param rf
     */
    @GetMapping("getOrderRechargeInfo")
    @Log(modelTitle = "销售-订单-会员充值详情（根据订单号）" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<MembersRechargeBo> getOrderRechargeInfo(@Validated OrderRf rf) {
        return AjaxResult.success(orderService.getOrderRechargeInfo(rf.getOrderId()));
    }

    /**
     * 会员充值详情（根据支付单号）
     * @param rf
     */
    @GetMapping("getOrderRechargeInfoByPayId")
    @Log(modelTitle = "销售-订单-会员充值详情（根据支付单号）" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<MembersRechargeBo> getOrderRechargeInfoByPayId(@Validated OrderRf rf) {
        return AjaxResult.success(orderService.getOrderRechargeInfoByPayId(rf.getOrderId()));
    }

    /**
     * 订单详情(合并明细）
     * @param rf
     */
    @GetMapping("getOrderDetailInfo")
    @Log(modelTitle = "销售-销售-营业-订单详情(合并明细）" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<OrderDetail> getOrderDetailInfo(@Validated PrimaryRf rf) {
        return AjaxResult.success(orderService.getOrderDetailInfo(rf.getPrimaryId()));
    }

    /**
     * 支付单列表
     * @param rf
     */
    @GetMapping("listOrderPay")
    @Log(modelTitle = "销售-订单-支付单列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public TableDataInfo<SysOrderPay> listOrderPay(@Validated ListOrderPayRf rf) {
        return orderService.listOrderPay(rf.getPageNum(), rf.getPageSize(), rf.getPayId(), rf.getRcType(), rf.getPayType(), rf.getStatus(), rf.getStartDate(), rf.getEndDate());
    }

    /**
     * 支付单详情
     * @param rf
     */
    @GetMapping("getOrderPayInfo")
    @Log(modelTitle = "销售-订单-支付单详情" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<SysOrderPay> getOrderPayInfo(@Validated PrimaryRf rf) {
        return AjaxResult.success(orderService.getOrderPayInfo(rf.getPrimaryId()));
    }

    /**
     * 根据支付单查询订单列表
     * @param rf
     */
    @GetMapping("listOrderByPayId")
    @Log(modelTitle = "销售-订单-根据支付单查询订单列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<SysOrder>> listOrderByPayId(@Validated PrimaryRf rf) {
        return AjaxResult.success(orderService.listOrderByPayId(rf.getPrimaryId()));
    }

    /**
     * 根据支付单查询订单明细列表
     * @param rf
     */
    @GetMapping("listOrderDetailByPayId")
    @Log(modelTitle = "销售-订单-根据支付单查询订单列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<SysOrderGoodsDetails>> listOrderDetailByPayId(@Validated PrimaryRf rf) {
        return AjaxResult.success(orderService.listOrderDetailByPayId(rf.getPrimaryId()));
    }



    /**
     * 根据房间号查询订单列表
     * @param rf
     */
    @GetMapping("listOrderByRoom")
    @Log(modelTitle = "销售-销售-营业-根据房间号查询订单列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<OrderDetail>> listOrderByRoom(@Validated RoomRf rf) {
        return AjaxResult.success(orderService.listOrderByRoom(rf.getRoomId()));
    }

    /**
     * 根据房间号查询支付单列表
     * @param rf
     */
    @GetMapping("listOrderPayByRoom")
    @Log(modelTitle = "销售-销售-营业-根据房间号查询支付单列表" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<OrderPay>> listOrderPayByRoom(@Validated RoomRf rf) {
        return AjaxResult.success(orderService.listOrderPayByRoom(rf.getRoomId()));
    }


    /**
     * 会员充值优惠信息
     */
    @GetMapping("getRechargeVoucher")
    @Log(modelTitle = "销售-销售-营业-会员充值优惠信息" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<GiftRechargeVoucherVo>> getRechargeVoucher() {
        return AjaxResult.success(giftService.listRechargeVoucherVo());
    }

    /**
     * 会员充值（提交订单）
     * @param rf
     */
    @PostMapping("/membersRecharge")
    @Log(modelTitle = "销售-销售-营业-会员充值（提交订单）" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysOrder> membersRecharge(@Validated @RequestBody MemberRechargeRf rf) {
        return AjaxResult.success(orderService.membersRecharge(rf.getMemberId(), rf.getEmpId(), rf.getAmount(), rf.getExtraAmount(), rf.getGiveAwayTag(), rf.getRemark(), rf.getListGoods(), rf.getSubmitTime()));
    }

    /**
     * 会员充值（付款）
     * @param rf
     */
    @PostMapping("/membersPay")
    @Log(modelTitle = "销售-销售-营业-会员充值（付款）" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysOrderPay> membersPay(@Validated @RequestBody MemberOrderPayRf rf) {
        return AjaxResult.success(orderService.membersPay(rf.getOrderId(), rf.getPayType(), rf.getRemark()));
    }










    /**
     * 商品点单提交订单
     * @param rf
     */
    @PostMapping("submitOrder")
    @Log(modelTitle = "销售-营业-商品点单提交订单" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<OrderVo> submitOrder(@Validated @RequestBody SubmitOrderRf rf) {
        return AjaxResult.success(orderService.submitOrder(rf.getRoomId(), rf.getVoucherId(), rf.getMembersId(), rf.getMembersName(), rf.getMembersPhone(), rf.getDiscountCope(), rf.getListSale(), rf.getRemark(), rf.getSubmitTime()));
    }

    /**
     * 订单修改(修改优惠券使用、会员）
     * @param rf
     */
    @PostMapping("editOrder")
    @Log(modelTitle = "销售-营业-订单修改(修改优惠券使用、会员）" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysOrder> editOrder(@Validated @RequestBody EditOrderRf rf) {
        return AjaxResult.success(orderService.editOrder(rf.getOrderId(), rf.getMembersId(), rf.getVoucherId(), rf.getDiscountCope(), rf.getRemark()));
    }

    /**
     * 整单取消
     * @param rf
     */
    @PostMapping("cancleOrder")
    @Log(modelTitle = "销售-营业-整单取消" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysOrder> cancleOrder(@Validated @RequestBody OrderRf rf) {
        return AjaxResult.success(orderService.cancleOrder(rf.getOrderId()));
    }

    /**
     * 整单退单
     * @param rf
     */
    @PostMapping("refundOrderComplete")
    @Log(modelTitle = "销售-营业-整单退单" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult<SysOrderPay> refundOrderComplete(@Validated @RequestBody OrderPayIdRf rf) {
        return AjaxResult.success(orderService.refundOrderComplete(rf.getOrderPayId()));
    }

    /**
     * 订单列表（当前）
     * @param rf
     */
    @GetMapping("listOrderVo")
    @Log(modelTitle = "销售-营业-订单列表（当前）" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<OrderInfoVo>> listOrderVo(@Validated ListOrderRf rf) {
        return AjaxResult.success(orderService.listOrderVo(rf.getRoomId(), rf.getOrderType()));
    }

    /**
     * 订单详情
     * @param rf
     */
    @GetMapping("getOrder")
    @Log(modelTitle = "销售-营业-订单详情" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<OrderInfoVo> getOrder(@Validated OrderRf rf) {
        return AjaxResult.success(orderService.getOrder(rf.getOrderId()));
    }

    /**
     * 订单列表（按支付单归纳）
     * @param rf
     */
    @GetMapping("listOrderForPay")
    @Log(modelTitle = "销售-营业-订单列表（按支付单归纳）" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<List<OrderPayInfoVo>> listOrderForPay(@Validated RoomStatusRf rf) {
        return AjaxResult.success(orderService.listOrderForPay(rf.getRoomId(), rf.getStatus()));
    }

    /**
     * 支付单详情
     * @param rf
     */
    @GetMapping("getOrderForPay")
    @Log(modelTitle = "销售-营业-支付单详情" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<OrderPayInfoVo> getOrderForPay(@Validated OrderPayIdRf rf) {
        return AjaxResult.success(orderService.getOrderForPay(rf.getOrderPayId()));
    }
}
