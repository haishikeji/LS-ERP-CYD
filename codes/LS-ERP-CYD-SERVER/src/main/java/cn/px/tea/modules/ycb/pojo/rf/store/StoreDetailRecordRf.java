package cn.px.tea.modules.ycb.pojo.rf.store;

import javax.validation.constraints.NotNull;

public class StoreDetailRecordRf {

    /**
     * 记录ID
     */
    @NotNull(message = "记录不能为空")
    private Long recordId;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
}
