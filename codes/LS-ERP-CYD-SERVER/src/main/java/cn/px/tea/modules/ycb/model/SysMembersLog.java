package cn.px.tea.modules.ycb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

/**
 * 会员操作日志表
 */
@TableName(value ="sys_members_log")
public class SysMembersLog {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 日志ID
     */
    private Long membersLogId;

    /**
     * 门店ID
     */
    private Long shopId;

    /**
     * 会员ID
     */
    private Long membersId;

    /**
     * 会员名称
     */
    private String membersName;

    /**
     * 操作类型 1-会员等级改动
     */
    private Integer operatorType;

    /**
     * 内容
     */
    private String content;

    /**
     * 备注
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
     * 时间戮
     */
    private Timestamp timestamp;

    public SysMembersLog() {
    }

    public SysMembersLog(Long membersLogId, Long shopId, Long membersId, String membersName, Integer operatorType, String content, String remark, Long operator, String operatorName, Timestamp timestamp) {
        this.id = 0L;
        this.membersLogId = membersLogId;
        this.shopId = shopId;
        this.membersId = membersId;
        this.membersName = membersName;
        this.operatorType = operatorType;
        this.content = content;
        this.remark = remark;
        this.operator = operator;
        this.operatorName = operatorName;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMembersLogId() {
        return membersLogId;
    }

    public void setMembersLogId(Long membersLogId) {
        this.membersLogId = membersLogId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getMembersId() {
        return membersId;
    }

    public void setMembersId(Long membersId) {
        this.membersId = membersId;
    }

    public String getMembersName() {
        return membersName;
    }

    public void setMembersName(String membersName) {
        this.membersName = membersName;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
