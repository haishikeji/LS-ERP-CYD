package cn.px.tea.modules.ycb.pojo.rf.store;

public class TundrawDetailRf {

    /**
     * 调拔明细单号
     */
//    @NotNull(message = "调拔明细单号不能为空")
    private Long tundrawDetailId;

    /**
     * 入库库存商品ID
     */
    private Long storeGoodsId;

    /**
     * 调出仓ID
     */
    private Long outStoreId;

    /**
     * 调出货架ID
     */
    private Long outShelfId;

    /**
     * 调出货架格子ID
     */
    private Long outLatticeId;

    /**
     * 调拔数量
     */
    private Integer quantity;

    /**
     * 规格ID
     */
    private Long goodsSpecificationId;



    public Long getTundrawDetailId() {
        return tundrawDetailId;
    }

    public void setTundrawDetailId(Long tundrawDetailId) {
        this.tundrawDetailId = tundrawDetailId;
    }

    public Long getStoreGoodsId() {
        return storeGoodsId;
    }

    public void setStoreGoodsId(Long storeGoodsId) {
        this.storeGoodsId = storeGoodsId;
    }

    public Long getOutStoreId() {
        return outStoreId;
    }

    public void setOutStoreId(Long outStoreId) {
        this.outStoreId = outStoreId;
    }

    public Long getOutShelfId() {
        return outShelfId;
    }

    public void setOutShelfId(Long outShelfId) {
        this.outShelfId = outShelfId;
    }

    public Long getOutLatticeId() {
        return outLatticeId;
    }

    public void setOutLatticeId(Long outLatticeId) {
        this.outLatticeId = outLatticeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getGoodsSpecificationId() {
        return goodsSpecificationId;
    }

    public void setGoodsSpecificationId(Long goodsSpecificationId) {
        this.goodsSpecificationId = goodsSpecificationId;
    }
}
