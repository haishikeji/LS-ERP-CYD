package cn.px.tea.modules.ycb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * 日报表
 */
@TableName(value ="sys_shift_daily_report")
public class SysShiftDailyReport {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 日报编号
     */
    private Long dailyReportId;

    /**
     * 门店ID
     */
    private Long shopId;

    /**
     * 员工
     */
    private Long empId;

    /**
     * 员工名称
     */
    private String empName;

    /**
     * 员工角色
     */
    private Long roleId;

    /**
     * 员工角色名称
     */
    private String roleName;

    /**
     * 班次起始时间
     */
    private Timestamp startTime;

    /**
     * 班次终止时间
     */
    private Timestamp endTime;

    /**
     * 班次日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dailyReportDate;

    /**
     * 提交时间
     */
    private Timestamp submitTime;

    /**
     * 状态 1-暂存 2-提交 3-审批 4-撤销
     */
    private Integer status;

    /**
     * 商品信息
     */
    private String goodsInfo;

    /**
     * 销售信息
     */
    private String saleInfo;

    /**
     * 接待信息
     */
    private String customInfo;

    /**
     * 寄存信息
     */
    private String teaStorageInfo;

    /**
     * 特殊备注信息
     */
    private String statiticsRemark;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 操作员
     */
    private Long operator;

    /**
     * 操作员名称
     */
    private String operatorName;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    public SysShiftDailyReport() {
    }

    public SysShiftDailyReport(Long dailyReportId, Long shopId, Long empId, String empName, Long roleId, String roleName, Timestamp startTime, Timestamp endTime, Date dailyReportDate, Timestamp submitTime, Integer status, String goodsInfo, String saleInfo, String customInfo, String teaStorageInfo, String statiticsRemark, String remark, Long operator, String operatorName, Timestamp timestamp) {
        this.id = 0L;
        this.dailyReportId = dailyReportId;
        this.shopId = shopId;
        this.empId = empId;
        this.empName = empName;
        this.roleId = roleId;
        this.roleName = roleName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dailyReportDate = dailyReportDate;
        this.submitTime = submitTime;
        this.status = status;
        this.goodsInfo = goodsInfo;
        this.saleInfo = saleInfo;
        this.customInfo = customInfo;
        this.teaStorageInfo = teaStorageInfo;
        this.statiticsRemark = statiticsRemark;
        this.remark = remark;
        this.operator = operator;
        this.operatorName = operatorName;
        this.createTime = timestamp;
        this.updateTime = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDailyReportId() {
        return dailyReportId;
    }

    public void setDailyReportId(Long dailyReportId) {
        this.dailyReportId = dailyReportId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Date getDailyReportDate() {
        return dailyReportDate;
    }

    public void setDailyReportDate(Date dailyReportDate) {
        this.dailyReportDate = dailyReportDate;
    }

    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(String saleInfo) {
        this.saleInfo = saleInfo;
    }

    public String getCustomInfo() {
        return customInfo;
    }

    public void setCustomInfo(String customInfo) {
        this.customInfo = customInfo;
    }

    public String getTeaStorageInfo() {
        return teaStorageInfo;
    }

    public void setTeaStorageInfo(String teaStorageInfo) {
        this.teaStorageInfo = teaStorageInfo;
    }

    public String getStatiticsRemark() {
        return statiticsRemark;
    }

    public void setStatiticsRemark(String statiticsRemark) {
        this.statiticsRemark = statiticsRemark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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
