<template>
    <view class="container">
        <view class="background-color"></view>
        <view class="form">
            <u-form
                :model="formData"
                :rules="rules"
                labelPosition="left"
                label-width="auto"
                :label-style="{fontSize: '15px',color: '#666666'}"
                ref="purchaseForm"
            >
                <view class="form-group">
                    <u-form-item
                        label="赠送会员"
                        prop="membersName"
                        :borderBottom="true">
                        <u-input
                            v-model="formData.membersName"
                            :customStyle="{...inputCustom}"
                            :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                            :border="false"
                            inputAlign="right"
                            disabled
                            disabledColor="#ffffff"
                            placeholder="请选择">
                        </u-input>
                        <template #right>
                            <u-icon v-if="!giveawayId" name="arrow-right"></u-icon>
                        </template>
                        <view v-if="!giveawayId" class="date-after" @click="membersShow = true"></view>
                    </u-form-item>
                    <u-form-item
                        label="赠送员工"
                        prop="operatorName"
                        :required="true"
                        :borderBottom="true">
                        <u-input
                            v-model="formData.operatorName"
                            :customStyle="{...inputCustom}"
                            :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                            :border="false"
                            inputAlign="right"
                            disabled
                            disabledColor="#ffffff"
                            placeholder="请选择">
                        </u-input>
                    </u-form-item>
                    <u-form-item
                        label="备注"
                        prop="remark"
                        :borderBottom="true">
                        <u-input
                            v-model="formData.remark"
                            :customStyle="{...inputCustom}"
                            :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                            :border="false"
                            inputAlign="right"
                            placeholder="请输入赠送备注">
                        </u-input>
                    </u-form-item>
                </view>

                <view class="button-group">
                    <view class="button-item">
                        <u-button type="primary" :plain="true" @click="handleOpenCommodity">
                            <image class="icon" :src="image.common.select"></image>
                            <text class="text">商品选择</text>
                        </u-button>
                    </view>
                    <view class="button-item">
                        <u-button type="primary" :plain="true" @click="scanCode">
                            <image class  ="icon" :src="image.common.scan"></image>
                            <text class="text">扫码赠送</text>
                        </u-button>
                    </view>
                </view>

                <view v-for="(item,index) in formData.listGoods">
                    <view class="card">
                        <custom-goods-card
                            :form="item"
                            :showLattice="false"
                            :isShow="true"
                            @remove="del"
                            @numberMinChange="numberMinChange"
                            @numberMaxChange="numberMaxChange">
                            <view class="other">
                                <view class="other-content text text-d">
                                    <view class="d-line">
                                        <text>库存总量：</text>
                                        <text>{{ item.totalQuantity || '0' }}</text>
                                    </view>
                                </view>

                                <view class="other-edit">
                                    <view v-for="(child,childIndex) in item.listLattice"
                                          :key="childIndex"
                                          v-if="item.listLattice.length > 0">
                                        <view class="edit-info card-item">
                                            <view class="left-item text">
                                                <text>货架{{childIndex +1}}: </text>
                                                <text>{{ child.goodsShelf }}</text>
                                            </view>
                                            <view class="right-item">
                                                <text class="text">存入数量：</text>
                                                <u-input
                                                    v-model="child.quantity"
                                                    inputAlign="left"
                                                    :border="false"
                                                    class="input__border-bottom"
                                                    fontSize="14px"
                                                ></u-input>
                                                <text class="text">{{ child.unit }}</text>
                                            </view>
                                        </view>
                                    </view>
                                </view>
                            </view>
                        </custom-goods-card>
                    </view>
                </view>
            </u-form>
        </view>

        <!-- 选择会员 -->
        <custom-depositor-select v-model="membersShow"
            @selected="selectMember">
        </custom-depositor-select>

        <!-- 选择商品 -->
        <custom-commodity-dlg v-model="isShowCommodity"
            :is-multiple="true"
            searchType="inventory"
            @confirm="handleConfirmCommodity">
        </custom-commodity-dlg>

        <view class="content-footer">
            <u-button @click="handleSubmit" type="primary" class="in-button">
                <text>确认</text>
            </u-button>
        </view>
    </view>
</template>

<script setup>
import {ref, reactive} from 'vue';
import {onLoad, onShow} from "@dcloudio/uni-app";
import {routeParams} from '@/router'
import http from "../../../lib/request";
import { storage } from '@/lib/system/storage'
import {areaToString,debounce} from "@/util/common";
import {handleStoreGoodsByCode,handleStoreGoodsInfo} from "../../../lib/system/asyncDict";
import {useFormStyle} from "../../index/mixins";

const {inputCustom,formLabelStyle,formPlaceHolderStyle} = useFormStyle()

let formData = reactive({
    membersId: '',
    membersName: '',
    storeId: '',
    storeName: '',
    operatorName: '',
    operatorId: '',
    listGoods: [],
    remark: ''
})

let rules = reactive({
    'membersName': {
        type: 'string',
        required: true,
        message: '请选择赠送会员',
        trigger: ['blur', 'change']
    }
})

// 赠送单ID
let giveawayId = ref('')
// 库存商品id
let storeGoodsId = ref('')
// 赠送会员
let membersShow = ref(false)
// 商品
let isShowCommodity = ref(false)

const purchaseForm = ref(null)

// 默认为当前员工
function getAssignerName() {
    const userInfo = storage.getUserInfo()
    if (formData.operatorName == '' && !formData.operatorName) {
        if (Object.keys(userInfo).length > 0) {
            formData.operatorId = userInfo.userId+''
            formData.operatorName = userInfo.nickname
        }
    }
}

// 选中会员
function selectMember(item) {
    formData.membersId = item.memberId
    formData.membersName = item.memberName
}

// 数量发生变化
function numberMaxChange(e,child) {
    let num1 = parseInt(child.quantity2)/parseInt(child.rate)
    let num2 = parseInt(child.quantity2)%parseInt(child.rate)
    let num3 = parseInt(child.quantity)*parseInt(child.rate)
    let num4 = parseInt(child.quantity)
    let num5 = parseInt(child.quantity2)
    let num6 = parseInt(child.number)
    let num7 = parseInt(child.number2)

    child.total = Math.floor((num3+num5-num7)/parseInt(child.rate))
    child.total2 = (num4-num6)*parseInt(child.rate)+num5
}

// 数量发生变化
function numberMinChange(e,child) {
    let num1 = parseInt(child.quantity2)/parseInt(child.rate)
    let num2 = parseInt(child.quantity2)%parseInt(child.rate)
    let num3 = parseInt(child.quantity)*parseInt(child.rate)
    let num4 = parseInt(child.quantity)
    let num5 = parseInt(child.quantity2)
    let num6 = parseInt(child.number)
    let num7 = parseInt(child.number2)

    child.total = Math.floor((num3+num5-num7)/parseInt(child.rate))
    child.total2 = (num4-num6)*parseInt(child.rate)+num5
}

// 删除
function del(child) {
    let i = 0
    let list = formData.listGoods
    list.forEach((item,index)=>{
        if (child.goodsId == item.goodsId) {
            i = index
        }
    })
    list.splice(i,1)
    formData.listGoods = []
    formData.listGoods = list
}

// 扫码
function scanCode() {
    handleStoreGoodsByCode((res)=>{
        if (!res)return
        let item = res.data
        let dataInfo = constructorData(item)
        formData.listGoods.unshift(dataInfo)
    })
}


// 打开商品选择
function handleOpenCommodity() {
  isShowCommodity.value = true
}

// 确认选择商品
function handleConfirmCommodity(selectedItem) {
    if(selectedItem && selectedItem.length > 0){
        let list = []
        let isRepetition = false
        let msg = ''
        selectedItem.forEach(item=>{
           if (item.rate >= 1) {
               let dataInfo = constructorData(item)
               list.push(dataInfo)
               console.log(dataInfo, 'dataInfo')
               formData.listGoods.forEach(val=>{
                   console.log(item.storeGoodsId, 'item')
                   console.log(val.storeGoodsId, 'val')
                   if (val.storeGoodsId == item.storeGoodsId) {
                       if (msg.length != 0) {
                           msg += '，'
                       }
                       msg += item.goodsName
                       list.shift()
                       isRepetition = true
                   }
               })
           }else {
               uni.showToast({
                  title: `请设置商品：${item.goodsName}的产品规格`
               })
           }
        })
      // 错误提示
      if (isRepetition){
        uni.showToast({
          title: msg + '不可重复添加',
          icon: 'none',
          duration: 1000,
        })
      }
        if(list.length === 0){
          return
        }

        formData.listGoods = [...list,...formData.listGoods]
    }
}

// 赠送单编辑
function getInfo() {
    http('presented.getGiveawayInfo',{primaryId:giveawayId.value},'加载中...').then(res=>{
        if (res.code === 200 && res.data) {
            formData.membersId = res.data.membersId
            formData.membersName = res.data.membersName
            formData.operatorId = res.data.operatorId
            formData.operatorName = res.data.operatorName
            formData.remark = res.data.remark

            formData.listGoods = []
            getGoodsDetail()
        }
    })
}

// 获取赠送单商品明细
function getGoodsDetail() {
    http('presented.listRecordByGiveawayId',{primaryId:giveawayId.value},'加载中...').then(res=>{
        if (res.code === 200 && res.data) {
            res.data.forEach(item=>{
                let dataInfo = constructorData(item)
                formData.listGoods.push(dataInfo)
            })

        }
    })
}

// 构造数据
function constructorData(item) {
  let goodsProduceArea = ''
  try{
    if(item.goodsProduceArea){
      // let area =  areaToString(item.goodsProduceArea)
      goodsProduceArea =  areaToString(item.goodsProduceArea)
      // goodsProduceArea = area.slice(0,area.length-1)
    }
  }catch(e){
    console.log(e, 'e')
  }
  return {
    logo: item.goodsLogo,
    goodsName : item.goodsName,
    goodsId : item.goodsId,
    goodsSpecification: item.goodsSpecification,
    goodsSupplierBrand: item.goodsSupplierBrand,
    storeGoodsId : item.storeGoodsId,
    unit: item.goodsUnitName,
    number: item.quantity?1:0,
    number2: item.quantity==0?1:0,
    quantity: item.quantity?parseInt(item.quantity):1,
    quantity2: item.quantity2?parseInt(item.quantity2):1,
    total: item.quantity+Math.floor(parseInt(item.quantity2)/parseInt(item.rate)),
    total2: item.quantity2+(parseInt(item.quantity)*parseInt(item.rate)),
    batchNo: item.batchNo,
    goodsProduceArea: goodsProduceArea,
    goodsSupplierName: item.goodsSupplierName,
    goodsKeepDay: item.goodsKeepDay,
    rate: parseInt(item.rate), //换率
    unit2: item.goodsUnitName2,
    salePrice: parseFloat(item.goodsSalePrice),
    salePrice2: parseFloat(item.goodsSalePrice2),
    totalQuantity: ((item.quantity?item.quantity:0)+item.goodsUnitName)+(item.quantity2?(item.quantity2+item.goodsUnitName2):''),
    listLattice: []
  }
}

function init() {
    if (giveawayId.value) {
        // 编辑
        uni.setNavigationBarTitle({
            title: '编辑赠送单'
        })
        getInfo()
    } else {
        // 新增
        uni.setNavigationBarTitle({
            title: '新增赠送单'
        })
        const info = storage.getUserInfo()
    }
}

// 验证及提交
// function handleSubmit() {
const handleSubmit = debounce(300,()=>{
    let totalCount = null
    let isSuccessValidate = true
    let validateList = (title) => {
        uni.showToast({ title: title, duration: 1000, icon: "none" })
        isSuccessValidate = false
    }
    if (!formData.membersName) {
      validateList('请选择赠送会员')
    }
    if (formData.listGoods.length < 1) {
        validateList('请选择赠送商品')
        return;
    }
    formData.listGoods.forEach(val=>{
        if ((!val.quantity || val.quantity == 0) && (!val.quantity2 || val.quantity2 == 0)) {
            validateList('请填写数量')
        }
        if(val.quantity < 1 && val.quantity2 < 1) {
          validateList('请填写数量')
        }
        // totalCount += parseFloat(val.quantity)
        // if (totalCount > parseFloat(val.total)) {
        //     validateList('赠送数量不能超过库存总数')
        // }
    })
    if (!isSuccessValidate)return;
    if (giveawayId.value) {
        submitEdit()
    } else {
        submitAdd()
    }
})

// 新增赠送单
function submitAdd() {
  let listGoods = []
  formData.listGoods.forEach(item=>{
    listGoods.push({
      storeGoodsId: item.storeGoodsId,
      quantity: item.number,
      quantity2: item.number2
    })
  })
  let vo = {
    membersId: formData.membersId,
    remark: formData.remark,
    listGoods: listGoods
  }
    http('presented.addGiveaway',vo).then(res=>{
        if (res.code === 200) {
          // if (storeGoodsId.value && storeGoodsId.value.length > 0) {
          //   uni.$emit('warehouse',{type: 'edit',id: storeGoodsId.value})
          // }
            uni.showToast({
                title: '保存成功',
                icon: 'none',
                duration: 1000,
                success: () => {
                    uni.redirectTo({
                        url: `/pages/application/giftGoods/giftGoodsDetail?giveawayId=${res.data.giveawayId}&storeGoodsId=${storeGoodsId.value || ''}`
                    })
                }
            });
        }
    })
}

// 编辑赠送单
function submitEdit() {
  let listGoods = []
  formData.listGoods.forEach(item=>{
    listGoods.push({
      storeGoodsId: item.storeGoodsId,
      quantity: item.number,
      quantity2: item.number2
    })
  })
  let vo = {
    membersId: formData.membersId,
    remark: formData.remark,
    listGoods: listGoods
  }
    http('presented.editGiveaway', {giveawayId:parseFloat(giveawayId.value),...vo}).then(res=>{
        if (res.code === 200) {
          // if (storeGoodsId.value && storeGoodsId.value.length > 0) {
          //   uni.$emit('warehouse',{type: 'edit',id: storeGoodsId.value})
          // }
            uni.showToast({
                title: '编辑成功',
                icon: 'none',
                duration: 1000,
                success: () => {
                    uni.redirectTo({
                        url: `/pages/application/giftGoods/giftGoodsDetail?giveawayId=${res.data.giveawayId}&storeGoodsId=${storeGoodsId.value || ''}`
                    })
                }
            })
        }
    })
}

onLoad(()=>{
    getAssignerName()
})

onShow(()=>{
    const params = routeParams()
    if (params) {
        giveawayId.value = params.giveawayId
        init(params)

      if (params.storeGoodsId) {
        storeGoodsId.value = params.storeGoodsId
        handleStoreGoodsInfo(params.storeGoodsId,(item)=>{
          let dataInfo = constructorData(item)
          formData.listGoods.push(dataInfo)
        })
      }
    }
})

</script>

<style lang="scss" scoped>
    @import "../../index/popup-select";

    .container {
        padding: 20rpx;
    }

    .form {
        padding-bottom: 130rpx;
    }

    .background-color {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: rgb(247, 247, 247);
        z-index: -1;
    }

    .form-group {
        background: #ffffff;
        border-radius: 20rpx;
        padding: 0 30rpx;
        margin-bottom: 30rpx;
    }

    .button-group{
        width: 550rpx;
        margin: 10px auto;
        display: flex;
        justify-content: space-between;
        .button-item{
          width: 46%;
        }
        .btn-card{
            box-sizing: border-box;
            margin-right: 10rpx;
        }
        .text{
            font-size: 14px;
            line-height: 12px;
        }
        .icon{
            width: 20px;
            height: 20px;
            margin-right: 10rpx;
        }
    }

    .card{
        border-radius: 10rpx;
        background: #FFFFFF;
        padding: 10px 30rpx;
        margin-bottom:10px;

        ::v-deep .u-form-item__body{
            padding: 0;
        }
    }

    .text-d{
        display: inline-flex;
        margin-bottom: 10px;
        font-size: 13px!important;
        line-height: 16px!important;
        width: 100%;
        .d-line{
            /*flex-basis: 48%;*/
            margin-right: 2%;
        }
    }

    .other{
        margin-top: 10px;
        line-height: 12px;

        .other-edit{
            display: flex;
            flex-direction: column;
            margin-top: 10rpx;

            .edit-info{
                flex-basis: 48%;
                margin-right: 2%;
                display: inline-flex;
                align-items: center;
                font-size: 14px;
                line-height: 12px;
                color: #999999;

                .right-item {
                    display: flex;
                    align-items: center;
                }
            }
            .card-item {
                display: flex;
                flex-direction: column;
                align-items: flex-start;
                margin-top: 20rpx;
            }
            .edit-info-line{
                flex-basis: 100%;
                display: inline-flex;
                align-items: center;
                font-size: 14px;
                line-height: 12px;
                color: #999999;
            }
            .btn-shelf {
                height: 80rpx;
                width: 200rpx;
                margin-top: 30rpx;
                margin-left: 30rpx;
            }
            ::v-deep button {
                height: 80rpx;
            }
            ::v-deep .u-input{
                padding: 0!important;
            }
        }

        .text{
            font-size: 14px;
            line-height: 12px;
            color: #999999;
        }
    }

    .content-footer{
        position: fixed;
        left: 0;
        right: 0;
        bottom: 0;
        //display: flex;
        //flex-direction: row;
        //justify-content: space-between;
        padding: 10rpx 30rpx 30rpx;
        height: 130rpx;
        box-sizing: border-box;
        background: #ffffff;
        z-index: 10;
        //.btn {
        //    width: 100%;
        //}
        //.in-button {
        //    width: 100%;
        //    height: 100%;
        //    //background-color: $uni-color-primary;
        //    border-radius: 10rpx;
        //
        //    display: flex;
        //    justify-content: center;
        //    align-items: center;
        //    color: #FFFFFF;
        //    .btn-icon {
        //        width: 36rpx;
        //        height: 36rpx;
        //        margin-right: 16rpx;
        //    }
        //}
        //.in-button:active {
        //    background-color: $u-primary;
        //}
    }
</style>
