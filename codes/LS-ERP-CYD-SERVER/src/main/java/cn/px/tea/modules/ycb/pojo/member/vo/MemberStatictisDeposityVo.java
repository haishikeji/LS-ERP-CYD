package cn.px.tea.modules.ycb.pojo.member.vo;

import java.math.BigDecimal;

public class MemberStatictisDeposityVo {

    /**
     * 商品种类
     */
    private Integer kind;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 总价值
     */
    private BigDecimal cope;

    public MemberStatictisDeposityVo() {
    }

    public MemberStatictisDeposityVo(Integer kind, Integer quantity, BigDecimal cope) {
        this.kind = kind;
        this.quantity = quantity;
        this.cope = cope;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCope() {
        return cope;
    }

    public void setCope(BigDecimal cope) {
        this.cope = cope;
    }
}
