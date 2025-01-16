<template>
    <view>
        <view class="background-color"></view>
        <view class="header">
            <view class="header-layer1">
                <view class="header-title">{{dataInfo.orderName}}</view>
            </view>
            <view class="header-layer2">
                <view v-if="typeName == 'lingQu'">领取时间：{{formatDate(dataInfo.orderDate)}}</view>
                <view v-if="typeName == 'jiCun'">寄存时间：{{formatDate(dataInfo.orderDate)}}</view>
<!--                <view v-if="typeName == 'jiCun'">寄存会员：{{dataInfo.membersName}}</view>-->
<!--                <view v-if="typeName == 'lingQu'">领取会员：{{dataInfo.membersName}}</view>-->
            </view>
        </view>

        <view class="card">
            <view v-for="(item, index) in dataInfo.listDetail" :key="index" class="card-row">
                <view class="picture-wrap">
                    <view v-if="!item.goodsLogo" class="picture-default">茶</view>
<!--                    <image v-else="item.goodsLogo" class="picture-img" :src="viewImage + item.goodsLogo + fileViewAfter"></image>-->
                    <image v-else="item.goodsLogo" class="picture-img" :src="viewImage + item.goodsLogo"></image>
                </view>
                <view class="card-row-info">
                    <view class="card-row-info-layer1">
                        <view>【{{item.goodsName}}】{{item.goodsSupplierBrand}}</view>
                    </view>
                    <view class="card-row-info-layer2">
                        <view>规格：{{item.goodsSpecification}}</view>
                    </view>
                    <view v-if="item.quantity > 0" class="card-row-info-layer2">
                        <view>{{keepDecimal(parseFloat(item.goodsSalePrice))}}元</view>
                        <view>x{{item.quantity}}{{ item.goodsUnitName }}</view>
                    </view>
                    <view v-if="item.quantity2 > 0" class="card-row-info-layer2">
                        <view>{{keepDecimal(parseFloat(item.goodsSalePrice2))}}元</view>
                        <view>x{{item.quantity2}}{{ item.goodsUnitName2 }}</view>
                    </view>
                    <view v-if="printName == 'jiCun'" class="card-row-info-layer2">
                        <view>寄存截止时间：{{(formatDate(item.depositEndDate)) || '无'}}</view>
                        <view></view>
                    </view>
                </view>
            </view>
            <view class="line"></view>
            <view class="info-row">
                <view class="info-row-label-main"></view>
                <view class="info-row-value-main">
                    合计: {{keepDecimal(parseFloat(dataInfo.totalSale))}}元
                </view>
            </view>
<!--            <view class="info-row">-->
<!--                <view class="info-row-label-main">-->
<!--                    {{`【${dataInfo.operatorName}】在${formatOtherDate(dataInfo.timestamp)}操作`}}-->
<!--                </view>-->
<!--                <view class="info-row-value-main"></view>-->
<!--            </view>-->
        </view>

        <view class="card">
            <view class="card-bottom">
                <view class="info-row no-margin-top">
                    <view class="info-row-label">
                        寄存单编号
                    </view>
                    <view class="info-row-value">
                        {{dataInfo.teaStorageOrderId}}
                        <view class="copy-btn" @click="handleCopy(dataInfo.orderNo)">
                            复制
                        </view>
                    </view>
                </view>
            </view>
        </view>
    </view>
</template>

<script setup>
import {ref, reactive} from "vue"
import {onLoad, onShow,onUnload} from "@dcloudio/uni-app";
import dayjs from 'dayjs'
import http from "../../../lib/request";
import inlineStyleConfig from "@/lib/system/inlineStyleConfig";
import { storage } from '@/lib/system/storage'
import {viewImage,fileViewAfter} from '@/env';
import { routeParams } from '@/router'
import {keepDecimal} from '@/util/common'

let teaStorageOrderId = ref(null)
let typeName = ref('')
let dataInfo = ref({
    // submitTime: null,
    // roomPeriod: '',
    // customNumber: 0,
    // timeRuleName: '',
    // empName: '',
    // membersName: '',
    // payTypeName: '',
    //
    listDetail: []
})

// 寄存消费详情
function getInfo() {
    http('teaStorage.getTeaStorageOrderInfo', {primaryId: teaStorageOrderId.value}, '加载中').then(res => {
        if (res.code === 200 && res.data) {
            dataInfo.value = res.data
            dataInfo.value.listDetail = []

            getInfoDetail()
        }
    })
}

// 寄存消费商品明细详情
function getInfoDetail() {
    http('teaStorage.listTeaStorageOrderByPrimaryId',{primaryId: teaStorageOrderId.value}, '加载中').then(res=>{
        if (res.code === 200) {
            dataInfo.value.listDetail = res.data
        }
    })
}

// 拷贝
function handleCopy(copyData) {
    uni.setClipboardData({
        data: copyData.toString(),
        success: ()=> {
            uni.showToast({
                title: '复制成功!',
                icon: 'none',
                duration: 800
            })
        }
    })
}

function formatDate(nowDate) {
    if (!nowDate) {
        return  ''
    }

    return dayjs(nowDate).format("YYYY-MM-DD")
}

onLoad((option) => {
    teaStorageOrderId.value = option.teaStorageOrderId
    if (option.typeName) {
        typeName.value = option.typeName
    }
    switch (option.typeName) {
        case 'jiCun':
            uni.setNavigationBarTitle({
                title: '寄存商品明细'
            })
            break;
        case 'lingQu':
            uni.setNavigationBarTitle({
                title: '领取寄存明细'
            })
            break;
    }
})

onShow(() => {
    getInfo()
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
            margin-bottom: 36rpx;
            .header-title {
                font-size: 40rpx;
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
        padding: 40rpx 30rpx;
        width: 85%;
        margin: 0 auto 20rpx auto;
        transform: translateY(-100rpx);
        .line {
            border-top: 1rpx solid #EEEEEE;
        }
        .card-row {
            margin-bottom: 22rpx;
            display: flex;
            align-items: center;
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
            .info-row-label-main {
                font-size: 28rpx;
                color: #666666;
            }
            .info-row-value-main {
                font-size: 32rpx;
                color: #FF1E1A;
            }
        }
    }

    .no-margin-top {
        margin-top: 0 !important;
    }
</style>
