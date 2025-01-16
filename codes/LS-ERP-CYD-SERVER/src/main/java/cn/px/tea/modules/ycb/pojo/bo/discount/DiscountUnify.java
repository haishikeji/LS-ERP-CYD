package cn.px.tea.modules.ycb.pojo.bo.discount;

import java.math.BigDecimal;

public class DiscountUnify {
    /**
     * 对象类型 1-厂家 2-加盟店 3-个人
     */
    private Integer objectType;

    /**
     * 门店/个人账号ID
     */
    private Long objectId;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 折扣
     */
    private BigDecimal discountRate;

    public DiscountUnify() {
    }

    public DiscountUnify(Integer objectType, Long objectId, Long goodsId, BigDecimal discountRate) {
        this.objectType = objectType;
        this.objectId = objectId;
        this.goodsId = goodsId;
        this.discountRate = discountRate;
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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }
}
