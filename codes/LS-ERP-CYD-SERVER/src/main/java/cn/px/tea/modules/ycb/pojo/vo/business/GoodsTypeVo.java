package cn.px.tea.modules.ycb.pojo.vo.business;

public class GoodsTypeVo {
    /**
     * 类型
     */
    private Long type;

    /**
     * 类型名称
     */
    private String name;

    public GoodsTypeVo() {
    }

    public GoodsTypeVo(Long type, String name) {
        this.type = type;
        this.name = name;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
