<template>
    <view class="wrapper">
        <view class="background-color"></view>
        <view class="header">
            <view class="header-layer1">
                <view class="header-title">赊账金额：{{keepDecimal(parseFloat(detailsInfo.realCope))}}</view>
<!--                <view>{{statusFilter(detailsInfo.status)}}</view>-->
            </view>
            <view class="header-layer2">
                <view>总额度：{{keepDecimal(detailsInfo.allQuota)}}</view>
                <view>剩余额度：{{keepDecimal(detailsInfo.afterSurplusQuota)}}</view>
            </view>
            <view class="line" style="margin-bottom: 20rpx"></view>
            <view class="header-layer2">
                <view>赊账日期：{{formatDate(detailsInfo.creditDate)}}</view>
                <view>操作员工：{{detailsInfo.operatorName}}</view>
            </view>
            <view class="line" style="margin-bottom: 10rpx"></view>
        </view>

        <view class="card">
            <view v-if="detailsInfo.listNode.length > 0" class="title">消费商品明细</view>
            <view v-else class="title">无商品明细</view>
            <view v-for="(item, index) in detailsInfo.listNode" :key="index" class="card-row item-card">
                <view v-if="detailsInfo.listNode.length > 1" class="head">订单：{{ index+1 }}--{{ orderTypeFilter(item.orderType) }}</view>

                <view
                    v-for="(child,childIndex) in item.listDetail"
                    :key="childIndex"
                    class="item-content">
                    <view class="picture-wrap">
                        <view v-if="!child.goodsLogo" class="picture-default">茶</view>
                        <image v-else="item.goodsLogo" class="picture-img" :src="viewImage + child.goodsLogo + fileViewAfter"></image>
                    </view>
                    <view class="card-row-info">
                        <view class="card-row-info-layer1">
                            <view>【{{child.goodsName}}】{{child.goodsKindName}}</view>
                        </view>
                        <view class="card-row-info-layer2">
                            <view>规格：{{child.goodsSpecification}}</view>
                        </view>
                        <view v-if="child.quantity > 0" class="card-row-info-layer2">
                            <view>{{keepDecimal(parseFloat(child.salePrice))}}元</view>
                            <view>x{{child.quantity}}{{ child.goodsUnitName }}</view>
                        </view>
                        <view v-if="child.quantity2 > 0" class="card-row-info-layer2">
                            <view>{{keepDecimal(parseFloat(child.salePrice2))}}元</view>
                            <view>x{{child.quantity2}}{{ child.goodsUnitName2 }}</view>
                        </view>
                    </view>
                </view>

                <view v-if="item.listDetail.length > 0" class="line" style="margin-top: 10rpx"></view>
<!--                <view class="info-row">-->
<!--                    <view class="info-row-label">-->
<!--                        消费类型：{{ rcTypeFilter(item.rcType) }}-->
<!--                    </view>-->
<!--                    <view class="info-row-value">-->
<!--                        支付方式：{{ payTypeFilter(item.payType) }}-->
<!--                    </view>-->
<!--                </view>-->
                <view class="info-row">
                    <view class="info-row-label">
                        优惠金额
                    </view>
                    <view class="info-row-value">
                        {{keepDecimal(parseFloat(item.discountCope))}}元
                    </view>
                </view>
                <view class="info-row">
                    <view class="info-row-label-main">
                        合计金额
                    </view>
                    <view class="info-row-value-main">
                        {{keepDecimal(parseFloat(item.realCope))}}元
                    </view>
                </view>

                <view class="line" style="margin-top: 10rpx"></view>
                <view class="info-row">
                    <view class="info-row-label-main">
                        开单时间
                    </view>
                    <view class="info-row-label">
                        {{formatDate(item.submitTime)}}
                    </view>
                </view>
                <view v-if="item.status !== 1 && item.status !== 3" class="info-row">
                    <view class="info-row-label-main">
                        结账时间
                    </view>
                    <view class="info-row-label">
                        {{formatDate(item.payTime)}}
                    </view>
                </view>
                <view v-if="item.status === 4" class="info-row">
                    <view class="info-row-label-main">
                        退单时间
                    </view>
                    <view class="info-row-label">
                        {{formatDate(item.refundTime)}}
                    </view>
                </view>
<!--                <view class="info-row">-->
<!--                    <view class="info-row-label-main">-->
<!--                        订单编号-->
<!--                    </view>-->
<!--                    <view class="info-row-label">-->
<!--                        {{detailsInfo.orderId}}-->
<!--                        <view class="copy-btn" @click="handleCopy(detailsInfo.orderId)">-->
<!--                            复制-->
<!--                        </view>-->
<!--                    </view>-->
<!--                </view>-->
            </view>
        </view>
    </view>
</template>

<script setup>
import {ref, reactive, onMounted} from "vue"
import {onShow, onUnload} from "@dcloudio/uni-app";
import {routeParams} from '@/router'
import http from "../../../lib/request";
import dayjs from 'dayjs'
import {viewImage,fileViewAfter} from '@/env';
import {keepDecimal} from '@/util/common'

let creditId = ref(null)

let detailsInfo =reactive({
    listNode: []
})

// 获取赊账明细详情
function handleInfo() {
    http('memberCredit.getCreditRecord',{primaryId: creditId.value},'加载中...').then(res=>{
        if (res.code === 200 && res.data) {
            detailsInfo = Object.assign(detailsInfo,{
                orderId: res.data.payId,
                realCope: res.data.realCope,
                allQuota: res.data.allQuota,
                afterSurplusQuota: res.data.afterSurplusQuota,
                creditDate: formatDate(res.data.creditDate),
                operatorName: res.data.operatorName
            })

            // 赊账消费明细
            listOrderByPayId(res.data.payId)
        }
    })
}

// 根据支付单号查询订单列表(消费明细)
function listOrderByPayId(id) {
    http('memberBill.listOrderByPayId',{orderPayId: id},'加载中...').then(res=>{
        if (res.code === 200 && res.data) {
            detailsInfo.listNode = []
            detailsInfo.listNode = res.data
        }
    })
}

function formatDate(nowDate) {
    if (!nowDate) {
        return  ''
    }

    return dayjs(nowDate).format("YYYY-MM-DD HH:mm")
}

// // 拷贝
// function handleCopy(copyData) {
//     uni.setClipboardData({
//         data: copyData.toString(),
//         success: ()=> {
//             uni.showToast({
//                 title: '复制成功!',
//                 icon: 'none',
//                 duration: 800
//             })
//         }
//     })
// }

function orderTypeFilter(status) {
    switch (status) {
        case 1:
            return '商品消费'
        case 2:
            return '服务消费'
        case 3:
            return '包间消费'
    }
}

onShow(()=>{
    let params = routeParams()
    if (params) {
        creditId.value = params.creditId
        handleInfo()
    }
})

</script>

<style lang="scss" scoped>
    .background-color {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: rgb(247, 247, 247);
        z-index: -1;
    }

    .header {
        height: 360rpx;
        background: linear-gradient(0deg, #8B6445 0%, #A57046 100%);
        border-radius: 0rpx 0rpx 50rpx 50rpx;
        padding: 50rpx 30rpx;
        color: #FFFFFF;
        .header-layer1 {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 39rpx;
            border-bottom: 2px dashed #CFAE96;
            opacity: 0.97;
            margin-bottom: 10rpx;
            .header-title {
                font-size: 44rpx;
            }
        }
        .header-layer2 {
            display: flex;
            justify-content: space-between;
            font-size: 28rpx;
        }
    }

    .card {
        border-radius: 30rpx;
        background: #FFFFFF;
        padding: 20rpx 30rpx 40rpx 30rpx;
        width: 85%;
        margin: 0 auto 20rpx auto;
        transform: translateY(-90rpx);
        .title {
            margin-bottom: 20rpx;
            font-size: 36rpx;
            font-weight: 600;
            color: #8B6445;
            text-align: center;
        }
        .line {
            border-top: 1rpx solid #EEEEEE;
        }
        .card-row {
            margin-bottom: 22rpx;
            display: flex;
            //align-items: center;

            .picture-wrap {
                width: 120rpx;
                height: 120rpx;

                .picture-default {
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    width: 100%;
                    height: 100%;
                    background-color: $uni-color-primary;
                    color: white;
                    border-radius: 10rpx;
                    font-size: 40rpx;
                }
                .picture-img {
                    width: 100%;
                    height: 100%;
                    border-radius: 10rpx;
                }
            }
            .card-row-info {
                margin-left: 20rpx;
                width: 100%;
                .card-row-info-layer1 {
                    display: flex;
                    justify-content: space-between;
                    font-size: 28rpx;
                    color: #202020;
                    margin-bottom: 20rpx;
                }
                .card-row-info-layer2 {
                    margin-left: 15rpx;
                    display: flex;
                    justify-content: space-between;
                    font-size: 24rpx;
                    color: #999999;
                }
            }
        }

        .item-card {
            display: flex;
            flex-direction: column;
            //align-items: center;

            .head {
                margin-top: 10rpx;
                border-top: 1rpx solid #EEEEEE;
                padding: 10rpx 0;
                font-size: 36rpx;
                color: #8B6445;
                font-weight: 600;
                display: flex;
                flex: 1;
                align-items: center;
            }

            .item-content {
                margin-bottom: 22rpx;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
        }

        .info-row {
            display: flex;
            justify-content: space-between;
            margin-top: 25rpx;
            .info-row-label {
                font-size: 28rpx;
                color: #666666;
            }
            .info-row-value {
                font-size: 28rpx;
                color: #202020;
                display: flex;
                align-items: center;
            }
            .info-row-label-main {
                font-size: 28rpx;
                color: #202020;
            }
            .info-row-value-main {
                font-size: 32rpx;
                color: #FF1E1A;
            }
        }

        .copy-btn {
            height: 50rpx;
            width: 82rpx;
            border: 1rpx solid #CCCCCC;
            border-radius: 8rpx;
            text-align: center;
            line-height: 50rpx;
            margin-left: 22rpx;
            position: relative;
        }
        .copy-btn:before {
            content: '';
            position: absolute;
            top: -10rpx;
            bottom: -10rpx;
            left: -10rpx;
            right: -10rpx;
        }
        .copy-btn:active {
            background-color: #EEEEEE;
        }
    }
</style>
