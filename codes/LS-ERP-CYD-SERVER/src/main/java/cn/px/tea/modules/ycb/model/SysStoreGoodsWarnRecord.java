package cn.px.tea.modules.ycb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 库存预警记录表
 */
@TableName(value ="sys_store_goods_warn_record")
public class SysStoreGoodsWarnRecord {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 预警单记录ID
     */
    private Long warnRecordId;

    /**
     * 商品ID
     */
    private Long shopId;

    /**
     * 库存ID
     */
    private Long storeId;

    /**
     * 预警的商品ID
     */
    private Long invGoodsId;

    /**
     * 类型
     */
    private Long goodsType;

    /**
     * 品种
     */
    private Long goodsKind;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 类型 1-调高 2-调低
     */
    private Integer type;

    /**
     * 调整的数量
     */
    private Integer adjustQuantity;

    /**
     * 调整前的预警数量
     */
    private Integer oriWarnQuantity;

    /**
     * 调整后的预警数量
     */
    private Integer afterWarnQuantity;

    /**
     * 调整时库存数量
     */
    private Integer quantity;

    /**
     * 预警调整日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date warnDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作员
     */
    private Long operatorId;

    /**
     * 操作员名称
     */
    private String operatorName;

    /**
     * 时间戮
     */
    private Timestamp timestamp;

    public SysStoreGoodsWarnRecord() {
    }

    public SysStoreGoodsWarnRecord(Long warnRecordId, Long shopId, Long storeId, Long invGoodsId, Long goodsType, Long goodsKind, String goodsName, Integer type, Integer adjustQuantity, Integer oriWarnQuantity, Integer afterWarnQuantity, Integer quantity, Date warnDate, String remark, Long operatorId, String operatorName, Timestamp timestamp) {
        this.id = 0L;
        this.warnRecordId = warnRecordId;
        this.shopId = shopId;
        this.storeId = storeId;
        this.invGoodsId = invGoodsId;
        this.goodsType = goodsType;
        this.goodsKind = goodsKind;
        this.goodsName = goodsName;
        this.type = type;
        this.adjustQuantity = adjustQuantity;
        this.oriWarnQuantity = oriWarnQuantity;
        this.afterWarnQuantity = afterWarnQuantity;
        this.quantity = quantity;
        this.warnDate = warnDate;
        this.remark = remark;
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarnRecordId() {
        return warnRecordId;
    }

    public void setWarnRecordId(Long warnRecordId) {
        this.warnRecordId = warnRecordId;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAdjustQuantity() {
        return adjustQuantity;
    }

    public void setAdjustQuantity(Integer adjustQuantity) {
        this.adjustQuantity = adjustQuantity;
    }

    public Integer getOriWarnQuantity() {
        return oriWarnQuantity;
    }

    public void setOriWarnQuantity(Integer oriWarnQuantity) {
        this.oriWarnQuantity = oriWarnQuantity;
    }

    public Integer getAfterWarnQuantity() {
        return afterWarnQuantity;
    }

    public void setAfterWarnQuantity(Integer afterWarnQuantity) {
        this.afterWarnQuantity = afterWarnQuantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getWarnDate() {
        return warnDate;
    }

    public void setWarnDate(Date warnDate) {
        this.warnDate = warnDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
