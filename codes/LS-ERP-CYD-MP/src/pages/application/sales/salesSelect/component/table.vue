<template>
    <view>
        <view class="table-header" :class="props.customClass">
            <view class="table-header__cell">商品名</view>
            <view class="table-header__cell">数量</view>
            <view class="table-header__cell">价格</view>
        </view>
        <view class="table-body">
<!--            <u-swipe-action>-->
            <view class="table-body__row" :class="props.customRowClass" v-for="(child,key) in props.staffRecord">
<!--                    <u-swipe-action-item-->
<!--                        :disabled="props.actionShow"-->
<!--                        :autoClose="true"-->
<!--                        :options="props.options"-->
<!--                        @click="(e)=>{swipeClick(e,child)}"-->
<!--                    >-->
                <view class="table-body__cell">
                    <view class="text">{{ child.info.name }}</view>
                    <view>
                        <text class="tip">{{ child.info.goodsSpecification }}</text>
<!--                        <text class="tip"-->
<!--                              v-for="(name,index) in child.info.tips">-->
<!--                            {{ name }}-->
<!--                        </text>-->
                    </view>
                </view>
                <view class="table-body__cell">
                    <view v-if="child.number > 0" class="first">
                        <text class="text">{{ keepDecimal(parseFloat(child.info.price)) }}{{ child.info.amountUnit }}</text>
                        <text class="char">x</text>
                        <text class="text">{{ child.number }}{{ child.info.unit }}</text>
                    </view>
                    <view v-if="child.number2 > 0" class="second">
                        <text class="text">{{ keepDecimal(parseFloat(child.info.price2)) }}{{ child.info.amountUnit }}</text>
                        <text class="char">x</text>
                        <text class="text">{{ child.number2 }}{{ child.info.unit2 }}</text>
                    </view>
                </view>
                <view class="table-body__cell">
                    <view v-if="child.number > 0" class="price">{{ keepDecimal(parseFloat(child.price)) }}{{ child.info.amountUnit }}</view>
                    <view v-if="child.number2 > 0" class="price">{{ keepDecimal(parseFloat(child.price2)) }}{{ child.info.amountUnit }}</view>
                </view>
<!--                </u-swipe-action-item>-->
            </view>
<!--            </u-swipe-action>-->
        </view>
    </view>
</template>
<script setup lang="ts">
import {defineProps,ref,defineEmits} from 'vue'
import {keepDecimal} from '@/util/common'
const emit = defineEmits(['swipeClick'])
const props = defineProps({
    staffRecord:{
        type: Object,
        default() {
            return {}
        }
    },
    customClass:{
        type: String,
        default(){
            return ''
        }
    },
    customRowClass:{
        type: String,
        default(){
            return ''
        }
    },
    // 是否警用
    actionShow:{
        type: Boolean,
        default() {
            return true
        }
    },
    options: {
        type: Array,
        default() {
            return [{
                text: '滑动',
                style: {
                    backgroundColor: '#3c9cff',
                }
            }]
        }
    }
})
// 滑动
function swipeClick(name,child) {
    emit('swipeClick',name,child)
}

</script>
<style lang="scss" scoped>
    .table-header{
        padding: 29rpx 24rpx 24rpx 24rpx;
        display: flex;
        border-radius: 20rpx 20rpx 0 0;
        background: $uni-color-primary;

        font-size: 28rpx;
        font-family: Hiragino Sans GB;
        font-weight: normal;
        color: #FFFFFF;

        &__cell:nth-child(1){
            width: 50%;
            flex-shrink: 0;
        }
        &__cell:nth-child(2){
            width: 30%;
            flex-shrink: 0;
            text-align: center;
        }
        &__cell:nth-child(3){
            width: 20%;
            flex-shrink: 0;
            text-align: right;
        }
    }
    .table-body{
        /*padding: 0 24rpx;*/
        background: #FFFFFF;

        &__row {
            display: flex;
            align-items: center;
            padding: 30rpx 0 29rpx 0;

            &:nth-last-of-type(n+2){
                border-bottom: 1rpx solid #eeeeee;
            }

            @mixin font{
                font-size: 24rpx;
                font-family: Hiragino Sans GB;
                font-weight: normal;
                color: #999999;
            }

            .text{
                @include font;
            }
            .char{
                margin: 0 10rpx;
                @include font
            }
            .price{
                @include font;
                color: $u-red-color;
            }
        }

        &__cell:nth-child(1){
            width: 40%;
            flex-shrink: 0;
        }
        &__cell:nth-child(2){
            width: 40%;
            flex-shrink: 0;
            text-align: center;
        }
        &__cell:nth-child(3){
            width: 20%;
            flex-shrink: 0;
            text-align: right;
        }

        .tip{
            font-size: 22rpx;
            color: #999999;
            &:nth-of-type(n+2){
                margin-left: 10rpx;
            }
        }
    }

    ::v-deep .u-swipe-action-item__content{
        flex-direction: row;
        align-items: center;
        padding: 0 24rpx;
        box-sizing: border-box;
    }

    ::v-deep .u-swipe-action-item{
        width: 100%;
    }

</style>