<template>
  <scroll-view :scroll-y="true" @scrolltolower="scrollChange">
    <!--header-->
    <total-account-card
        :totalCope="totalCope"
        @clickDate="handleDate"
    >
      <template #date>
        {{dateFormat(searchData.selectDate)}}
      </template>
    </total-account-card>

    <view class="form">
      <view class="form-group">
        <view class="form-header">
          <view class="header-left">
            <view class="icon"></view>
            <text class="text">账款记录</text>
          </view>
          <view class="header-right" @click="showPopup = true">
            <text class="screen-text">全部</text>
            <image :src="image.plugIcon.screen" class="screen-icon"></image>
          </view>
        </view>
        <view v-if="billList.length > 0">
          <view
              @click="paymentInfo(item.code)"
              class="content-item"
              v-for="(item,index) in billList"
              :key="index">
            <view class="item-text">
              <view class="box1">
                <text class="goods-type">{{ item.type }}</text>
                <text class="money-text">{{ keepDecimal(item.money) }}</text>
              </view>
              <view class="box2">
<!--                <text class="time-text">{{ dayjs(item.time).format('HH:mm:ss') || '' }}</text>-->
                <text class="time-text">{{ item.time || '' }}</text>
                <text class="payment-method">{{ item.paymentMethod }}</text>
              </view>
            </view>
          </view>
        </view>

        <view class="none" v-else>
          <u-empty
              mode="data"
              :icon="image.plugIcon.empty"
              text="暂无记录"
              class="u-empty">
          </u-empty>
        </view>
      </view>

      <view class="last-text">{{ message }}</view>
    </view>

<!--    <u-datetime-picker-->
<!--        :show="showDatetime"-->
<!--        v-model="datetime"-->
<!--        mode="time"-->
<!--        @close="showDatetime = false"-->
<!--        @cancel="showDatetime = false"-->
<!--        @confirm="confirmDate"-->
<!--    ></u-datetime-picker>-->

    <u-picker mode="time"
              @confirm="confirmDate"
              @cancel="showDatetime = false"
              v-model="showDatetime" :params="{
					year: true,
					month: true,
					day: true,
					hour: false,
					minute: false,
					second: false
				}"></u-picker>

<!--    弹出层-->
    <u-popup
        class="popup"
        :customStyle="{width: '650rpx',marginTop: '88rpx',padding: '0 30rpx',position: 'relative',boxSizing: 'border-box'}"
        :show = "showPopup"
        :safeAreaInsetTop="true"
        @close="closePopup"
        @open="openPopup"
        mode="right">
      <view class="popup-content">
        <view class="account-content">
          <view class="account-type">账款类型</view>
          <text class="goods-purchase text">商品采购</text>
          <text class="member-consumption text">会员消费</text>
        </view>

        <view class="payment-content">
          <view class="payment-method">支付方式</view>
          <view class="method">
            <text class="payment-hy text">会员支付</text>
            <text class="payment-wx text">微信支付</text>
            <text class="payment-zfb text">支付宝</text>
            <text class="payment-xj text">现金支付</text>
          </view>
        </view>

        <view class="amount-content">
          <view class="range-amount">帐款金额范围(元)</view>
          <view class="input">
            <u-input v-model="minMoney"
                     :border="false"
                     inputAlign="center"
                     :customStyle="{width: '210rpx',background: '#F7F7F7',borderRadius: '6rpx',padding: '16rpx 0',boxSizing: 'border-box'}"
                     :placeholderStyle="{color: '#B9B9B9'}"
                     fontSize="28rpx"
                     disabledColor="#ffffff"
                     placeholder="最低价">
            </u-input>
            <view class="icon">——</view>
            <u-input v-model="maxMoney"
                     :border="false"
                     inputAlign="center"
                     :customStyle="{width: '190rpx',background: '#F7F7F7',borderRadius: '6rpx',padding: '16rpx 0',boxSizing: 'border-box'}"
                     :placeholderStyle="{color: '#B9B9B9'}"
                     fontSize="28rpx"
                     disabledColor="#ffffff"
                     placeholder="最高价">
            </u-input>
          </view>
        </view>

        <view class="popup-footer">
          <u-button
              :plain="true"
              class="content-button"
              :customStyle="{...btnStyle1,}">
            <text class="content-button-text">重置</text>
          </u-button>

          <u-button
              :plain="true"
              class="content-button btn2"
              :customStyle="{...btnStyle2,}">
            <text class="content-button-text">确定</text>
          </u-button>
        </view>
      </view>
    </u-popup>
  </scroll-view>
</template>

<script setup>
  import { ref,reactive,onMounted } from 'vue'
  // import inlineStyleConfig from "@/lib/system/inlineStyleConfig";
  import dayjs from 'dayjs'
  import {keepDecimal} from '@/util/common'
  import totalAccountCard from '../../receivables/component/totalAccountCard'
  import {image} from '@/lib/system/imageStorage'

  let btnStyle1 = reactive({borderRadius: '10rpx',border: '1rpx solid #CCCCCC',background: '#FFFFFF'})
  let btnStyle2 = reactive({borderRadius: '10rpx',border:false,background: '#886445'})

  let message = ref('')
  // 总金额
  let totalCope = ref(null)
  // 最低金额
  let minMoney = ref('')
  // 最高金额
  let maxMoney = ref('')

  // 是否显示日期选择框
  let showDatetime = ref(false)

  let datetime = ref(dayjs())

  let searchData = reactive({
    // 1-日营业 2-月营业 3-年营业
    mode: 1,
    selectDate: dayjs().format('YYYY-MM-DD')
  })

  let paramsQuery = reactive({
    pageNum: 1,
    pageSize: 15,
    total: null
  })

  // 是否显示弹出层
  let showPopup = ref(false)

  // 帐单列表
  let billList = ref([
    {id: 0,code: '0',money: 365, time: '15:56:02',type: '商品采购',paymentMethod: '微信支付'},
    {id: 1,code: '1',money: 365, time: '15:56:02',type: '商品采购',paymentMethod: '微信支付'},
    {id: 2,code: '2',money: 365, time: '15:56:02',type: '商品采购',paymentMethod: '微信支付'},
    {id: 3,code: '3',money: 365, time: '15:56:02',type: '商品采购',paymentMethod: '微信支付'},
    {id: 4,code: '4',money: 365, time: '15:56:02',type: '商品采购',paymentMethod: '支付宝支付'},
    {id: 5,code: '5',money: 365, time: '15:56:02',type: '商品采购',paymentMethod: '微信支付'},
  ])

  // 计算总金额
  function getTotalCope() {
    // 数据
    let data = []
    if (billList.value) {
      billList.value.forEach(item=>{
        data.push(item.money)
      })
      for (let i = 0; i < data.length; i++) {
        totalCope.value += data[i]
      }
    }
  }

  function handleDate() {
    showDatetime.value = true
  }

  function confirmDate(val) {
    // searchData.selectDate = dayjs(val.value).format('YYYY-MM-DD')
    searchData.selectDate = `${val.year}-${val.month}-${val.day}`
    showDatetime.value = false

    // getInfo()
    getTotalCope()
  }

  // 格式化时间
  function dateFormat(val) {
    return dayjs(val).format("YYYY年MM月DD日")
  }

  // 弹出层收起
  function closePopup() {
    showPopup.value = false
  }

  // 弹出层打开
  function openPopup() {
    showPopup.value = true
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

  // 跳转进入详情界面
  function paymentInfo() {
    uni.navigateTo({
      url: '/pages/application/capital/payables/info'
    })
  }

  onMounted(()=>{
    getTotalCope()
  })

</script>

<style lang="scss" scoped>
  .form {
    position: relative;
    margin-top: 20rpx;

    .form-group {
      background: #ffffff;
      border-radius: 20rpx;
      padding: 0 30rpx;
      margin-bottom: 20rpx;
      position: relative;
      overflow: hidden;

      .form-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 35rpx 0;
        border-bottom: 1rpx solid #EEEEEE;

        .header-left {
          display: flex;
          flex-direction: row;
          align-items: center;

          .icon {
            width: 4rpx;
            height: 30rpx;
            background: $uni-color-primary;
            border-radius: 2rpx;
            margin-right: 12rpx;
          }

          .text {
            font-size: 32rpx;
            font-family: Hiragino Sans GB;
            font-weight: normal;
            color: #202020;
            line-height: 44rpx;
          }
        }

        .header-right {
          display: flex;
          align-items: center;

          .screen-text {
            font-size: 28rpx;
            font-family: Hiragino Sans GB;
            font-weight: normal;
            color: #666666;
            //line-height: 36rpx;
          }

          .screen-icon {
            width: 32rpx;
            height: 32rpx;
          }
        }
      }

      .content-item {
        display: flex;
        align-items: center;
        padding: 30rpx 0;
        border-bottom: 1rpx solid #EEEEEE;

        &:last-child {
          border-bottom: 0;
        }

        .item-text {
          flex: 1;

          .box1,
          .box2 {
            display: flex;
            justify-content: space-between;
            align-items: center;
          }

          .box1 {
            margin-bottom: 20rpx;
            font-size: 32rpx;
            font-family: HiraginoSansGB-W3;
            font-weight: normal;
            color: #202020;
            //line-height: 50rpx;
          }

          .box2 {
            font-size: 28rpx;
            font-family: Hiragino Sans GB;
            font-weight: normal;
            color: #999999;
            //line-height: 50rpx;
          }
        }
      }
    }

    .last-text{
      text-align: center;
      margin-top: 25rpx;
      margin-bottom: 25rpx;
      font-size: 28rpx;
      color: #999999;
    }

    .none{
      padding-bottom: 20rpx;
    }
  }

  .popup-content {
    position: relative;

    .account-content {
      margin-top: 58rpx;

      .goods-purchase {
        margin-right: 20rpx;
      }
    }

    .payment-content {
      display: flex;
      flex-wrap: wrap;
      margin-top: 58rpx;

      .method {
        display: flex;
        flex-wrap: wrap;

        .text {
          margin-right: 20rpx;
        }

        .payment-zfb {
          margin-right: 0;
        }

        .payment-xj {
          margin-top: 20rpx;
        }
      }
    }

    .amount-content {
      margin-top: 58rpx;

      .input {
        display: inline-flex;
        align-items: center;

        .icon {
          margin-right: 20rpx;
          margin-left: 20rpx;
          color: #DBDBDB;
        }
      }
    }

    .popup-footer {
      position: fixed;
      box-sizing: border-box;
      width: 580rpx;
      bottom: 0;
      right: 0;
      height: 110rpx;

      display: flex;
      align-items: flex-start;
      justify-content: space-between;

      .content-button {
        width: 245rpx;
        height: 80rpx;
        font-size: 32rpx;
        font-family: Hiragino Sans GB;
        font-weight: normal;
        color: #202020;
        line-height: 36rpx;
      }

      .btn2 {
        color: #FFFFFF;
      }
    }

    // popup中的公共样式
    .account-type,
    .payment-method,
    .range-amount {
      margin-bottom: 24rpx;
      font-size: 28rpx;
      font-family: Hiragino Sans GB;
      font-weight: normal;
      color: #666666;
      //line-height: 36rpx;
    }

    .text {
      box-sizing: border-box;
      padding: 16rpx 25rpx;
      background: #F7F7F7;
      border-radius: 6rpx;
      font-size: 28rpx;
      font-family: Hiragino Sans GB;
      font-weight: normal;
      color: #202020;
      //line-height: 36rpx;
    }
  }
</style>