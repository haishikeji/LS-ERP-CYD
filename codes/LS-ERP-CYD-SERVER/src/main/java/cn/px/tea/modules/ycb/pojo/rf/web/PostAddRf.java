package cn.px.tea.modules.ycb.pojo.rf.web;

import javax.validation.constraints.NotBlank;

public class PostAddRf {
    /**
     * 岗位名称
     */
    @NotBlank(message = "岗位名称不能为空")
    private String postName;

    /**
     * 岗位KEY
     */
    @NotBlank(message = "岗位KEY不能为空")
    private String postKey;

    /**
     * 备注
     */
    private String remark;

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
