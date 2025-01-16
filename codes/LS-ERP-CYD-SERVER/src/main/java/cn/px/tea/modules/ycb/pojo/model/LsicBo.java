package cn.px.tea.modules.ycb.pojo.model;

import java.math.BigDecimal;

public class LsicBo {

    /**
     * 类型
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 个数
     */
    private Integer count;

    /**
     * 金额
     */
    private BigDecimal cope;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
