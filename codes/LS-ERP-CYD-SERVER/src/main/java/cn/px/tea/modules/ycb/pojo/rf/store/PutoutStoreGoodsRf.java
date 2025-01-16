package cn.px.tea.modules.ycb.pojo.rf.store;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PutoutStoreGoodsRf {

    /**
     * 库存ID
     */
    @NotNull(message = "请选择入库库存")
    private Long storeId;

    /**
     * 明细
     */
    private List<PutoutStoreGoodsNode> listNode;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public List<PutoutStoreGoodsNode> getListNode() {
        return listNode;
    }

    public void setListNode(List<PutoutStoreGoodsNode> listNode) {
        this.listNode = listNode;
    }
}
