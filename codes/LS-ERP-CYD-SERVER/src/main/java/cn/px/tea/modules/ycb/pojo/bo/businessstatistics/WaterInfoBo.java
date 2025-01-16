package cn.px.tea.modules.ycb.pojo.bo.businessstatistics;

import java.math.BigDecimal;

public class WaterInfoBo {
    /**
     * 支付方式
     */
    private String payType;

    /**
     * 支付金额
     */
    private BigDecimal cope;

    /**
     * 流水号
     */
    private String waterNo;

    public WaterInfoBo() {
    }

    public WaterInfoBo(String payType, BigDecimal cope, String waterNo) {
        this.payType = payType;
        this.cope = cope;
        this.waterNo = waterNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public BigDecimal getCope() {
        return cope;
    }

    public void setCope(BigDecimal cope) {
        this.cope = cope;
    }

    public String getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(String waterNo) {
        this.waterNo = waterNo;
    }
}
