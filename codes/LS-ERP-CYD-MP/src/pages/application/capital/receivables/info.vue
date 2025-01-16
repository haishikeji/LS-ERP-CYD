<template>
  <view class="container">
    <view class="content">
      <view class="content-header">
        <view class="header-title">账款金额(元)</view>
        <view class="header-content" v-if="pageType == 'consumptionStore'">
          {{ parseFloat(consumptionStore.realCope).toFixed(2) }}
        </view>
        <view class="header-content" v-else-if="pageType == 'goodsRetail'">
          {{ parseFloat(goodsRetail.realCope).toFixed(2) }}
        </view>
        <view class="header-content" v-else-if="pageType == 'memberCharged'">
          {{ parseFloat(memberCharged.realCope).toFixed(2) }}
        </view>
      </view>

      <view class="amount-info">
        <view class="amount-type content-item">
          <text class="amount-text text">账款类型</text>
          <view class="amount-content box1" v-if="pageType == 'consumptionStore'">{{ consumptionStore.orderTypeName }}</view>
          <view class="amount-content box1" v-else-if="pageType == 'goodsRetail'">{{ goodsRetail.orderTypeName }}</view>
          <view class="amount-content box1" v-else-if="pageType == 'memberCharged'">{{ memberCharged.orderTypeName }}</view>
        </view>

        <view>
          <view class="xfwz content-item" v-if="pageType == 'consumptionStore'">
            <text class="xfwz-text text">消费位置</text>
            <view class="xfwz-content box1">{{ consumptionStore.roomName }}</view>
          </view>

          <view class="hyxm content-item" v-else-if="pageType == 'memberCharged'">
            <text class="hyxm-text text">会员姓名</text>
            <view class="hyxm-content box1">{{ memberCharged.memberName }}</view>
          </view>
        </view>

        <view class="zffs content-item">
          <text class="zffs-text text">支付方式</text>
          <view class="zffs-content box1" v-if="pageType == 'consumptionStore'">{{ consumptionStore.orderPayTypeName }}</view>
          <view class="zffs-content box1" v-else-if="pageType == 'goodsRetail'">{{ goodsRetail.orderPayTypeName }}</view>
          <view class="zffs-content box1" v-else-if="pageType == 'memberCharged'">{{ memberCharged.payTypeName }}</view>
        </view>

        <view class="kdyg content-item">
          <view>
            <text class="kdyg-text text" v-if="isMemberCharged == false">操作员工</text>
            <text class="kdyg-text text" v-else>充值员工</text>
          </view>
          <view>
            <view class="kdyg-content box1" v-if="pageType == 'consumptionStore'">{{ consumptionStore.operatorName }}</view>
            <view class="kdyg-content box1" v-else-if="pageType == 'goodsRetail'">{{ goodsRetail.operatorName }}</view>
            <view class="kdyg-content box1" v-else-if="pageType == 'memberCharged'">{{ memberCharged.operatorName }}</view>
          </view>
        </view>

        <view v-if="pageType == 'consumptionStore'">
          <view class="bjf content-item">
            <text class="bjf-text text">包间费</text>
            <view class="bjf-content box1">{{ consumptionStore.bjf }}</view>
          </view>

          <view class="fwf content-item">
            <text class="fwf-text text">服务费</text>
            <view class="fwf-content box1">{{ consumptionStore.fwf }}</view>
          </view>
        </view>

        <view  v-if="isMemberCharged == false">
          <view class="goods content-item" v-if="pageType.value == 'consumptionStore'">
            <text class="goods-text text">商品消费</text>
            <view  class="goods-content box1">
              <view v-for="item in consumptionStore.goodsList" :key="item.id">
                <text class="goods-purchased">{{ item.name }},</text>
                <text class="goods-purchased">共{{ consumptionStore.goodsList.length }}品种</text>
              </view>
              <view class="logo">
<!--                :src=" getViewImage() + consumptionStore.goodsList[0].logo + fileViewAfter"-->
                <u-avatar
                    class="goods-logo"
                    :text="consumptionStore.goodsList[0].logo ? '' : '商品'"
                    fontSize="18"
                    :src=" getViewImage() + consumptionStore.goodsList[0].logo"
                    size="90"
                    mode="square"
                    randomBgColor
                ></u-avatar>
              </view>
              <view class="enter-info">
                <text class="info text">查看详情</text>
                <u-icon class="arrow-right" name="arrow-right" color="#999999" size="16"></u-icon>
              </view>
            </view>
          </view>

          <view class="goods content-item" v-if="pageType == 'goodsRetail'">
            <text class="goods-text text">商品购买</text>
            <view  class="goods-content box1">
              <view v-for="item in goodsRetail.goodsList" :key="item.id">
                <text class="goods-purchased">{{ item.name }},</text>
                <text class="goods-purchased">共{{ goodsRetail.goodsList.length }}品种</text>
              </view>
              <view class="logo">
<!--                :src=" getViewImage() + goodsRetail.goodsList[0].logo + fileViewAfter"-->
                <u-avatar
                    class="goods-logo"
                    :text="goodsRetail.goodsList[0].logo ? '' : '商品'"
                    fontSize="18"
                    :src=" getViewImage() + goodsRetail.goodsList[0].logo"
                    size="90"
                    mode="square"
                    randomBgColor
                ></u-avatar>
              </view>
              <view class="enter-info">
                <text class="info text">查看详情</text>
                <u-icon class="arrow-right" name="arrow-right" color="#999999" size="16"></u-icon>
              </view>
            </view>
          </view>
        </view>

        <view class="time content-item">
          <text class="time-text text">下单时间</text>
          <view
              class="time-content box1"
              v-if="pageType == 'consumptionStore'">
            {{ dayjs(consumptionStore.payTime).format('YYYY-MM-DD HH:mm:ss') }}
          </view>

          <view
              class="time-content box1"
              v-else-if="pageType == 'goodsRetail'">
            {{ dayjs(goodsRetail.payTime).format('YYYY-MM-DD HH:mm:ss') }}
          </view>

          <view
              class="time-content box1"
              v-else-if="pageType == 'memberCharged'">
            {{ dayjs(memberCharged.payTime).format('YYYY-MM-DD HH:mm:ss') }}
          </view>
        </view>

        <view class="number content-item" >
          <text class="number-text text">订单单号</text>
          <view class="number-content box1" v-if="pageType == 'consumptionStore'">{{ consumptionStore.orderId }}</view>
          <view class="number-content box1" v-else-if="pageType == 'goodsRetail'">{{ goodsRetail.orderId }}</view>
          <view class="number-content box1" v-else-if="pageType == 'memberCharged'">{{ memberCharged.orderId }}</view>
        </view>

        <view class="remark content-item">
          <text class="remark-text text">备注</text>
          <view class="remark-content box1" v-if="pageType == 'consumptionStore'">{{ consumptionStore.remark }}</view>
          <view class="remark-content box1" v-else-if="pageType == 'goodsRetail'">{{ goodsRetail.remark }}</view>
          <view class="remark-content box1" v-else-if="pageType == 'memberCharged'">{{ memberCharged.remark }}</view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
  import { ref,reactive } from 'vue'
  import { onShow,onLoad } from  "@dcloudio/uni-app";
  import http from "../../../../lib/request";
  import dayjs from 'dayjs'
  import {getViewImage,fileViewAfter} from '@/env'

  // 账单ID
  let orderId = ref('')

  let orderType = ref(null)

  let pageType = ref('')
  let isMemberCharged = ref(true)

  // 门店消费详情
  let consumptionStore = ref({})
  // 零售商品详情
  let goodsRetail = ref({})
  // 会员充值详情
  let memberCharged = ref({})

  // 判断账款类型
  // function confirmAmountType() {
  //
  // }

  // 获取门店消费详情
  function getConsumptionStoreInfo() {
    let orderIds = orderId.value.toString()
    console.log('orderId.value', orderIds)
    http('capital.getConsumptionStoreInfo',orderIds,'正在获取账单详情...').then(res=>{
      console.log(res.data,'111')
      if (res.code === 200 && res.data) {
        consumptionStore.value = Object.assign(consumptionStore.value, {...res.data})
        // orderTypeName.value = res.data.orderTypeName
      }
    })
  }

  // 获取零售商品详情
  function getGoodsRetailInfo() {
    let orderIds = orderId.value.toString()
    console.log('orderId.value', orderIds)
    http('capital.getGoodsRetailInfo',orderIds,'正在获取账单详情...').then(res=>{
      console.log(res.data,'222')
      if (res.code === 200 && res.data) {
        goodsRetail.value = Object.assign(goodsRetail.value, {...res.data})
        // orderTypeName.value = res.data.orderTypeName
      }
    })
    console.log(goodsRetail.value,'goodsRetail')
  }

  // 获取会员充值详情
  function getMemberChargedInfo() {
    let orderIds = orderId.value.toString()
    console.log('orderId.value', orderIds)
    http('capital.getMemberChargedInfo',orderIds,'正在获取账单详情...').then(res=>{
      console.log(res.data,'333')
      if (res.code === 200 && res.data) {
        memberCharged.value = Object.assign(memberCharged.value, {...res.data})
        // orderTypeName.value = res.data.orderTypeName
        console.log(memberCharged.value,'memberCharged.value')
      }
    })
  }

  onLoad((params)=>{
    // console.log('params', params)
    orderId.value = params.orderId
    orderType.value = params.orderType
    if (params) {
      // console.log('111',params)

      switch (params.orderType) {
        case '2':
          getConsumptionStoreInfo(params.orderId)
          isMemberCharged.value = false
          pageType.value = 'consumptionStore'
          break;
        case '3':
          getGoodsRetailInfo(params.orderId)
          isMemberCharged.value = false
          pageType.value = 'goodsRetail'
          break;
        case '1':
          // console.log('params000', params)
          getMemberChargedInfo(params.orderId)
          isMemberCharged.value = true
          pageType.value = 'memberCharged'
          break;
        default:
          alert('暂无其他类型的账款数据');
      }
    }
  })

  onShow(()=>{
    // getConsumptionStoreInfo()
    // getGoodsRetailInfo()
    // getMemberChargedInfo()
  })
</script>

<style lang="scss" scoped>
  .container {
    // #ifdef H5 || MP-WEIXIN
    min-height: calc(100vh - var(--window-top));
    // #endif
    // #ifdef APP-PLUS
    min-height: 100vh;
    // #endif
    background: #EDEFF3;
    padding: 20rpx;
    position: relative;

    .content {
      background: #ffffff;
      border-radius: 20rpx;
      padding: 0 50rpx;

      .content-header {
        padding: 100rpx 0 60rpx 0;
        text-align: center;
        border-bottom: 1rpx solid #EEEEEE;

        .header-title {
          margin-bottom: 15rpx;
          font-size: 24rpx;
          font-family: Hiragino Sans GB;
          font-weight: normal;
          color: #666666;
        }

        .header-content {
          font-size: 60rpx;
          font-family: FZLTCHK--GBK1-0;
          font-weight: 400;
          color: #202020;
        }
      }

      .amount-info {
        margin-top: 50rpx;
        padding-bottom: 62rpx;

        // 公共样式
        .text {
          margin-right: 40rpx;
          font-size: 28rpx;
          font-family: Hiragino Sans GB;
          font-weight: normal;
          color: #999999;
        }

        .box1 {
          font-size: 28rpx;
          font-family: Hiragino Sans GB;
          font-weight: normal;
          color: #202020;
        }

        .content-item {
          display: flex;
          flex-direction: row;
          margin-bottom: 40rpx;
        }

        .remark {
          margin-bottom: 0;
        }

        // 公共样式结束

        .bjf-content,
        .fwf-content {
          margin-left: 28rpx;
        }

        .goods-content {
          display: flex;
          flex-direction: column;

          .enter-info {
            display: flex;
            flex-direction: row;
            margin-top: 18rpx;

            .info {
              margin-right: 0;
            }
          }

          .logo {
            width: 90rpx;
            height: 90rpx;
            margin-top: 24rpx;
          }
        }

        .remark-content {
          margin-left: 56rpx;
        }
      }
    }
  }
</style>
