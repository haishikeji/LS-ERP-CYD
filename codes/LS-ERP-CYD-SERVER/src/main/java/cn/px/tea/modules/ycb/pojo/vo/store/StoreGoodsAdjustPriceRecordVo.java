package cn.px.tea.modules.ycb.pojo.vo.store;

import java.math.BigDecimal;
import java.sql.Date;

public class StoreGoodsAdjustPriceRecordVo {
    /**
     * 调价ID
     */
    private Long adjustRecordId;

    /**
     * 商品ID
     */
    private Long shopId;

    /**
     * 库存ID
     */
    private Long storeId;

    /**
     * 调价的商品ID
     */
    private Long invGoodsId;

    /**
     * 成本单价调整类型 1-调高 2-调低
     */
    private Integer costType;

    /**
     * 成本单价调整金额
     */
    private BigDecimal costAdjustPrice;

    /**
     * 调整前成本单价
     */
    private BigDecimal costOriPrice;

    /**
     * 调整后成本单价
     */
    private BigDecimal costAfterPrice;

    /**
     * 零售单价调整类型 1-调高 2-调低
     */
    private Integer saleType;

    /**
     * 零售单价调整金额
     */
    private BigDecimal saleAdjustPrice;

    /**
     * 调整前零售单价
     */
    private BigDecimal saleOriPrice;

    /**
     * 调整后零售单价
     */
    private BigDecimal saleAfterPrice;

    /**
     * 调价涉及的数量（库存剩余数量)
     */
    private Integer quantity;

    /**
     * 调价日期
     */
    private Date adjustDate;

    /**
     * 备注
     */
    private String remark;


    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品类别：1-茶叶 2-茶具 3-其它
     */
    private Long goodsType;

    /**
     * 品种
     */
    private Long goodsKind;

    /**
     * 单位
     */
    private String unit;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 商品图标
     */
    private String logo;

    /**
     * 生产日期
     */
    private String produceDate;

    /**
     * 保质期
     */
    private String exceedDate;

    public StoreGoodsAdjustPriceRecordVo() {
    }

    public StoreGoodsAdjustPriceRecordVo(Long adjustRecordId, Long shopId, Long storeId, Long invGoodsId, Integer costType, BigDecimal costAdjustPrice, BigDecimal costOriPrice, BigDecimal costAfterPrice, Integer saleType, BigDecimal saleAdjustPrice, BigDecimal saleOriPrice, BigDecimal saleAfterPrice, Integer quantity, Date adjustDate, String remark, Long goodsId, String goodsName, Long goodsType, Long goodsKind, String unit, String batchNo, String logo, String produceDate, String exceedDate) {
        this.adjustRecordId = adjustRecordId;
        this.shopId = shopId;
        this.storeId = storeId;
        this.invGoodsId = invGoodsId;
        this.costType = costType;
        this.costAdjustPrice = costAdjustPrice;
        this.costOriPrice = costOriPrice;
        this.costAfterPrice = costAfterPrice;
        this.saleType = saleType;
        this.saleAdjustPrice = saleAdjustPrice;
        this.saleOriPrice = saleOriPrice;
        this.saleAfterPrice = saleAfterPrice;
        this.quantity = quantity;
        this.adjustDate = adjustDate;
        this.remark = remark;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsType = goodsType;
        this.goodsKind = goodsKind;
        this.unit = unit;
        this.batchNo = batchNo;
        this.logo = logo;
        this.produceDate = produceDate;
        this.exceedDate = exceedDate;
    }

    public Long getAdjustRecordId() {
        return adjustRecordId;
    }

    public void setAdjustRecordId(Long adjustRecordId) {
        this.adjustRecordId = adjustRecordId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getInvGoodsId() {
        return invGoodsId;
    }

    public void setInvGoodsId(Long invGoodsId) {
        this.invGoodsId = invGoodsId;
    }

    public Integer getCostType() {
        return costType;
    }

    public void setCostType(Integer costType) {
        this.costType = costType;
    }

    public BigDecimal getCostAdjustPrice() {
        return costAdjustPrice;
    }

    public void setCostAdjustPrice(BigDecimal costAdjustPrice) {
        this.costAdjustPrice = costAdjustPrice;
    }

    public BigDecimal getCostOriPrice() {
        return costOriPrice;
    }

    public void setCostOriPrice(BigDecimal costOriPrice) {
        this.costOriPrice = costOriPrice;
    }

    public BigDecimal getCostAfterPrice() {
        return costAfterPrice;
    }

    public void setCostAfterPrice(BigDecimal costAfterPrice) {
        this.costAfterPrice = costAfterPrice;
    }

    public Integer getSaleType() {
        return saleType;
    }

    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
    }

    public BigDecimal getSaleAdjustPrice() {
        return saleAdjustPrice;
    }

    public void setSaleAdjustPrice(BigDecimal saleAdjustPrice) {
        this.saleAdjustPrice = saleAdjustPrice;
    }

    public BigDecimal getSaleOriPrice() {
        return saleOriPrice;
    }

    public void setSaleOriPrice(BigDecimal saleOriPrice) {
        this.saleOriPrice = saleOriPrice;
    }

    public BigDecimal getSaleAfterPrice() {
        return saleAfterPrice;
    }

    public void setSaleAfterPrice(BigDecimal saleAfterPrice) {
        this.saleAfterPrice = saleAfterPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getAdjustDate() {
        return adjustDate;
    }

    public void setAdjustDate(Date adjustDate) {
        this.adjustDate = adjustDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Long goodsType) {
        this.goodsType = goodsType;
    }

    public Long getGoodsKind() {
        return goodsKind;
    }

    public void setGoodsKind(Long goodsKind) {
        this.goodsKind = goodsKind;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(String produceDate) {
        this.produceDate = produceDate;
    }

    public String getExceedDate() {
        return exceedDate;
    }

    public void setExceedDate(String exceedDate) {
        this.exceedDate = exceedDate;
    }
}
