package cn.px.tea.modules.ycb.pojo.rf.store;

import javax.validation.constraints.NotNull;

public class TundrawIdRf {

    /**
     * 调拔单
     */
    @NotNull(message = "调拔单不能为空")
    private Long tundrawId;

    public Long getTundrawId() {
        return tundrawId;
    }

    public void setTundrawId(Long tundrawId) {
        this.tundrawId = tundrawId;
    }
}
