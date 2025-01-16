<template>
  <scroll-view class="scroll-view" :scroll-y="true" @scrolltolower="scrollChange">
    <view class="card">
      <!-- 个人 -->
      <view v-show="isPersonage">
        <!-- header -->
        <total-statistics-card
            :type="1"
            :personageInfo="percentageInfo"
            @switchType="handlePersonage"
            @clickDate="handleDate"
        >
          <template #date>
            {{dateFormat(searchData.selectDate)}}
          </template>
        </total-statistics-card>

        <sj-card title="业绩来源" :isContentTop="false" class="sj-card">
          <view class="source-performance">
            <view class="content-item">
              <view class="item-text">充值提成(元)</view>
              <view class="column-label">{{ keepDecimal(sourcePerformanceInfo.percentageChargeCope) }}</view>
              <view class="column-value">共{{ sourcePerformanceInfo.percentageReceptionCount }}笔</view>
            </view>

            <view class="content-item">
              <view class="item-text">接待提成(元)</view>
              <view class="column-label">{{ keepDecimal(sourcePerformanceInfo.percentageReceptionCope) }}</view>
              <view class="column-value">共{{ sourcePerformanceInfo.percentageReceptionCount }}笔</view>
            </view>

            <view class="content-item">
              <view class="item-text">协助提成(元)</view>
              <view class="column-label">{{ keepDecimal(sourcePerformanceInfo.percentageAssistCope) }}</view>
              <view class="column-value">共{{ sourcePerformanceInfo.percentageAssistCount }}笔</view>
            </view>
          </view>
        </sj-card>

        <sj-card title="员工销售排行榜" :isContentTop="false">
          <view class="card-header">
            <view
                @click="changeType(1)"
                :class="isSelected == 'all' ? 'select-btn' : 'origin-btn'"
            >全部</view>
            <view
                @click="changeType(2)"
                :class="isSelected == 'recharge' ? 'select-btn' : 'origin-btn'"
            >按充值</view>
            <view
                @click="changeType(3)"
                :class="isSelected == 'receive' ? 'select-btn' : 'origin-btn'"
            >按接待</view>
            <view
                @click="changeType(4)"
                :class="isSelected == 'assist' ? 'select-btn' : 'origin-btn'"
            >按协助</view>
          </view>

          <scroll-view   class="scroll" :scroll-y="true" @scrolltolower="scrollChange">
            <view v-for="(item,index) in staffListData"
                  :key="index"
                  @click="toForm(item.staffId)"
                  class="members-content"
            >
              <view class="members-row box">
                <view class="members-row-left box-left">
                  <view class="left-index">{{ index + 1 }}</view>

                  <view class="left-content">
                    <view class="member-name">{{ item.staffName}}</view>
                    <view class="member-num">共{{ item.staffCount || '0' }}笔</view>
                  </view>
                </view>

                <view class="right-content">{{ keepDecimal(item.staffCope) }}元</view>

              </view>
            </view>

            <view class="last-text">{{ message }}</view>
          </scroll-view>
        </sj-card>
      </view>

      <!-- 团队 -->
      <view v-show="!isPersonage">
        <!-- header -->
        <total-statistics-card
            :type="2"
            :teamInfo="teamInfo"
            @switchType="handlePersonage"
            @clickDate="handleDate"
        >
          <template #date>
            {{dateFormat(searchData.selectDate)}}
          </template>
        </total-statistics-card>

        <sj-card title="每日团队业绩提成分布" :isContentTop="false">
          <span class="statistical-hint">
            本月 <span style="color: #202020">{{ reportPerformanceDetail.maxDate }}</span>日的业绩提成最高，
            共<span style="color: #202020">{{ keepDecimal(reportPerformanceDetail.maxCope) }}</span>元
          </span>

          <bar-chart
              class="bar-chart"
              v-show="reportPerformanceDetail.listNode.length > 0"
              :list="reportPerformanceDetail.listNode">
          </bar-chart>
          <view v-show="reportPerformanceDetail.listNode.length === 0" class="prompt">暂无数据</view>
        </sj-card>
      </view>
    </view>

    <u-picker mode="time" v-model=showDatetime :params="{year: true,month: true,day: false,hour:false,minute:false,second:false}"
        @close="showDatetime = false"
        @cancel="showDatetime = false"
        @confirm="confirmDate"
    ></u-picker>
  </scroll-view>
</template>

<script setup>
import {ref,reactive,onMounted} from "vue";
import http from "../../../../../lib/request";
import dayjs from 'dayjs'
import {keepDecimal} from '@/util/common'
import totalStatisticsCard from './totalStatisticsCard'
import BarChart from '../../sales-overview/component/BarChart'
import SjCard from '../../sales-overview/component/SjCard'

let message = ref('已没有更多信息')

// 是否为消费
let isPersonage = ref(true)

// 是否显示日期选择框
let showDatetime = ref(false)

let datetime = ref(dayjs())

let searchData = reactive({
  // 1-日统计 2-月统计 3-年统计
  mode: 2,
  selectDate: dayjs().format('YYYY-MM')
})

let paramsQuery = reactive({
  pageNum: 1,
  pageSize: 15,
  total: null
})

// 单击选择--默认为 all
let isSelected = ref('all')

// 个人业绩详情
let percentageInfo = reactive({
  cope: 55928, // 业绩总额
})

// 团队业绩详情
let teamInfo = reactive({
  cope: 625920, // 业绩总额
})

let sourcePerformanceInfo = reactive({
  percentageChargeCope: 39968, // 充值提成金额
  percentageChargeCount: 36, // 充值提成笔数
  percentageReceptionCope: 12360, // 接待提成金额
  percentageReceptionCount: 65, // 接待提成笔数
  percentageAssistCope: 12360, // 协助提成金额
  percentageAssistCount: 65, // 协助提成笔数
})

// 员工销售统计列表
let staffListData = ref([
  {staffId: '1',staffName: '周明华',staffCount: 98,staffCope: 36000},
  {staffId: '2',staffName: '李美娜',staffCount: 62,staffCope: 33000},
  {staffId: '3',staffName: '刘清峰',staffCount: 65,staffCope: 30000},
  {staffId: '4',staffName: '王红梅',staffCount: 55,staffCope: 28000},
  {staffId: '5',staffName: '陈美敏',staffCount: 36,staffCope: 12000},
  {staffId: '6',staffName: '张华',staffCount: 1,staffCope: 12000}
])

let reportPerformanceDetail = ref({
  maxDate: 0, //每月最高日 / 每年最高月",
  maxCope: 0, //每月最高日额 / 每年最高月额",
  listNode: [
    // {
    //     "type":"int //类型",
    //     "typeName":"string //类型名称",
    //     "count":"int //笔数",
    //     "cope":"double //金额"
    // }
  ]
})

// 充值or消费
function handlePersonage(value) {
  let type = value.type
  isPersonage.value = type == 1 ? true : false
  // getInfo()
}

// 显示日期选择框
function handleDate() {
  showDatetime.value = true
}

// 选择日期
function confirmDate(val) {
  // searchData.selectDate = dayjs(val.value).format('YYYY-MM')
  searchData.selectDate = `${val.year}-${val.month}`
  showDatetime.value = false

  // getInfo()
}

// 格式化时间
function dateFormat(val) {
  return dayjs(val).format("YYYY年MM月")
}

function isNotScrollEnd() {
  return paramsQuery.total > paramsQuery.pageNum * paramsQuery.pageSize
}
// 滚动
function scrollChange() {
  if (isNotScrollEnd()) {
    paramsQuery.pageNum++;
    // handleList(false);
  } else {
    setMessage(2)
  }
}
//设置信息
function setMessage(status) {
  switch (status + '') {
    case '1':
      message.value = '正在加载更多信息'
      break;
    case '2':
      message.value = '已没有更多信息'
      break;
    case '0':
      message.value = '上拉获取更多信息'
      break;
  }

}

// 全部 or 充值 or 接待 or 协助
function changeType(value) {
  // int 1-全部 2-充值 3-接待 4-协助
  switch (value) {
    case 1:
      // type.value = value
      isSelected.value = 'all'
      break;
    case 2:
      // type.value = value
      isSelected.value = 'recharge'
      break;
    case 3:
      // type.value = value
      isSelected.value = 'receive'
      break;
    case 4:
      // type.value = value
      isSelected.value = 'assist'
      break;
  }
}

// 跳转到员工业绩详情
function toForm() {
  uni.navigateTo({
    url: '/pages/application/report/performance-staff-statistics/index'
  })
}

onMounted(()=>{

})
</script>

<style lang="scss" scoped>
.card {
  box-sizing: border-box;
  //margin: 20rpx 0;

  font-family: Hiragino Sans GB;
  font-weight: normal;

  .sj-card {
    .source-performance {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-top: 20rpx;

      .content-item {
        text-align: center;

        .item-text,
        .column-value {
          font-size: 24rpx;
          color: #999999;
        }

        .column-label {
          margin-top: 10rpx;
          color: #20252C;
        }

        .column-value {
          margin-top: 10rpx;
        }
      }
    }
  }

  .card-header {
    display: flex;
    align-items: center;
    font-size: 24rpx;
    margin-top: 20rpx;

    .select-btn {
      box-sizing: border-box;
      color: $uni-color-primary;
      border: 1rpx solid $uni-color-primary;
      border-radius: 50rpx;
      padding: 10rpx 20rpx;
      position: relative;
    }
    .origin-btn {
      box-sizing: border-box;
      color: #666666;
      padding: 10rpx 20rpx;
      position: relative;
    }
    // 扩大可点击区域
    .select-btn::before, .origin-btn::before {
      content: '';
      position: absolute;
      top: -10rpx;
      right: -15rpx;
      bottom: -10rpx;
      left: -15rpx;
    }
  }

  .scroll {
    box-sizing: border-box;
    padding-top: 20rpx;
    height: calc(26vh - 50rpx);

    .members-content {

      &:first-child .left-index{
        font-size: 32rpx;
        color: #F6971B;
      }

      &:nth-child(2) .left-index{
        font-size: 32rpx;
        color: #747982;
      }

      &:nth-child(3) .left-index{
        font-size: 32rpx;
        color: #EE6741;
      }

      &:nth-child(n+4) .left-index{
        font-size: 28rpx;
        color: #666666;
      }

      .box {
        display: flex;
        justify-content: space-between;
        padding: 24rpx 0;
        border-bottom: 1rpx solid #EEEEEE;

        .box-left {
          display: flex;
          align-items: center;

          .left-content {
            margin-left: 45rpx;

            .consume-name,
            .member-name {
              font-size: 28rpx;
              color: #202020;
            }

            .consume-num,
            .member-num {
              font-size: 24rpx;
              color: #999999;
            }
          }
        }
        .right-content {
          font-size: 28rpx;
          color: #202020;
        }
      }
    }
  }

  .statistical-hint {
    font-size: 28rpx;
    color: #999999;
    margin: 20rpx 0;
    display: inline-block;
  }

  .bar-chart {
    height: 1900rpx;
  }

  .prompt {
    margin-top: 20rpx;
    color: #666666;
    text-align: center;
  }

  .last-text{
    text-align: center;
    margin-top: 25rpx;
    font-size: 28rpx;
    color: #999999;
  }
}
</style>