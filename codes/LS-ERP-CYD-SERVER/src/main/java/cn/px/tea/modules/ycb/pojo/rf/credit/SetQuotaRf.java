package cn.px.tea.modules.ycb.pojo.rf.credit;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SetQuotaRf {

    /**
     * 会员ID
     */
    @NotNull(message = "会员ID不能为空")
    private Long memberId;

    /**
     * 额度值
     */
    @NotNull(message = "额度值不能为空")
    private BigDecimal allQuota;

    /**
     * 备注
     */
    private String remark;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getAllQuota() {
        return allQuota;
    }

    public void setAllQuota(BigDecimal allQuota) {
        this.allQuota = allQuota;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
