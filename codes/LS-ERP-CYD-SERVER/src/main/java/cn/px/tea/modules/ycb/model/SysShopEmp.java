package cn.px.tea.modules.ycb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

/**
 * 门店员工表
 */
@TableName(value ="sys_shop_emp")
public class SysShopEmp {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 门店编号
     */
    private Long shopId;

    /**
     * 门店名称
     */
    private Long empId;

    /**
     * 员工名称（冗余）
     */
    private String empName;

    /**
     * 联系电话（冗余）
     */
    private String phone;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色KEY
     */
    private String roleKey;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 岗位ID
     */
    private Long postId;

    /**
     * 岗位KEY
     */
    private String postKey;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 状态 0-禁用 1-正常 2-离职
     */
    private Integer status;

    /** 操作员 */
    private Long operatorId;

    /** 创建时间 */
    private Timestamp createTime;

    /** 时间戮 */
    private Timestamp timestamp;

    public SysShopEmp() {
    }

    public SysShopEmp(Long shopId, Long empId, String empName, String phone, Long roleId, String roleKey, String roleName, Long postId, String postKey, String postName, Integer status, Long operatorId, Timestamp timestamp) {
        this.id = 0L;
        this.shopId = shopId;
        this.empId = empId;
        this.empName = empName;
        this.phone = phone;
        this.roleId = roleId;
        this.roleKey = roleKey;
        this.roleName = roleName;
        this.postId = postId;
        this.postKey = postKey;
        this.postName = postName;
        this.status = status;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
