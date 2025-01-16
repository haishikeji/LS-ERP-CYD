<template>
    <view class="container-home" :class="{'container-none': !props.showModal}">
        <view class="content">
            <view class="grid" v-for="(item, index) in menuRouterList" :key="index">
                <view class="grid-title">{{ item.categoryName }}</view>
                <view class="u-grid-child">
                    <view
                        v-for="(listItem, listIndex) in item.listMenu"
                        @click="click(listItem)"
                        class="u-grid-item"
                        :key="listIndex">
                        <image :src="listItem.icon" class="img-icon"></image>
                        <text class="grid-text">{{listItem.menuName}}</text>
                    </view>
                </view>
            </view>
            <u-empty
                v-if="menuRouterList.length === 0"
                mode="data"
                :icon="image.plugIcon.empty"
                text="暂无应用信息"
                class="u-empty">
            </u-empty>
            <!-- 高度占位符-->
<!--            <view class="placeholder"></view>-->
        </view>
    </view>
</template>
<script setup lang="ts">
import {ref, reactive, onMounted, defineEmits, defineProps, watch} from 'vue'
import {onLoad} from "@dcloudio/uni-app";
import http from "@/lib/request";
import menu from '@/lib/system/menu'


let routerKey = reactive({...menu.menuList()})

let emit = defineEmits(['selectTabBar'])

let menuRouterList = ref([])

let props = defineProps({
  showModal:{
    type: Boolean,
    default() {
      return false
    }
  },
  newShopId:{
    type: String,
    default() {
      return ''
    }
  }
})

watch(()=>props.showModal,(v)=>{
  if (v && menuRouterList.value.length < 1) {
    getMenu()
  }
},{immediate: true})

watch(()=>props.newShopId,(v)=>{
  console.log(111,'111')
  if (v) {
    console.log(v,'v')
    getMenu()
  }
})

onLoad(()=>{
    getMenu()
})

function getMenu() {
    http('menu.navigationBar').then(res => {

        if (res.code === 200 && res.data) {
            menuRouterList.value = res.data.map(val => {
                if (val.listMenu && val.listMenu.length > 0) {
                    val.listMenu = val.listMenu.map(item => {
                        item = Object.assign(item,{...routerKey[item.menuKey]})
                        return item
                    })
                }
                return val
            })

        }
    })
}

function click(item) {
    const url = item.path
    if (item.isTabBar && url) {
        // 跳转到tabBar
        // uni.switchTab({url: url})
        //
        // todo 跳转
        emit('selectTabBar',item.tabBarKey)
        // uni.redirectTo({
        //     url: '/pages/home/index'
        // })

    } else if (url) {
        uni.navigateTo({
            url: url
        });
    } else {
        uni.showToast({
            title: '功能待开发中...',
            icon: 'none',
            duration: 500
        })
    }
}

</script>
<style lang="scss" scoped>
.container-home {
    background: #f8f8f8;
    //height: 100%;
    position: relative;
}

.container-none{
  display: none;
}

.content {
    //width: calc(100% - 180rpx);
    //width: 100%;
    height: inherit;
    box-sizing: border-box;
    padding: 0 20rpx;
    //display: inline-block;
    vertical-align: top;
    overflow: auto;
    //touch-action: pan-y;
    //-webkit-overflow-scrolling: touch;

    .grid {
        border-radius: 20rpx;
        overflow: hidden;
        padding-bottom: 20rpx;
        /*padding: 30rpx 0;*/
        background: #ffffff;
        display: flex;
        width: 100%;
        flex-direction: column;
         margin-top: 20rpx;


        .u-grid-child {
            width: 100%;
            display: flex;
            flex-wrap: wrap;
            position: relative;
            margin-top: 20rpx;
        }

        .u-grid-item {
            width: calc(100% / 3);
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            align-items: center;
            padding-bottom: 20rpx;
        }

        .u-grid-item:active{
            background: #EEEEEE;
        }

        .grid-title{
            width: 100%;
            margin: 35rpx 0 0rpx 24rpx;
            font-size: 32rpx;
            font-family: HiraginoSansGB-W3;
            font-weight: normal;
            color: #202020;
        }

        &:last-child {
            margin-bottom: 20rpx;
        }

        &-item{
            padding-top: 30rpx;
        }
    }
    .grid-text{
        font-size: 24rpx;
        font-family: Hiragino Sans GB;
        font-weight: normal;
        color: #202020;
    }
}
.img-icon{
    width: 60rpx;
    height: 60rpx;
    padding:15rpx
}

.placeholder {
    height: 120rpx;
}
</style>
