<template>
    <view class="plug">
        <view class="plug-value" @click="showAll">
<!--            <view class="plug-label">{{ currentValue.label }}</view>-->
            <slot></slot>
        </view>
        <view class="ul" v-if="spread">
            <view class="plug-ul" :class="visibleStyle ? 'plug-visible': 'plug-hidden'"
                  :style="{'--number':list.length}">
                <view class="plug-item" v-for="item in list" :key="item.code"
                      @click="current(item)">{{ item.name }}</view>
            </view>
        </view>
        <view class="mask" @click="clearMask" v-if="spread"></view>
    </view>
</template>
<script setup lang="ts">
import {onMounted,reactive,ref,defineProps,defineEmits,watch} from 'vue'
let currentValue = reactive({value: '',label:''})
// let list = ref([{name: '全部状态',code: '0'}, {name: '使用中',code: '1'},{name:'已选定',code: '2'},{name:'空闲中',code: '3'}])
let spread = ref(false)
let visibleStyle = ref(false)
const props = defineProps({
    selectValue: {
        type: [String,Number],
    },
    list:{
        type: Array,
        required: true
    }
})
const emit = defineEmits(['update:selectValue', 'selected'])
let list = ref(props.list)



onMounted(()=>{
    currentValue.value = list.value[0].code;
    currentValue.label = list.value[0].name
})

watch(()=>props.selectValue,(newValue,oldValue)=>{
    if (newValue) {
        currentValue.value = newValue;
        currentValue.label = currentName(newValue).name
    }
})

function currentName(code) {
    if (list.value.length < 0) {
        return ''
    }
    if (!code ||code == '') {
        return  ''
    }
    const find = list.value.filter(item=>{
        return item.code == code
    })
    return find && find[0]
}

function showAll() {
    if (visibleStyle.value) {
        clearMask()
    } else {
        spread.value = !spread.value;
        setTimeout(() => {
            visibleStyle.value = !visibleStyle.value
        }, 300);
    }
}
// 关闭遮罩
function clearMask() {
    setTimeout(() => {
        spread.value = !spread.value
    },300)
    visibleStyle.value = !visibleStyle.value
}
// 选中
function current(item) {
    currentValue.value = item.code
    currentValue.label = item.name
    
    emit('update:selectValue',currentValue.value)
    emit('selected', item)
    
    clearMask()
}

</script>
<style lang="scss" scoped>
.plug{
    display: block;
    position: relative;
}
.plug-value{
    display: flex;
    background: #ffffff;
    padding-right: 30rpx;
    justify-content: space-between;
    .plug-label{
        font-size: 28rpx;
        color: #202020;
        width: 150rpx;
        text-align: right;
    }
}
.ul{
    position: absolute;
    //top: -400rpx;
    top: -300rpx;
    left: -90rpx;
    z-index: 81;
}
.plug-ul{
    display: flex;
    flex-direction: column;
    justify-content: center;
    background: #FFFFFF;
    box-shadow: 0 0 10rpx 0 rgba(0,0,0,0.2);
    border-radius: 16rpx;
    padding: 0 30rpx;
    width: 200rpx;
    box-sizing: border-box;
    overflow: hidden;
    animation:window-open 0.3s 1;
}

@keyframes window-open
{
    0%{transform:scale(1,0);}
    100%{transform:scale(1,1);}
}

.plug-visible{
    height: calc(90rpx * var(--number));
    &:after{
        content: ' ';
        display: inline-block;
        border-bottom: 20rpx solid transparent;
        border-right: 20rpx solid transparent;
        border-left: 20rpx solid transparent;
        border-top: 20rpx solid #FFFFFF;
        position: absolute;
        //top: 362rpx;
        top: 272rpx;
        right: 20%;
        //background-color: #EEEEEE;
        filter: drop-shadow(0 0rpx 4rpx rgba(0,0,0,0.2));
    }
}

.plug-hidden{
    height: 0;
}

.plug-item{
    font-size: 32rpx;
    color: #202020;
    line-height: 90rpx;
    height: 90rpx;
    text-align: center;
    border-bottom: 1rpx solid #EEEEEE;
}

.icon{
    transition: transform 150ms;
}
.icon-select{
    transform: rotate(-90deg);
}
.mask{
    position: fixed;
    left: 0;
    top: 0;
    height: 100vh;
    width: 100%;
    z-index: 80;
}
</style>
