package cn.px.tea.modules.ycb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 赊账日志表
 */
@TableName(value ="sys_members_credit_log")
public class SysMembersCreditLog {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 赊账日志ID
     */
    private Long creditLogId;

    /**
     * 门店ID
     */
    private Long shopId;

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 会员手机号
     */
    private String memberPhone;

    /**
     * 类型 1-调整额度 2-清账
     */
    private Integer type;

    /**
     * 操作金额
     */
    private BigDecimal cope;

    /**
     * 赠送金额
     */
    private BigDecimal giveawayCope;

    /**
     * 原信用额度
     */
    private BigDecimal oriAllQuota;

    /**
     * 调整后信用额度
     */
    private BigDecimal afterAllQuota;

    /**
     * 原剩余额度
     */
    private BigDecimal oriSurplusQuota;

    /**
     * 调整后剩余额度
     */
    private BigDecimal afterSurplusQuota;

    /**
     * 原已用额度
     */
    private BigDecimal oriSpentQuota;

    /**
     * 调整后已用额度
     */
    private BigDecimal afterSpentQuota;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作员
     */
    private Long operator;

    /**
     * 操作员名称
     */
    private String operatorName;

    /**
     * 调整日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date adjustDate;

    /**
     * 调整时间
     */
    private Timestamp adjustTime;

    public SysMembersCreditLog() {
    }

    public SysMembersCreditLog(Long creditLogId, Long shopId, Long memberId, String memberName, String memberPhone, Integer type, BigDecimal cope, BigDecimal giveawayCope, BigDecimal oriAllQuota, BigDecimal afterAllQuota, BigDecimal oriSurplusQuota, BigDecimal afterSurplusQuota, BigDecimal oriSpentQuota, BigDecimal afterSpentQuota, String remark, Long operator, String operatorName, Date adjustDate, Timestamp adjustTime) {
        this.id = 0L;
        this.creditLogId = creditLogId;
        this.shopId = shopId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberPhone = memberPhone;
        this.type = type;
        this.cope = cope;
        this.giveawayCope = giveawayCope;
        this.oriAllQuota = oriAllQuota;
        this.afterAllQuota = afterAllQuota;
        this.oriSurplusQuota = oriSurplusQuota;
        this.afterSurplusQuota = afterSurplusQuota;
        this.oriSpentQuota = oriSpentQuota;
        this.afterSpentQuota = afterSpentQuota;
        this.remark = remark;
        this.operator = operator;
        this.operatorName = operatorName;
        this.adjustDate = adjustDate;
        this.adjustTime = adjustTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreditLogId() {
        return creditLogId;
    }

    public void setCreditLogId(Long creditLogId) {
        this.creditLogId = creditLogId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getCope() {
        return cope;
    }

    public void setCope(BigDecimal cope) {
        this.cope = cope;
    }

    public BigDecimal getGiveawayCope() {
        return giveawayCope;
    }

    public void setGiveawayCope(BigDecimal giveawayCope) {
        this.giveawayCope = giveawayCope;
    }

    public BigDecimal getOriAllQuota() {
        return oriAllQuota;
    }

    public void setOriAllQuota(BigDecimal oriAllQuota) {
        this.oriAllQuota = oriAllQuota;
    }

    public BigDecimal getAfterAllQuota() {
        return afterAllQuota;
    }

    public void setAfterAllQuota(BigDecimal afterAllQuota) {
        this.afterAllQuota = afterAllQuota;
    }

    public BigDecimal getOriSurplusQuota() {
        return oriSurplusQuota;
    }

    public void setOriSurplusQuota(BigDecimal oriSurplusQuota) {
        this.oriSurplusQuota = oriSurplusQuota;
    }

    public BigDecimal getAfterSurplusQuota() {
        return afterSurplusQuota;
    }

    public void setAfterSurplusQuota(BigDecimal afterSurplusQuota) {
        this.afterSurplusQuota = afterSurplusQuota;
    }

    public BigDecimal getOriSpentQuota() {
        return oriSpentQuota;
    }

    public void setOriSpentQuota(BigDecimal oriSpentQuota) {
        this.oriSpentQuota = oriSpentQuota;
    }

    public BigDecimal getAfterSpentQuota() {
        return afterSpentQuota;
    }

    public void setAfterSpentQuota(BigDecimal afterSpentQuota) {
        this.afterSpentQuota = afterSpentQuota;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getAdjustDate() {
        return adjustDate;
    }

    public void setAdjustDate(Date adjustDate) {
        this.adjustDate = adjustDate;
    }

    public Timestamp getAdjustTime() {
        return adjustTime;
    }

    public void setAdjustTime(Timestamp adjustTime) {
        this.adjustTime = adjustTime;
    }
}
