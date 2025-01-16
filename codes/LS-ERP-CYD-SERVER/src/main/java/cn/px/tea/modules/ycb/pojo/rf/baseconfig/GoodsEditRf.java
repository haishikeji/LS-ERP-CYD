package cn.px.tea.modules.ycb.pojo.rf.baseconfig;

import javax.validation.constraints.NotNull;

public class GoodsEditRf {

    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;

    /**
     * 商品二维码
     */
    private String goodsCode;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品图标
     */
    private String goodsLogo;

    /**
     * 商品类型分类 1-茶品  2-茶具、茶杯、碗口等 3-伴手礼、活动产品
     */
    private Integer goodsTypeSort;

    /**
     * 商品类别 1-岩茶 2-白茶
     */
    private Long goodsType;

    /**
     * 产品品种
     */
    private Long goodsKind;

    /**
     * 商品批次
     */
    private String goodsBatch;

    /**
     * 上市日期（YYYY-MM-dd)
     */
    private String goodsPublicDate;

    /**
     * 保质时间（天）
     */
    private Integer goodsKeepDay;

    /**
     * 供应商ID
     */
    private Long goodsSupplierId;

    /**
     * 产品标识（备用）
     */
    private String goodsIdentify;

    /**
     * 商品详情地址（备用）
     */
    private String goodsUrl;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsTypeSort() {
        return goodsTypeSort;
    }

    public void setGoodsTypeSort(Integer goodsTypeSort) {
        this.goodsTypeSort = goodsTypeSort;
    }

    public String getGoodsLogo() {
        return goodsLogo;
    }

    public void setGoodsLogo(String goodsLogo) {
        this.goodsLogo = goodsLogo;
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

    public String getGoodsBatch() {
        return goodsBatch;
    }

    public void setGoodsBatch(String goodsBatch) {
        this.goodsBatch = goodsBatch;
    }

    public String getGoodsPublicDate() {
        return goodsPublicDate;
    }

    public void setGoodsPublicDate(String goodsPublicDate) {
        this.goodsPublicDate = goodsPublicDate;
    }

    public Integer getGoodsKeepDay() {
        return goodsKeepDay;
    }

    public void setGoodsKeepDay(Integer goodsKeepDay) {
        this.goodsKeepDay = goodsKeepDay;
    }

    public Long getGoodsSupplierId() {
        return goodsSupplierId;
    }

    public void setGoodsSupplierId(Long goodsSupplierId) {
        this.goodsSupplierId = goodsSupplierId;
    }

    public String getGoodsIdentify() {
        return goodsIdentify;
    }

    public void setGoodsIdentify(String goodsIdentify) {
        this.goodsIdentify = goodsIdentify;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
