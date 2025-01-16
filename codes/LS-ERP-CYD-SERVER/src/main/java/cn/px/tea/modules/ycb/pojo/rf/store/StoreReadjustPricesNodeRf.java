package cn.px.tea.modules.ycb.pojo.rf.store;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class StoreReadjustPricesNodeRf {
    /**
     * 调价详情单ID（修改时需要）
     */
    private Long readjustDetailsId;

    /**
     * 库存商品id
     */
    @NotNull(message = "库存商品ID不能为空")
    private Long invGoodsId;

    /**
     * 成本价调整
     */
    private BigDecimal readjustCostPrice;

    /**
     * 成本价调整2
     */
    private BigDecimal readjustCostPrice2;

    /**
     * 零售价调整
     */
    private BigDecimal readjustSalePrice;

    /**
     * 零售价调整2
     */
    private BigDecimal readjustSalePrice2;

    public Long getReadjustDetailsId() {
        return readjustDetailsId;
    }

    public void setReadjustDetailsId(Long readjustDetailsId) {
        this.readjustDetailsId = readjustDetailsId;
    }

    public Long getInvGoodsId() {
        return invGoodsId;
    }

    public void setInvGoodsId(Long invGoodsId) {
        this.invGoodsId = invGoodsId;
    }

    public BigDecimal getReadjustCostPrice() {
        return readjustCostPrice;
    }

    public void setReadjustCostPrice(BigDecimal readjustCostPrice) {
        this.readjustCostPrice = readjustCostPrice;
    }

    public BigDecimal getReadjustCostPrice2() {
        return readjustCostPrice2;
    }

    public void setReadjustCostPrice2(BigDecimal readjustCostPrice2) {
        this.readjustCostPrice2 = readjustCostPrice2;
    }

    public BigDecimal getReadjustSalePrice() {
        return readjustSalePrice;
    }

    public void setReadjustSalePrice(BigDecimal readjustSalePrice) {
        this.readjustSalePrice = readjustSalePrice;
    }

    public BigDecimal getReadjustSalePrice2() {
        return readjustSalePrice2;
    }

    public void setReadjustSalePrice2(BigDecimal readjustSalePrice2) {
        this.readjustSalePrice2 = readjustSalePrice2;
    }
}
