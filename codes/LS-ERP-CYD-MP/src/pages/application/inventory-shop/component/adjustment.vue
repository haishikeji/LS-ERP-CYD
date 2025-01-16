<template>
    <view class="container">
        <view class="form">
            <u-form labelPosition="left"
                     :model="form"
                     :rules="rules"
                     :error-type="['toast']"
                     label-width="auto"
                     :labelStyle="{...formLabelStyle}"
                     ref="formRef">
                <view class="form-group">
                    <u-form-item :borderBottom="true"
                                 label="库存数量">
                        <u-input v-model="form.number"
                                 :border="false"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 placeholder="请输入库存数量"
                        >
                        </u-input>
                    </u-form-item>
                    <u-form-item :borderBottom="false"
                                  label="调整原因">
                    <u-input v-model="form.remark"
                             :border="false"
                             inputAlign="right"
                             :customStyle="{...inputCustom}"
                             :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                             fontSize="32rpx"
                             placeholder="请输入调整原因"
                    >
                    </u-input>
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
                        label="商品单位">
                        <u-input v-model="form.unit"
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
                        label="商品数量">
                        <u-input v-model="form.quantity"
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
                        :borderBottom="false"
                        label="生产日期">
                        <u-input v-model="form.date"
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

        <view class="footer">
            <u-button type="primary" @click="tipShow = true" throttleTime="500">
                <text class="text">确定</text>
            </u-button>
        </view>
        <u-modal v-model="tipShow"
            @confirm="confirmTip"
            @cancel="cancelTip"
            :showCancelButton="true"
            title="库存调整"
            content='当前操作将变更库存，确认要继续操作？'></u-modal>

    </view>
</template>
<script setup lang="ts">
import {reactive,ref} from 'vue'
import {routeParams} from "@/router";
import {onReady, onShow} from "@dcloudio/uni-app";
import http from "@/lib/request";

let form = reactive({
    number: '',
    remark: '',
    shopName: '',
    batchNo: '',
    unit: '',
    date: '',
    quantity: '',
})

let invGoodsId = ref('')

let rules = reactive({
    'number':{
        type: 'string',
        required: true,
        message: '请输入调整数量',
        trigger: ['blur', 'change']
    },
    'remark':{
        type: 'string',
        required: true,
        message: '请输入调整原因',
        trigger: ['blur', 'change']
    }
})
let formRef = ref(null)
let tipShow = ref(false)
onShow(()=>{
    let params = routeParams()
    if (params && Object.keys(params).length > 0) {
        invGoodsId.value = params.invGoodsId
        info()
    }
})

// 获取商品详情接口
function info() {
    http('stock.getGoodsInfo',{invGoodsId: invGoodsId.value}).then(res=>{
        if (res.code === 200) {
            form.shopName = res.data.name || '-'
            form.batchNo = res.data.batchNo || '-'
            form.unit = res.data.unit || '-'
            form.date = res.data.produceDate || '-'
            form.quantity = res.data.quantity || 0
        }
    })
}

// 提示确认
function confirmTip() {
    submit()
    cancelTip()
}
// 提示取消
function cancelTip() {
    tipShow.value = false
}
// 库存变更
function submit() {
    formRef.value.validate(valid=>{
      if (valid) {
        http('stock.checkQuantity',{
          "invGoodsId":invGoodsId.value,
          "quantity":form.number,
          "remark":form.remark
        },'库存调整中...').then(res=>{
          if (res.code === 200) {
            uni.showToast({
              title: '调整完成',
              icon: "success",
              complete:()=>{
                setTimeout(()=>{
                  uni.navigateBack()
                },200)
              }
            })
          }
        })
      }
    })
}

onReady(()=>{
  setFormRule()
})

function setFormRule() {
  formRef.value.setRules(rules);
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
</style>