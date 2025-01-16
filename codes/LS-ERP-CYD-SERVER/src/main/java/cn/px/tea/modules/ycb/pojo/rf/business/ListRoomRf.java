package cn.px.tea.modules.ycb.pojo.rf.business;

public class ListRoomRf {

    /**
     * 房间类型 1-大厅(散座) 2-包间
     */
    private Integer roomType;

    /**
     * 状态 0-维护中 1-空闲 2-使用中
     */
    private Integer status;

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
