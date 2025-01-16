<template>
    <view class="content">
        <view class="header">
            <u-tabs
                v-model="currentTab"
                class="header-tabs"
                :list="orderTypeList"
                @change="changeTabStatus"
            ></u-tabs>

            <view class="filter-option" @click="isSearchShow = true">
                筛选
                <image class="filter-option-icon" :src="image.common.filter"></image>
            </view>
        </view>

        <view class="card-content">
            <view
                v-for="(item,index) in orderPayList"
                :key="index"
                @click="handleGoodsDetail(item)"
                class="item-card">
                <view class="item-head">
                    <view class="left">{{ item.payId }}</view>
                    <view class="right">{{ statusFilter(item.status) }}</view>
                </view>
                <view class="item-content">
                    <view class="row">
                        <span class="row-label">消费类型：</span>{{rcTypeFilter(item.rcType)}}
                    </view>
                    <view class="row">
                        <span class="row-label">支付方式：</span>{{payTypeFilter(item.payType)}}
                    </view>
                    <view class="row">
                        <span class="row-label">结账时间：</span>{{dateFilter(item.payTime)}}
                    </view>
                    <view v-if="item.status === 4" class="row">
                        <span class="row-label">退单时间：</span>{{dateFilter(item.refundTime)}}
                    </view>
                    <view class="row">
                        <span class="row-label">应付金额：</span>{{item.dealCope}}元
                    </view>
                    <view class="row">
                        <span class="row-label">实付金额：</span>{{item.waterCope}}元
                    </view>
                    <view class="row-bottom">{{item.operatorName ? '['+item.operatorName+']' : ''}} 创建于{{dateFilter(item.createTime)}}</view>
                </view>
            </view>
            <view v-if="orderPayList.length === 0" class="hint">
                <image class="hint-icon" :src="image.common.hintTo"></image>
                <view>暂无支付单信息</view>
            </view>
        </view>

        <!-- @open="openSearch" -->
        <u-popup v-model="isSearchShow" mode="right" class="search-popup" @close="isSearchShow = false" >
            <view class="search-content">
                <u-form
                    labelPosition="left"
                    :model="searchData"
                    :rules="rules"
                    ref="form1"
                    label-width="160rpx"
                >
                    <u-form-item
                        label="订单状态"
                        prop="userInfo.name"
                        :borderBottom="true"
                    >
                        <u-input
                            v-model="searchData.statusName"
                            type="number"
                            placeholder="请选择"
                            :border="false"
                            inputAlign="right"
                        ></u-input>
                        <template #right>
                            <u-icon
                                name="arrow-right"
                            ></u-icon>
                        </template>
                        <view class="date-after" @click="showStatusType = true"></view>
                    </u-form-item>
                    <u-form-item
                        label="支付方式"
                        prop="userInfo.name"
                        :borderBottom="true"
                    >
                        <u-input
                            v-model="searchData.payTypeName"
                            type="number"
                            placeholder="请选择"
                            :border="false"
                            inputAlign="right"
                        ></u-input>
                        <template #right>
                            <u-icon
                                name="arrow-right"
                            ></u-icon>
                        </template>
                        <view class="date-after" @click="showPayType = true"></view>
                    </u-form-item>
                    <u-form-item
                        label="起始时间"
                        prop="userInfo.startTime"
                        :borderBottom="true"
                    >
                        <u-input
                            v-model="searchData.startDate"
                            disabled
                            disabledColor="#ffffff"
                            placeholder="请选择"
                            :border="false"
                            inputAlign="right"
                        ></u-input>
                        <template #right>
                            <u-icon
                                name="arrow-right"
                            ></u-icon>
                        </template>
                        <view class="date-after" @click="openDateSelect(1)"></view>
                    </u-form-item>
                    <u-form-item
                        label="截止时间"
                        prop="userInfo.name"
                        :borderBottom="true"
                        placeholder="请输入"
                    >
                        <u-input
                            v-model="searchData.endDate"
                            disabled
                            disabledColor="#ffffff"
                            placeholder="请选择"
                            :border="false"
                            inputAlign="right"
                        ></u-input>
                        <template #right>
                            <u-icon
                                name="arrow-right"
                            ></u-icon>
                        </template>
                        <view class="date-after" @click="openDateSelect(2)"></view>
                    </u-form-item>
                </u-form>
                <view class="search-bottom">
                    <view class="btn" @click="handleSearchReset">重置</view>
                    <view class="btn-primary" @click="handleSearchSubmit">确定</view>
                </view>
            </view>
        </u-popup>

        <!-- 选择订单状态 -->
        <u-picker
            mode="selector"
            v-model="showStatusType"
            :range="statusList"
            range-key="name"
            @cancel="showStatusType = false"
            @confirm="selectStatusType"
        ></u-picker>

        <!-- 选择支付方式 -->
        <u-picker
            mode="selector"
            v-model="showPayType"
            :range="payTypeList"
            range-key="name"
            @cancel="showPayType = false"
            @confirm="selectPayType"
        ></u-picker>

        <!-- 选择起始时间节点 -->
        <u-picker
            ref="picker"
            v-model="isShowPicker"
            :params="{year: true,month:true,day:true,hour:false,minute:false,second:false}"
            cancelText="取消"
            @cancel="isShowPicker = false"
            @confirm="confirmDate"
        ></u-picker>
    </view>
</template>

<script setup>
import {reactive, ref, inject} from 'vue'
import dayjs from 'dayjs'
import http from "../../../lib/request";
import {onShow, onReachBottom, onLoad} from "@dcloudio/uni-app";

let global = inject('global')
let {on,getPage} = global.__listStorage('order-inquiry')
let {$showModal} = inject('defaultModal')

let currentTab = ref(0)
// 支付单列表
let orderPayList = ref([])

let orderTypeList = ref([
    {name: '会员充值',id:1},
    {name: '会员消费',id:2},
])

// 订单状态
let showStatusType = ref(false)
let statusList = ref([
    // {name: '全部',id: null},
    {name: '待付款',id: 1},
    {name: '已付款',id: 2},
    {name: '已退单',id: 3},
    {name: '已退款',id: 4},
])

// 支付方式
let showPayType = ref(false)
let payTypeList = ref([
    // {name: '全部',id: null},
    {name: '现金',id: 1},
    {name: '微信',id: 2},
    {name: '支付宝',id: 3},
    {name: '会员余额',id: 4},
    {name: '挂账',id: 5},
])

let setPage = () => {
    return {
        pageNum: 1,
        pageSize: 10
    }
}

let searchData = reactive({
    rcType : orderTypeList.value[0].id,
    // rcTypeName : '',
    payType : null,
    payTypeName : '',
    status : null,
    statusName : '',
    startDate: null,
    endDate: null,
    ...setPage()
})

let listTotal = 0
// 弹出层显示
let isSearchShow = ref(false)
// 显示时间选择框
let isShowPicker = ref(false)
let datetime = ref(dayjs().format('YYYY-MM-DD'))
// 1开始日期，2结束日期
let dateType = 1

let rules = reactive({})

// 搜索
function handleSearch() {
    // 重置分页
    searchData.pageNum = setPage().pageNum
    searchData.pageSize = setPage().pageSize
    handleOrderPayList()
}

// 查询
function handleSearchSubmit() {
    handleSearch()
    isSearchShow.value = false
}

// 重置
function handleSearchReset() {
    clearList()
    searchData.payType = null
    searchData.payTypeName = ''
    searchData.status = null
    searchData.statusName = ''
    searchData.startDate= null
    searchData.endDate= null
    searchData.pageNum = 0
    handleOrderPayList()
}

// 清空列表
function clearList() {
    orderPayList.value = []
}

// 改变标签状态
function changeTabStatus(index) {
    let e = orderTypeList.value[index]
    clearList()
    searchData.rcType = e.id
    handleSearch()
}

// 打开时间选择器
function openDateSelect(type) {
    dateType = type
    isShowPicker.value = true
}

// 选中时间
function confirmDate(e) {
    if (dateType === 1) {
        searchData.startDate = `${e.year}-${e.month}-${e.day}`
    } else {
        searchData.endDate = `${e.year}-${e.month}-${e.day}`
    }
    isShowPicker.value = false;
}

// 选中订单状态
function selectStatusType(index) {
    let item = statusList.value[index[0]]
    searchData.status = item.id
    searchData.statusName = item.name
    showStatusType.value = false

}

// 选中支付方式
function selectPayType(index) {
    let item = payTypeList.value[index[0]]
    searchData.payType = item.id
    searchData.payTypeName = item.name
    showPayType.value = false
}

// 时间数据化
function inventoryDateFilter(date) {
    return dayjs(date).format('YYYY-MM-DD')
}

function dateFilter(date) {
    if (!date) return ''
    return dayjs(date).format('YYYY-MM-DD  HH:mm')
}

function statusFilter(status) {
    switch (status) {
        case 1:
            return '待付款'
        case 2:
            return '已付款'
        case 3:
            return '已退单'
        case 4:
            return '已退款'
    }
}

function payTypeFilter(status) {
    switch (status) {
        case 1:
            return '现金'
        case 2:
            return '微信'
        case 3:
            return '支付宝'
        case 4:
            return '会员余额'
        case 5:
            return '挂账'
    }
}

function rcTypeFilter(status) {
    switch (status) {
        case 1:
            return '会员充值'
        case 2:
            return '商品消费'
    }
}

// 获取支付单列表
async function handleOrderPayList(callback = null) {
    let data = JSON.parse(JSON.stringify(searchData))

    let res = await http('order.listOrderPay',data,'加载中...')
    // .then(res=>{
    if (res.code === 200 && res.rows) {
        let resList = res.rows
        if (!callback) {
            if (data.pageNum === 1) {
                orderPayList.value = resList;
            } else {
                orderPayList.value = [...orderPayList.value, ...resList];
            }
        } else {
            callback(resList)
        }
        listTotal = res.total;
    }
    // })
}

// 跳转查看订单or支付单商品明细详情
function handleGoodsDetail(item) {
    let typeName = ''
    switch (item.rcType) {
        case 1:
            typeName = 'recharge'
            break;
        case 2:
            typeName = 'consumption'
            break;
    }
    uni.navigateTo({
        url: `/pages/application/order-inquiry/orderPayInfo?orderPayId=${item.payId}&typeName=${typeName}`
    })
}

onLoad(() => {
    handleSearch()
    on(async (reload,id,func)=>{
        if (reload) {
            handleSearch();
        } else {
            // let pageNum = paramsQuery.pageNum
            searchData.pageNum = getPage(searchData.pageSize,'payId',id,orderPayList.value);
            let list = []
            await handleOrderPayList((lists)=>{list = [...list,...lists]})
            // 获取下一页
            if (listTotal > searchData.pageNum * searchData.pageSize){
                searchData.pageNum++
                await handleOrderPayList((lists)=>{list = [...list,...lists]})
            }
            func(orderPayList.value,list,'payId',searchData.pageSize)
        }
    })
})

onReachBottom(() => {
    let currentOrderNum = searchData.pageNum * searchData.pageSize;
    if (currentOrderNum < listTotal) {
        searchData.pageNum++
        handleOrderPayList()
    }
})

</script>

<style lang="scss" scoped>
    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        background-color: white;
        .header-tabs {
            flex: 1;
        }
        .filter-option {
            display: flex;
            align-items: center;
            padding: 30rpx;
            color: #666666;
            position: relative;

            .filter-option-icon {
                width: 32rpx;
                height: 32rpx;
                margin-left: 10rpx;
            }
        }

        .filter-option::before {
            content: '';
            position: absolute;
            top: -10rpx;
            right: -10rpx;
            bottom: -10rpx;
            left: -10rpx;
        }
    }

    .card-content {
        padding: 20rpx;
        font-size: 28rpx;

        .item-card {
            padding: 36rpx 30rpx 25rpx 30rpx;
            background: #FFFFFF;
            border-radius: 20rpx;
            margin-bottom: 20rpx;
            .item-head {
                display: flex;
                justify-content: space-between;
                .left {
                    color: #202020;
                    font-weight: 600;
                }
                .right {
                    color: #8B6445;
                }
            }
            .item-content {
                .row {
                    margin-top: 20rpx;
                    .row-label {
                        color: #666666;
                    }
                }
                .row-bottom {
                    border-top: 1rpx solid #EEEEEE;
                    margin-top: 30rpx;
                    padding-top: 27rpx;

                    color: #666666;
                }
            }
        }
    }

    .hint {
        position: absolute;
        top: 40%;
        left: 50%;
        transform: translate(-40%,-50%);
        color: #7b7b7b;
        text-align: center;
        .hint-icon {
            width: 240rpx;
            height: 240rpx;
        }
    }

    .search-popup {
        width: 500rpx;
    }

    .search-content {
        width: 550rpx;
        padding: 30rpx;
    }

    .search-bottom {
        display: flex;
        justify-content: space-between;
        position: absolute;
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
</style>
