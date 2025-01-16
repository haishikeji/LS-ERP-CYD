package cn.px.tea.modules.ycb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 分档折扣表
 */
@TableName(value ="sys_discount_grade")
public class SysDiscountGrade {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 主键ID
     */
    private Long discountId;

    /**
     * 对象类型 1-厂家 2-加盟店 3-个人
     */
    private Integer objectType;

    /**
     * 门店/个人账号ID
     */
    private Long objectId;

    /**
     * 商品类别  1-岩茶 2-白茶
     */
    private Long goodsType;

    /**
     * 起始金额
     */
    private BigDecimal startCope;

    /**
     * 终止金额
     */
    private BigDecimal endCope;


    /**
     * 折扣
     */
    private BigDecimal discountRate;

    /**
     * 状态 0-禁用 1-正常 2-删除
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    public SysDiscountGrade() {
    }

    public SysDiscountGrade(Long discountId, Integer objectType, Long objectId, Long goodsType, BigDecimal startCope, BigDecimal endCope, BigDecimal discountRate, Integer status, String remark, Timestamp timestamp) {
        this.id = 0L;
        this.discountId = discountId;
        this.objectType = objectType;
        this.objectId = objectId;
        this.goodsType = goodsType;
        this.startCope = startCope;
        this.endCope = endCope;
        this.discountRate = discountRate;
        this.status = status;
        this.remark = remark;
        this.createTime = timestamp;
        this.updateTime = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
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

    public Long getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Long goodsType) {
        this.goodsType = goodsType;
    }

    public BigDecimal getStartCope() {
        return startCope;
    }

    public void setStartCope(BigDecimal startCope) {
        this.startCope = startCope;
    }

    public BigDecimal getEndCope() {
        return endCope;
    }

    public void setEndCope(BigDecimal endCope) {
        this.endCope = endCope;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
