<template>
    <view class="wrapper">
        <sj-card title="基础信息" :isShowLine="false">
            <view class="info-row">
                <view class="label">调拨单号</view>
                <view class="value">{{formData.tunDrawNum}}</view>
            </view>
            <view class="info-row">
                <view class="label">调拨日期</view>
                <view class="value">{{formData.tundrawDate}}</view>
            </view>
            <view class="info-row">
                <view class="label">调出仓库</view>
                <view class="value">{{formData.outStoreName}}</view>
            </view>
            <view class="info-row">
                <view class="label">调入仓库</view>
                <view class="value">{{formData.inStoreName}}</view>
            </view>
            <view class="info-row">
                <view class="label">调拨员工</view>
                <view class="value">{{formData.operatorName}}</view>
            </view>
            <view class="info-row">
                <view class="label">调拨状态</view>
                <view class="value main-color">{{statusFilter(formData.status)}}</view>
            </view>
            <view class="info-row"></view>
        </sj-card>

        <sj-card title="调拨商品" :isShowLine="false">
            <view
                v-for="(item,index) in formData.listDetail"
                :key="index"
                class="goods-row">
                <view class="card-left">
                    <image :src="!!item.goodsLogo ? fileSrc(item.goodsLogo) : ''" class="staff-icon"></image>
                </view>
                <view class="card-right">
                    <text>商品名称: {{ item.goodsName }}</text>
                    <text>调出货架: {{ item.outShelfName }}</text>
                    <text>调出格子: {{ item.outLatticeKey }}</text>
                    <text>调出数量: {{ item.quantity }} {{ item.goodsUnitName }}</text>
                </view>
            </view>
            <view v-if="formData.listDetail.length === 0" style="color: #bcbcbc; padding: 30rpx 0;">暂无数据</view>
        </sj-card>
        <sj-card title="备注/说明" :isShowLine="false">
            {{ formData.remark }}
            <view v-if="!formData.remark" style="color: #bcbcbc;">暂无数据</view>
        </sj-card>

        <view class="footer-occupy"></view>
        <view v-if="formData.status === 1" class="footer-option">
            <view class="btn" @click="handleCancel">取消</view>
            <view class="btn margin-left" @click="confirmCommitted">调拨</view>
            <view class="btn-primary margin-left" @click="handleUpdate">修改</view>
        </view>
    </view>
</template>

<script setup>
import {ref, reactive} from "vue"
import {onShow} from "@dcloudio/uni-app";
import {routeParams} from '@/router'
import http from "../../../lib/request";
import dayjs from 'dayjs'
import SjCard from './component/SjCard'
import {fileSrc} from '@/env'

let tundrawId = ref('')

// 调拨详情
let formData = reactive({
    listDetail: [
        // {}
    ]
})

// 获取调拨详情
function getTunDrawInfo() {
    http('committed.getTunDrawInfo',{tundrawId:tundrawId.value},'加载中...').then(res=>{
        if (res.code === 200) {
            let resData = res.data

            formData.tunDrawNum = 'TBD'+ resData.tundrawId
            formData.tundrawDate = dayjs(resData.tundrawDate).format('YYYY-MM-DD')
            formData.outStoreId = resData.outStoreId
            formData.outStoreName = resData.outStoreName
            formData.inStoreId = resData.inStoreId
            formData.inStoreName = resData.outStoreName
            formData.operatorId = resData.operatorId
            formData.operatorName = resData.operatorName
            formData.status = resData.status
            formData.remark = resData.comment

            formData.listDetail = resData.listDetail
            console.log(formData)
        }
    })
}

function statusFilter(status) {
    switch (status) {
        case 1:
            return '未调拨'
        case 2:
            return '已调拨'
        case 3:
            return '已取消'
    }
}

// 跳转编辑调拨信息
function handleUpdate() {
    uni.redirectTo({
        url: `/pages/application/committed/edit?tundrawId=${tundrawId.value}`
    })
}

// 取消调拨
function handleCancel() {
    let setCancel = () => {
        http('committed.cancelTunDraw',{tundrawId: tundrawId.value}).then(res => {
            if (res.code === 200) {
                uni.showToast({
                    title: '取消成功',
                    icon: 'none',
                    duration: 1000,
                    success: () => {
                        getTunDrawInfo()
                    }
                })
            }
        })
    }

    uni.showModal({
        title: '提示',
        content: '确认取消吗',
        success: (res) => {
            if (res.confirm) {
                setCancel()
            }
        }
    })
}

// 确定调拨
function confirmCommitted() {
    let setCommitted = () => {
        http('committed.tunDraw',{tundrawId: tundrawId.value}).then(res => {
            if (res.code === 200) {
                uni.showToast({
                    title: '调拨成功',
                    icon: 'none',
                    duration: 1000,
                    success: () => {
                        getTunDrawInfo()
                    }
                })
            }
        })
    }

    uni.showModal({
        title: '提示',
        content: '确认调拨吗',
        success: (res) => {
            if (res.confirm) {
                setCommitted()
            }
        }
    })
}

onShow(()=>{
    const params = routeParams()
    if (params) {
        tundrawId.value = params.tundrawId
        getTunDrawInfo()
    }
})
</script>

<style lang="scss" scoped>
    .wrapper {
        padding: 20rpx;
    }
    .info-row {
        padding: 0 2rpx;
        display: flex;
        justify-content: space-between;
        margin-top: 35rpx;
        .label {
            font-size: 32rpx;
            color: #666666;
        }
        .value {
            font-size: 32rpx;
            color: #202020;
            text-align: right;
        }
        .main-color {
            color: $uni-color-primary;
        }
    }

    .goods-row {
        display: flex;
        flex-direction: row;
        align-items: center;
        margin-top: 30rpx;

        .card-left {
            margin-right: 20rpx;

            .staff-icon{
                width: 90rpx;
                height: 90rpx;
            }
        }

        .card-right {
            display: flex;
            flex-direction: column;
            align-items: flex-start;

            text {
                margin-bottom: 20rpx;
                font-size: 30rpx;
                color: #999999;
                line-height: 12px;
            }
        }
    }

    .footer-occupy {
        height: 110rpx;
    }

    .footer-option {
        position: fixed;
        left: 0;
        right: 0;
        bottom: 0;
        padding: 15rpx 30rpx 30rpx;
        background: #ffffff;
        z-index: 10;

        display: flex;
        align-items: center;
        justify-content: space-between;
        .btn, .btn-primary {
            font-size: 32rpx;
            text-align: center;
            padding: 25rpx 0;
            width: 100%;
            border: 1rpx solid $uni-color-primary;
            border-radius: 10rpx;
            color: white;
            background-color: $uni-color-primary;
        }
        .btn {
            color: $uni-color-primary;
            background-color: white;
        }
        .margin-left {
            margin-left: 20rpx;
        }
    }
</style>