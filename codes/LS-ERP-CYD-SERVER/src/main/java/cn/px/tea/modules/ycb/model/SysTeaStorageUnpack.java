package cn.px.tea.modules.ycb.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.px.tea.modules.ycb.pojo.bo.base.BaseGoods;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 寄存拆包单
 */
@TableName(value ="sys_tea_storage_unpack")
public class SysTeaStorageUnpack extends BaseGoods {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 拆包单ID
     */
    private Long unpackId;

    /**
     * 门店ID
     */
    private Long shopId;

    /**
     * 会员类型 0-客户 1-会员 2-SVIP
     */
    private Integer customType;

    /**
     * 客户ID
     */
    private Long customId;

    /**
     * 客户名称
     */
    private String customName;

    /**
     * 类型 1-拆包 2-装包
     */
    private Integer type;

    /**
     * 报损的商品ID
     */
    private Long storeGoodsId;



    /**
     * 拆包数量
     */
    private Integer unpackQuantity;

    /**
     * 拆后剩余库存量
     */
    private Integer afterQuantity;

    /**
     * 拆包数量2
     */
    private Integer unpackQuantity2;

    /**
     * 拆后剩余库存量2
     */
    private Integer afterQuantity2;

    /**
     * 拆包/装包日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date unpackDate;

    /**
     * 操作员
     */
    private Long operator;

    /**
     * 操作员名称
     */
    private String operatorName;

    /**
     * 时间戮
     */
    private Timestamp timestamp;

    public SysTeaStorageUnpack() {
    }

    public SysTeaStorageUnpack(Long unpackId, Long shopId, Integer customType, Long customId, String customName, Integer type, Long storeGoodsId, Long goodsId, String goodsName, String goodsCode, String goodsLogo, Integer goodsTypeSort, Long goodsType, String goodsTypeName, Long goodsKind, String goodsKindName, Date goodsPublicDate, Integer goodsKeepDay, Date goodsExceedDate, Long goodsSupplierId, String goodsSupplierName, String goodsSupplierBrand, String goodsProduceArea, String goodsIdentify, String goodsUrl, String goodsBatch, String goodsSpecification, Integer rate, Long goodsSpecificationId, BigDecimal weight, Long goodsUnit, String goodsUnitName, BigDecimal goodsCostPrice, BigDecimal goodsSalePrice, Integer unpackQuantity, Integer afterQuantity, Long goodsSpecificationId2, BigDecimal weight2, Long goodsUnit2, String goodsUnitName2, BigDecimal goodsCostPrice2, BigDecimal goodsSalePrice2, Integer unpackQuantity2, Integer afterQuantity2, Date unpackDate, Long operator, String operatorName, Timestamp timestamp) {
        super(goodsId, goodsCode, goodsName, goodsLogo, goodsTypeSort, goodsType, goodsTypeName, goodsKind, goodsKindName, goodsPublicDate, goodsKeepDay, goodsExceedDate, goodsSupplierId, goodsSupplierName, goodsSupplierBrand, goodsProduceArea, goodsIdentify, goodsUrl, goodsBatch, goodsSpecification, rate, goodsSpecificationId, weight, goodsUnit, goodsUnitName, goodsCostPrice, goodsSalePrice, goodsSpecificationId2, weight2, goodsUnit2, goodsUnitName2, goodsCostPrice2, goodsSalePrice2);
        this.id = null;
        this.unpackId = unpackId;
        this.shopId = shopId;
        this.customType = customType;
        this.customId = customId;
        this.customName = customName;
        this.type = type;
        this.storeGoodsId = storeGoodsId;
        this.unpackQuantity = unpackQuantity;
        this.afterQuantity = afterQuantity;
        this.unpackQuantity2 = unpackQuantity2;
        this.afterQuantity2 = afterQuantity2;
        this.unpackDate = unpackDate;
        this.operator = operator;
        this.operatorName = operatorName;
        this.timestamp = timestamp;
    }
//    public SysTeaStorageUnpack(Long unpackId, Long shopId, Integer customType, Long customId, String customName, Integer type, Long storeGoodsId, Long goodsId, String goodsName, String goodsCode, String goodsLogo, Long goodsType, String goodsTypeName, Long goodsKind, String goodsKindName, Date goodsPublicDate, Integer goodsKeepDay, Date goodsExceedDate, Long goodsSupplierId, String goodsSupplierName, String goodsSupplierBrand, String goodsProduceArea, String goodsIdentify, String goodsUrl, String goodsBatch, String goodsSpecification, Integer rate, Long goodsSpecificationId, BigDecimal weight, Long goodsUnit, String goodsUnitName, BigDecimal goodsCostPrice, BigDecimal goodsSalePrice, Integer unpackQuantity, Integer afterQuantity, Long goodsSpecificationId2, BigDecimal weight2, Long goodsUnit2, String goodsUnitName2, BigDecimal goodsCostPrice2, BigDecimal goodsSalePrice2, Integer unpackQuantity2, Integer afterQuantity2, Date unpackDate, Long operator, String operatorName, Timestamp timestamp) {
//        this.id = 0L;
//        this.unpackId = unpackId;
//        this.shopId = shopId;
//        this.customType = customType;
//        this.customId = customId;
//        this.customName = customName;
//        this.type = type;
//        this.storeGoodsId = storeGoodsId;
//        this.goodsId = goodsId;
//        this.goodsName = goodsName;
//        this.goodsCode = goodsCode;
//        this.goodsLogo = goodsLogo;
//        this.goodsType = goodsType;
//        this.goodsTypeName = goodsTypeName;
//        this.goodsKind = goodsKind;
//        this.goodsKindName = goodsKindName;
//        this.goodsPublicDate = goodsPublicDate;
//        this.goodsKeepDay = goodsKeepDay;
//        this.goodsExceedDate = goodsExceedDate;
//        this.goodsSupplierId = goodsSupplierId;
//        this.goodsSupplierName = goodsSupplierName;
//        this.goodsSupplierBrand = goodsSupplierBrand;
//        this.goodsProduceArea = goodsProduceArea;
//        this.goodsIdentify = goodsIdentify;
//        this.goodsUrl = goodsUrl;
//        this.goodsBatch = goodsBatch;
//        this.goodsSpecification = goodsSpecification;
//        this.rate = rate;
//        this.goodsSpecificationId = goodsSpecificationId;
//        this.weight = weight;
//        this.goodsUnit = goodsUnit;
//        this.goodsUnitName = goodsUnitName;
//        this.goodsCostPrice = goodsCostPrice;
//        this.goodsSalePrice = goodsSalePrice;
//        this.unpackQuantity = unpackQuantity;
//        this.afterQuantity = afterQuantity;
//        this.goodsSpecificationId2 = goodsSpecificationId2;
//        this.weight2 = weight2;
//        this.goodsUnit2 = goodsUnit2;
//        this.goodsUnitName2 = goodsUnitName2;
//        this.goodsCostPrice2 = goodsCostPrice2;
//        this.goodsSalePrice2 = goodsSalePrice2;
//        this.unpackQuantity2 = unpackQuantity2;
//        this.afterQuantity2 = afterQuantity2;
//        this.unpackDate = unpackDate;
//        this.operator = operator;
//        this.operatorName = operatorName;
//        this.timestamp = timestamp;
//    }

}
