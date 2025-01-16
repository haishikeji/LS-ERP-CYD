package cn.px.tea.modules.ycb.pojo.bo.base;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * 定货单明细表
 */
public class BaseStoreGoods extends BaseGoods {

    /**
     * 库存数量
     */
    protected Integer quantity;

    /**
     * 库存数量2
     */
    protected Integer quantity2;

    /**
     * 折扣
     */
    protected BigDecimal discount;




    public BaseStoreGoods() {
    }

//    public BaseStoreGoods(BigDecimal discount) {
//        this.discount = discount;
//    }

    public BaseStoreGoods(Long goodsId, String goodsCode, String goodsName, String goodsLogo, Integer goodsTypeSort, Long goodsType, String goodsTypeName, Long goodsKind, String goodsKindName, Date goodsPublicDate, Integer goodsKeepDay, Date goodsExceedDate, Long goodsSupplierId, String goodsSupplierName, String goodsSupplierBrand, String goodsProduceArea, String goodsIdentify, String goodsUrl, String goodsBatch, String goodsSpecification, Integer rate, Long goodsSpecificationId, BigDecimal weight, Long goodsUnit, String goodsUnitName, BigDecimal goodsCostPrice, BigDecimal goodsSalePrice, Integer quantity, Long goodsSpecificationId2, BigDecimal weight2, Long goodsUnit2, String goodsUnitName2, BigDecimal goodsCostPrice2, BigDecimal goodsSalePrice2, Integer quantity2, BigDecimal discount) {
        super(goodsId, goodsCode, goodsName, goodsLogo, goodsTypeSort, goodsType, goodsTypeName, goodsKind, goodsKindName, goodsPublicDate, goodsKeepDay, goodsExceedDate, goodsSupplierId, goodsSupplierName, goodsSupplierBrand, goodsProduceArea, goodsIdentify, goodsUrl, goodsBatch, goodsSpecification, rate, goodsSpecificationId, weight, goodsUnit, goodsUnitName, goodsCostPrice, goodsSalePrice, goodsSpecificationId2, weight2, goodsUnit2, goodsUnitName2, goodsCostPrice2, goodsSalePrice2);
        this.quantity = quantity;
        this.quantity2 = quantity2;
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity2() {
        return quantity2;
    }

    public void setQuantity2(Integer quantity2) {
        this.quantity2 = quantity2;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
