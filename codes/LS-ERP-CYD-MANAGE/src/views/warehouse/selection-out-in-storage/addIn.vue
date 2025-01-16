<template>
  <div class="page-container page-mg">
    <div class="container">
      <div class="font-title-drive">
        新增入库单
      </div>
<!--      <div class="search">-->
<!--          <span>仓库：</span><span>{{ form.storeName }}</span>-->
<!--      </div>-->
      <select-page :list="list" v-model="activeName">
        <purchase-in-store v-if="activeName == 'second'" :shelfList="shelfList" :form="form"></purchase-in-store>
        <setting-in-store v-else :shelfList="shelfList" :form="form"></setting-in-store>
      </select-page>
    </div>

<!--    <el-dialog-->
<!--      title="选择入库仓库"-->
<!--      :visible.sync="storeVisible"-->
<!--      :close-on-click-modal="false"-->
<!--      :close-on-press-escape="false"-->
<!--      :show-close="false"-->
<!--      width="500px">-->
<!--      <el-form :inline="true"-->
<!--               :model="form"-->
<!--               ref="form"-->
<!--               @submit.native.prevent-->
<!--               label-width="80px">-->
<!--        <el-form-item label="仓库：" prop="name">-->
<!--            <el-select v-model="form.storeId"-->
<!--                       filterable-->
<!--                       placeholder="请选择入库仓库"-->
<!--                       size="small"-->
<!--                       clear style="width: 300px" @change="storeIdChange">-->
<!--              <el-option-->
<!--                v-for="item in storeList"-->
<!--                :key="item.storeId"-->
<!--                :label="item.fullName"-->
<!--                :value="item.storeId">-->
<!--              </el-option>-->
<!--            </el-select>-->
<!--        </el-form-item>-->
<!--        <div class="dialog-msg">-->
<!--          <span v-if="shelfList.length<1">暂无货架</span>-->
<!--          <div v-else>-->
<!--            <span v-for="(item,index) in shelfList">-->
<!--              <span>{{index !==0 ? '‘': ''}}</span>-->
<!--              <span>{{index+1+ '.' + item.shelfName}}</span>-->
<!--            </span>-->
<!--          </div>-->
<!--        </div>-->
<!--      </el-form>-->
<!--      <div  class="store-footer">-->
<!--      <el-button type="primary" @click="next">下一步</el-button>-->
<!--      </div>-->
<!--    </el-dialog>-->

  </div>
</template>
<script>
import {handleStoreList} from "@/utils/commonHandle";
import {listShelf} from "@/api/business/storage";
import SelectPage from "@/components/SelectPage"
// import settingInStore from "./component/settingInStore";
import settingInStore from "./component/settingInStoreNew";
import PurchaseInStore from "./component/purchaseInStore";

export default {
  components:{PurchaseInStore,SelectPage,settingInStore},
  data(){
    return{
      activeName: 'first',
      list: [
        {label: '自定义入库',name: 'first'},
        {label: '采购单入库',name: 'second'},
      ],
      storeList: [],
      // storeVisible: true,
      form: {
        storeId: '',
        storeName: '',
        purchaseId: '' // 采购单id
      },
      shelfList: [],// 货架列表
    }
  },
  mounted() {
    // this.handleInfo()
    this.init()
  },
  methods: {
    // handleInfo() {
    //   handleStoreList().then(({data}) => {
    //     this.storeList = data
    //   })
    // },

    // 初始化
    init() {
      this.form.purchaseId = this.$route.query.id || ''
      this.activeName = this.$route.query.type || 'first'
    },
    // 采购入库
    purchaseInStore() {
        let form = {
          purchaseId: this.form.purchaseId,
          storeId:this.form.storeId,
        }
    },
    // storeId 发生变化
    storeIdChange(value) {
      let find = this.storeList.filter(item => {
        return item.storeId == value
      })
      if (find.length > 0) {
        this.form.storeName = find[0].fullName
        this.handleShelfList()
      }
    },
    // next() {
    //   if (!this.form.storeId) {
    //     this.$message.warning('请选择入库仓库')
    //     return
    //   }
    //   this.storeVisible = false;
    // },
    // 获取货架列表
    handleShelfList() {
      listShelf({storeId: this.form.storeId}).then(res=>{
        if (res.code === 200) {
          this.shelfList = res.data
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.search{
  padding: 0 15px;
  font-size: 14px;
  color: #606266;
}
.container-component{
  padding: 0!important;
}
.dialog-msg{
  margin-left: 80px;
  border: 1px solid #eeeeee;
  padding: 15px;
  width: 300px;
  margin-bottom: 22px;
  line-height: 1.5;
  font-size: 14px;
}
.store-footer{
  text-align: center;
}
</style>
