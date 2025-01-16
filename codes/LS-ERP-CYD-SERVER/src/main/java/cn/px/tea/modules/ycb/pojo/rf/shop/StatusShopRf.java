package cn.px.tea.modules.ycb.pojo.rf.shop;

import javax.validation.constraints.NotNull;

public class StatusShopRf {
    /**
     * 门店编号
     */
    @NotNull(message = "门店编号不能为空")
    private Long shopId;

    /** 状态 0-禁用 1-正常状态 2-删除 */
    @NotNull(message = "状态不能为空")
    private Integer status;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
