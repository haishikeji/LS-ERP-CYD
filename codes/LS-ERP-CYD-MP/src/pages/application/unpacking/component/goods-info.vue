<template>
    <view>
        <view class="staff">
            <view class="staff-base">
                <image :src="!!form.logo ? fileSrc(form.logo) : ''" class="staff-icon"></image>
                <view class="staff-text">
                    <view class="staff-title">
                        <view class="title">
                            <text>
                                {{ form.goodsName || '-' }}
                                <text class="title-desc">{{ form.goodsSupplierBrand || '' }}</text>
                            </text>
                        </view>
                        <view v-if="isShowBtn" class="button">
                            <u-button type="primary" :plain="true" @click="remove">
                                <text class="text">删除</text>
                            </u-button>
                        </view>
                    </view>
<!--                    <view class="staff-content-text">-->
<!--                        <text>供应商：</text>-->
<!--                        <text>{{ form.goodsSupplierName || '暂无' }}</text>-->
<!--                    </view>-->
                    <view class="staff-content-text">
                        <text>规格：</text>
                        <text>{{ form.goodsSpecification || '暂无' }}</text>
                    </view>
                    <view class="staff-content-text">
                        <text>产地：</text>
                        <text>{{ form.goodsProduceArea || '暂无' }}</text>
                    </view>
                </view>
            </view>
            <view class="staff-slot">
                <slot></slot>
            </view>
            <view class="staff-action">
                <view class="staff-content-text-n-b">
                    <text>转换率：</text>
                    <text>{{  form.rate  || '0' }}</text>
                </view>
                <view>
                    <view class="number">
                        <text></text>
                        <u-number-box
                            :min="0"
                            :max="form.total"
                            @change="numberChange"
                            v-model="form.number">
                        </u-number-box>
                    </view>
                </view>
            </view>
        </view>
    </view>
</template>
<script>
import {fileSrc} from '@/env'
export default {
    name: 'index',
    props:{
        form:{
            type:Object,
            default(){
                return {
                    goodsName: '',// 商品名称
                    goodsSupplierBrand: '',//品牌名称
                    goodsSpecification: '',// 规格
                    goodsProduceArea: '',// 产地
                    // goodsSupplierName: '',// 供应商
                    number: '',// 数量
                    total: '',// 最大数量
                    rate: '',// 换率
                    unit: '',// 单位
                    id: '',
                    logo:''
                }
            }
        },
        isShowBtn:{
            type:Boolean,
            default() {
                return true;
            }
        }
    },
    data(){
        return {
          number:0
        }
    },
    options:{
        addGlobalClass: true, //  表示页面样式将影响到自定义组件，但自定义组件中指定的样式不会影响页面。这个选项等价于设置 styleIsolation: apply-shared
        virtualHost: true,  //  将自定义节点设置成虚拟的，更加接近Vue组件的表现。我们不希望自定义组件的这个节点本身可以设置样式、响应 flex 布局等，而是希望自定义组件内部的第一层节点能够响应 flex 布局或者样式由自定义组件本身完全决定
        multipleSlots: true
    },
    methods: {
        fileSrc,
        // 删除
        remove(){
            this.$emit('remove',this.form)
        },
        // 发生变化
        numberChange(e){
            this.$emit('numberChange',e,this.form)
        }
    }
}
</script>

<style lang="scss" scoped>
    .staff-base{
        display: flex;
        align-items: center;

        .staff-icon{
            width: 45px;
            height: 45px;
            margin-right: 10rpx;
        }
        .staff-text{
            flex: 1;
            .staff-title{
                display: flex;
                justify-content: space-between;
                align-items: center;
                width: 100%;
                margin-bottom: 6px;

                .title{
                    font-size: 16px;
                    color: #202020;
                    display: flex;
                    width: calc(100% - 120rpx);
                    flex-wrap: wrap;
                    line-height: 14px;

                    .title-desc{
                        font-size: 12px;
                        color: #8B6445;
                        align-items: flex-start;
                        margin-left: 10rpx;
                    }
                }
                .button{
                    height: 20px;
                    width: 120rpx;

                    .text{
                        font-size: 12px;
                    }

                    ::v-deep button{
                        height: 20px;
                    }
                }
            }

            .staff-content-text{
                font-size: 14px;
                color: #999999;
                line-height: 12px;
                margin-bottom: 6px;
            }
        }
    }
    .staff-content-text-n-b{
      font-size: 14px;
      color: #999999;
      line-height: 12px;
    }
    .staff-action{
      display: flex;
      justify-content: space-between;
      align-items: center;
      .number{
        display: flex;
        align-items: center;
        color: #999999;
        font-size: 14px;
        .temp__out-input{
          display: inline-flex;
          align-items: center;
          margin-right: 10rpx;
          .inner-input{
            width: 30px;
            .base-input{
              text-align: center;
              color: #202020;
              font-size: 14px;
            }
          }
        }

        .minus {
          width: 20px;
          height: 20px;
          border: 1px solid #E6E6E6;
          border-top-left-radius: 100px;
          border-top-right-radius: 100px;
          border-bottom-left-radius: 100px;
          border-bottom-right-radius: 100px;
          @include flex;
          justify-content: center;
          align-items: center;
        }

        .plus {
          width: 20px;
          height: 20px;
          background-color: $uni-color-primary;
          border-radius: 50%;
          /* #ifndef APP-NVUE */
          display: flex;
          /* #endif */
          justify-content: center;
          align-items: center;
        }
      }
    }
</style>