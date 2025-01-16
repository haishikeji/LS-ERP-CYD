<template>
    <view class="card">
        <view v-if="props.isShowLine" class="line line1"></view>
        <view class="card-head">
            <view class="card-head-left">
                <view class="icon"></view>
                <view class="label">{{props.title}}</view>
            </view>
            <view class="card-head-right">
                <slot name="head-right">
                    {{props.titleInfo}}
                </slot>
            </view>
        </view>
        <view v-if="props.isShowLine" class="line line2"></view>
        <slot></slot>
    </view>
</template>

<script setup>
import {ref, reactive} from 'vue'
import {onLoad, onShow} from '@dcloudio/uni-app';

const props = defineProps({
    title: {
        required:false,
        type:String,
        default:""
    },
    titleInfo: {
        required:false,
        type:String,
        default:""
    },
    isShowLine: {
        required:false,
        type:Boolean,
        default:true
    }
})

</script>

<style lang="scss" scoped>
    .card {
        //padding: 35rpx 30rpx 0rpx 30rpx;
        background-color: white;
        border-radius: 20rpx;
        margin-bottom: 20rpx;
        .card-head {
            display: flex;
            justify-content: space-between;
            align-items: center;
            .card-head-left {
                display: flex;
                align-items: center;
                .icon {
                    width: 4rpx;
                    height: 28rpx;
                    background: $uni-color-primary;
                    border-radius: 2rpx;
                    margin-right: 17rpx;
                }
                .label {
                    font-size: 32rpx;
                    color: $uni-color-primary;
                }
            }
            .card-head-right {
                color: $uni-color-primary;
            }
        }
        .card-content {
            margin-top: 40rpx;
        }
        .no-margin-top {
            margin-top: 0;
        }
    }

    .line {
        border-bottom: 1rpx solid #EEEEEE;
    }

    .line1 {
        margin-bottom: 20rpx;
    }

    .line2 {
        margin-top: 30rpx;
    }


</style>
