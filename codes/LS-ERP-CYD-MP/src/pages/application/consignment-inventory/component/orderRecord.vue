<template>
  <view :class="{wrapper: !props.modeShow}">
<!--    <view class="background-color"></view>-->
<!--    <view class="header">-->
<!--      <u-tabs-->
<!--          class="header-tabs"-->
<!--          :list="tabs"-->
<!--          v-model="currentTab"-->
<!--          v-bind="tabsAttribute"-->
<!--          @change="changeTabStatus"-->
<!--      >-->
<!--      </u-tabs>-->

<!--      <view class="filter-option" @click="isSearchShow = true">-->
<!--        筛选-->
<!--        <image class="filter-option-icon" :src="image.common.filter"></image>-->
<!--      </view>-->

<!--    </view>-->

    <view class="content">
      <view v-for="(item, index) in dataList" :key="index" class="card" @click="handleInfo(item)">
        <view class="card-head">
          <view class="left">{{item.inventoryName}}</view>
          <view class="right">{{statusFilter(item.status)}}</view>
        </view>
        <view class="card-content">
          <view class="row">
            <span class="row-label">盘点会员：</span>{{item.membersName}}
          </view>
          <view class="row">
            <span class="row-label">盘点商品：</span>{{item.kindNumber}}种
          </view>
          <view class="row">
            <span class="row-label">盘点日期：</span>{{inventoryDateFilter(item.inventoryDate)}}
          </view>
          <view class="row-bottom">{{item.operatorName ? '['+item.operatorName+']' : ''}} 创建于{{dateFilter(item.createTime)}}</view>
        </view>
      </view>
       <view @click="handleAdd" class="add-btn">
           <u-icon name="plus" :color="inlineStyleConfig.uniColorPrimary" size=""></u-icon>
       </view>
      <view v-if="dataList.length === 0" class="hint">
        <image class="hint-icon" :src="image.common.hintTo"></image>
        <view>暂无寄存盘点单</view>
      </view>
    </view>

    <!-- @open="openSearch" -->
    <u-popup v-model="props.optionShow" mode="right" class="search-popup" @close="emit('close')" >
      <view class="search-content">
        <u-form
            labelPosition="left"
            :model="searchData"
            :rules="rules"
            ref="form1"
            label-width="160rpx"
        >
          <u-form-item
              label="寄存客户"
              prop="userInfo.name"
              :borderBottom="true"
          >
            <u-input
                v-model="searchData.membersName"
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
            <view class="date-after" @click="membersShow = true"></view>
          </u-form-item>
          <u-form-item
              label="盘点日期起"
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
              label="盘点日期止"
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

          <u-form-item label="盘点状态" label-position="top">
            <u-radio-group v-model="searchData.status" :wrap="true">
              <u-radio
                  v-for="(item, index) in tabs"
                  :key="item.status"
                  :name="item.status"
              >
                {{item.name}}
              </u-radio>
            </u-radio-group>
          </u-form-item>
        </u-form>
        <view class="search-bottom">
          <view class="btn" @click="handleSearchReset">重置</view>
          <view class="btn-primary" @click="handleSearchSubmit">确定</view>
        </view>
      </view>
    </u-popup>

    <!-- 会员选择-->
    <custom-depositor-select
        v-model="membersShow"
        @selected="selectMember">
    </custom-depositor-select>

    <!-- 选择起始时间节点 -->
    <u-picker ref="picker" v-model="isShowPicker" :params="{year: true,month:true,day:true,hour:false,minute:false,second:false}"
              cancelText="取消"
              @cancel="isShowPicker = false"
              @confirm="confirmDate"
    ></u-picker>
  </view>
</template>

<script setup>
import {ref, reactive, inject,defineProps,defineEmits} from 'vue'
import {onLoad, onReachBottom, onShow} from '@dcloudio/uni-app';
import inlineStyleConfig from "@/lib/system/inlineStyleConfig";
import http from "@/lib/request";
import dayjs from 'dayjs'

let global = inject('global')
let {on,getPage} = global.__listStorage('consignment-inventory')

let props = defineProps({
  modeShow:{
    type: Boolean,
    required: true
  },
  optionShow:{
    type: Boolean,
    required: true
  }
})

let emit = defineEmits(['close'])

const tabsAttribute = reactive({
  lineColor: inlineStyleConfig.uniColorPrimary,
  inactiveStyle: {color: '#666666',fontSize: '32rpx',lineHeight: '32rpx'},
  activeStyle: {color:inlineStyleConfig.uniColorPrimary,fontSize: '32rpx',lineHeight: '32rpx'}
})

const tabs = ref([
  {name: '全部', status: 0},
  {name: '未盘点', status: 1},
  {name: '已盘点', status: 2},
  {name: '已取消', status: 3}
])
// let currentTab = ref(0)
let setPage = () => {
  return {
    pageNum: 1,
    pageSize: 10
  }
}

let searchData = reactive({
  membersId: null,
  membersName: '',
  status: tabs.value[0].status,
  startDate: null,
  endDate: null,
  ...setPage()
})
let listTotal = 0

let rules = reactive({})

// // 弹出层显示
// let isSearchShow = ref(false)
// 会员
let membersShow = ref(false)
// 显示时间选择框
let isShowPicker = ref(false)
let datetime = ref(dayjs().format('YYYY-MM-DD'))
// 1开始日期，2结束日期
let dateType = 1

// 寄存盘点单列表
let dataList = ref([])

// // 改变标签状态
// function changeTabStatus(index) {
//   let e = tabs.value[index]
//   searchData.status = e.status
//   dataList.value = []
//   handleSearch()
// }

// 选中会员
function selectMember(item) {
  searchData.membersId = item.memberId
  searchData.membersName = item.memberName
  membersShow.value = false
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

// 搜索
function handleSearch() {
  // 重置分页
  searchData.pageNum = setPage().pageNum
  searchData.pageSize = setPage().pageSize
  getList()
}

// 打开时间选择器
function openDateSelect(type) {
  dateType = type
  isShowPicker.value = true
}

// 重置
function handleSearchReset() {
  searchData.membersId = null
  searchData.membersName = ''
  searchData.startDate = null
  searchData.endDate = null
  searchData.status = 0

  handleSearch()
  emit('close')
  // isSearchShow.value = false
}

// 查询
function handleSearchSubmit() {
  handleSearch()
  emit('close')
  // isSearchShow.value = false
}

function formatter(type, value) {
  if (type === 'year') {
    return `${value}年`
  }
  if (type === 'month') {
    return `${value}月`
  }
  if (type === 'day') {
    return `${value}日`
  }
  return value
}

function statusFilter(status) {
  switch (status) {
    case 1:
      return '未盘点'
    case 2:
      return '已盘点'
    case 3:
      return '已取消'
  }
}

function inventoryDateFilter(date) {
  return dayjs(date).format('YYYY-MM-DD')
}

function dateFilter(date) {
  if (!date) return ''
  return dayjs(date).format('YYYY-MM-DD  HH:mm')
}

// 获取寄存盘点单列表
async function getList(callback = null) {
  let data = JSON.parse(JSON.stringify(searchData))
  let res = await http('deposit.pageInventory',data,'加载中...')
  // .then(res=>{
  if (res.code === 200  && res.rows) {
    let resList = res.rows
    if (!callback) {
      if (data.pageNum === 1) {
        dataList.value = resList;
      } else {
        dataList.value = [...dataList.value, ...resList];
      }
    } else {
      callback(resList)
    }
    listTotal = res.total;
  }
  // })
}

// 跳转查看寄存盘点单详情
function handleInfo(item) {
  uni.navigateTo({
    url: `/pages/application/consignment-inventory/info?inventoryId=${item.inventoryId}`
  })
}

// 跳转新增寄存盘点单
function handleAdd() {
    uni.navigateTo({
        url: '/pages/application/consignment-inventory/edit'
    })
}

onLoad(() => {
  handleSearch()
  on(async (reload,id,func)=>{
    if (reload) {
      handleSearch();
    } else {
      searchData.pageNum = getPage(searchData.pageSize,'inventoryId',id,dataList.value)
      let list = []
      await getList((lists)=>{list = [...list,...lists]})
      if (listTotal > searchData.pageNum * searchData.pageSize) {
        searchData.pageNum++
        await getList((lists)=>{list = [...list,...lists]})
      }
      func(dataList.value,list,'inventoryId',searchData.pageSize)
    }
  })
})

onReachBottom(() => {
  let currentNum = searchData.pageNum * searchData.pageSize
  if (currentNum < listTotal) {
    searchData.pageNum ++
    getList()
  }
})

</script>

<style lang="scss" scoped>

//.background-color {
//  position: fixed;
//  top: 0;
//  left: 0;
//  right: 0;
//  bottom: 0;
//  background: rgb(247, 247, 247);
//  z-index: -1;
//}

//.header {
//  display: flex;
//  justify-content: space-between;
//  align-items: center;
//  background-color: white;
//  .header-tabs {
//    flex: 1;
//  }
//  .filter-option {
//    display: flex;
//    align-items: center;
//    padding: 30rpx;
//    color: #666666;
//    position: relative;
//
//    .filter-option-icon {
//      width: 32rpx;
//      height: 32rpx;
//      margin-left: 10rpx;
//    }
//  }
//
//  .filter-option::before {
//    content: '';
//    position: absolute;
//    top: -10rpx;
//    right: -10rpx;
//    bottom: -10rpx;
//    left: -10rpx;
//  }
//}
.wrapper{
  display: none;
}

.content {
  padding: 20rpx;
  font-size: 28rpx;
}

.card {
  padding: 36rpx 30rpx 25rpx 30rpx;
  background: #FFFFFF;
  border-radius: 20rpx;
  margin-bottom: 20rpx;
  .card-head {
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
  .card-content {
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

.add-btn {
  width: 100rpx;
  height: 100rpx;
  border-radius: 50rpx;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 50rpx;
  position: fixed;
  right: 20rpx;
  bottom: 50rpx;
  box-shadow: 0px 1px 1px 1px #efefef,
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