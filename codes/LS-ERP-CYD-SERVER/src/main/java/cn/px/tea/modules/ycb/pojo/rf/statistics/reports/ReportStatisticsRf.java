package cn.px.tea.modules.ycb.pojo.rf.statistics.reports;

import javax.validation.constraints.NotNull;

public class ReportStatisticsRf {
    /**
     * 模式 1-日营业 2-月营业 3-年营业
     */
    @NotNull(message = "模式不能为空")
    private Integer mode;

    /**
     * 日期 （2022-11-12日 或 2022-11月 或 2022年）
     */
    private String selectDate;

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public String getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(String selectDate) {
        this.selectDate = selectDate;
    }
}
