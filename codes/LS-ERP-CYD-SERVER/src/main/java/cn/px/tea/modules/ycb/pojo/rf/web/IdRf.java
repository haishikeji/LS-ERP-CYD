package cn.px.tea.modules.ycb.pojo.rf.web;

import javax.validation.constraints.NotNull;

public class IdRf {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
