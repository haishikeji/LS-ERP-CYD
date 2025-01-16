package cn.px.tea.modules.ycb.pojo.vo.businessstatistics;

import java.math.BigDecimal;

public class StatisticsConsumeVo {
    /**
     * 消费总笔数
     */
    private Integer totalCount;

    /**
     * 消费总金额
     */
    private BigDecimal totalCope;

    /**
     * 商品消费笔数
     */
    private Integer totalGoodsCount;

    /**
     * 商品消费额
     */
    private BigDecimal totalGoodsCope;

    /**
     * 服务笔数
     */
    private Integer totalServiceCount;

    /**
     * 服务费
     */
    private BigDecimal totalServiceCope;

    /**
     * 包间笔数
     */
    private Integer totalRoomCount;

    /**
     * 包间费
     */
    private BigDecimal totalRoomCope;


    /**
     * 门店营业收入总笔数
     */
    private Integer roomCount;

    /**
     * 门店营业今日总收入
     */
    private BigDecimal roomCope;

    /**
     * 零售商品收入总笔数
     */
    private Integer retailCount;

    /**
     * 零售商品今日总收入
     */
    private BigDecimal retailCope;

    public StatisticsConsumeVo() {
        this.totalCount = 0;
        this.totalCope = BigDecimal.ZERO;
        this.totalGoodsCount = 0;
        this.totalGoodsCope = BigDecimal.ZERO;
        this.totalServiceCount = 0;
        this.totalServiceCope = BigDecimal.ZERO;
        this.totalRoomCount = 0;
        this.totalRoomCope = BigDecimal.ZERO;

        this.roomCount = 0;
        this.roomCope = BigDecimal.ZERO;
        this.retailCount = 0;
        this.retailCope = BigDecimal.ZERO;
    }

    public StatisticsConsumeVo(Integer totalCount, BigDecimal totalCope, Integer totalGoodsCount, BigDecimal totalGoodsCope, Integer totalServiceCount, BigDecimal totalServiceCope, Integer totalRoomCount, BigDecimal totalRoomCope, Integer roomCount, BigDecimal roomCope, Integer retailCount, BigDecimal retailCope) {
        this.totalCount = totalCount;
        this.totalCope = totalCope;
        this.totalGoodsCount = totalGoodsCount;
        this.totalGoodsCope = totalGoodsCope;
        this.totalServiceCount = totalServiceCount;
        this.totalServiceCope = totalServiceCope;
        this.totalRoomCount = totalRoomCount;
        this.totalRoomCope = totalRoomCope;
        this.roomCount = roomCount;
        this.roomCope = roomCope;
        this.retailCount = retailCount;
        this.retailCope = retailCope;
    }
//    public StatisticsConsumeVo(Integer totalCount, BigDecimal totalCope, Integer totalGoodsCount, BigDecimal totalGoodsCope, Integer totalServiceCount, BigDecimal totalServiceCope, Integer totalRoomCount, BigDecimal totalRoomCope) {
//        this.totalCount = totalCount;
//        this.totalCope = totalCope;
//        this.totalGoodsCount = totalGoodsCount;
//        this.totalGoodsCope = totalGoodsCope;
//        this.totalServiceCount = totalServiceCount;
//        this.totalServiceCope = totalServiceCope;
//        this.totalRoomCount = totalRoomCount;
//        this.totalRoomCope = totalRoomCope;
//    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalCope() {
        return totalCope;
    }

    public void setTotalCope(BigDecimal totalCope) {
        this.totalCope = totalCope;
    }

    public Integer getTotalGoodsCount() {
        return totalGoodsCount;
    }

    public void setTotalGoodsCount(Integer totalGoodsCount) {
        this.totalGoodsCount = totalGoodsCount;
    }

    public BigDecimal getTotalGoodsCope() {
        return totalGoodsCope;
    }

    public void setTotalGoodsCope(BigDecimal totalGoodsCope) {
        this.totalGoodsCope = totalGoodsCope;
    }

    public Integer getTotalServiceCount() {
        return totalServiceCount;
    }

    public void setTotalServiceCount(Integer totalServiceCount) {
        this.totalServiceCount = totalServiceCount;
    }

    public BigDecimal getTotalServiceCope() {
        return totalServiceCope;
    }

    public void setTotalServiceCope(BigDecimal totalServiceCope) {
        this.totalServiceCope = totalServiceCope;
    }

    public Integer getTotalRoomCount() {
        return totalRoomCount;
    }

    public void setTotalRoomCount(Integer totalRoomCount) {
        this.totalRoomCount = totalRoomCount;
    }

    public BigDecimal getTotalRoomCope() {
        return totalRoomCope;
    }

    public void setTotalRoomCope(BigDecimal totalRoomCope) {
        this.totalRoomCope = totalRoomCope;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public BigDecimal getRoomCope() {
        return roomCope;
    }

    public void setRoomCope(BigDecimal roomCope) {
        this.roomCope = roomCope;
    }

    public Integer getRetailCount() {
        return retailCount;
    }

    public void setRetailCount(Integer retailCount) {
        this.retailCount = retailCount;
    }

    public BigDecimal getRetailCope() {
        return retailCope;
    }

    public void setRetailCope(BigDecimal retailCope) {
        this.retailCope = retailCope;
    }
}
