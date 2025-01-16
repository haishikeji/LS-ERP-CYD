package cn.px.tea.modules.ycb.pojo.vo.store;

import cn.px.tea.modules.ycb.model.SysPlanGoodsDetail;
import cn.px.tea.modules.ycb.model.SysStoreGoods;

public class PlanGoodsDetailVo extends SysPlanGoodsDetail {

    /**
     * 库存数量1
     */
    private Integer storeQuantity1;

    /**
     * 库存数量2
     */
    private Integer storeQuantity2;

    /**
     * 是否足够
     */
    private boolean enough;

    public PlanGoodsDetailVo() {
    }

    public PlanGoodsDetailVo(SysPlanGoodsDetail detail, SysStoreGoods sysStoreGoods/*Integer storeQuantity1, Integer storeQuantity2*/) {
        super(
                detail.getGoodsId(),
                detail.getGoodsCode(),
                detail.getGoodsName(),
                detail.getGoodsLogo(),
                detail.getGoodsTypeSort(),
                detail.getGoodsType(),
                detail.getGoodsTypeName(),
                detail.getGoodsKind(),
                detail.getGoodsKindName(),
                detail.getGoodsPublicDate(),
                detail.getGoodsKeepDay(),
                detail.getGoodsExceedDate(),
                detail.getGoodsSupplierId(),
                detail.getGoodsSupplierName(),
                detail.getGoodsSupplierBrand(),
                detail.getGoodsProduceArea(),
                detail.getGoodsIdentify(),
                detail.getGoodsUrl(),
                detail.getGoodsBatch(),
                detail.getGoodsSpecification(),
                detail.getRate(),
                detail.getGoodsSpecificationId(),
                detail.getWeight(),
                detail.getGoodsUnit(),
                detail.getGoodsUnitName(),
                detail.getGoodsCostPrice(),
                detail.getGoodsSalePrice(),
                detail.getQuantity(),
                detail.getGoodsSpecificationId2(),
                detail.getWeight2(),
                detail.getGoodsUnit2(),
                detail.getGoodsUnitName2(),
                detail.getGoodsCostPrice2(),
                detail.getGoodsSalePrice2(),
                detail.getQuantity2(),
                detail.getDiscount(),
                detail.getId(),
                detail.getWeightBig(),
                detail.getGoodsUnitBig(),
                detail.getGoodsUnitNameBig(),
                detail.getGoodsSalePriceBig(),
                detail.getRateBig(),
                detail.getQuantity3(),
                detail.getRateStatus(),
                detail.getMatchFormal(),
                detail.getMatchSample(),
                detail.getPlanOrderDetailId(),
                detail.getShopId(),
                detail.getShopId(),
                detail.getRemark(),
                detail.getCreateTime(),
                detail.getUpdateTime());
        this.storeQuantity1 = 0;
        this.storeQuantity2 = 0;
        if (sysStoreGoods != null) {
            this.storeQuantity1 = sysStoreGoods.getQuantity();
            this.storeQuantity2 = sysStoreGoods.getQuantity2();
        }

        if (this.storeQuantity1 < detail.getQuantity() || this.storeQuantity2 < detail.getQuantity2()) {
            this.enough = false;
        } else {
            this.enough = true;
        }
    }

    public Integer getStoreQuantity1() {
        return storeQuantity1;
    }

    public void setStoreQuantity1(Integer storeQuantity1) {
        this.storeQuantity1 = storeQuantity1;
    }

    public Integer getStoreQuantity2() {
        return storeQuantity2;
    }

    public void setStoreQuantity2(Integer storeQuantity2) {
        this.storeQuantity2 = storeQuantity2;
    }

    public boolean isEnough() {
        return enough;
    }

    public void setEnough(boolean enough) {
        this.enough = enough;
    }
}
