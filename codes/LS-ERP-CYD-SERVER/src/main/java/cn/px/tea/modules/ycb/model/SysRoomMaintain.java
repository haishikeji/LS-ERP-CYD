package cn.px.tea.modules.ycb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

/**
 * 房间维修表
 */
@TableName(value ="sys_room_maintain")
public class SysRoomMaintain {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房间维修ID
     */
    private Long roomMaintainId;

    /**
     * 类型 1-日常维护 2-电器维修 3-其它
     */
    private Integer type;

    /**
     * 所属门店
     */
    private Long shopId;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 房间编号
     */
    private String roomNo;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 关联员工ID
     */
    private Long empId;

    /**
     * 员工名称
     */
    private String empName;

    /**
     * 员工联系电话
     */
    private String empPhone;

    /**
     * 维修时间段
     */
    private String periodTime;

    /**
     * 起始时间
     */
    private Timestamp startTime;

    /**
     * 终止时间
     */
    private Timestamp endTime;

    /**
     * 状态 1-维修中 2-维修完成
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作员
     */
    private Long operatorId;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 时间戮
     */
    private Timestamp timestamp;

    public SysRoomMaintain() {
    }

    public SysRoomMaintain(Long roomMaintainId, Integer type, Long shopId, Long roomId, String roomNo, String roomName, Long empId, String empName, String empPhone, String periodTime, Timestamp startTime, Timestamp endTime, Integer status, String remark, Long operatorId, Timestamp timestamp) {
        this.id = 0L;
        this.roomMaintainId = roomMaintainId;
        this.type = type;
        this.shopId = shopId;
        this.roomId = roomId;
        this.roomNo = roomNo;
        this.roomName = roomName;
        this.empId = empId;
        this.empName = empName;
        this.empPhone = empPhone;
        this.periodTime = periodTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.remark = remark;
        this.operatorId = operatorId;
        this.createTime = timestamp;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomMaintainId() {
        return roomMaintainId;
    }

    public void setRoomMaintainId(Long roomMaintainId) {
        this.roomMaintainId = roomMaintainId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public String getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(String periodTime) {
        this.periodTime = periodTime;
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

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
