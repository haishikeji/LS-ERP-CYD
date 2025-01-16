package cn.px.tea.modules.ycb.pojo.rf.business;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderMultiRf {

    /**
     * 订单ID
     */
    @NotNull(message = "订单号不能为空")
    private List<Long> listOrderId;

    public List<Long> getListOrderId() {
        return listOrderId;
    }

    public void setListOrderId(List<Long> listOrderId) {
        this.listOrderId = listOrderId;
    }
}
