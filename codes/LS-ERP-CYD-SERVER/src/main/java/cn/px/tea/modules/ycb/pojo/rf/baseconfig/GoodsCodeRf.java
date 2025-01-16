package cn.px.tea.modules.ycb.pojo.rf.baseconfig;

import javax.validation.constraints.NotBlank;

public class GoodsCodeRf {

    /**
     * 二维码
     */
    @NotBlank(message = "二维码不能为空")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
