package cn.px.tea.modules.ycb.pojo.rf.com;

import javax.validation.constraints.NotNull;

public class OrderPayRf {

    /**
     * 订单ID
     */
    @NotNull(message = "结算单ID不能为空")
    private Long orderId;

    /**
     * 支付方式 1-现金 2-微信 3-支付宝 4-会员支付 5-赊账
     */
    @NotNull(message = "支付方式不能为空")
    private Integer payType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 销账会员ID
     */
    private Long writeoffMemberId;

    /**
     * 销账会员名称
     */
    private String writeoffMemberName;

    /**
     * 销账会员手机号
     */
    private String writeoffMemberPhone;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getWriteoffMemberId() {
        return writeoffMemberId;
    }

    public void setWriteoffMemberId(Long writeoffMemberId) {
        this.writeoffMemberId = writeoffMemberId;
    }

    public String getWriteoffMemberName() {
        return writeoffMemberName;
    }

    public void setWriteoffMemberName(String writeoffMemberName) {
        this.writeoffMemberName = writeoffMemberName;
    }

    public String getWriteoffMemberPhone() {
        return writeoffMemberPhone;
    }

    public void setWriteoffMemberPhone(String writeoffMemberPhone) {
        this.writeoffMemberPhone = writeoffMemberPhone;
    }
}
