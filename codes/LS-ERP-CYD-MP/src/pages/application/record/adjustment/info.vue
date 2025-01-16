<template>
    <view class="container">
        <view class="form">
            <u-form labelPosition="left"
                     :model="form"
                     :rules="rules"
                    :error-type="['toast']"
                     label-width="auto"
                     :label-style="{...formLabelStyle}"
                     ref="formRef">
                <view class="form-group">
                    <u-form-item :borderBottom="false"
                                 label="商品图片">
                        <view class="item-img">
<!--                          :src="getViewImage() + form.logo + fileViewAfter"-->
                            <u-avatar :text="form.logo ? '' : '商品'"
                                      :src="getViewImage() + form.logo"
                                      mode="square"
                                      size="150"></u-avatar>
                        </view>
                    </u-form-item>
                </view>

                <view class="form-group">
                    <u-form-item :borderBottom="true"
                                 label="商品名称">
                        <u-input v-model="form.shopName"
                                 :border="false"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 :disabled="true"
                                 disabledColor="#ffffff">
                        </u-input>
                    </u-form-item>
                    <u-form-item :borderBottom="true"
                                 label="销售单价">
                        <u-input v-model="form.salePrice"
                                 :border="false"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 :disabled="true"
                                 disabledColor="#ffffff">
                        </u-input>
                    </u-form-item>
                    <u-form-item
                        :borderBottom="true"
                        @click="chargeShow = true"
                        label="批次号">
                        <u-input v-model="form.batchNo"
                                 :border="false"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 :disabled="true"
                                 disabledColor="#ffffff">
                        </u-input>
                    </u-form-item>
                    <u-form-item
                        :borderBottom="true"
                        @click="chargeShow = true"
                        label="生产日期">
                        <u-input v-model="form.createTime"
                                 :border="false"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 :disabled="true"
                                 disabledColor="#ffffff">
                        </u-input>
                    </u-form-item>
                    <u-form-item
                        :borderBottom="true"
                        @click="chargeShow = true"
                        label="盘点数量">
                        <u-input v-model="form.quantity"
                                 :border="false"
                                 inputAlign="right"
                                 :color="inlineStyleConfig.uniColorPrimary"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 :disabled="true"
                                 disabledColor="#ffffff">
                        </u-input>
                    </u-form-item>
                    <u-form-item
                        :borderBottom="false"
                        label="盘点时间">
                        <u-input v-model="form.checkDate"
                                 :border="false"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 :disabled="true"
                                 disabledColor="#ffffff">
                        </u-input>
                    </u-form-item>
                </view>

                <view class="form-group">
                    <u-form-item
                        :borderBottom="true"
                        label="盘点结果">
                        <u-input v-model="form.typeName"
                                 :border="false"
                                 :color="form.color"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 :disabled="true"
                                 disabledColor="#ffffff">
                        </u-input>
                    </u-form-item>
                    <u-form-item  :borderBottom="true"
                                  label="盘点原因">
                        <view class="remark">
                            <text class="text">
                                {{ form.remark }}
                                <!--                                由于商品包装袋破裂！导致包装漏气，从而导致商品质量下滑，未达到食品售卖规范。自此提出商品盘点。-->
                            </text>
                        </view>
                    </u-form-item>
                    <u-form-item
                        :borderBottom="false"
                        label="盘点后的数量">
                        <u-input v-model="form.lossQuantity"
                                 :border="false"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 :disabled="true"
                                 disabledColor="#ffffff">
                        </u-input>
                    </u-form-item>
                </view>

            </u-form>
        </view>

    </view>
</template>
<script setup lang="ts">
import {reactive,ref} from 'vue'
import {routeParams} from "@/router";
import {getViewImage,fileViewAfter} from '@/env'
import {onShow} from "@dcloudio/uni-app";
import http from "@/lib/request";
import inlineStyleConfig from "@/lib/system/inlineStyleConfig";
import {adjustmentList} from "@/lib/system/dict";

let form = reactive({
    logo:'',
    shopName: '',
    salePrice: '',
    batchNo: '',
    createTime: '',
    quantity: '',
    checkDate: '',
    lossQuantity: '',
    remark: '',
    typeName: '',
    color: ''
})

let recordId = ref('')

onShow(()=>{
    let params = routeParams()
    if (params && Object.keys(params).length > 0) {
        recordId.value = params.checkRecordId
        info()
    }
})

// 获取商品详情接口
function info() {
    http('stock.getCheckQuantityInfo',{recordId: recordId.value}).then(res=>{
        if (res.code === 200) {
            form.shopName = res.data.goodsName || '-'
            form.batchNo = res.data.batchNo || '-'
            form.logo = res.data.logo || ''
            form.salePrice = (res.data.salePrices || 0) + '元'
            form.createTime = res.data.produceDate || '-'
            form.checkDate = res.data.checkDate || '-'
            form.lossQuantity = (res.data.afterQuantity || 0)  + res.data.unit
            form.quantity = (res.data.quantity || 0) + res.data.unit
            form.remark = res.data.remark ||'-'
            let find = adjustmentList.filter(item=>{
                return item.code == res.data.type
            })
            form.typeName = (find && find.length > 0) ? find[0].name : '-'
            form.color = res.data.type  == 1 ? inlineStyleConfig.uPprimary : inlineStyleConfig.uWwarning
        }
    })
}


</script>
<style lang="scss" scoped>
    @import "~@/pages/index/form.scss";


    .footer{
        position: fixed;
        bottom: 0;
        left: 0;
        width: 100%;
        padding: 10rpx 30rpx;
        box-sizing: border-box;
        background: #FFFFFF;
        .text{
            font-size: 32rpx;
            font-family: Hiragino Sans GB;
            font-weight: normal;
            color: #FFFFFF;
        }
    }
    .remark{
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: flex-end;
        min-height: 60rpx;
        padding-left: 20rpx;
    }
</style>
