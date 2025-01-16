<template>
    <view class="container">
        <view class="background-color"></view>
        <view class="header">
            <view class="header-layer1">
                <view class="header-title">{{infoData.planName}}</view>
                <view v-if="infoData.cancelStatus === 0">{{filterStatusName(infoData.status)}}</view>
                <view v-if="infoData.cancelStatus === 1">已作废</view>
            </view>
            <view class="header-layer2 header-layer">
                <view>定货时间：{{infoData.applyTime}}</view>
<!--                <view>品种：{{infoData.totalKind}}种</view>-->
                <view>操作员：{{infoData.applyName}}</view>
            </view>
        </view>
        <view class="card">
            <view class="card-header">
                <view v-if="infoData.status !== 5&& infoData.status > 1" class="card-header-item">
                    <view class="card-item">
                        <view>
                            <span style="color: #666666">接收员工：</span>
                            <span>{{infoData.receiveName}}</span>
                        </view>
                        <view>
                            <span style="color: #666666">接收时间：</span>
                            <span>{{infoData.receiveTime}}</span>
                        </view>
                    </view>
                    <view v-if="infoData.status === 3" class="card-item">
                        <view>
                            <span style="color: #666666">发货员工：</span>
                            <span>{{infoData.deliverName}}</span>
                        </view>
                        <view>
                            <span style="color: #666666">发货时间：</span>
                            <span>{{infoData.deliverTime}}</span>
                        </view>
                    </view>
                    <view v-if="infoData.status === 5 || infoData.cancelStatus === 1" class="card-item">
                        <view>
                            <span style="color: #666666">取消员工：</span>
                            <span>{{infoData.cancelName}}</span>
                        </view>
                        <view>
                            <span style="color: #666666">取消时间：</span>
                            <span>{{infoData.cancelTime}}</span>
                        </view>
                    </view>
                </view>
            </view>
            <view class="card-content">
                <view v-for="(item,index) in infoData.listDetail" :key="index" class="card-layer-wrap">
                    <view class="picture-wrap">
                        <view v-if="!item.goodsLogo" class="picture-default">茶</view>
                        <image v-else="item.goodsLogo" class="picture-img" :src="viewImage + item.goodsLogo + fileViewAfter"></image>
                    </view>
                    <view class="card-row-info">
                        <view class="card-row-info-layer1">
                            <view>{{ index + 1 }}.【{{item.goodsName}}】{{item.goodsSupplierBrand}}</view>
                        </view>
                        <view class="card-row-info-layer2">
                            <view>规格：{{item.goodsSpecification}}</view>
                        </view>
                        <view class="card-row-info-layer2">
                            <view>
                                样泡：每&nbsp;{{item.matchFormal}}&nbsp;{{item.goodsUnitName}}&nbsp;&nbsp;赠&nbsp;{{item.matchSample}}&nbsp;{{item.goodsUnitName2}}
                            </view>
                        </view>
                        <view v-if="item.quantity > 0" class="card-row-info-layer2">
                            <view>{{keepDecimal(parseFloat(item.goodsSalePrice))}}元</view>
                            <view>x{{item.quantity}}{{ item.goodsUnitName }}</view>
                        </view>
                        <view v-if="item.quantity2 > 0" class="card-row-info-layer2">
                            <view>{{keepDecimal(parseFloat(item.goodsSalePrice2))}}元</view>
                            <view>x{{item.quantity2}}{{ item.goodsUnitName2 }}</view>
                        </view>
                        <view v-if="item.rateBig && item.rateBig > 1 && item.quantity >= item.rateBig" class="card-row-info-layer2">
                            <view>合计：</view>
                            <view>
                                <span>{{ Math.floor(parseInt(item.quantity) / parseInt(item.rateBig)) }}{{ item.goodsUnitNameBig }}</span>
                                <span>{{ parseInt(item.quantity) % parseInt(item.rateBig) }}{{ item.goodsUnitName }}</span>
                                <span v-if="item.quantity2 > 0">{{ item.quantity2 }}{{ item.goodsUnitName2 }}</span>
                            </view>
                        </view>
                    </view>
                </view>
            </view>

            <view style="height: 1rpx; background-color: #EEEEEE; margin: 39rpx 0;"></view>

            <view>
                <u-form
                    labelPosition="left"
                    :model="infoData"
                    :rules="rules"
                    ref="formRef"
                    label-width="160rpx">
                    <Sj-card
                        v-if="infoData.status >= 0"
                        title="申请理由">
                        <u-form-item
                            label=""
                            prop="applyReason">
                            <u-input
                                type="textarea"
                                v-model="infoData.applyReason"
                                placeholder="无"
                                disabledColor="#ffffff"
                                disabled
                                :border="false"></u-input>
                        </u-form-item>
                    </Sj-card>

                    <Sj-card
                        v-if="infoData.status > 1 && infoData.status <= 3"
                        title="接收意见">
                        <u-form-item
                            label=""
                            prop="receiveReason">
                            <u-input
                                type="textarea"
                                v-model="infoData.receiveReason"
                                placeholder="无"
                                disabledColor="#ffffff"
                                disabled
                                :border="false"></u-input>
                        </u-form-item>
                    </Sj-card>

                    <Sj-card
                        v-if="infoData.status === 3"
                        title="发货意见">
                        <u-form-item
                            label=""
                            prop="deliverReason">
                            <u-input
                                type="textarea"
                                v-model="infoData.deliverReason"
                                placeholder="无"
                                disabledColor="#ffffff"
                                disabled
                                :border="false"></u-input>
                        </u-form-item>
                    </Sj-card>

                    <Sj-card
                        v-if="infoData.status === 5 || infoData.cancelStatus === 1"
                        title="取消意见">
                        <u-form-item
                            label=""
                            prop="cancelReason">
                            <u-input
                                type="textarea"
                                v-model="infoData.cancelReason"
                                placeholder="无"
                                disabledColor="#ffffff"
                                disabled
                                :border="false"></u-input>
                        </u-form-item>
                    </Sj-card>
                </u-form>
            </view>
        </view>

        <view v-if="infoData.cancelStatus === 0 && infoData.status < 3" class="foot">
            <view v-if="infoData.status < 3" style="margin-right: 20rpx">
                <u-button
                    @click="otherCancelShow = true"
                    style=""
                    type="info"
                    :customStyle="{borderRadius: '16rpx'}">
                    作废
                </u-button>
            </view>
            <view v-if="infoData.status > 0 || infoData.status < 3">
                <u-button
                    @click="handleOptionPurchase(infoData.status, infoData)"
                    style=""
                    type="primary"
                    :customStyle="{borderRadius: '16rpx'}">
                    {{filterStatusBtn(infoData.status)}}
                </u-button>
            </view>
        </view>

        <u-popup v-model="otherShow" round="30rpx" mode="center" @close="otherShow = false" :safeAreaInsetBottom="false">
            <view class="modal-content">
                <Sj-card :title="title">
                    <u-form-item
                        label=""
                        prop="reason">
                        <u-input
                            type="textarea"
                            v-model="infoData.reason"
                            placeholder="请输入"
                            :border="false"></u-input>
                    </u-form-item>
                </Sj-card>
                <view class="search-bottom">
                    <view>
                        <u-button
                            @click="otherShow = false"
                            style=""
                            type="primary"
                            :plain="true"
                            :customStyle="{borderRadius: '16rpx'}">
                            取消
                        </u-button>
                    </view>
                    <view>
                        <u-button
                            @click="handlePopupPurchase(infoData.status)"
                            style=""
                            type="primary"
                            :customStyle="{borderRadius: '16rpx'}">
                            {{formatPopupStatusBtn(infoData.status)}}
                        </u-button>
                    </view>
                </view>
            </view>
        </u-popup>

        <!-- 作废 -->
        <u-popup v-model="otherCancelShow" round="30rpx" mode="center" @close="otherCancelShow = false" :safeAreaInsetBottom="false">
            <view class="modal-content">
                <Sj-card title="作废定货单">
                    <u-form-item
                        label=""
                        prop="reason">
                        <u-input
                            type="textarea"
                            v-model="infoData.reason"
                            placeholder="请输入"
                            :border="false"
                        ></u-input>
                    </u-form-item>
                </Sj-card>
                <view class="search-bottom">
                    <view class="btn" @click="otherCancelShow = false">取消</view>
                    <view class="btn-primary" @click="handleCancelPurchase">确定</view>
                </view>
            </view>
        </u-popup>
    </view>
</template>

<script setup>
import {ref, reactive, onMounted} from "vue"
import http from "../../../lib/request";
import {onLoad, onShow} from "@dcloudio/uni-app";
import {keepDecimal,debounce} from '@/util/common'
import dayjs from 'dayjs'
import {viewImage,fileViewAfter} from '@/env';
import {routeParams} from "@/router";
import SjCard from './component/SjCard'
import inlineStyleConfig from "@/lib/system/inlineStyleConfig";

let infoData = reactive({
  // sysPurchase: {},
  // listDetail: []
})

// 作废显示层
let cancelShow = ref(false)
// 弹出层显示
let otherShow = ref(false)
let otherCancelShow = ref(false)
let title = ref('')

let rules = reactive({})

let planOrderId = ''

let isInit = true

// 获取定货单详情
function getInfo() {
    http('planGoods.getPlanGoods',{primaryId: planOrderId},'加载中...').then(res=>{
        if (res.code === 200) {
            infoData = Object.assign(infoData,{
                ...res.data,
                applyTime: dayjs(res.data.applyTime).format('YYYY-MM-DD HH:mm'),
                receiveTime: dayjs(res.data.receiveTime).format('YYYY-MM-DD HH:mm'),
                deliverTime: dayjs(res.data.deliverTime).format('YYYY-MM-DD HH:mm'),
                cancelTime: dayjs(res.data.cancelTime).format('YYYY-MM-DD HH:mm'),
                listDetail: [],
                reason: ''
            })
            getInfoList()
        }
    })
}

// 获取定货单商品明细
function getInfoList() {
    http('planGoods.getPlanGoodsDetail',{primaryId: planOrderId},'加载中...').then(res=>{
        if (res.code === 200) {
            infoData.listDetail = res.data
        }
    })
}

// 接收定货单
function receivePurchase() {
    // const setSubmit = () => {
        http('planGoods.receivePlanGoods',
            {orderId: planOrderId,remark:infoData.reason}).then(res=>{
            if (res.code === 200) {
                uni.showToast({
                    title: '接收成功！',
                    icon: 'none',
                    duration: 1000,
                    complete: () =>{
                        infoData.reason = ''
                        otherShow.value = false
                        uni.$emit('franchise-goods',{type: 'edit',id: planOrderId})
                        getInfo()
                    }
                })
            }
        })
    // }

    // uni.showModal({
    //     title: '提示',
    //     content: '确定要接收定货单吗？',
    //     success: function (res) {
    //         if (res.confirm) {
    //             setSubmit()
    //         }
    //     }
    // })
}

// 作废定货单
// function handleCancelPurchase() {
const handleCancelPurchase = debounce(500,()=>{
    // const setCancel = () => {
        http('planGoods.cancelPlanGoods',
            {orderId: planOrderId,remark:infoData.reason}).then(res=>{
            if (res.code === 200) {
                uni.showToast({
                    title: `${title.value}成功！`,
                    icon: 'none',
                    duration: 1000,
                    success: () =>{
                        infoData.reason = ''
                        otherCancelShow.value = false
                        uni.$emit('franchise-goods',{type: 'edit',id: planOrderId})
                        getInfo()
                    }
                })
            }
        })
    // }

    // uni.showModal({
    //     title: '提示',
    //     content: '确定要作废该定货单吗？',
    //     confirmColor: inlineStyleConfig.uniColorPrimary,
    //     success: function (res) {
    //         if (res.confirm) {
    //             setCancel()
    //         }
    //     }
    // })
})

// 定货单发货
function deliverGoods() {
    let msg = ''
    infoData.listDetail.forEach((item,index)=>{
        if (item.quantity > item.storeQuantity1 && item.quantity2 > item.storeQuantity2) {
            if (index === infoData.listDetail.length - 1) {
                msg += `${item.goodsName}`
            }else {
                msg += `${item.goodsName},`
            }
        }
    })
    otherShow.value = false
    if (msg && msg !== '') {
        uni.showToast({
            title: msg + '等商品库存数量不足',
            icon: 'none',
            duration: 3000
        })
    }else {
        uni.navigateTo({
            url: `/pages/application/ship-goods/edit?planOrderId=${planOrderId}`
        })
    }
}

// 定货单状态
function filterStatusName(status) {
    switch (status) {
        case 1:
            return '待接收'
        case 2:
            return '待发货'
        case 3:
            return '已发货'
        case 5:
            return '已作废'
    }
}

function filterStatusBtn(status) {
    switch (status) {
        case 1:
            return '接收'
        case 2:
            return '发货'
    }
}

// 操作流程
function handleOptionPurchase(status,infoData) {
    switch (status) {
        case 1:
            // 接收定货单
            otherShow.value = true
            title.value = '接收意见'
            break;
        case 2:
            // 发货
            otherShow.value = true
            title.value = '发货意见'
            break;
    }
}

// 弹出层流程
// function handlePopupPurchase(status) {
const handlePopupPurchase = debounce(500,(status)=>{
    switch (status) {
        case 1:
            receivePurchase()
            break;
        case 2:
            // 发货
            deliverGoods()
            break;
    }
})

function formatPopupStatusBtn(status) {
    switch (status) {
        case 1:
            return '确定接收'
        case 2:
            return '确定发货'
    }
}

onLoad(() => {
    // planOrderId = option.planOrderId
    // getInfo()
    // isInit = false
})

onShow(() => {
    let params = routeParams()
    if (params) {
        planOrderId = params.planOrderId
        getInfo()
        // getInfoList()
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
            margin-bottom: 36rpx;
            .header-title {
                font-size: 44rpx;
            }
        }
        .header-layer2 {
            display: flex;
            justify-content: space-between;
            font-size: 24rpx;
        }

        .header-layer {
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
            //border-bottom: 1rpx solid #EEEEEE;
        }
        .head-item {
            padding-top: 20rpx;
        }
        .card-header-item {
            display: flex;
            flex-direction: column;
            //align-items: center;
            //justify-content: space-between;
            .card-item {
                display: flex;
                //align-items: center;
                justify-content: space-between;
                font-size: 28rpx;
                padding: 20rpx 0;
                border-bottom: 1rpx solid #EEEEEE;
            }
        }

        .card-content {
            //padding: 39rpx 0;

            .card-content-header {
                display: flex;
                justify-content: space-between;
                color: #666666;
            }
            .card-layer-wrap {
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
                //.card-layer1 {
                //    //padding-top: 23rpx;
                //}
                //.card-layer2 {
                //    padding-top: 15rpx;
                //    //padding-bottom: 20rpx;
                //}
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
    }

    .modal-content {
        width: 600rpx;
        padding: 30rpx;
        box-sizing: border-box;

        .search-bottom {
            display: flex;
            justify-content: space-between;
            bottom: 30rpx;
            .btn, .btn-primary {
                padding: 0 80rpx;
                height: 80rpx;
                border-radius: 10px;
                text-align: center;
                line-height: 80rpx;
            }
            .btn {
                border: 1px solid #CCCCCC;
                color: #202020;
            }

            .btn-primary {
                background-color: $uni-color-primary;
                border: 1px solid $uni-color-primary;
                color: white;
                margin-left: 30rpx;
            }
        }
    }
</style>
