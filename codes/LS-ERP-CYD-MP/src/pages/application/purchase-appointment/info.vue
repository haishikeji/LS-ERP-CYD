<template>
    <view class="container">
        <view class="background-color"></view>
        <view class="header">
            <view class="header-layer1">
                <view class="header-title">{{infoData.name}}</view>
                <view>{{filterStatusName(infoData.status)}}</view>
            </view>
            <view class="header-layer2">
                <view>时间：{{infoData.orderDate}}</view>
                <view>品种：{{infoData.listDetail ? infoData.listDetail.length : 0}}</view>
                <view>采购人：{{infoData.empName}}</view>
            </view>
        </view>
        <view class="card">
            <view class="card-header">
                <span style="color: #666666">供应商：</span>
                <span>{{infoData.goodsSupplierName}}</span>
            </view>
            <view class="card-content">
                <view class="card-content-header">
                    <view>内容：</view>
                    <view>
                        <span style="font-size: 24rpx; margin-right: 10rpx;">合计</span>
                        <span style="font-size: 36rpx; color: #FF1E1A;">{{infoData.totalPrices}}</span>
                        <span style="font-size: 24rpx; color: #FF1E1A;">元</span>
                    </view>
                </view>
                <view v-for="(value,index) in infoData.listDetail" class="card-layer-wrap">
                    <view class="card-layer1">
                        <view class="layer-title">{{value.goodsName}}</view>
                        <view class="layer-price">{{value.totalPrices}}元</view>
                    </view>
                    <view class="card-layer2">
                        <view>{{value.goodsCostPrice}}元/单</view>
                        <view>x{{value.quantity}}</view>
                    </view>
                </view>
                <!-- 线条-->
                <view style="height: 1rpx; background-color: #EEEEEE; margin: 39rpx 0;"></view>
                <view class="remark">
                    <view style="margin-bottom: 24rpx;">备注：</view>
                    <view>{{infoData.remark}}</view>
                </view>
            </view>
        </view>
        
        <view class="foot">
            <!--            编辑采购（保留）请勿删除-->
            <!--            <view v-if="infoData.sysPurpose.status !== 9" style="margin-right: 20rpx">-->
            <!--                <u-button-->
            <!--                    @click="handleEditPurchase"-->
            <!--                    style=""-->
            <!--                    type="info"-->
            <!--                    text="编辑采购"-->
            <!--                    :customStyle="{borderRadius: '16rpx'}">-->
            <!--                </u-button>-->
            <!--            </view>-->

            <view v-if="infoData.status !== 9" class="foot-btn">
                <u-button
                    @click="handleCancelPurchase"
                    style=""
                    type="info"
                    text="取消采购"
                    :customStyle="{borderRadius: '16rpx'}">
                </u-button>
            </view>
            <view v-if="[1].includes(infoData.status)" class="foot-btn">
                <u-button
                    @click="handleTransitionPurchase"
                    style=""
                    type="info"
                    :customStyle="{borderRadius: '16rpx'}">
                  转采购单
                </u-button>
            </view>
            <view class="foot-btn">
                <u-button
                    @click="handleOptionPurchase(infoData.status)"
                    style=""
                    type="primary"
                    :text="filterStatusBtn(infoData.status)"
                    :customStyle="{
                        borderRadius: '16rpx'
                    }">
                </u-button>
            </view>
      
        </view>
    </view>
</template>

<script setup>
import {ref, reactive} from "vue"
import http from "@/lib/request";
import {onLoad} from "@dcloudio/uni-app";
import inlineStyleConfig from "@/lib/system/inlineStyleConfig";

let infoData = reactive({})

let procurementId = ''

onLoad((option) => {
    procurementId = option.procurementId
    getInfo()
})

function getInfo() {
    http('purchaseOrder.getPurposeInfo',{procurementId: procurementId}).then(res=>{
        
        if (res.code === 200) {
            infoData = Object.assign(infoData,{...res.data})
            
            let total = 0;
            
            infoData.listDetail.forEach(val => {
                let price = val.totalPrices ? val.totalPrices : 0
                total += price
            })
            // 计算总价
            infoData.totalPrices = total.toFixed(2)
        }
    })
}

function filterStatusName(status) {
    switch (status) {
        case 1:
            return '正常'
        case 2:
            return '转采购单'
        case 9:
            return '取消'
        default:
            return '未知'
    }
}

function filterStatusBtn(status) {
    switch (status) {
        case 1:
            return '更新订单'
        case 2:
            return '更新订单'
        case 9:
            return '已取消'
    }
}

// 取消采购
function handleCancelPurchase() {
    const setCancel = () => {
        http('purchaseOrder.canclePurpose',{procurementId: procurementId}).then(res=>{
            if (res.code === 200) {
                uni.showToast({
                    title: '取消成功！',
                    duration: 1000,
                    complete: () => {
                        getInfo()
                    }
                })
            }
        })
    }
    
    uni.showModal({
        title: '提示',
        content: '确认取消采购吗？',
        confirmColor: inlineStyleConfig.uniColorPrimary,
        success: function (res) {
            if (res.confirm) {
                setCancel()
            }
        }
    })
}

// 转采购单
function handleTransitionPurchase() {
    const setCancel = () => {
        http('purchaseOrder.usePurpose',{procurementId: procurementId}).then(res=>{
            if (res.code === 200) {
                uni.showToast({
                    title: '转采购单成功！',
                    duration: 1000,
                    complete: () => {
                        getInfo()
                    }
                })
            }
        })
    }
    
    uni.showModal({
        title: '提示',
        content: '确认转采购单吗？',
        confirmColor: inlineStyleConfig.uniColorPrimary,
        success: function (res) {
            if (res.confirm) {
                setCancel()
            }
        }
    })
}

// 编辑采购单
function handleEditPurchase() {
    uni.navigateTo({
        url: `/pages/application/purchase-appointment/edit?procurementId=${procurementId}`
    })
}

function handleOptionPurchase(status) {
    switch (status) {
        case 1:
            // 更新订单
            getInfo()
            uni.showToast({ title: '更新成功', duration: 1000, icon: "none" })
            break
        case 2:
            // 更新订单
            getInfo()
            uni.showToast({ title: '更新成功', duration: 1000, icon: "none" })
            break
        case 3:
            // 入库
            break
        case 4:
            // 入库管理
            break
        case 9:
            // 已取消
            break
    }
}

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
    padding: 40rpx 30rpx;
    width: 85%;
    margin: 0 auto;
    transform: translateY(-100rpx);
    .card-header {
        padding-bottom: 39rpx;
        border-bottom: 1rpx solid #EEEEEE;
    }
    .card-content {
        padding: 39rpx 0;
        
        .card-content-header {
            display: flex;
            justify-content: space-between;
            color: #666666;
        }
        .card-layer-wrap {
            padding-top: 35rpx;
            .card-layer1, .card-layer2  {
                display: flex;
                justify-content: space-between;
                align-items: center;
                font-size: 24rpx;
                color: #999999;
                .layer-title {
                    font-size: 28rpx;
                    font-weight: bold;
                    color: #202020;
                }
                .layer-price {
                    font-size: 32rpx;
                    font-weight: normal;
                    color: #202020;
                }
            }
            .card-layer1 {
                //padding-top: 23rpx;
            }
            .card-layer2 {
                padding-top: 15rpx;
                //padding-bottom: 20rpx;
            }
        }
        .remark {
            color: #666666;
        }
    }
}

.foot {
    background: #ffffff;
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 15rpx 30rpx 30rpx;
    display: flex;
    justify-content: flex-end;
    //align-items: center;
    overflow: visible;
    .foot-btn {
        margin-left: 20rpx;
    }
}

</style>
