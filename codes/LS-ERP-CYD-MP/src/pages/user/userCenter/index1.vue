<template>
    <view class="container-user-center" v-if="props.showModal">
        <view class="header">
            <image :src="image.bg.newUserCenter" class="header-img"></image>
            <!--            <image :src="image.bg.userCenter" class="header-img"></image>-->
            <view class="userInfo">
                <template v-if="Object.keys(info).length < 1">
                    <view class="portrait-name">
                        <u-avatar text="头像" size="120"></u-avatar>
                        <text class="title">未登录</text>
                    </view>
                </template>
                <template v-else>
                    <view class="portrait-name"  @click="handlePersonalData">
<!--                        <image :src="info.avatar ? viewImage + info.avatar + fileViewAfter : image.imgNone"-->
<!--                               class="portrait-icon" mode="aspectFit"></image>-->
                        <image :src="info.avatar ? viewImage + info.avatar : image.imgNone"
                               class="portrait-icon" mode="aspectFit"></image>
                        <text class="title">{{ info.nickname }}</text>
                    </view>
<!--                    <view class="user-other" @click="handlePersonalData">-->
<!--                        <text class="user-title">个人资料</text>-->
<!--                        <image class="plug-icon" :src="image.plugIcon.right"></image>-->
<!--                    </view>-->
                </template>
            </view>
        </view>
        <view class="list">
            <view class="first-list">
                <view class="list-item" v-if="loginTag">
                    <view class="content list-attribute" @click="handleStoresChoose">
                        <view class="item-left">
                            <image :src="image.plugIcon.shops" class="img"></image>
                            <view class="text">{{ text }}</view>
                        </view>
                        <u-icon class="arrow-right" name="arrow-right" color="#999999" size="18"></u-icon>
                    </view>
                    <view class="list-attribute content" @click="handleEditPersonalData">
                        <view class="item-left">
                            <image :src="image.plugIcon.editPersonalData" class="img"></image>
                            <view class="text">修改个人资料</view>
                        </view>
                        <u-icon class="arrow-right" name="arrow-right" color="#999999" size="18"></u-icon>
                    </view>
                    <view class="list-attribute" @click="handleEditPassword">
                        <view class="item-left">
                            <image :src="image.plugIcon.editPassword" class="img"></image>
                            <view class="text">修改密码</view>
                        </view>
                        <u-icon class="arrow-right" name="arrow-right" color="#999999" size="18"></u-icon>
                    </view>
                </view>

                <view class="list-item list-attribute" v-else>
                    <text>登录</text>
                    <u-icon class="arrow-right" name="arrow-right" color="#999999" size="18"></u-icon>
                </view>
            </view>
            <!--            <u-list class="first-list">-->
            <!--                <u-list-item class="list-item list-attribute" :height="'auto'" v-if="loginTag">-->
            <!--                    <u-cell-->
            <!--                        :border="false"-->
            <!--                        :isLink="true"-->
            <!--                        :title="text"-->
            <!--                        :customStyle="{padding: '0 30rpx','justifyContent':'space-between'}"-->
            <!--                        rightIcon="arrow-right"-->
            <!--                        @click="handleStoresChoose"-->
            <!--                    >-->
            <!--                        <template #icon>-->
            <!--&lt;!&ndash;                            <u-avatar&ndash;&gt;-->
            <!--&lt;!&ndash;                                mode="square"&ndash;&gt;-->
            <!--&lt;!&ndash;                                :src="image.plugIconshops"&ndash;&gt;-->
            <!--&lt;!&ndash;                                customStyle="margin: -3rpx 5rpx -3rpx 0"&ndash;&gt;-->
            <!--&lt;!&ndash;                            ></u-avatar>&ndash;&gt;-->
            <!--                            <image :src="image.plugIcon.shops" class="img"></image>-->
            <!--                        </template>-->
            <!--                    </u-cell>-->
            <!--                </u-list-item>-->
            <!--                <u-list-item class="list-item list-attribute" :height="'auto'" v-else>-->
            <!--                    <u-cell :customStyle="{padding: '0 30rpx','justifyContent':'center','textAlign': 'center'}"-->
            <!--                            :titleStyle="{fontSize: '36rpx'}"-->
            <!--                            title="登录"-->
            <!--                            :border="false"></u-cell>-->
            <!--                </u-list-item>-->
            <!--            </u-list>-->

            <view class="list-item list-content">
                <view
                    class="card"
                    v-for="(item,index) in centerList"
                    :key="index">
                    <view class="list-attribute card-item" @click="handleAboutUs(item.path)">
                        <view class="item-left">
                            <image :src="item.staticIcon" class="img"></image>
                            <view class="text">{{ item.title }}</view>
                        </view>
                        <u-icon class="arrow-right" name="arrow-right" color="#999999" size="18"></u-icon>
                    </view>
                </view>
            </view>

            <!--            <u-list class="list-item list-content">-->
            <!--                <u-list-item-->
            <!--                    class="list-attribute"-->
            <!--                    v-for="(item,index) in centerList">-->
            <!--                    <u-cell-->
            <!--                        :border="centerList.length-1 != index"-->
            <!--                        :title="item.title"-->
            <!--                        :rightIcon="item.after"-->
            <!--                        :customStyle="{-->
            <!--                          height:'120rpx',-->
            <!--                        padding: '0 30rpx',-->
            <!--                        fontFamily:'Hiragino Sans GB'}"-->
            <!--                        :isLink="true"-->
            <!--                        @click="handleAboutUs(item.path)"-->
            <!--                    >-->
            <!--                        <template #icon>-->
            <!--                            <image :src="item.staticIcon" class="img"></image>-->
            <!--&lt;!&ndash;                            <u-avatar&ndash;&gt;-->
            <!--&lt;!&ndash;                                mode="square"&ndash;&gt;-->
            <!--&lt;!&ndash;                                size="35"&ndash;&gt;-->
            <!--&lt;!&ndash;                                :src="item.staticIcon"&ndash;&gt;-->
            <!--&lt;!&ndash;                                customStyle="margin: -3rpx 5rpx -3rpx 0"&ndash;&gt;-->
            <!--&lt;!&ndash;                            ></u-avatar>&ndash;&gt;-->
            <!--                        </template>-->
            <!--                    </u-cell>-->
            <!--                </u-list-item>-->
            <!--            </u-list>-->

        </view>
        <view class="btn">
            <u-button type="primary" class="button" @click="showModel">退出登录</u-button>
        </view>
        <view v-if="is.length > 0" class="title-o">{{is}}</view>

        <show-modal />
    </view>
</template>
<script setup lang="ts">
import {reactive, ref, onMounted, defineProps, defineEmits, watch, inject} from 'vue'
import {onLoad, onShow} from "@dcloudio/uni-app";
import {userInfo} from "@/lib/system/control";
import {storage} from "@/lib/system/storage"
import http from "@/lib/request";
import {viewImage,fileViewAfter,sys_t} from '@/env'
import { image } from '@/lib/system/imageStorage'

let {$showModal} = inject('defaultModal')

let menuList = ref([
    // {title: '考勤打卡',staticIcon: image.plugIcon.exchangeUser,after: "arrow-right",path: '/pages/user/clockIn/index'},
    // {title: '交班',staticIcon: image.plugIcon.exchangeUser,after: "arrow-right",path: '/pages/user/myShift/index?type=',key: 'shift_class'},
    // {title: '交班记录',staticIcon: image.plugIcon.exchangeHistory,after: "arrow-right",path: '/pages/user/shiftRecord/index',key: 'shift_class_record'},
    {title: '关于我们',staticIcon: image.plugIcon.about,after: "arrow-right",path: '/pages/user/aboutUs/index' }
])

// let show = ref(false)
let is = ref('')

let loginTag = ref(true)

let emit = defineEmits(['selectStore'])

let props = defineProps({
  showModal:{
    type: Boolean,
    default() {
      return false
    }
  }
})

watch(()=>props.showModal,(newValue,oldValue)=>{
  if (newValue) {
    centerList.value = [];
    beforeInit()
    // getStorage()
    is.value = sys_t
  }
},{immediate: true})

// 初始化
function init() {
    userInfo(true,() =>{
        info = Object.assign(info,{...storage.getUserInfo()})
        // text.value = info.shopName
        http('user.info').then( res => {
            if( res.code === 200) {
                if(info.shopId === res.data.shopId) {
                    text.value = info.shopName
                }
            }
        })
    });
}

const text = ref(null)

let info = reactive({})
let centerList = ref([])
let onBool = ref(false)

// onShow(()=>{
//     storage.getCenterBar()
//     let list = storage.getCenterBar()
//     console.log(list,'list')
// })

// onMounted(() =>{
//     beforeInit()
//     // getStorage()
//   is.value = sys_t
// })

function  showModel() {
    $showModal({
        title: '登出提示',
        content: '确定要退出登录吗?',
        success: (res)=>{
            if (res.confirm) {
                storage.clearLogin()
            }
        }
    })
}

function beforeInit() {
    // let userInfo = storage.getUserInfo()
    // if (!userInfo) {
    //     loginTag.value = false
    //     return
    // }
    // loginTag.value = true
    setMenuCenterStorage()
}

function setMenuCenterStorage() {
    let centerBar = menuList.value
    handleCenterBar(resSuccess)
    // 获取成功
    function resSuccess(data){
        init()
        data.forEach(item=>{
            for (let i = 0; i < centerBar.length; i++) {
                if(centerBar[i].key == item.menuKey) {
                    centerList.value.push(centerBar[i])
                }
            }
        })

        menuList.value.forEach(item=>{
            if(!item.key) {
                centerList.value.push(item)
            }
        })
        // 设置个人中心菜单缓存
        storage.setCenterBar(centerList.value)
        if(centerList.value.length < 1){
            uni.showToast({
                title: '暂无个人中心菜单',
                icon: 'none'
            })
            return
        }
    }

}

// function getStorage() {
//   let centerStorageList = storage.getCenterBar()
//   console.log(centerStorageList,'000')
// }

function handlePersonalData() {
    uni.navigateTo({
        url: "/pages/user/personalData/index"
    })
}

function handleEditPersonalData() {
    uni.navigateTo({
        url: '/pages/user/editPersonalData/index'
    })
}

function handleEditPassword() {
    uni.navigateTo({
        url: "/pages/user/personalData/editPassword/index"
    })
}

// 跳转去切换门店
function handleStoresChoose() {
    bindEvent(onBool.value)
    uni.navigateTo( {
        url: "/pages/user/storesChoose/index"
    })
}

function handleAboutUs(path) {
    uni.navigateTo( {
        url: path
    })
}

function bindEvent(bool = true) {
    if (bool) {
        uni.$off('select-store')
    }
    uni.$on('select-store',(e)=>{
        if (e.refresh) {
            emit('selectStore', e)
        }
        uni.$off('select-store')
    });
    onBool.value = !onBool.value
}

// 获取centerBar设置
function handleCenterBar(callback){
    http('menu.getBottomNavigation').then(res=>{
        // console.log(res.data,'res.data')
        if(res.code == 200){
            callback(res.data)
        }
    })
}
</script>
<style lang="scss" scoped>
.title-o{
  position: absolute;
  bottom: 10;
  right: 0;
}

    .container-user-center{
        background: #f8f8f8;
        height: 100%;
      position: relative;
    }

    // 兼容信息卡片占满
    // #ifdef MP-WEIXIN
    ::v-deep .u-list-item{
        width: 100%;
    }
    .list-item{
        align-items: center;
    }

    ::v-deep .u-list{
        height: auto!important;
    }
    ::v-deep .u-cell__body{
        height: 120rpx;
    }
    // #endif
    .header{
        width: 100%;
        height: 380rpx;
        position: relative;

        .header-img{
            width: 100%;
            height: 420rpx;
            position: absolute;
            //z-index: -1;
        }

        .userInfo{
            box-sizing: border-box;
            padding: 80rpx 40rpx 0 42rpx;
            display: inline-flex;
            width: 100%;
            align-items: center;
            justify-content: space-between;


            .portrait-name{
                display: inline-flex;
                align-items: center;
                z-index: 3;

                .portrait-icon{
                    height: 120rpx;
                    width: 120rpx;
                    border-radius: 50%;
                }
                .title{
                    font-size: 44rpx;
                    font-family: Hiragino Sans GB;
                    font-weight: normal;
                    color: #FFFFFF;
                    margin-left: 26rpx;
                }
            }

            .user-other{
                display: inline-block;
                z-index: 3;

                .user-title{
                    font-size: 28rpx;
                    font-family: Hiragino Sans GB;
                    font-weight: normal;
                    color: #FFFFFF;
                    vertical-align: middle;
                }
            }
        }
    }
    .list{
        margin-top: 40rpx;
        padding: 0 20rpx;
        transform: translateY(-100rpx);

        .img{
            width: 40rpx;
            height: 40rpx;
            margin-right: 10rpx;
        }

        .list-attribute{
            height: 120rpx;

            display: flex;
            justify-content: space-between;
            align-items: center;

            .item-left {
                display: flex;
                align-items: center;
            }
        }

        .content {
            border-bottom: 1rpx solid #EEEEEE;
        }

        .list-item{
            padding: 0 30rpx;
            background: #FFFFFF;
            box-shadow: 0 0 10rpx 0 rgba(48,48,48,0.08);
            border-radius: 20rpx;
        }

        .list-content{
            height: inherit!important;
            /*padding: 0 30rpx;*/
            box-sizing: border-box;
            margin-top: 20rpx;

            .card {
                .card-item {
                    border-bottom: 2rpx solid #EEEEEE;
                }
            }
        }

        .first-list{
            padding-top: 30rpx;
            height: inherit!important;
        }
    }
    .plug-icon{
        width: 28rpx;
        height: 28rpx;
        vertical-align: middle;
    }

    .btn {
        padding: 0 20rpx;
    }

    .button{
        height: 100rpx;
        background: #ffffff;
        border: 0;
        border-radius: 20rpx;
        font-size: 32rpx;
        color: #8B6445;
    }

    ::v-deep .u-cell__body{
        padding: 30rpx 0;
        height: 100%;
        box-sizing: border-box;
    }

    ::v-deep .u-cell__title-text{
        font-size: 32rpx;
        font-family: Hiragino Sans GB;
        font-weight: normal;
        color: #202020;
    }
</style>
