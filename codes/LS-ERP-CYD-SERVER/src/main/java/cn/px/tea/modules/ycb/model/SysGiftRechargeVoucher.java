package cn.px.tea.modules.ycb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 会员充值优惠表
 */
@TableName(value ="sys_gift_recharge_voucher")
public class SysGiftRechargeVoucher {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 优惠ID
     */
    private Long voucherId;

    /**
     * 优惠名称
     */
    private String voucherName;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 档次
     */
    private Integer level;

    /**
     * 赠送金额
     */
    private BigDecimal giveCope;

    /**
     * 最小金额
     */
    private BigDecimal minCope;

    /**
     * 最大金额
     */
    private BigDecimal maxCope;

    /**
     * 礼品模板ID
     */
    private Long giftModelId;

    /**
     * 状态 0-禁用 1-正常
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public BigDecimal getGiveCope() {
        return giveCope;
    }

    public void setGiveCope(BigDecimal giveCope) {
        this.giveCope = giveCope;
    }

    public BigDecimal getMinCope() {
        return minCope;
    }

    public void setMinCope(BigDecimal minCope) {
        this.minCope = minCope;
    }

    public BigDecimal getMaxCope() {
        return maxCope;
    }

    public void setMaxCope(BigDecimal maxCope) {
        this.maxCope = maxCope;
    }

    public Long getGiftModelId() {
        return giftModelId;
    }

    public void setGiftModelId(Long giftModelId) {
        this.giftModelId = giftModelId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
