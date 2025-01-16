package cn.px.tea.modules.ycb.pojo.bo.report;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class ReportTypeBo {

    /**
     * 类型
     */
    private Integer type;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 笔数
     */
    private Integer count;

    /**
     * 金额
     */
    private BigDecimal cope;

    public ReportTypeBo() {
        this.type = 0;
        this.typeName = "";
        this.count = 0;
        this.cope = BigDecimal.ZERO;
    }

    public ReportTypeBo(Integer type, String typeName, Integer count, BigDecimal cope) {
        if (type == null) {
            type = 0;
        }
        this.type = type;
        if (StringUtils.isBlank(typeName)) {
            typeName = "";
        }
        this.typeName = typeName;
        if (count == null) {
            count = 0;
        }
        this.count = count;
        if (cope == null) {
            cope = BigDecimal.ZERO;
        }
        this.cope = cope;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getCope() {
        return cope;
    }

    public void setCope(BigDecimal cope) {
        this.cope = cope;
    }
}
