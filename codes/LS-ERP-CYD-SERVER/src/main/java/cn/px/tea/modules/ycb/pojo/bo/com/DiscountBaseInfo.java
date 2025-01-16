package cn.px.tea.modules.ycb.pojo.bo.com;

import cn.px.tea.modules.ycb.pojo.bo.base.BaseGoods;

import java.math.BigDecimal;

public class DiscountBaseInfo {

    private Long goodsId;
    private BigDecimal discount;
    private BigDecimal cost;
    private BigDecimal sale;
    private BigDecimal cost2;
    private BigDecimal sale2;

    private BaseGoods baseGoods;


    public DiscountBaseInfo() {
    }

    public DiscountBaseInfo(Long goodsId, BigDecimal discount, BigDecimal sale, BigDecimal sale2, BaseGoods baseGoods) {
        if (discount == null || discount.compareTo(BigDecimal.ZERO) <= 0) {
            discount = BigDecimal.ONE;
        }
        if (sale == null || sale.compareTo(BigDecimal.ZERO) < 0) {
            sale = BigDecimal.ZERO;
        }

        if (sale2 == null || sale2.compareTo(BigDecimal.ZERO) < 0) {
            sale2 = BigDecimal.ZERO;
        }


        this.goodsId = goodsId;
        this.discount = discount;
        this.sale = sale;
        this.cost = sale;
        if (sale.compareTo(BigDecimal.ZERO) > 0) {
            this.cost = this.sale.multiply(discount);
        }

        this.sale2 = sale2;
        this.cost2 = sale2;
        if (sale2.compareTo(BigDecimal.ZERO) > 0) {
            this.cost2 = this.sale2.multiply(discount);
        }

        this.baseGoods = baseGoods;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getSale() {
        return sale;
    }

    public void setSale(BigDecimal sale) {
        this.sale = sale;
    }

    public BigDecimal getCost2() {
        return cost2;
    }

    public void setCost2(BigDecimal cost2) {
        this.cost2 = cost2;
    }

    public BigDecimal getSale2() {
        return sale2;
    }

    public void setSale2(BigDecimal sale2) {
        this.sale2 = sale2;
    }

    public BaseGoods getBaseGoods() {
        return baseGoods;
    }

    public void setBaseGoods(BaseGoods baseGoods) {
        this.baseGoods = baseGoods;
    }
}
