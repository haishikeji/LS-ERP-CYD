<template>
 <scroll-view :scroll-y="true" @scrolltolower="scrollChange">
   <!--header-->
<!--   <total-account-card-->
<!--       :totalCope="totalCope"-->
<!--       @clickDate="handleDate"-->
<!--   >-->
<!--     <template #date>-->
<!--       {{dateFormat(searchData.selectDate)}}-->
<!--     </template>-->
<!--   </total-account-card>-->

    <view class="form">
      <view class="form-group">
        <view class="header">
          <view class="title">
            <view class="title-text">账款总金额(元)</view>
            <view class="header-item" @click="handleDate">
              <text class="item-type">
                {{dateFormat(searchData.selectDate)}}
              </text>
              <view class="icon">
                <u-icon class="arrow-down" name="arrow-down" color="#999999" size="12"></u-icon>
              </view>
            </view>
          </view>
          <view class="title-content">5688900.00</view>
        </view>
      </view>

      <view class="form-group charts">
        <view class="group-title-item">
          <view class="title">每日应付账款分布</view>
        </view>
       <view class="title-font">
         本月 <text>28日</text>的应付账款最高，共 <text>366000.00元</text>
       </view>

        <qiun-data-charts type="bar"
                          style="height: 1900rpx"
                          :opts="opts"
                          :chartData="chartData"/>


      </view>

      <view class="last-text">{{ message }}</view>
    </view>

<!--   <u-datetime-picker-->
<!--       :show="showDatetime"-->
<!--       v-model="datetime"-->
<!--       mode="year-month"-->
<!--       @close="showDatetime = false"-->
<!--       @cancel="showDatetime = false"-->
<!--       @confirm="confirmDate"-->
<!--   ></u-datetime-picker>-->

   <u-picker mode="time"
             @confirm="confirmDate"
             @cancel="showDatetime = false"
             v-model="datetime" :params="{
					year: true,
					month: true,
					day: false,
					hour: false,
					minute: false,
					second: false
				}"></u-picker>

 </scroll-view>
</template>

<script setup>
  import { ref,reactive,onMounted } from 'vue'
  import dayjs from 'dayjs'
  import echarts from '@/lib/system/echarts'
  import inlineStyleConfig from '@/lib/system/inlineStyleConfig'
  import totalAccountCard from '../../receivables/component/totalAccountCard'

  let message = ref('')
  // 总金额
  let totalCope = ref(null)

  // 是否显示日期选择框
  let showDatetime = ref(false)

  let datetime = ref(dayjs())

  let searchData = reactive({
    // 1-日营业 2-月营业 3-年营业
    mode: 1,
    selectDate: dayjs().format('YYYY-MM')
  })

  let paramsQuery = reactive({
    pageNum: 1,
    pageSize: 15,
    total: null
  })

  let opts = reactive({})
  let chartData = reactive({})


  function handleDate() {
    showDatetime.value = true
  }

  function confirmDate(val) {
    // searchData.selectDate = dayjs(val.value).format('YYYY-MM')
    searchData.selectDate = `${val.year}-${val.month}`
    showDatetime.value = false

    // getInfo()
    // getTotalCope()
  }

  // 格式化时间
  function dateFormat(val) {
    return dayjs(val).format("YYYY年MM月")
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

  //设置图表数据
  function serveData(){
    let chartVo = new echarts()
    // 数据
    let data = []
    // 日期
    let day = []
    let max = 0
    for(let i = 1; i < 28; i++){
      day.push((i.toString().length > 1 ? i : '0'+i)+'日')
      data.push(parseFloat(Math.random() * 1000000).toFixed(2))
      if(max < data[i-1]){
        max = data[i-1]
      }
    }
    chartVo.setOpts({
      xAxis:{
        boundaryGap: 'justify',
        axisLine: false,
        disableGrid: true,
        disabled: true,
        max: max * 1.5
      },
      yAxis:{
        disableGrid:true,
        axisLine: false
      }
    })
    chartVo.setExtra({
      bar:{
        categoryGap: 6,
        activeBgOpacity: 0,
        linearType: 'custom',
        barBorderRadius: [
          2,
          2,
          0,
          0
        ],
        colorStop: 0,
        customColor: ['#ffffff',inlineStyleConfig.uniColorPrimary]
      }
    })
    chartVo.setChartsCateGories(day)
    chartVo.setChartsSeries([{
      data:data,
      textColor:inlineStyleConfig.uniColorPrimary,
      textSize: 12,
      format: 'money',
    }])

    opts = Object.assign(opts,{...chartVo.getOpts()})
    chartData = Object.assign(chartData,{...chartVo.getCharts()})
    console.log(chartData, 'ddd')
  }

  onMounted(()=>{
    serveData()
  })

</script>

<style lang="scss" scoped>
  .form {
    margin-top: 20rpx;

    .form-group {
      background: #ffffff;
      border-radius: 20rpx;
      padding: 0 30rpx;
      margin-bottom: 20rpx;
      position: relative;
      overflow: hidden;

      .header {
        padding: 45rpx 0;

        .title {
          display: flex;
          justify-content: space-between;
          align-items: center;
        }

        .title-text {
          font-size: 28rpx;
          font-family: Hiragino Sans GB;
          font-weight: normal;
          color: #666666;
          //line-height: 100rpx;
        }

        .title-content {
          margin-top: 20rpx;
          font-size: 66rpx;
          font-family: FZLTCHK--GBK1-0;
          font-weight: 400;
          color: #202020;
          //line-height: 100rpx;
        }

        .header-item {
          display: flex;
          flex-direction: row;
          flex: 1;
          justify-content: flex-end;
          align-items: center;

          .item-type {
            margin-right: 15rpx;
            font-size: 32rpx;
            font-family: Hiragino Sans GB;
            font-weight: normal;
            color: #202020;
            //line-height: 100rpx;
          }
        }
      }
    }

    .group-title-item{
      display: flex;
      align-items: center;

      &:before{
        content: ' ';
        display: inline-block;
        width: 4rpx;
        height: 28rpx;
        background: $uni-color-primary;
        margin-right: 17rpx;
      }

      .title{
        font-size: 32rpx;
        font-family: Hiragino Sans GB;
        font-weight: normal;
        color: #202020;
        height: 44rpx;
      }
    }

    .charts{
      padding-top: 40rpx;
      padding-bottom: 40rpx;
    }

    .title-font{
      margin-top: 30rpx;

      font-size: 28rpx;
      font-family: Hiragino Sans GB;
      font-weight: normal;
      color: #999999;
      line-height: 44rpx;
      margin-left: 2rpx;

      text{
        font-size: 28rpx;
        font-family: Hiragino Sans GB;
        font-weight: normal;
        color: #202020;
        line-height: 44rpx;
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
