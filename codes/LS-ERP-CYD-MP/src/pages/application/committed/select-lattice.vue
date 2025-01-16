<template>
    <view class="container">
        <view class="content">
            <scroll-view class="scroll" :scroll-y="true">
                <view v-if="goodsList.length > 0">
                    <view
                        @click="selectLattice(item)"
                        class="content-item"
                        v-for="(item,index) in goodsList"
                        :key="index">
                        <view class="title">
                            <view class="icon"></view>
                            <text class="text-unitName">{{ item.shelfName + '-' + item.latticeKey }}</text>
                        </view>
                        <view class="goods">
                            <view class="name text">商品名称: {{ item.goodsName }}</view>
                            <view class="count text">规格: {{ item.specification }}</view>
                            <view class="count text">数量: {{ item.quantity }}</view>
                        </view>
                    </view>
                </view>

                <u-empty
                    v-else
                    mode="data"
                    :icon="image.plugIcon.empty"
                    text="暂无信息"
                    class="u-empty">
                </u-empty>
            </scroll-view>
        </view>
    </view>
</template>

<script setup>
import {ref, reactive, watchEffect} from "vue"
import {onShow} from "@dcloudio/uni-app";
import {routeParams} from '@/router'
import http from "../../../lib/request";

let storeId = ref('')
let goodsId = ref('')
// 商品信息列表
let goodsList = ref([])

// 获取货架与商品映射信息
function handleSgByGoodsId() {
    http('baseConfig.getSgByGoodsId',{goodsId:goodsId.value},'加载中...').then(res=>{
        if (res.code === 200 && res.data) {
            let list = res.data
            list.forEach(item=>{
                if (item.storeId == storeId.value) {
                    goodsList.value.push(item)
                }
            })
            console.log(goodsList.value,'goodsList')
        }
    })
}

// 选中格子获取商品数据
function selectLattice(item) {
    let eventKey = 'committed/edit'
    uni.$emit(eventKey,{
        storeId : item.storeId,
        shelfId : item.shelfId,
        shelfName : item.shelfName,
        lattice : item.latticeKey,
        latticeId : item.latticeId,
        quantity : item.quantity,
        goodsId : item.goodsId,
        storeGoodsId : item.storeGoodsId,
    })
    uni.navigateBack()
}

onShow(()=>{
    const params = routeParams()
    if (params) {
        storeId.value = params.storeId
        goodsId.value = params.goodsId
        handleSgByGoodsId()
    }
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
    }

    .content {
        box-sizing: border-box;
        padding: 0 20rpx 130rpx 20rpx;
        position: relative;

        .content-item {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            box-sizing: border-box;
            background: #ffffff;
            border-radius: 20rpx;
            margin-bottom: 20rpx;
            padding: 36rpx 30rpx;

            &:first-child {
                margin-top: 20rpx;
            }

            .title {
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

                .text-unitName {
                    font-size: 32rpx;
                    font-family: HiraginoSansGB-W3;
                    font-weight: normal;
                    color: #202020;
                    line-height: 44rpx;
                }
            }

            .goods {
                display: flex;
                flex-direction: column;
                align-items: flex-start;
                margin-top: 10rpx;

                .text {
                    font-size: 28rpx;
                    color: #666666;
                }
            }
        }


        .scroll,
        .u-empty {
          height: 100%;
        }
    }
</style>