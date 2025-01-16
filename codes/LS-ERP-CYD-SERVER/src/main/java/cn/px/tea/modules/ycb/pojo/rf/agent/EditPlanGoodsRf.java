package cn.px.tea.modules.ycb.pojo.rf.agent;

import cn.px.tea.modules.ycb.pojo.bo.agent.PlanGoodsNode;

import javax.validation.constraints.NotNull;
import java.util.List;

public class EditPlanGoodsRf {

    /**
     * 定货单ID
     */
    @NotNull(message = "定货单ID不能为空")
    private Long planOrderId;

    /**
     * 定货单
     */
    private List<PlanGoodsNode> listDetail;

    /**
     * 状态  0-保存草稿 1-提交
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    public Long getPlanOrderId() {
        return planOrderId;
    }

    public void setPlanOrderId(Long planOrderId) {
        this.planOrderId = planOrderId;
    }

    public List<PlanGoodsNode> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<PlanGoodsNode> listDetail) {
        this.listDetail = listDetail;
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
}
