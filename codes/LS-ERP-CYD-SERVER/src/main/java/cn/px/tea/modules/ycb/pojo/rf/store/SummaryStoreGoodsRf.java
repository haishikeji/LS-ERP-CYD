package cn.px.tea.modules.ycb.pojo.rf.store;

import javax.validation.constraints.NotNull;

public class SummaryStoreGoodsRf {

    /**
     * 门店ID
     */
    @NotNull(message = "门店ID不能为空")
    private Long shopId;

    /**
     * 商品类别 1-岩茶 2-白茶
     */
    private Long goodsType;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getGoodsType() {
//        if (goodsType == null) {
//            return 1L;
//        }
        return goodsType;
    }

    public void setGoodsType(Long goodsType) {
        this.goodsType = goodsType;
    }
}
