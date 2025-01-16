package cn.px.tea.modules.ycb.pojo.rf.deposit;

import javax.validation.constraints.NotNull;

public class DrawDepositNode {

    /**
     * 寄存ID
     */
    @NotNull(message = "寄存ID不能为空")
    private Long depositId;

    /**
     * 领取数量
     */
    @NotNull(message = "领取数量不能为空")
    private Integer quantity;

    /**
     * 备注
     */
    private String remark;

    public Long getDepositId() {
        return depositId;
    }

    public void setDepositId(Long depositId) {
        this.depositId = depositId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
