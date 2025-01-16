//package cn.px.tea.modules.ycb.service.base.impl;
//
//import cn.px.tea.common.util.DateUtils;
//import cn.px.tea.common.util.uuid.IdUtils;
//import cn.px.tea.modules.ycb.define.CommonDefine;
//import cn.px.tea.modules.ycb.define.StoreDefine;
//import cn.px.tea.modules.ycb.model.*;
//import cn.px.tea.modules.ycb.pojo.bo.base.BaseGoods;
//import cn.px.tea.modules.ycb.pojo.bo.com.DiscountBaseInfo;
//import cn.px.tea.modules.ycb.pojo.rf.store.StoreInout;
//import cn.px.tea.modules.ycb.service.base.inter.BaseStoreService;
//import cn.px.tea.modules.ycb.service.base.inter.SmsService;
//import cn.px.tea.modules.ycb.service.com.inter.DiscountService;
//import cn.px.tea.modules.ycb.service.table.intel.*;
//import cn.px.tea.security.annotation.CustomException;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.sql.Date;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class BastStoreImpl implements BaseStoreService {
//
//    @Autowired
//    private SysStoreService sysStoreService;
//    @Autowired
//    private SysGoodsService sysGoodsService;
//    @Autowired
//    private SysStoreGoodsService sysStoreGoodsService;
//
//    @Autowired
//    private SysShopService sysShopService;
//    @Autowired
//    private SysEmpService sysEmpService;
//
//    @Autowired
//    private SysStoreInoutService sysStoreInoutService;
//    @Autowired
//    private SysStoreInoutDetailService sysStoreInoutDetailService;
//
//    @Autowired
//    private DiscountService discountService;
//
//    @Autowired
//    private SmsService smsService;
//
//
//    @Transactional
//    @Override
//    public SysStoreInout storeIn(Long shopId, int catalogType, Integer objectType, Long objectId, String objectName, int childKind, Long orderId, List<StoreInout> listStoreIn, Long operator, String operatorName) {
//        if (shopId == null || shopId == 0L || catalogType <= 0 || childKind <= 0 || listStoreIn == null || listStoreIn.size() == 0 || operator == null || operator == 0L || StringUtils.isBlank(operatorName)) {
//            throw CustomException.instance("入库参数有误");
//        }
//
//        SysStore sysStore = sysStoreService.getByDefault(shopId);
//        if (sysStore == null) {
//            throw CustomException.instance("库存不存在!");
//        }
//        Long storeId = sysStore.getStoreId();
//        String storeName = sysStore.getFullName();
//
//        Date storeInDate = DateUtils.getNowDate();
//        Timestamp curTime = DateUtils.getNowTimestamp();
//        Long storeInoutId = IdUtils.getUnid();
//        if (orderId == null || orderId == 0L) {
//            orderId = storeInoutId;
//        }
//        SysStoreInout sysStoreInout = new SysStoreInout(storeInoutId, shopId, objectType, objectId, objectName, catalogType, StoreDefine.STORE_IN, childKind, storeInDate + "入库单", 0, BigDecimal.ZERO, BigDecimal.ZERO, storeInDate, orderId, 0L, "", operator, operatorName, curTime);
//
//        List<SysStoreGoods> listAdd = new ArrayList<>();
//        List<SysStoreGoods> listUpdate = new ArrayList<>();
//
//        int totalQuantity = 0;
//        BigDecimal totalCost = BigDecimal.ZERO;
//        BigDecimal totalSale = BigDecimal.ZERO;
//        List<SysStoreInoutDetail> listRecord = new ArrayList<>();
//        for (StoreInout storeIn : listStoreIn) {
//            Long goodsId = storeIn.getGoodsId();
//            Integer quantity1 = storeIn.getQuantity();
//            Integer quantity2 = storeIn.getQuantity2();
//            if (quantity1 == null) {
//                quantity1 = 0;
//            }
//            if (quantity2 == null) {
//                quantity2 = 0;
//            }
//
//            if (goodsId == null || goodsId == 0L) {
//                throw CustomException.instance("商品不存在");
//            }
//            if (quantity1 < 0 || quantity2 < 0 || quantity1 + quantity2 < 1) {
//                throw CustomException.instance("数量不能为0");
//            }
//
//            SysStoreGoods sysStoreGoods = sysStoreGoodsService.getByGoodsId(shopId, goodsId);
//            if (sysStoreGoods == null) {
//                SysGoods sysGoods = sysGoodsService.getByPrimaryId(goodsId);
//                if (sysGoods == null) {
//                    throw CustomException.instance("商品不存在");
//                }
//                sysStoreGoods = SysStoreGoods.createNew(shopId, sysGoods);
//                if (sysStoreGoods == null) {
//                    throw CustomException.instance("请先设置商品规格");
//                }
//                listAdd.add(sysStoreGoods);
//            } else {
//                listUpdate.add(sysStoreGoods);
//            }
//            int oriQuantity1 = sysStoreGoods.getQuantity();
//            int afterQuantity1 = oriQuantity1 + quantity1;
//
//            int oriQuantity2 = sysStoreGoods.getQuantity2();
//            int afterQuantity2 = oriQuantity2 + quantity2;
//
//            if (!shopId.equals(sysStoreGoods.getShopId())) {
//                throw CustomException.instance("您没有权限操作");
//            }
//
//            Long storeInoutRecordId = IdUtils.getUnid();
//            Long relevanceDetailId = storeIn.getOrderDetailId();
//            if (relevanceDetailId == null || relevanceDetailId == 0L) {
//                relevanceDetailId = storeInoutRecordId;
//            }
//
//            DiscountBaseInfo discountBaseInfo = discountService.getDiscount(objectType, shopId, goodsId);
//            if (discountBaseInfo == null) {
//                throw CustomException.instance("获取不到折扣值");
//            }
//            BaseGoods baseGoods = discountBaseInfo.getBaseGoods();
//
//            SysStoreInoutDetail record = new SysStoreInoutDetail(
//                    storeInoutRecordId,
//                    sysStoreInout.getStoreInoutId(),
//                    shopId,
//                    objectType,
//                    objectId,
//                    objectName,
//                    catalogType,
//                    sysStoreInout.getStoreInoutType(),
//                    sysStoreInout.getChildKind(),
//                    0L,
//                    relevanceDetailId,
//                    storeId,
//                    storeName,
//                    storeInDate,
//                    sysStoreGoods.getStoreGoodsId(),
//                    baseGoods.getGoodsId(),
//                    baseGoods.getGoodsCode(),
//                    baseGoods.getGoodsLogo(),
//                    baseGoods.getGoodsTypeSort(),
//                    baseGoods.getGoodsName(),
//                    baseGoods.getGoodsType(),
//                    baseGoods.getGoodsTypeName(),
//                    baseGoods.getGoodsKind(),
//                    baseGoods.getGoodsKindName(),
//                    baseGoods.getGoodsPublicDate(),
//                    baseGoods.getGoodsKeepDay(),
//                    baseGoods.getGoodsExceedDate(),
//                    baseGoods.getGoodsSupplierId(),
//                    baseGoods.getGoodsSupplierName(),
//                    baseGoods.getGoodsSupplierBrand(),
//                    baseGoods.getGoodsProduceArea(),
//                    baseGoods.getGoodsIdentify(),
//                    baseGoods.getGoodsUrl(),
//                    baseGoods.getGoodsBatch(),
//                    baseGoods.getGoodsSpecification(),
//                    baseGoods.getRate(),
//                    discountBaseInfo.getDiscount(),
//                    baseGoods.getGoodsSpecificationId(),
//                    baseGoods.getWeight(),
//                    baseGoods.getGoodsUnit(),
//                    baseGoods.getGoodsUnitName(),
//                    discountBaseInfo.getCost(),
//                    discountBaseInfo.getSale(),
//                    quantity1,
//                    oriQuantity1,
//                    afterQuantity1,
//                    baseGoods.getGoodsSpecificationId2(),
//                    baseGoods.getWeight2(),
//                    baseGoods.getGoodsUnit2(),
//                    baseGoods.getGoodsUnitName2(),
//                    discountBaseInfo.getCost2(),
//                    discountBaseInfo.getSale2(),
//                    quantity2,
//                    oriQuantity2,
//                    afterQuantity2,
//                    "",
//                    operator,
//                    operatorName,
//                    curTime
//            );
//
//            listRecord.add(record);
//
//
//
//            // 设置库存的折扣、数量、成本、售价（平均分摊法）
//            if (!caclStoreDiscount(sysStoreGoods, discountBaseInfo, quantity1, quantity2, StoreDefine.STORE_IN)) {
//                throw CustomException.instance("入库失败");
//            }
//
//            totalQuantity += record.getQuantity() + record.getQuantity2();
//            if (record.getGoodsCostPrice() != null && record.getGoodsCostPrice().compareTo(BigDecimal.ZERO) > 0) {
//                totalCost = totalCost.add(record.getGoodsCostPrice().multiply(new BigDecimal(record.getQuantity())));
//            }
//            if (record.getGoodsCostPrice2() != null && record.getGoodsCostPrice2().compareTo(BigDecimal.ZERO) > 0) {
//                totalCost = totalCost.add(record.getGoodsCostPrice2().multiply(new BigDecimal(record.getQuantity2())));
//            }
//            if (record.getGoodsSalePrice() != null && record.getGoodsSalePrice().compareTo(BigDecimal.ZERO) > 0) {
//                totalSale = totalSale.add(record.getGoodsSalePrice().multiply(new BigDecimal(record.getQuantity())));
//            }
//            if (record.getGoodsSalePrice2() != null && record.getGoodsSalePrice2().compareTo(BigDecimal.ZERO) > 0) {
//                totalSale = totalSale.add(record.getGoodsSalePrice2().multiply(new BigDecimal(record.getQuantity2())));
//            }
//        }
//
//        if (listAdd.size() > 0) {
//            if (!sysStoreGoodsService.saveBatch(listAdd)) {
//                throw CustomException.instance("入库操作失败");
//            }
//        }
//        if (listUpdate.size() > 0) {
//            if (!sysStoreGoodsService.updateBatchById(listUpdate)) {
//                throw CustomException.instance("入库操作失败");
//            }
//        }
//
//        sysStoreInout.setTotalQuantity(totalQuantity);
//        sysStoreInout.setTotalCost(totalCost);
//        sysStoreInout.setTotalSale(totalSale);
//        if (!sysStoreInoutService.save(sysStoreInout)) {
//            throw CustomException.instance("入库操作失败");
//        }
//        if (!sysStoreInoutDetailService.saveBatch(listRecord)) {
//            throw CustomException.instance("入库操作失败");
//        }
//
//        return sysStoreInout;
//    }
//
//    @Transactional
//    @Override
//    public SysStoreInout storeOut(Long shopId, int catalogType, Integer objectType, Long objectId, String objectName, int childKind, Long orderId, List<StoreInout> listStoreOut, Long operator, String operatorName) {
//        if (shopId == null || shopId == 0L || catalogType == 0 || listStoreOut == null || listStoreOut.size() == 0 || operator == null || operator == 0L || StringUtils.isBlank(operatorName)) {
//            throw CustomException.instance("出库参数有误");
//        }
//        if (orderId == null) {
//            orderId = 0L;
//        }
//
//        SysShop sysShop = sysShopService.getByShop(shopId);
//        if (sysShop == null) {
//            throw CustomException.instance("商店不存在！");
//        }
//
//        SysStore sysStore = sysStoreService.getByDefault(shopId);
//        if (sysStore == null) {
//            throw CustomException.instance("库存不存在!");
//        }
//        Long storeId = sysStore.getStoreId();
//        String storeName = sysStore.getFullName();
//        Date storeOutDate = DateUtils.getNowDate();
//        Timestamp curTime = DateUtils.getNowTimestamp();
//        Long storeInoutId = IdUtils.getUnid();
//        if (orderId == null || orderId == 0L) {
//            orderId = storeInoutId;
//        }
//        SysStoreInout sysStoreInout = new SysStoreInout(storeInoutId, shopId, objectType, objectId, objectName, catalogType, StoreDefine.STORE_OUT, childKind, storeOutDate + "出库单", 0, BigDecimal.ZERO, BigDecimal.ZERO, storeOutDate, orderId, 0L, "", operator, operatorName, curTime);
//
//        List<SysStoreGoods> listUpdate = new ArrayList<>();
//        List<SysStoreGoods> listWarnGoods = new ArrayList<>();
//        int totalQuantity = 0;
//        BigDecimal totalCost = BigDecimal.ZERO;
//        BigDecimal totalSale = BigDecimal.ZERO;
//        List<SysStoreInoutDetail> listRecord = new ArrayList<>();
//        for (StoreInout storeInout : listStoreOut) {
//            Long goodsId = storeInout.getGoodsId();
//            Integer quantity1 = storeInout.getQuantity();
//            Integer quantity2 = storeInout.getQuantity2();
//            if (quantity1 == null) {
//                quantity1 = 0;
//            }
//            if (quantity2 == null) {
//                quantity2 = 0;
//            }
//            if (quantity1 < 0 || quantity2 < 0 || quantity1 + quantity2 < 1) {
//                throw CustomException.instance("数量不对");
//            }
//
//            SysStoreGoods sysStoreGoods = sysStoreGoodsService.getByStoreGoodsId(goodsId);
//            if (sysStoreGoods == null) {
//                throw CustomException.instance("库存商品不存在");
//            }
//            if (!shopId.equals(sysStoreGoods.getShopId())) {
//                throw CustomException.instance("您没有权限操作");
//            }
//
//            Integer afterQuantity1 = sysStoreGoods.getQuantity() - quantity1;
//            Integer afterQuantity2 = sysStoreGoods.getQuantity2() - quantity2;
//            if (afterQuantity1 < 0 || afterQuantity2 < 0) {
//                throw CustomException.instance("库存商品不足");
//            }
//
//            Long storeInoutRecordId = IdUtils.getUnid();
//            Long relevanceDetailId = storeInout.getOrderDetailId();
//            if (relevanceDetailId == null || relevanceDetailId == 0L) {
//                relevanceDetailId = storeInoutRecordId;
//            }
//
//            SysStoreInoutDetail record = new SysStoreInoutDetail(
//                    storeInoutRecordId,
//                    sysStoreInout.getStoreInoutId(),
//                    shopId,
//                    objectType,
//                    objectId,
//                    objectName,
//                    catalogType,
//                    sysStoreInout.getStoreInoutType(),
//                    sysStoreInout.getChildKind(),
//                    0L,
//                    relevanceDetailId,
//                    storeId,
//                    storeName,
//                    storeOutDate,
//                    sysStoreGoods.getStoreGoodsId(),
//                    sysStoreGoods.getGoodsId(),
//                    sysStoreGoods.getGoodsCode(),
//                    sysStoreGoods.getGoodsLogo(),
//                    sysStoreGoods.getGoodsTypeSort(),
//                    sysStoreGoods.getGoodsName(),
//                    sysStoreGoods.getGoodsType(),
//                    sysStoreGoods.getGoodsTypeName(),
//                    sysStoreGoods.getGoodsKind(),
//                    sysStoreGoods.getGoodsKindName(),
//                    sysStoreGoods.getGoodsPublicDate(),
//                    sysStoreGoods.getGoodsKeepDay(),
//                    sysStoreGoods.getGoodsExceedDate(),
//                    sysStoreGoods.getGoodsSupplierId(),
//                    sysStoreGoods.getGoodsSupplierName(),
//                    sysStoreGoods.getGoodsSupplierBrand(),
//                    sysStoreGoods.getGoodsProduceArea(),
//                    sysStoreGoods.getGoodsIdentify(),
//                    sysStoreGoods.getGoodsUrl(),
//                    sysStoreGoods.getGoodsBatch(),
//                    sysStoreGoods.getGoodsSpecification(),
//                    sysStoreGoods.getRate(),
//                    sysStoreGoods.getDiscount(),
//                    sysStoreGoods.getGoodsSpecificationId(),
//                    sysStoreGoods.getWeight(),
//                    sysStoreGoods.getGoodsUnit(),
//                    sysStoreGoods.getGoodsUnitName(),
//                    sysStoreGoods.getGoodsCostPrice(),
//                    sysStoreGoods.getGoodsSalePrice(),
//                    quantity1,
//                    sysStoreGoods.getQuantity(),
//                    afterQuantity1,
//                    sysStoreGoods.getGoodsSpecificationId2(),
//                    sysStoreGoods.getWeight2(),
//                    sysStoreGoods.getGoodsUnit2(),
//                    sysStoreGoods.getGoodsUnitName2(),
//                    sysStoreGoods.getGoodsCostPrice2(),
//                    sysStoreGoods.getGoodsSalePrice2(),
//                    quantity2,
//                    sysStoreGoods.getQuantity2(),
//                    afterQuantity2,
//                    "",
//                    operator,
//                    operatorName,
//                    curTime
//            );
//            listRecord.add(record);
//
//            sysStoreGoods.setQuantity(afterQuantity1);
//            sysStoreGoods.setQuantity2(afterQuantity2);
//
//            if (afterQuantity1 < 0 || afterQuantity2 < 0) {
//                throw CustomException.instance("库存数量不足");
//            }
//
//            Integer warnStatus = 1;
//            if (sysStoreGoods.getWarnQuantity() > 0) {
//                if (sysStoreGoods.getQuantity() <= 0) {
//                    warnStatus = 3;
//                } else if (sysStoreGoods.getQuantity() < sysStoreGoods.getWarnQuantity()) {
//                    warnStatus = 2;
//                } else {
//                    warnStatus = 1;
//                }
//            }
//            sysStoreGoods.setWarnStatus(warnStatus);
//            sysStoreGoods.setUpdateTime(curTime);
//            listUpdate.add(sysStoreGoods);
//
//            totalQuantity += record.getQuantity() + record.getQuantity2();
//            if (record.getGoodsCostPrice() != null && record.getGoodsCostPrice().compareTo(BigDecimal.ZERO) > 0) {
//                totalCost = totalCost.add(record.getGoodsCostPrice().multiply(new BigDecimal(record.getQuantity())));
//            }
//            if (record.getGoodsCostPrice2() != null && record.getGoodsCostPrice2().compareTo(BigDecimal.ZERO) > 0) {
//                totalCost = totalCost.add(record.getGoodsCostPrice2().multiply(new BigDecimal(record.getQuantity2())));
//            }
//            if (record.getGoodsSalePrice() != null && record.getGoodsSalePrice().compareTo(BigDecimal.ZERO) > 0) {
//                totalSale = totalSale.add(record.getGoodsSalePrice().multiply(new BigDecimal(record.getQuantity())));
//            }
//            if (record.getGoodsSalePrice2() != null && record.getGoodsSalePrice2().compareTo(BigDecimal.ZERO) > 0) {
//                totalSale = totalSale.add(record.getGoodsSalePrice2().multiply(new BigDecimal(record.getQuantity2())));
//            }
//
//            if (sysStoreGoods.getWarnStatus() != 1) {
//                listWarnGoods.add(sysStoreGoods);
//            }
//        }
//
//        sysStoreInout.setTotalQuantity(totalQuantity);
//        sysStoreInout.setTotalCost(totalCost);
//        sysStoreInout.setTotalSale(totalSale);
//        if (!sysStoreInoutService.save(sysStoreInout)) {
//            throw CustomException.instance("出库操作失败");
//        }
//        if (!sysStoreInoutDetailService.saveBatch(listRecord)) {
//            throw CustomException.instance("出库操作失败");
//        }
//
//        if (listUpdate.size() > 0) {
//            if (!sysStoreGoodsService.updateBatchById(listUpdate)) {
//                throw CustomException.instance("出库操作失败");
//            }
//        }
//
//        if (listWarnGoods.size() > 0) {
//            SysEmp sysEmp = sysEmpService.getByEmp(sysShop.getManagerId());
//            if (sysEmp != null) {
//                for (SysStoreGoods sysStoreGoods2 : listWarnGoods) {
//                    smsService.sendWarn(sysEmp.getPhone(), sysStoreGoods2.getGoodsName(), sysStoreGoods2.getQuantity(), sysStoreGoods2.getGoodsUnitName());
//                }
//            }
//        }
//
//        return sysStoreInout;
//    }
//
//    @Transactional
//    @Override
//    public SysStoreInout reback(Long relevanceId, Long operator, String operatorName) {
//        SysStoreInout sysStoreInout = sysStoreInoutService.getByRelevanceId(relevanceId);
//        if (sysStoreInout == null) {
//            return null;
//        }
//        if (sysStoreInout.getDelTag() != CommonDefine.DELTAG_NO) {
//            return null;
//        }
//        List<SysStoreInoutDetail> listDetail = sysStoreInoutDetailService.listByStoreInoutId(sysStoreInout.getStoreInoutId());
//        if (listDetail == null || listDetail.size() == 0) {
//            return null;
//        }
//        Long shopId = sysStoreInout.getShopId();
//        Integer objectType = sysStoreInout.getObjectType();
//
//        List<StoreInout> listStoreInout = new ArrayList<>();
//        for (SysStoreInoutDetail sysStoreInoutDetail : listDetail) {
//            listStoreInout.add(new StoreInout(sysStoreInoutDetail.getGoodsId(), 0L, sysStoreInoutDetail.getQuantity(), sysStoreInoutDetail.getQuantity2()));
//        }
//
//        boolean storeInoutResult = false;
//        int inoutType = sysStoreInout.getStoreInoutType();
//        if (inoutType == StoreDefine.STORE_IN) {
//            storeInoutResult = storeOut(shopId, objectType, listStoreInout);
//        } else if (inoutType == StoreDefine.STORE_OUT)  {
//            storeInoutResult = storeIn(shopId, objectType, listStoreInout);
//        }
//        if (!storeInoutResult) {
//            return null;
//        }
//
//        Timestamp curTime = DateUtils.getNowTimestamp();
//        sysStoreInout.setDelTag(CommonDefine.DELTAG_YES);
//        sysStoreInout.setUpdateUser(operator);
//        sysStoreInout.setUpdateUserName(operatorName);
//        sysStoreInout.setUpdateTime(curTime);
//        for (SysStoreInoutDetail sysStoreInoutDetail : listDetail) {
//            sysStoreInoutDetail.setDelTag(CommonDefine.DELTAG_YES);
//            sysStoreInoutDetail.setUpdateUser(operator);
//            sysStoreInoutDetail.setUpdateUserName(operatorName);
//            sysStoreInoutDetail.setUpdateTime(curTime);
//        }
//        if (!sysStoreInoutService.updateById(sysStoreInout)) {
//            return null;
//        }
//        if (!sysStoreInoutDetailService.updateBatchById(listDetail)) {
//            return null;
//        }
//
//        return sysStoreInout;
//    }
//
//    // 撤回入库
//    private boolean storeIn(Long shopId, Integer objectType, List<StoreInout> listStoreIn) {
//        if (listStoreIn == null || listStoreIn.size() == 0) {
//            return false;
//        }
//        List<Long> listGoodsId = listStoreIn.stream().map(StoreInout::getGoodsId).collect(Collectors.toList());
//        List<SysStoreGoods> listStoreGoods = sysStoreGoodsService.listByGoodsId(shopId, listGoodsId);
//        if (listStoreGoods == null || listStoreGoods.size() == 0) {
//            return false;
//        }
//        Map<Long, SysStoreGoods> mapStoreGoods = listStoreGoods.stream().collect(Collectors.toMap(SysStoreGoods::getGoodsId, d->d,(key1 , key2)-> key2));
//
//        List<SysStoreGoods> listAdd = new ArrayList<>();
//        List<SysStoreGoods> listUpdate = new ArrayList<>();
//
//        for (StoreInout storeInout : listStoreIn) {
//            long goodsId = storeInout.getGoodsId();
//            SysStoreGoods sysStoreGoods = mapStoreGoods.get(goodsId);
//            if (sysStoreGoods == null) {
//                SysGoods sysGoods = sysGoodsService.getByPrimaryId(goodsId);
//                if (sysGoods == null) {
//                    return false;
//                }
//                sysStoreGoods = SysStoreGoods.createNew(shopId, sysGoods);
//                if (sysStoreGoods == null) {
//                    return false;
//                }
//                listAdd.add(sysStoreGoods);
//            } else {
//                listUpdate.add(sysStoreGoods);
//            }
//
//            DiscountBaseInfo discountBaseInfo = discountService.getDiscount(objectType, shopId, goodsId);
//            if (discountBaseInfo == null) {
//                throw CustomException.instance("获取不到折扣值");
//            }
//            // 设置库存的折扣、数量、成本、售价（平均分摊法）
//            if (!caclStoreDiscount(sysStoreGoods, discountBaseInfo, storeInout.getQuantity(), storeInout.getQuantity2(), StoreDefine.STORE_IN)) {
//                throw CustomException.instance("入库失败");
//            }
//        }
//
//        if (listAdd.size() > 0) {
//            if (!sysStoreGoodsService.saveBatch(listAdd)) {
//                return false;
//            }
//        }
//        if (listUpdate.size() > 0) {
//            if (!sysStoreGoodsService.updateBatchById(listUpdate)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    // 撤回出库
//    private boolean storeOut(Long shopId, Integer objectType, List<StoreInout> listStoreOut) {
//        if (listStoreOut == null || listStoreOut.size() == 0) {
//            return false;
//        }
//        List<Long> listGoodsId = listStoreOut.stream().map(StoreInout::getGoodsId).collect(Collectors.toList());
//        List<SysStoreGoods> listStoreGoods = sysStoreGoodsService.listByGoodsId(shopId, listGoodsId);
//        if (listStoreGoods == null || listStoreGoods.size() == 0) {
//            return false;
//        }
//        if (listStoreGoods.size() != listStoreOut.size()) {
//            return false;
//        }
//
//        Map<Long, SysStoreGoods> mapStoreGoods = listStoreGoods.stream().collect(Collectors.toMap(SysStoreGoods::getGoodsId, d->d,(key1 , key2)-> key2));
//
//        List<SysStoreGoods> listUpdate = new ArrayList<>();
//
//        for (StoreInout storeInout : listStoreOut) {
//            long goodsId = storeInout.getGoodsId();
//            SysStoreGoods sysStoreGoods = mapStoreGoods.get(goodsId);
//            if (sysStoreGoods == null) {
//                return false;
//            }
//
//            DiscountBaseInfo discountBaseInfo = discountService.getDiscount(objectType, shopId, goodsId);
//            if (discountBaseInfo == null) {
//                throw CustomException.instance("获取不到折扣值");
//            }
//            // 设置库存的折扣、数量、成本、售价（平均分摊法）
//            if (!caclStoreDiscount(sysStoreGoods, discountBaseInfo, storeInout.getQuantity(), storeInout.getQuantity2(), StoreDefine.STORE_OUT)) {
//                throw CustomException.instance("出库失败");
//            }
//            listUpdate.add(sysStoreGoods);
//        }
//
//        if (listUpdate.size() > 0) {
//            if (!sysStoreGoodsService.updateBatchById(listUpdate)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//
//    private boolean caclStoreDiscount(SysStoreGoods sysStoreGoods, DiscountBaseInfo discountBaseInfo, Integer quantity1, Integer quantity2, int inoutType) {
//        Integer rate = sysStoreGoods.getRate();
//
//        BigDecimal oriQuantity = BigDecimal.ZERO;
//        if (sysStoreGoods.getQuantity() != null && sysStoreGoods.getQuantity() > 0) {
//            oriQuantity = new BigDecimal(sysStoreGoods.getQuantity());
//        }
//        // 先把小单位的数量换算成大单位的数量
//        if (rate != null && rate > 0 && sysStoreGoods.getQuantity2() != null && sysStoreGoods.getQuantity2() > 0) {
//            BigDecimal smallQuantity = new BigDecimal(sysStoreGoods.getQuantity2()).divide(new BigDecimal(rate), 4, RoundingMode.HALF_UP);
//            oriQuantity = oriQuantity.add(smallQuantity);
//        }
//        BigDecimal oriAllSale = sysStoreGoods.getGoodsSalePrice().multiply(oriQuantity);
//        BigDecimal oriAllCost = oriAllSale.multiply(sysStoreGoods.getDiscount());
//
//        BigDecimal curQuantity = BigDecimal.ZERO;
//        if (quantity1 != null && quantity1 > 0) {
//            curQuantity = new BigDecimal(quantity1);
//        }
//        if (rate != null && rate > 0 && quantity2 != null && quantity2 > 0) {
//            BigDecimal smallQuantity = new BigDecimal(quantity2).divide(new BigDecimal(rate), 4, RoundingMode.HALF_UP);
//            curQuantity = curQuantity.add(smallQuantity);
//        }
//        BigDecimal curAllSale = discountBaseInfo.getSale().multiply(curQuantity);
//        BigDecimal curAllCost = curAllSale.multiply(discountBaseInfo.getDiscount());
//
//        BigDecimal allSale = null;
//        BigDecimal allCost = null;
//        Integer allQuantity1 = 0;
//        Integer allQuantity2 = 0;
//        BigDecimal allQuantity = null;
//        if (inoutType == StoreDefine.STORE_IN) {
//            allSale = oriAllSale.add(curAllSale);
//            allCost = oriAllCost.add(curAllCost);
//            allQuantity1 = sysStoreGoods.getQuantity() + quantity1;
//            allQuantity2 = sysStoreGoods.getQuantity2() + quantity2;
//            allQuantity = oriQuantity.add(curQuantity);
//        } else {
//            allSale = oriAllSale.subtract(curAllSale);
//            allCost = oriAllCost.subtract(curAllCost);
//            allQuantity1 = sysStoreGoods.getQuantity() - quantity1;
//            allQuantity2 = sysStoreGoods.getQuantity2() - quantity2;
//            allQuantity = oriQuantity.subtract(curQuantity);
//        }
//        if (allSale.compareTo(BigDecimal.ZERO) < 0 || allCost.compareTo(BigDecimal.ZERO) < 0 || allQuantity1 < 0 || allQuantity2 < 0 || allQuantity.compareTo(BigDecimal.ZERO) < 0) {
//            return false;
//        }
//        BigDecimal discountRate = BigDecimal.ONE;
//        if (allSale.compareTo(BigDecimal.ZERO) > 0 && allCost.compareTo(BigDecimal.ZERO) > 0) {
//            discountRate = allCost.divide(allSale, 2, RoundingMode.HALF_UP);
//        }
//
//        BigDecimal newCost = BigDecimal.ZERO;    // 大单位的平均成本价
//        if (allQuantity.compareTo(BigDecimal.ZERO) > 0) {
//            newCost = allSale.multiply(discountRate).divide(allQuantity, 2, RoundingMode.HALF_UP);
//        } else {
//            newCost = discountBaseInfo.getSale().multiply(discountRate);
//        }
//        BigDecimal newCost2 = BigDecimal.ZERO;  // 小单位的平均成本价
//        if (allQuantity1 > 0 && allQuantity2 > 0) {
//            newCost2 = allCost.subtract(newCost.multiply(new BigDecimal(allQuantity1))).divide(new BigDecimal(allQuantity2), RoundingMode.HALF_UP);  // 小单位的平均成本价
//        } else {
//            newCost2 = discountBaseInfo.getSale2().multiply(discountRate);
//        }
//
//        sysStoreGoods.setQuantity(allQuantity1);
////        sysStoreGoods.setGoodsSalePrice(discountBaseInfo.getSale());
//        sysStoreGoods.setGoodsCostPrice(newCost);
//        sysStoreGoods.setQuantity2(allQuantity2);
////        sysStoreGoods.setGoodsSalePrice2(discountBaseInfo.getSale2());
//        sysStoreGoods.setGoodsCostPrice2(newCost2);
//        sysStoreGoods.setDiscount(discountRate);
//
//        Integer warnStatus = 1;
//        if (sysStoreGoods.getWarnQuantity() > 0) {
//            if (sysStoreGoods.getQuantity() <= 0) {
//                warnStatus = 3;
//            } else if (sysStoreGoods.getQuantity() < sysStoreGoods.getWarnQuantity()) {
//                warnStatus = 2;
//            } else {
//                warnStatus = 1;
//            }
//        }
//        sysStoreGoods.setWarnStatus(warnStatus);
//        sysStoreGoods.setUpdateTime(DateUtils.getNowTimestamp());
//
//        return true;
//    }
//
//}
