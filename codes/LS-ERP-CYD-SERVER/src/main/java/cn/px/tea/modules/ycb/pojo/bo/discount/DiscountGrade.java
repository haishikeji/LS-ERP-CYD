package cn.px.tea.modules.ycb.pojo.bo.discount;

import java.math.BigDecimal;

public class DiscountGrade {
    /**
     * 茶品类别 1-岩茶 2-白茶
     */
    private Long goodsType;

    /**
     * 对象类型 1-厂家 2-加盟店 3-个人
     */
    private Integer objectType;

    /**
     * 门店/个人账号ID
     */
    private Long objectId;

    /**
     * 起始金额
     */
    private BigDecimal startCope;

    /**
     * 终止金额
     */
    private BigDecimal endCope;


    /**
     * 折扣
     */
    private BigDecimal discountRate;

    public DiscountGrade() {
    }

    public DiscountGrade(Long goodsType, Integer objectType, Long objectId, BigDecimal startCope, BigDecimal endCope, BigDecimal discountRate) {
        this.goodsType = goodsType;
        this.objectType = objectType;
        this.objectId = objectId;
        this.startCope = startCope;
        this.endCope = endCope;
        this.discountRate = discountRate;
    }

    public Long getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Long goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public BigDecimal getStartCope() {
        return startCope;
    }

    public void setStartCope(BigDecimal startCope) {
        this.startCope = startCope;
    }

    public BigDecimal getEndCope() {
        return endCope;
    }

    public void setEndCope(BigDecimal endCope) {
        this.endCope = endCope;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }
}
