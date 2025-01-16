<template>
  <view class="business-chart">
    <qiun-data-charts type="ring" :opts="options" :chartData="chartData" />
  </view>
</template>

<script setup>
import {ref, reactive, watch, defineProps, onMounted} from 'vue'
import {onLoad, onShow} from '@dcloudio/uni-app';
import echarts from '@/lib/system/echarts'

const props = defineProps({
  // 列表数据
  listRing: {
    required: true,
    type: Array,
    default: []
  }
})

// watch(()=> props.listRing,(list,o) => {
//   formatList(list)
//   console.log(props.listRing,'listRing')
// },{deep: true})



let chartData = ref({
  categories: [],
  series: []
})

let options = ref({})

onMounted(() => {
  formatList()
  init()
})

function formatList(list) {

  let data = {
    categories: [],
    series: [{
      data: props.listRing
    }]
  }
  chartData.value.categories = data.categories
  chartData.value.series = data.series
}

function init() {
  let chart = new echarts()
  let option = {
    rotate: false,
    rotateLock: false,
    color: ["#785940","#8B6445","#AC7F5C"],
    padding: [5,5,5,5],
    dataLabel: true,
    legend: {
      show: true,
      position: "right",
      float: 'center',
      lineHeight: 25,
      fontSize: 12
    },
    title: {
      name: ""
    },
    subtitle: {
      name: ""
    },
    extra: {
      ring: {
        ringWidth: 25,
        activeOpacity: 0,
        activeRadius: 5,
        offsetAngle: 20,
        labelWidth: 2,
        border: false,
        borderWidth: 0,
        linearType: 'none'
      }
    }
  }

  options.value = {
    ...chart.opts,
    ...option
  }
}
</script>

<style lang="scss" scoped>
.business-chart {
  height: 400rpx;
}
</style>
