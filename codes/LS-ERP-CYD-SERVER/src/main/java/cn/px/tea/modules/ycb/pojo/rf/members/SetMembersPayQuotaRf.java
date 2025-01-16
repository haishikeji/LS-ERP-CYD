package cn.px.tea.modules.ycb.pojo.rf.members;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SetMembersPayQuotaRf {
    /**
     * 会员ID
     */
    @NotNull(message = "会员ID不能为空")
    private Long membersId;

    /**
     * 会员支付透支额度
     */
    @NotNull(message = "会员支付透支额度不能为空")
    private BigDecimal payQuota;

    /**
     * 备注
     */
    private String remark;

    public Long getMembersId() {
        return membersId;
    }

    public void setMembersId(Long membersId) {
        this.membersId = membersId;
    }

    public BigDecimal getPayQuota() {
        return payQuota;
    }

    public void setPayQuota(BigDecimal payQuota) {
        this.payQuota = payQuota;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
