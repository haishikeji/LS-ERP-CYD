package cn.px.tea.modules.ycb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

/**
 * 调拔明细表
 */
@TableName(value ="sys_store_tundraw_detail")
public class SysStoreTundrawDetail {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 调拔明细ID
     */
    private Long tundrawDetailId;

    /**
     * 店铺编号
     */
    private Long shopId;

    /**
     * 调拔ID
     */
    private Long tundrawId;

    /**
     * 入库库存商品ID
     */
    private Long storeGoodsId;

    /**
     * 调出仓ID
     */
    private Long outStoreId;

    /**
     * 调出仓名称
     */
    private String outStoreName;

    /**
     * 调出货架ID
     */
    private Long outShelfId;

    /**
     * 调出货架名称
     */
    private String outShelfName;

    /**
     * 调出货架格子ID
     */
    private Long outLatticeId;

    /**
     * 调出货架格子KEY
     */
    private String outLatticeKey;

    /**
     * 调入仓ID
     */
    private Long inStoreId;

    /**
     * 调入仓名称
     */
    private String inStoreName;

    /**
     * 调入货架ID
     */
    private Long inShelfId;

    /**
     * 调入货架名称
     */
    private String inShelfName;

    /**
     * 调入货架ID
     */
    private Long inLatticeId;

    /**
     * 调入货架格子KEY
     */
    private String inLatticeKey;

    /**
     * 调拔数量
     */
    private Integer quantity;

    /**
     * 当前库存总量
     */
    private Integer totalQuantity;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    public SysStoreTundrawDetail() {
    }

    public SysStoreTundrawDetail(Long tundrawDetailId, Long shopId, Long tundrawId, Long storeGoodsId, Long outStoreId, String outStoreName, Long outShelfId, String outShelfName, Long outLatticeId, String outLatticeKey, Long inStoreId, String inStoreName, Long inShelfId, String inShelfName, Long inLatticeId, String inLatticeKey, Integer quantity, Integer totalQuantity, Timestamp timestamp) {
        this.id = 0L;
        this.tundrawDetailId = tundrawDetailId;
        this.shopId = shopId;
        this.tundrawId = tundrawId;
        this.storeGoodsId = storeGoodsId;
        this.outStoreId = outStoreId;
        this.outStoreName = outStoreName;
        this.outShelfId = outShelfId;
        this.outShelfName = outShelfName;
        this.outLatticeId = outLatticeId;
        this.outLatticeKey = outLatticeKey;
        this.inStoreId = inStoreId;
        this.inStoreName = inStoreName;
        this.inShelfId = inShelfId;
        this.inShelfName = inShelfName;
        this.inLatticeId = inLatticeId;
        this.inLatticeKey = inLatticeKey;
        this.quantity = quantity;
        this.totalQuantity = totalQuantity;
        this.createTime = timestamp;
        this.updateTime = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTundrawDetailId() {
        return tundrawDetailId;
    }

    public void setTundrawDetailId(Long tundrawDetailId) {
        this.tundrawDetailId = tundrawDetailId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getTundrawId() {
        return tundrawId;
    }

    public void setTundrawId(Long tundrawId) {
        this.tundrawId = tundrawId;
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

    public String getOutStoreName() {
        return outStoreName;
    }

    public void setOutStoreName(String outStoreName) {
        this.outStoreName = outStoreName;
    }

    public Long getOutShelfId() {
        return outShelfId;
    }

    public void setOutShelfId(Long outShelfId) {
        this.outShelfId = outShelfId;
    }

    public String getOutShelfName() {
        return outShelfName;
    }

    public void setOutShelfName(String outShelfName) {
        this.outShelfName = outShelfName;
    }

    public Long getOutLatticeId() {
        return outLatticeId;
    }

    public void setOutLatticeId(Long outLatticeId) {
        this.outLatticeId = outLatticeId;
    }

    public String getOutLatticeKey() {
        return outLatticeKey;
    }

    public void setOutLatticeKey(String outLatticeKey) {
        this.outLatticeKey = outLatticeKey;
    }

    public Long getInStoreId() {
        return inStoreId;
    }

    public void setInStoreId(Long inStoreId) {
        this.inStoreId = inStoreId;
    }

    public String getInStoreName() {
        return inStoreName;
    }

    public void setInStoreName(String inStoreName) {
        this.inStoreName = inStoreName;
    }

    public Long getInShelfId() {
        return inShelfId;
    }

    public void setInShelfId(Long inShelfId) {
        this.inShelfId = inShelfId;
    }

    public String getInShelfName() {
        return inShelfName;
    }

    public void setInShelfName(String inShelfName) {
        this.inShelfName = inShelfName;
    }

    public Long getInLatticeId() {
        return inLatticeId;
    }

    public void setInLatticeId(Long inLatticeId) {
        this.inLatticeId = inLatticeId;
    }

    public String getInLatticeKey() {
        return inLatticeKey;
    }

    public void setInLatticeKey(String inLatticeKey) {
        this.inLatticeKey = inLatticeKey;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
