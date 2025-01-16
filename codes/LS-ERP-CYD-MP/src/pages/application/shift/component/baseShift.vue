<template>
  <view class="component-container" :class="{'page-no': !show}">
    <u-form :model="form" ref="uForm" label-width="130">
      <u-form-item label="接班时间" label-align="left" :borderBottom="false" class="no-input">
        <u-input :border="true" v-model="form.handinTime" placeholder="请选择接班时间"/>
        <view class="date-after" @click="isShowPicker= true"/>
      </u-form-item>
      <u-form-item label="交班员工" label-align="left" :borderBottom="false" class="no-input">
        <u-input :border="true" v-model="form.shiftEmpName" placeholder="请选择交班员工" :disabled="props.disabledField === 'shiftEmpName'"/>
        <view class="date-after" @click="openEmp('1')" v-if="props.disabledField !== 'shiftEmpName'"/>
      </u-form-item>
      <u-form-item label="接班员工" label-align="left" :borderBottom="false" class="no-input">
        <u-input :border="true" v-model="form.pickupEmpName" placeholder="请选择接班员工" :disabled="props.disabledField === 'pickupEmpName'"/>
        <view class="date-after" @click="openEmp('2')" v-if="props.disabledField !== 'pickupEmpName'"/>
      </u-form-item>
      <u-form-item label="备注" :borderBottom="false" label-position="top">
        <u-input :border="true" type="textarea" v-model="form.remark" placeholder="请输入备注"/>
      </u-form-item>
    </u-form>
    <other-remark :list="readList" :showOtherRemark="props.otherRemark"/>
<!--    <view class="remark" v-if="props.otherRemark">-->
<!--      <view class="title">-->
<!--        其他备注-->
<!--      </view>-->
<!--      <template v-for="(row,field) in readList" :key="field">-->
<!--        <view v-if="row.length > 0">-->
<!--          <text class="desc-title">{{ descTitle(field) }}</text>-->
<!--          <view v-for="(item,index) in row" :key="index">-->
<!--            <view class="text-title">{{item.name}}：</view>-->
<!--            <view class="text">{{item.remark}}</view>-->
<!--          </view>-->
<!--        </view>-->
<!--      </template>-->
<!--    </view>-->

    <!-- 员工选择-->
    <custom-employee-select v-model="empShow"
                            :type="role.CASHIER"
                            @selected="select">
    </custom-employee-select>

    <u-picker ref="picker" v-model="isShowPicker" :params="{year: true,month:true,day:true,hour:true,minute:true,second:false}"
              cancelText="取消"
              @cancel="isShowPicker = false"
              @confirm="confirmDate"
    ></u-picker>

    <view class="content-footer" v-if="props.type !='onlyRead'">
      <view class="left-button">
        <u-button type="primary" plain @click="clear">
          <text>清空缓存</text>
        </u-button>
      </view>
      <view class="right-button">
        <u-button type="primary" @click="shiftAction">
          <text>员工交班</text>
        </u-button>
      </view>
    </view>
  </view>
</template>

<script setup>
import {reactive, ref,defineEmits,defineProps,watch} from 'vue'
import http from "@/lib/request";
import {storage} from "@/lib/system/storage";
import OtherRemark from "./otherRemark"
import {role} from "@/lib/system/dict";
import {debounce} from '@/util/common'

 let form = reactive({
   pickupEmpId: '',//接班员工
   pickupEmpName: '',//接班员工
   shiftEmpId: '',// 交班员工
   shiftEmpName: '',// 交班员工
   handinTime: '',//交班时间
   remark: '',//交班备注
 })
let storeList = reactive({})
let isShowPicker = ref(false)
let empShow = ref(false)
let selectIndex = ref(0)
// 其他备注
let readList = reactive({})


let props = defineProps({
  defaultInfo:{
    type: Object,
    default() {
      return {}
    }
  },
  type: {
    type:String,
    default() {
      return 'WriteRead' //onlyRead特殊 WriteRead普通
    }
  },
  otherRemark:{
    type: Boolean,
    default() {
      return true
    }
  },
  show:{
    type:Boolean,
    default() {
      return true
    }
  },
  disabledField:{
    type:String,
    default() {
      return ''
    }
  }
})
watch(()=>props.defaultInfo,(newVale,oldValue)=>{
  if (Object.keys(newVale).length > 0) {
    form.shiftEmpId = newVale.shiftEmpId ||''
    form.shiftEmpName = newVale.shiftEmpName ||''
    form.handinTime = newVale.handinTime || ''
    form.pickupEmpId = newVale.pickupEmpId || ''
    form.pickupEmpName = newVale.pickupEmpName || ''
    change()
  }
},{deep: true})


watch(()=>props.show,(newValue,oldValue)=>{
  if (newValue) {
    getStorage()
  }
})

watch(()=>props.disabledField,(newValue,oldValue)=>{
  let userInfo = storage.getUserInfo()
  if (userInfo && Object.keys(userInfo).length > 0) {
    if (newValue === 'shiftEmpName') {
      form.shiftEmpName = userInfo.cardName
      form.shiftEmpId = userInfo.empId
    }else if (newValue === 'pickupEmpName') {
      form.pickupEmpName = userInfo.cardName
      form.pickupEmpId = userInfo.empId
    }
  }
})


let emits= defineEmits(['change'])
// 选中时间
function confirmDate(e) {
  form.handinTime = e.year+'-'+e.month+'-'+e.day+' '+e.hour+':'+e.minute
  isShowPicker.value = false
  change()
}


// 选中员工
function select(item, controlName) {
  if (selectIndex.value == '1') {
    form.shiftEmpId = item.empId
    form.shiftEmpName = item.cardName
    change()
  } else if (selectIndex.value == '2') {
    form.pickupEmpId = item.empId
    form.pickupEmpName = item.cardName
  }
  empShow.value = false
}

// 打开员工寻找  1交班 2接班
function openEmp(type) {
  selectIndex.value = type
  empShow.value = true
}

// 获取放生变化
function change() {
  emits('change',{
    pickupEmpId: form.pickupEmpId,pickupEmpName:form.pickupEmpName,
    shiftEmpId:form.shiftEmpId,shiftEmpName:form.shiftEmpName,
    handinTime: form.handinTime
  })
}

// 获取缓存
function getStorage() {
  let goodsList = uni.getStorageSync('shift-goods')
  let member = uni.getStorageSync('shift-member')
  // let deposit = uni.getStorageSync('shift-deposit')
  // let temp = {goods: [],member:[],deposit:[]}
  let temp = {goods: [],member:[]}
  if (goodsList) {
    loop(goodsList,'goods','storeGoodsId');
  }
  // if (deposit) {
  //   loop(deposit,'deposit','teaStorageOrderGoodsId');
  // }
  if (member) {
    loop(member,'member','customKey');
  }

  readList = Object.assign(readList,{...temp})

  // 循环取值
  function loop(list,field,objKey) {
    for (const key in list) {
      if (list[key].remark) {
        temp[field].push({...list[key],[objKey]: key});
      }
    }
  }
}

// 清空回收站
function clear() {
  uni.removeStorageSync('shift-goods')
  uni.removeStorageSync('shift-member')
  uni.removeStorageSync('shift-deposit')
}

// 交班
// function shiftAction() {
const shiftAction = debounce(300,()=>{
  uni.showModal({
    title: '交班提示',
    content: form.handinTime +'发起交班是否确认发起？',
    success:(result => {
      if (result.confirm) {
        http('shift.handinClasses',{
          pickupEmpId:form.pickupEmpId,
          handinTime: form.handinTime,
          remark:form.remark,
          statiticsRemark: JSON.stringify(readList)
        }).then(res=>{
          if (res.code === 200) {
            clear()
            uni.showToast("交班成功")
            uni.navigateBack()
          }
        })
      }else if (result.cancel) {

      }
    })
  })
})

</script>

<style lang="scss" scoped>
.component-container{
  height: calc(100% - 80rpx - 70rpx);
  padding: 0 30rpx 130rpx;
  overflow-y: auto;
}
.none{
  display: none;
}
.content-footer{
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  padding: 10rpx 30rpx 30rpx;
  height: 130rpx;
  box-sizing: border-box;
  background: #ffffff;
  z-index: 10;

  display: flex;
  .left-button{
    flex-basis: calc(100% / 5);
    flex-shrink: 1;
  }
  .right-button{
    flex-basis: calc(100% - 100% / 5);
    flex-shrink: 1;
    padding-left: 20rpx;
    box-sizing: border-box;
  }
}
</style>
