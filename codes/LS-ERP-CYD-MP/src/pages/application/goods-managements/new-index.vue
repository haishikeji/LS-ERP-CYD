<template>
  <view class="container">
    <view class="header">
      <u-search
          placeholder="商品名称"
          searchIconSize="28rpx"
          bgColor="#F7F7F7"
          mode="square"
          :showAction="true"
          :actionStyle="{...searchTextStyle}"
          v-model="search"
          @change="searchList"
          @custom="searchList"
      ></u-search>
    </view>

    <view class="navbar">
      <u-tabs
          class="tabs"
          @click="currentTabs"
          :list="typeList"
          :lineColor="inlineStyleConfig.uniColorPrimary"
          :activeStyle="{color:inlineStyleConfig.uniColorPrimary,fontSize: '32rpx',fontFamily: 'Hiragino Sans GB'}"
          :inactiveStyle="{color: '#666666',fontSize: '32rpx',fontFamily: 'Hiragino Sans GB'}"
          :itemStyle="{lineHeight: '90rpx'}"
          :scrollable="false"
          lineWidth="30rpx"
          lineHeight="4rpx"></u-tabs>
    </view>

    <view class="content">
      <scroll-view class="scroll" :scroll-y="true" @scrolltolower="scrollChange">
        <view v-if="shoppingList.length > 0">
          <view
              class="content-item"
              @click="toGoodsInfo(item.goodsId)"
              v-for="(item,index) in shoppingList"
              :key="item.id">
            <view class="item-text">
              <view class="icon">
                <u-avatar
                    class="avatar-icon"
                    :text="item.logo ? '' : '商品'"
                    fontSize="18"
                    :src=" getViewImage() + item.logo + fileViewAfter"
                    size="120"
                    mode="square"
                    randomBgColor
                ></u-avatar>
              </view>

              <view>
                <view class="text-goodsName">
                  <text class="text">品名: </text>
                  <text class="produceArea">{{ item.name}}</text>
                </view>
                <view class="text-produceArea">
                  <text class="text">品牌: </text>
                  <text class="produceArea">{{ item.produceArea || '暂无' }}</text>
                </view>

                <view class="text-createTime">
                  <text class="text">销售价: </text>
                  <text class="createTime">5999元/盒</text>
<!--                  <text class="createTime">{{ dayjs(item.createTime).format('YYYY-MM-DD HH:mm:ss') || '' }}</text>-->
                </view>

                <view class="text-createTime">
                  <text class="text">净含量: </text>
                  <text class="createTime">50g</text>
                </view>

              </view>
            </view>

            <view class="text-type">{{ item.typeName || '' }}</view>
          </view>
          <view class="last-text">{{ message }}</view>
        </view>

        <u-empty
            v-else
            mode="data"
            :icon="image.plugIcon.empty"
            :text="msg"
            class="u-empty">
        </u-empty>

      </scroll-view>
    </view>

    <view class="content-footer">
      <u-button
          @click="toForm('', '')"
          type="primary"
          :customStyle="{borderRadius: '10rpx'}">
        <view class="primary-text">
          <u-icon :name="image.plugIcon.add" size="36"></u-icon>
          <text>新增商品</text>
        </view>
      </u-button>
    </view>
  </view>
</template>

<script setup>
import {ref, reactive, computed} from 'vue'
import dayjs from 'dayjs'
import inlineStyleConfig from "@/lib/system/inlineStyleConfig";
import { onShow,onLoad } from "@dcloudio/uni-app";
import {userBasePage} from "../../index/mixins";
import http from "@/lib/request";
import {getViewImage,fileViewAfter} from '@/env'
import {getUrlWithParams} from "@/util/url";

let search = ref('')
let type = ref(1)
let paramsQuery = reactive({
  pageNum: 1,
  pageSize: 15,
  total: null
})
let message = ref('已没有更多信息')
// const goodsLogo = ref(null)


// let goodsArea = ref( '商品产地:')
// let createTimes = ref( '创建时间:')

let msg = ref( '暂无商品信息')


let searchTextStyle = reactive({
  fontSize: '32rpx',
  fontFamily: 'Hiragino Sans GB',
  fontWeight: 'normal',
  color: inlineStyleConfig.uniColorPrimary
})

// let {baseParams} = userBasePage()
// let baseConstructor = new baseParams()
// let staffParams = reactive(Object.assign({select: ''},{...baseConstructor}))

let shoppingList = ref([])
let typeList = ref([])



function searchList() {
  paramsQuery = Object.assign(paramsQuery,{
    pageNum: 1,
    pageSize: 15,
    total: null
  })

  clearShoppingList()
  handleList()
}

function isNotScrollEnd() {
  return paramsQuery.total > paramsQuery.pageNum * paramsQuery.pageSize
}
// 滚动
function scrollChange() {
  if (isNotScrollEnd()) {
    paramsQuery.pageNum++;
    handleList(false);
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

function categoryDict(){
  let dict = {}
  typeList.value.forEach(item=>{
    dict[item.code+''] = item.name
  })
  return dict
}

// 清空列表
function clearShoppingList(){
  shoppingList.value = []
}

// 获取商品列表
async function handleList(e) {
  const {code,rows} =  await http('commodity.pageGoods',{select: search.value,type: type.value,...paramsQuery},'加载中')

  if (code !== 200 ) {
    return
  }

  const dict = categoryDict()
  shoppingList.value = rows.map(item=>{
    item.typeName = dict[item.type+'']
    return item
  })
}


function currentTabs(e) {
  type.value = e.code
  let query = {type: e.code}
  paramsQuery = Object.assign(paramsQuery,{...query});
  handleList();
}

// 页面跳转
function toGoodsInfo(id) {
  toForm(id,'show')
}


function toForm(id,type) {
  const url  = getUrlWithParams('/pages/application/goods-managements/new-form',{
    type,
    id
  })

  uni.navigateTo({url})
}

/**
 * 获取分类信息
 * @returns {Promise<unknown>}
 */
function getCategory(){
  return new Promise(resolve => {
    http('commodity.listGoodsType').then(res=>{
      if (res.code === 200 && res.data) {
        typeList.value = res.data.map(item=>{
          item.code = item.type + ''
          return item
        })
        // typeList.value.unshift({name: '全部',code: 'all'})
        resolve()
      }
    })
  })
}

onShow( async () => {
  // 1. 获取商品分类
  await getCategory()

  // 2. 获取选中分类下的数据
  clearShoppingList()
  handleList()
})
</script>

<style lang="scss" scoped>
.container {
  // #ifdef H5 || MP-WEIXIN
  min-height: calc(100vh - var(--window-top));
  height: calc(100vh - var(--window-top));
  // #endif
  // #ifdef APP-PLUS
  min-height: 100vh;
  height: 100vh;
  // #endif
  background: #EDEFF3;
  //padding: 20rpx;
  position: relative;

  .header{
    box-sizing: border-box;
    padding: 0 30rpx;
    height: 100rpx;
    line-height: 100rpx;
    background: #ffffff;
  }

  .tabs{
    background: #ffffff;

    ::v-deep .u-tabs__wrapper__nav__line{
      bottom: 14rpx;
    }
  }
  .content {
    height: calc(100% - 210rpx);
    box-sizing: border-box;
    padding: 0 20rpx 110rpx 20rpx;
    position: relative;

    .content-item {
      display: flex;
      align-items: center;
      box-sizing: border-box;
      //height: 160rpx;
      height: 100%;
      background: #ffffff;
      border-radius: 20rpx;
      margin-bottom: 20rpx;
      padding: 20rpx 30rpx;

      &:first-child{
        margin-top: 20rpx;
      }

      .item-text {
        display: flex;
        flex-direction: row;
        align-items: center;

        .text-goodsName,
        .text-produceArea,
        .text-createTime {
          font-size: 24rpx;
          font-family: Hiragino Sans GB;
          font-weight: normal;
          color: #666666;
          line-height: 44rpx;
        }

        .text {
          margin-right: 20rpx;
          color: #999999;
        }
      }

      .text-type {
        display: flex;
        flex: 1;
        align-self: flex-start;
        justify-content: flex-end;
        margin-right: 10rpx;
        font-size: 24rpx;
        font-family: Hiragino Sans GB;
        font-weight: normal;
        color: #666666;
        line-height: 44rpx;
      }
    }

    .scroll,
    .u-empty {
      height: 100%;
    }
  }

  .icon {
    margin-right: 20rpx;
  }

  .content-footer {
    box-sizing: border-box;
    width: 100%;
    background: #ffffff;
    bottom: 0;
    left: 0;

    // #ifndef MP-WEIXIN
    height: 110rpx;
    line-height: 110rpx;
    align-items: center;
    padding: 0 30rpx;
    display: flex;
    // #endif

    // #ifdef MP-WEIXIN
    padding:10rpx 30rpx;
    // #endif


    position: fixed;
    left: 0;
    bottom: 0;

    .primary-text{
      display: flex;
      align-items: center;
      text{
        line-height: 1;
        font-family: Hiragino Sans GB;
        font-weight: normal;
        color: #FFFFFF;
        font-size: 32rpx;
        margin-left: 16rpx;
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
}
</style>
