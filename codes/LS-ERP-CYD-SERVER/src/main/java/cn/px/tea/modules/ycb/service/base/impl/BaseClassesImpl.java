package cn.px.tea.modules.ycb.service.base.impl;

import cn.px.tea.modules.ycb.define.OrderDefine;
import cn.px.tea.modules.ycb.model.SysMembersChargeRecord;
import cn.px.tea.modules.ycb.model.SysOrder;
import cn.px.tea.modules.ycb.model.SysStoreInoutDetail;
import cn.px.tea.modules.ycb.model.SysTeaStorageOrderGoods;
import cn.px.tea.modules.ycb.pojo.bo.classes.ShiftClassesCustomInfo;
import cn.px.tea.modules.ycb.pojo.bo.classes.ShiftClassesSaleInfo;
import cn.px.tea.modules.ycb.pojo.bo.classes.ShiftClassesStatistics;
import cn.px.tea.modules.ycb.pojo.bo.classes.ShiftClassesTeaStorageInfo;
import cn.px.tea.modules.ycb.service.table.intel.SysMembersChargeRecordService;
import cn.px.tea.modules.ycb.service.table.intel.SysOrderService;
import cn.px.tea.modules.ycb.service.table.intel.SysStoreInoutDetailService;
import cn.px.tea.modules.ycb.service.table.intel.SysTeaStorageOrderGoodsService;
import cn.px.tea.modules.ycb.model.*;
import cn.px.tea.modules.ycb.pojo.bo.classes.*;
import cn.px.tea.modules.ycb.service.base.inter.BaseClassesService;
import cn.px.tea.modules.ycb.service.table.intel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class BaseClassesImpl implements BaseClassesService {

    @Autowired
    private SysOrderService sysOrderService;

    @Autowired
    private SysMembersChargeRecordService sysMembersChargeRecordService;

    @Autowired
    private SysTeaStorageOrderGoodsService sysTeaStorageOrderGoodsService;

    @Autowired
    private SysStoreInoutDetailService sysStoreInoutRecordService;


    @Override
    public ShiftClassesStatistics waterStatistics(ShiftClassesStatistics shiftClassesStatistics, Integer type) {
        if (type == null || type != 1) {
            return shiftClassesStatistics;
        }

        // 只看自己的
        Long empId = shiftClassesStatistics.getEmpId();

        /**
         * 接待信息
         */
        List<ShiftClassesCustomInfo> customInfo = shiftClassesStatistics.getCustomInfo();
        List<ShiftClassesCustomInfo> _customInfo = new ArrayList<>();
        for (ShiftClassesCustomInfo shiftClassesCustomInfo : customInfo) {
            if (empId.equals(shiftClassesCustomInfo.getEmpId())) {
                _customInfo.add(shiftClassesCustomInfo);
            }
        }
        shiftClassesStatistics.setCustomInfo(_customInfo);

        /**
         * 寄存信息
         */
        List<ShiftClassesTeaStorageInfo> teaStorageInfo = shiftClassesStatistics.getTeaStorageInfo();
        List<ShiftClassesTeaStorageInfo> _teaStorageInfo = new ArrayList<>();
        for (ShiftClassesTeaStorageInfo shiftClassesTeaStorageInfo : teaStorageInfo) {
            if (empId.equals(shiftClassesTeaStorageInfo.getEmpId())) {
                _teaStorageInfo.add(shiftClassesTeaStorageInfo);
            }
        }
        shiftClassesStatistics.setTeaStorageInfo(_teaStorageInfo);

        return shiftClassesStatistics;
    }


    @Override
    public ShiftClassesStatistics statisticsShiftClasses(Long shopId, Long empId, String empName, Long roleId, String roleName, Timestamp startTime, Timestamp endTime) {

        // 商品信息
        List<SysStoreInoutDetail> listInout = sysStoreInoutRecordService.listByCondition(shopId, startTime, endTime);
        if (listInout == null) {
            listInout = new ArrayList<>();
        }

        //销售信息
        List<SysOrder> listOrder = sysOrderService.listByCondition(shopId, null, OrderDefine.ORDERSTATUS_PAY, startTime, endTime);
        if (listOrder == null) {
            listOrder = new ArrayList<>();
        }

        //接待信息
        List<ShiftClassesCustomInfo> customInfo = statisticsCustomInfo(shopId, startTime, endTime);
        if (customInfo == null) {
            customInfo = new ArrayList<>();
        }

        //寄存信息
        List<ShiftClassesTeaStorageInfo> teaStorageInfo = statisticsTeaStorageInfo(shopId, startTime, endTime);
        if (teaStorageInfo == null) {
            teaStorageInfo = new ArrayList<>();
        }

        return new ShiftClassesStatistics(empId, empName, roleId, roleName, startTime, endTime, listInout, listOrder, customInfo, teaStorageInfo);
    }

    private List<ShiftClassesTeaStorageInfo> statisticsTeaStorageInfo(Long shopId, Timestamp startTime, Timestamp endTime) {
        List<SysTeaStorageOrderGoods> list = sysTeaStorageOrderGoodsService.listByContidion(shopId, startTime, endTime);
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        List<ShiftClassesTeaStorageInfo> listVo = new ArrayList<>();
        for(SysTeaStorageOrderGoods sysTeaStorageOrderGoods: list) {
            ShiftClassesTeaStorageInfo vo = new ShiftClassesTeaStorageInfo(
                    sysTeaStorageOrderGoods.getTeaStorageOrderGoodsId(),
                    sysTeaStorageOrderGoods.getTeaStorageOrderId(),
                    sysTeaStorageOrderGoods.getOrderType(),
                    sysTeaStorageOrderGoods.getStoreGoodsId(),
                    sysTeaStorageOrderGoods.getGoodsId(),
                    sysTeaStorageOrderGoods.getGoodsCode(),
                    sysTeaStorageOrderGoods.getGoodsName(),
                    sysTeaStorageOrderGoods.getGoodsLogo(),
                    sysTeaStorageOrderGoods.getGoodsType(),
                    sysTeaStorageOrderGoods.getGoodsTypeName(),
                    sysTeaStorageOrderGoods.getGoodsKind(),
                    sysTeaStorageOrderGoods.getGoodsKindName(),
                    sysTeaStorageOrderGoods.getGoodsUnit(),
                    sysTeaStorageOrderGoods.getGoodsUnitName(),
                    sysTeaStorageOrderGoods.getGoodsSpecificationId(),
                    sysTeaStorageOrderGoods.getGoodsSpecification(),
                    sysTeaStorageOrderGoods.getGoodsSupplierId(),
                    sysTeaStorageOrderGoods.getGoodsSupplierName(),
                    sysTeaStorageOrderGoods.getGoodsSupplierBrand(),
                    sysTeaStorageOrderGoods.getGoodsProduceArea(),
                    sysTeaStorageOrderGoods.getGoodsExceedDate(),
                    sysTeaStorageOrderGoods.getGoodsCostPrice(),
                    sysTeaStorageOrderGoods.getGoodsSalePrice(),
                    sysTeaStorageOrderGoods.getOrderDate(),
                    sysTeaStorageOrderGoods.getDepositEndDate(),
                    sysTeaStorageOrderGoods.getQuantity(),
                    sysTeaStorageOrderGoods.getStoreCost(),
                    sysTeaStorageOrderGoods.getStoreSale(),
                    sysTeaStorageOrderGoods.getOrderDetailId(),
                    sysTeaStorageOrderGoods.getTimestamp(),
                    sysTeaStorageOrderGoods.getMembersType(),
                    sysTeaStorageOrderGoods.getMembersId(),
                    sysTeaStorageOrderGoods.getMembersName(),
                    sysTeaStorageOrderGoods.getOperator(),
                    sysTeaStorageOrderGoods.getOperatorName()
            );
            listVo.add(vo);
        }
        return listVo;
    }

    private List<ShiftClassesCustomInfo> statisticsCustomInfo(Long shopId, Timestamp startTime, Timestamp endTime) {
        List<SysMembersChargeRecord> listRecord = sysMembersChargeRecordService.listByContidion(shopId, startTime, endTime);
        if (listRecord == null || listRecord.size() == 0) {
            return new ArrayList<>();
        }
        List<ShiftClassesCustomInfo> listVo = new ArrayList<>();
        for(SysMembersChargeRecord sysMembersChargeRecord : listRecord) {
            ShiftClassesCustomInfo vo = new ShiftClassesCustomInfo(
                    sysMembersChargeRecord.getRecordId(),
                    sysMembersChargeRecord.getOrderPayId(),
                    sysMembersChargeRecord.getPayType(),
                    sysMembersChargeRecord.getType(),
                    sysMembersChargeRecord.getAmount(),
                    sysMembersChargeRecord.getRelevanceInfo(),
                    sysMembersChargeRecord.getTimestamp(),
                    "",
                    sysMembersChargeRecord.getMemberType(),
                    sysMembersChargeRecord.getMemberId(),
                    sysMembersChargeRecord.getMemberName(),
                    sysMembersChargeRecord.getOperator(),
                    sysMembersChargeRecord.getOperatorName()
            );
            listVo.add(vo);
        }
        return listVo;
    }

    private List<ShiftClassesSaleInfo> statisticsSaleInfo(Long shopId, Timestamp startTime, Timestamp endTime) {
        List<SysOrder> listOrder = sysOrderService.listByCondition(shopId, null, null, OrderDefine.ORDERSTATUS_PAY, startTime, endTime);
        if (listOrder == null || listOrder.size() == 0) {
            return new ArrayList<>();
        }
        List<ShiftClassesSaleInfo> listVo = new ArrayList<>();
        for(SysOrder sysOrder : listOrder) {
            ShiftClassesSaleInfo vo = new ShiftClassesSaleInfo(
                    sysOrder.getOrderId(),
                    sysOrder.getRcType(),
                    sysOrder.getOrderType(),
                    sysOrder.getSaleType(),
                    sysOrder.getRoomType(),
                    sysOrder.getRelevanceId(),
                    sysOrder.getPayType(),
                    sysOrder.getOrderPayId(),
                    sysOrder.getSubmitTime(),
                    sysOrder.getPayTime(),
                    sysOrder.getDealCope(),
                    sysOrder.getVoucherCope(),
                    sysOrder.getDiscountCope(),
                    sysOrder.getRealCope(),
                    sysOrder.getDepositCope(),
                    sysOrder.getWaterCope(),
                    sysOrder.getStatus(),
                    sysOrder.getCustomType(),
                    sysOrder.getCustomId(),
                    sysOrder.getCustomName(),
                    sysOrder.getOperator(),
                    sysOrder.getOperatorName()
            );
            listVo.add(vo);
        }
        return listVo;
    }

//    private List<ShiftClassessGoodsInfo> statisticsGoodsInfo(Long shopId, Timestamp startTime, Timestamp endTime) {
//        List<SysOrderGoodsDetails> listDetail = sysOrderGoodsDetailsService.listByCondition(shopId, OrderDefine.ORDERSTATUS_PAY, startTime, endTime);
//        if (listDetail == null || listDetail.size() == 0) {
//            return new ArrayList<>();
//        }
//        List<ShiftClassessGoodsInfo> listVo = new ArrayList<>();
//        for(SysOrderGoodsDetails sysOrderGoodsDetails : listDetail) {
//            ShiftClassessGoodsInfo vo = new ShiftClassessGoodsInfo(
//                    sysOrderGoodsDetails.getInvGoodsId(),
//                    sysOrderGoodsDetails.getGoodsId(),
//                    sysOrderGoodsDetails.getGoodsCode(),
//                    sysOrderGoodsDetails.getGoodsName(),
//                    sysOrderGoodsDetails.getGoodsLogo(),
//                    sysOrderGoodsDetails.getGoodsType(),
//                    sysOrderGoodsDetails.getGoodsTypeName(),
//                    sysOrderGoodsDetails.getGoodsKind(),
//                    sysOrderGoodsDetails.getGoodsKindName(),
//                    sysOrderGoodsDetails.getGoodsUnit(),
//                    sysOrderGoodsDetails.getGoodsUnitName(),
//                    sysOrderGoodsDetails.getGoodsSpecificationId(),
//                    sysOrderGoodsDetails.getGoodsSpecification(),
//                    sysOrderGoodsDetails.getGoodsPublicDate(),
//                    sysOrderGoodsDetails.getGoodsKeepDay(),
//                    sysOrderGoodsDetails.getGoodsExceedDate(),
//                    sysOrderGoodsDetails.getGoodsSupplierId(),
//                    sysOrderGoodsDetails.getGoodsSupplierName(),
//                    sysOrderGoodsDetails.getGoodsSupplierBrand(),
//                    sysOrderGoodsDetails.getGoodsProduceArea(),
//                    sysOrderGoodsDetails.getGoodsBatch(),
//                    sysOrderGoodsDetails.getCostPrice(),
//                    sysOrderGoodsDetails.getSalePrice(),
//                    sysOrderGoodsDetails.getQuantity(),
//                    sysOrderGoodsDetails.getDealCope(),
//                    sysOrderGoodsDetails.getCustomType(),
//                    sysOrderGoodsDetails.getCustomId(),
//                    sysOrderGoodsDetails.getCustomName(),
//                    sysOrderGoodsDetails.getOperator(),
//                    sysOrderGoodsDetails.getOperatorName()
//            );
//            listVo.add(vo);
//        }
//        return listVo;
//    }

}
