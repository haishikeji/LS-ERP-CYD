package cn.px.tea.modules.ycb.pojo.rf.store;

import javax.validation.constraints.NotNull;

public class StatusRf {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空")
    private Long primary;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空")
    private Integer status;

    public Long getPrimary() {
        return primary;
    }

    public void setPrimary(Long primary) {
        this.primary = primary;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
