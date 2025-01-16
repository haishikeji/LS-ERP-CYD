package cn.px.tea.modules.ycb.pojo.rf.room;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AddListRoomRf {

    /**
     * 门店编号
     */
    @NotNull(message = "门店编号不能为空")
    private Long shopId;

    /**
     * 房间类型 1-大厅(散座) 2-包间
     */
    @NotNull(message = "必须选择房间类型")
    private Integer roomType;

    /**
     * 批量添加房间信息
     */
    @NotNull(message = "房间信息不能为空")
    private List<RoomNode> listRoom;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public List<RoomNode> getListRoom() {
        return listRoom;
    }

    public void setListRoom(List<RoomNode> listRoom) {
        this.listRoom = listRoom;
    }
}
