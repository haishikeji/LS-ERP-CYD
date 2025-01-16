package cn.px.tea.modules.ycb.pojo.rf.buyorder;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CreateBuyOrderRf {

    /**
     * 采购名称
     */
    @NotBlank(message = "采购名称不能为空")
    private String buyOrderName;

    /**
     * 采购明细
     */
    private List<BuyOrderDetailRf> listDetail;

    /**
     * 备注
     */
    private String remark;

    public String getBuyOrderName() {
        return buyOrderName;
    }

    public void setBuyOrderName(String buyOrderName) {
        this.buyOrderName = buyOrderName;
    }

    public List<BuyOrderDetailRf> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<BuyOrderDetailRf> listDetail) {
        this.listDetail = listDetail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
