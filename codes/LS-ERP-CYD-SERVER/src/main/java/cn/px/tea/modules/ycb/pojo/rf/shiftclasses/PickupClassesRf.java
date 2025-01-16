package cn.px.tea.modules.ycb.pojo.rf.shiftclasses;

public class PickupClassesRf {

    /**
     * 交班员工
     */
    private Long handinEmpId;

    /**
     * 接班时间（yyyy-MM-dd HH:mm)
     */
    private String pickupTime;

    public Long getHandinEmpId() {
        return handinEmpId;
    }

    public void setHandinEmpId(Long handinEmpId) {
        this.handinEmpId = handinEmpId;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }
}
