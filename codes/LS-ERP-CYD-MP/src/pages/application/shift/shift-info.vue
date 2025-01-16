<template>
  <view class="container">
    <view class="container-sub">
      <u-subsection :list="tabType"
                    v-model="currentType"
                    active-color="#7D5131"
                    @change="changeSubsection"
                    mode="subsection"></u-subsection>
    </view>
    <u-tabs :list="list" v-model="currentTab" @change="change"></u-tabs>
    <goods-list :show="list[currentTab].value==='goodsList'" :list="goodsInfo" type="onlyRead"></goods-list>
<!--    <deposit-list :show="list[currentTab].value==='depositRecord'" :list="teaStorageInfo" type="onlyRead"></deposit-list>-->
    <member-list :show="list[currentTab].value==='memberRecord'" :memberList="customInfo" type="onlyRead"></member-list>
    <order-list :show="list[currentTab].value==='payRecord'" :orderList="saleInfo" type="onlyRead"></order-list>
    <base-shift :show="list[currentTab].value === 'baseShift'"
                :type="status === 1 ? 'WriteRead' : 'onlyRead'"
                :defaultInfo="defaultInfo"
                :staffInfo= "stuffList"
                :readList="readList"></base-shift>
  </view>
</template>

<script lang="ts" setup>
import {ref,reactive} from 'vue'
import GoodsList from './component/goodsList'
import MemberList from './component/memberList'
import OrderList from './component/oderList'
import DepositList from './component/depositList'
import BaseShift from './component/baseShiftInfo'
import BaseStaff from './component/baseStaff'
import {onLoad} from "@dcloudio/uni-app";
import {storage} from '@/lib/system/storage'
import dayjs from "dayjs";
import http from '@/lib/request'

let list = ref([
  {name: '基础信息',value:'baseShift'},
  {name: '已销售商品',value:'goodsList'},
  {name: '已支出情况',value:'payRecord'},
  {name: '已接待情况',value:'memberRecord'},
  // {name: '领寄存情况',value:'depositRecord'},
])
// 班次
let tabType = ref([])
let currentType = ref(0)
let currentTab = ref(0)
let defaultInfo = reactive({
  shiftEmpId: '',// 交班员工
  shiftEmpName: '',// 交班员工
  startTime: '',
  endTime: '',
  handinEmpId: '',// 接班员工
  handinEmpName: '', // 接班员工
  remark: '', // 备注
  otherRemark: '' // 其他备注
})
let readList = reactive({})
let stuffList = ref([])

let goodsInfo = ref([])
let saleInfo = ref([])
let teaStorageInfo = ref([])
let customInfo = ref([])
let id = ref('')
let status = ref(2) //1-待接班 2-在班 3-已交班

function init() {
  getStaff((res)=>{
    // 是否有的上次记录
    if (res.data.beforeEmpId && res.data.beforeEmpId !== 0) {
      tabType.value = [
        {name: res.data.beforeEmpName, value: res.data.beforeClassesId},
        {name: res.data.empName, value: res.data.classesId}
      ];
    } else {
      tabType.value = [
        {name: res.data.empName, value: res.data.classesId}
      ];
    }

    currentType.value = tabType.value.length - 1;
    constructorData(res)
  })
}

// 获取接班员工
function getStaff(callback=(e)=>{}) {
  http('shift.getClassesInfo',{primaryId:id.value}).then(res=>{
    if (res.code === 200) {
      customInfo.value = []
      teaStorageInfo.value = []
      saleInfo.value = []
      goodsInfo.value =[]
      callback(res)

    }
  })
}

function constructorData(res) {
  let remarkText = res.data.statiticsRemark;
  status.value = res.data.status
  if (remarkText && remarkText.length > 0 ) {
    remarkText = JSON.parse(remarkText)
  }else {
    remarkText = {goods: [],member:[],deposit:[]}
  }
  readList = Object.assign(readList,{...remarkText})

  if (res.data.customInfo && res.data.customInfo.length > 0) {
    customInfo.value = JSON.parse(res.data.customInfo);
    remarkText.member.forEach(item=>{
      customInfo.value.map(child=>{
        if (item.customId+'' === child.customId+''){
          child.remark = item.remark
        }
        return child
      })
    })
  }

  if (res.data.teaStorageInfo && res.data.teaStorageInfo.length > 0) {
    teaStorageInfo.value = JSON.parse(res.data.teaStorageInfo);
    remarkText.deposit.forEach(item=>{
      teaStorageInfo.value.map(child=>{
        if (item.teaStorageOrderGoodsId+'' === child.teaStorageOrderGoodsId+''){
          child.remark = item.remark
        }
        return child
      })
    })
  }

  if (res.data.saleInfo && res.data.saleInfo.length > 0) {
    saleInfo.value = JSON.parse(res.data.saleInfo);
  }


  if (res.data.goodsInfo && res.data.goodsInfo.length > 0) {
    goodsInfo.value = JSON.parse(res.data.goodsInfo).listGoods;
    remarkText.goods.forEach(item=>{
      goodsInfo.value.map(child=>{
        if (item.storeGoodsId+'' === child.storeGoodsId+''){
          child.remark = item.remark
        }
        return child
      })
    })
  }

   stuffList.value = []
  if (res.data.dailyInfo && res.data.dailyInfo.length > 0) {
    let dailyInfo = JSON.parse(res.data.dailyInfo)
    dailyInfo.map(item=>{
      remarkText = {member:[]}
      if (item.staticRemark && item.staticRemark.length > 0) {
        remarkText = Object.assign(remarkText,{...JSON.parse(item.staticRemark)})
      }
      stuffList.value.push({
        empId: item.empId,
        empName: item.empName,
        readList: remarkText,
        remark: item.remark,
        submitTime: item.submitTime
      })
    })
  }

  // 员工客户列表
  customInfo.value.map(child=>{
    stuffList.value.forEach(item=>{
      if (child.empId + '' === item.empId + '') {
        item.readList.member.forEach(val=>{
          if (val.customId + '' === child.customId + '') {
            child.remark = val.remark
          }
        })
      }
    })
    return child
  })

  // stuffList.value.forEach(item=>{
  //   customInfo.value.map(child=>{
  //     if (item.empId+'' === child.empId+''){
  //       item.readList.member.forEach(val=>{
  //         if (val.customId + '' === child.customId + '') {
  //           child.remark = val.remark;
  //         }
  //       })
  //     }
  //     return child
  //   })
  // })

  defaultInfo = Object.assign(defaultInfo,{
    handinEmpId: res.data.empId,
    handinEmpName: res.data.empName,
    shiftEmpName: res.data.afterEmpName,
    shiftEmpId: res.data.afterEmpId,
    createTime: res.data.createTime,
    startTime: res.data.startTime,
    endTime: res.data.endTime,
    remark: res.data.remark
  });

}

function change(index) {
  let item = list.value[index]
}

function changeSubsection(index) {
  let classesId = tabType.value[index].value
  id.value = classesId
  currentType.value = index
  getStaff((res)=>{
    constructorData(res)
  })
}

onLoad((params)=>{
  id.value = params.id
  init()
})
</script>

<style lang="scss" scoped>
.container {
  position: relative;
  height: calc(100vh - var(--status-bar-height));

  .container-sub{
    padding: 0 20rpx;
  }
}
</style>