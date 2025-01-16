package cn.px.tea.modules.ycb.pojo.bo.com;

import java.math.BigDecimal;

public class DiscountInfo {

    private BigDecimal discount;
    private Integer totalKind;
    private Integer totalQuantity;
    private BigDecimal totalCost;
    private BigDecimal totalSale;

    public DiscountInfo() {
    }

    public DiscountInfo(BigDecimal discount, Integer totalKind, Integer totalQuantity, BigDecimal totalCost, BigDecimal totalSale) {
        this.discount = discount;
        this.totalKind = totalKind;
        this.totalQuantity = totalQuantity;
        this.totalCost = totalCost;
        this.totalSale = totalSale;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getTotalKind() {
        return totalKind;
    }

    public void setTotalKind(Integer totalKind) {
        this.totalKind = totalKind;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(BigDecimal totalSale) {
        this.totalSale = totalSale;
    }
}
