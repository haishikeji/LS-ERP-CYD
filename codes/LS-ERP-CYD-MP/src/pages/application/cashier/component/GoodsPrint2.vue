<template>
    <view>
        <u-modal v-model="visibleShow"
            @confirm="confirmModal"
            @cancel="cancel"
            closeOnClickOverlay
            showCancelButton
            :confirmColor="inlineStyleConfig.uPprimary">
            <view class="content">
                <view class="title">{{dataInfo.shopName}}</view>
                <view class="title-info">{{dataInfo.aliasShopName}}</view>
                <view class="header">
                    <view class="header-row">
                        <view class="header-row-label">机号：</view>
                        <view>{{dataInfo.machineNumber}}</view>
                    </view>
                    <view class="header-row">
                        <view class="header-row-label">订单号：</view>
                        <view>{{dataInfo.orderNo}}</view>
                    </view>
                    <view class="header-row">
                        <view class="header-row-label">类型：</view>
                        <view>{{dataInfo.saleTypeName}}</view>
                    </view>
                    <view class="header-row">
                        <view class="header-row-label">操作员工：</view>
                        <view>{{dataInfo.empName}}</view>
                    </view>
                    <view class="header-row">
                        <view class="header-row-label">使用时间：</view>
                        <view>{{formatDate(dataInfo.openTime)}}</view>
                    </view>
                </view>
                <view class="double-line"></view>

                <!-- 列表-->
                <view class="layer">
                    <view class="table-header">
                        <view class="table-header-column1">商品</view>
                        <view class="table-header-column">单价</view>
                        <view class="table-header-column">数量</view>
                        <view class="table-header-column">成交价</view>
                    </view>
                    <view v-for="(item,index) in dataInfo.listDetail" :key="index" class="table-row">
                        <view class="table-row-column1">{{item.goodsName}}</view>
                        <view class="table-row-column">{{keepDecimal(parseFloat(item.salePrice))}}</view>
                        <view class="table-row-column">{{item.quantity || 0}}</view>
                        <view class="table-row-column">{{keepDecimal(parseFloat(item.dealCope))}}</view>
                    </view>
                </view>
                <view class="double-line"></view>
                <view class="layer">
                    <view class="layer-row1">
                        <view>小计</view>
                        <view>{{keepDecimal(parseFloat(dataInfo.dealCope))}}</view>
                    </view>
                    <view class="layer-row">
                        <view>合计优惠</view>
                        <view>{{keepDecimal(parseFloat(dataInfo.discountsCope))}}</view>
                    </view>
                    <view class="layer-row">
                        <view>应付</view>
                        <view>{{keepDecimal(parseFloat(dataInfo.realCope))}}</view>
                    </view>
                </view>
                <view class="double-line"></view>
                <view class="layer">
                    <view class="layer-row1">
                        <view>{{dataInfo.payType}}</view>
                        <view>{{keepDecimal(parseFloat(dataInfo.realCope))}}</view>
                    </view>
                    <view class="layer-row">
                        <view>流水号：{{dataInfo.waterNo}}</view>
                    </view>
                </view>
                <view class="line"></view>
                <view class="layer">
                    <view class="layer-row">
                        请确认小票上的消费清单，保留好小票作开发
                        票，退货凭证
                    </view>
                    <view class="layer-row">
                        客服电话：{{dataInfo.customPhone}}
                    </view>
                </view>
            </view>
        </u-modal>
    </view>
</template>

<script setup>
import {reactive, ref, defineProps, defineEmits, onMounted, watch, computed} from 'vue'
import {onLoad, onShow} from '@dcloudio/uni-app'
import dayjs from 'dayjs'
import {keepDecimal} from '@/util/common'

import inlineStyleConfig from "@/lib/system/inlineStyleConfig"
import http from '@/lib/request'

const props = defineProps({
    show: {
        type: Boolean,
        default: false
    },
    orderId: {
        type: Number,
        default: null
    }
})

const emit = defineEmits(['input','update:modelValue', 'confirm'])

let dataInfo = ref({
    orderId:"", //订单ID
    shopName:"", //门店名称
    aliasShopName:"", //门店别名
    machineNumber:"", //机号
    orderNo:"", //订单号
    saleTypeName:"", //销售类型 1-门店营业 2-零售商品"
    empName:"", //操作员工
    openTime:"", //使用时间
    roomName:"", //房间名称
    roomCope:"", //包间消费
    serviceCope:"", //服务费
    dealCope:"", //小计
    discountsCope:"", //优惠金额
    realCope:"", //应付
    payType:"", //支付方式
    waterNo:"", //流水号
    customPhone:"", //客服联系电
    listDetail: [
        // "orderDetailId":"long //销售订单详情ID",
        // "invGoodsId":"long //库存商品ID",
        // "goodsName":"string //商品名称",
        // "unit":"string //单位",
        // "quantity":"int //数量",
        // "costPrice":"double //成本价",
        // "salePrice":"double //零售价",
        // "costCope":"double //成本金额",
        // "dealCope":"double //应付金额"
    ]
})

let visible = computed(()=>{
  // #ifndef VUE3
  return props.value;
  // #endif

  // #ifdef VUE3
  return props.modelValue;
  // #endif
})
let visibleShow = ref(false)

watch(()=>visible.value,(newValue,oldValue)=>{
  visibleShow.value = newValue
})

watch(() => visibleShow.value, (newValue, oldValue) => {
    if (newValue) {
        getInfo()
    }
})

function getInfo() {
    http('cashier.getOrderTicket', {orderId: props.orderId}).then(res => {
        if (res.code === 200 && res.data) {
            dataInfo.value = res.data
        }
    })
}

function confirmModal() {
    cancel()
}

function cancel() {
  emit('update:modelValue', false);
  emit('input', false);
}

function formatDate(nowDate) {
    if (!nowDate) {
        return  ''
    }
    return dayjs(nowDate).format("YYYY-MM-DD HH:mm")
}

</script>

<style lang="scss" scoped>
.content {
    width: 100%;
    height: 70vh;
    overflow-y: scroll;
}

.title {
    text-align: center;
}

.title-info {
    text-align: center;
}

.header {
    margin-top: 40rpx;
    margin-bottom: 4rpx;
    .header-row {
        display: flex;
        margin-bottom: 16rpx;
    }
}

.double-line {
    width: 100%;
    height: 6rpx;
    border-top: 1rpx dashed #333333;
    border-bottom: 1rpx dashed #333333;
    //margin-bottom: 6rpx;
}

.line {
    width: 100%;
    border-bottom: 1rpx dashed #333333;
    //margin-bottom: 6rpx;
}

.layer {
    padding: 20rpx 0;
    .layer-row1 {
        display: flex;
        justify-content: space-between;
    }
    .layer-row {
        display: flex;
        justify-content: space-between;
        padding-top: 15rpx;
    }
}

.table-header {
    display: flex;
    .table-header-column1 {
        flex: 1;
        text-align: start;
    }
    .table-header-column {
        flex: 1;
        text-align: end;
    }
}
.table-row {
    display: flex;
    .table-row-column1 {
        flex: 1;
        text-align: start;
    }
    .table-row-column {
        flex: 1;
        text-align: end;
    }
}

</style>
