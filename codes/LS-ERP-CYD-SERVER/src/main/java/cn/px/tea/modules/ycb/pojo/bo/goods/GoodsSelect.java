package cn.px.tea.modules.ycb.pojo.bo.goods;

public class GoodsSelect {

    /**
     * 库存商品ID
     */
    private Long storeGoodsId;

//    /**
//     * 仓库编号
//     */
//    private Long goodsSpecificationId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 数量2
     */
    private Integer quantity2;

    public Long getStoreGoodsId() {
        return storeGoodsId;
    }

    public void setStoreGoodsId(Long storeGoodsId) {
        this.storeGoodsId = storeGoodsId;
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
}
