package cn.px.tea.modules.ycb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 采购单
 */
@TableName(value ="sys_buy_order")
public class SysBuyOrder {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 采购单ID
     */
    private Long buyOrderId;

    /**
     * 商店ID
     */
    private Long shopId;

    /**
     * 采购名称
     */
    private String buyOrderName;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 采购种类数
     */
    private Integer totalKind;

    /**
     * 采购数量
     */
    private Integer totalQuantity;

    /**
     * 采购总价
     */
    private BigDecimal totalCost;

    /**
     * 状态： 0-草稿 1-申请（待审批） 2-审批（待收货） 3-到货（待入库） 4-入库（完成） 5-撤销 6-审批拒绝 7-验货拒绝 8-作废
     */
    private Integer status;

    /**
     * 申请人
     */
    private Long applyId;

    /**
     * 申请人名称
     */
    private String applyName;

    /**
     * 申请日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;

    /**
     * 申请时间
     */
    private Timestamp applyTime;

    /**
     * 审批人
     */
    private Long approveId;

    /**
     * 审批人名称
     */
    private String approveName;

    /**
     * 审批日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date approveDate;

    /**
     * 审批时间
     */
    private Timestamp approveTime;

    /**
     * 审批意见
     */
    private String approveReason;

    /**
     * 验货人
     */
    private Long inspectionId;

    /**
     * 验货人名称
     */
    private String inspectionName;

    /**
     * 验货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inspectionDate;

    /**
     * 验货时间
     */
    private Timestamp inspectionTime;

    /**
     * 验货意见
     */
    private String inspectionReason;

    /**
     * 入库人
     */
    private Long putinId;

    /**
     * 入库人名称
     */
    private String putinName;

    /**
     * 入库日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date putinDate;

    /**
     * 入库时间
     */
    private Timestamp putinTime;

    /**
     * 撤销时间
     */
    private Timestamp revocationTime;

    /**
     * 撤销意见
     */
    private String revocationReason;

    /**
     * 作废时间
     */
    private Timestamp cancelTime;

    /**
     * 作废意见
     */
    private String cancelReason;


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

    public SysBuyOrder() {
    }

    public SysBuyOrder(Long buyOrderId, Long shopId, String buyOrderName, Long supplierId, String supplierName, Integer totalKind, Integer totalQuantity, BigDecimal totalCost, Integer status, Long applyId, String applyName, Date applyDate, String remark, Timestamp timestamp) {
        this.id = 0L;
        this.buyOrderId = buyOrderId;
        this.shopId = shopId;
        this.buyOrderName = buyOrderName;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.totalKind = totalKind;
        this.totalQuantity = totalQuantity;
        this.totalCost = totalCost;
        this.status = status;
        this.applyId = applyId;
        this.applyName = applyName;
        this.applyDate = applyDate;
        this.applyTime = timestamp;
        this.approveId = 0L;
        this.approveName = "";
        this.approveDate = null;
        this.approveTime = null;
        this.inspectionId = 0L;
        this.inspectionName = "";
        this.inspectionDate = null;
        this.inspectionTime = null;
        this.putinId = 0L;
        this.putinName = "";
        this.putinDate = null;
        this.putinTime = null;
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

    public Long getBuyOrderId() {
        return buyOrderId;
    }

    public void setBuyOrderId(Long buyOrderId) {
        this.buyOrderId = buyOrderId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getBuyOrderName() {
        return buyOrderName;
    }

    public void setBuyOrderName(String buyOrderName) {
        this.buyOrderName = buyOrderName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getTotalKind() {
        return totalKind;
    }

    public void setTotalKind(Integer totalKind) {
        this.totalKind = totalKind;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }

    public Long getApproveId() {
        return approveId;
    }

    public void setApproveId(Long approveId) {
        this.approveId = approveId;
    }

    public String getApproveName() {
        return approveName;
    }

    public void setApproveName(String approveName) {
        this.approveName = approveName;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Timestamp getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Timestamp approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveReason() {
        return approveReason;
    }

    public void setApproveReason(String approveReason) {
        this.approveReason = approveReason;
    }

    public Long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Long inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public Timestamp getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(Timestamp inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getInspectionReason() {
        return inspectionReason;
    }

    public void setInspectionReason(String inspectionReason) {
        this.inspectionReason = inspectionReason;
    }

    public Long getPutinId() {
        return putinId;
    }

    public void setPutinId(Long putinId) {
        this.putinId = putinId;
    }

    public String getPutinName() {
        return putinName;
    }

    public void setPutinName(String putinName) {
        this.putinName = putinName;
    }

    public Date getPutinDate() {
        return putinDate;
    }

    public void setPutinDate(Date putinDate) {
        this.putinDate = putinDate;
    }

    public Timestamp getPutinTime() {
        return putinTime;
    }

    public void setPutinTime(Timestamp putinTime) {
        this.putinTime = putinTime;
    }

    public Timestamp getRevocationTime() {
        return revocationTime;
    }

    public void setRevocationTime(Timestamp revocationTime) {
        this.revocationTime = revocationTime;
    }

    public String getRevocationReason() {
        return revocationReason;
    }

    public void setRevocationReason(String revocationReason) {
        this.revocationReason = revocationReason;
    }

    public Timestamp getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Timestamp cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
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
