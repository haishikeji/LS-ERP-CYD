<template>
    <div class="receive-info" v-loading="loading">
      <el-table
        :data="tableData"
        height="calc(100% - 48px)"
        style="width: calc(100% - 10px)"
        border>
        <el-table-column
          label="商品名称">
          <template slot-scope="{row}">
            <div class="shop-text">
              <el-avatar shape="square" size="large" :key="row.goodsLogo" :src="$imageView(row.goodsLogo)">商品</el-avatar>
              <el-tooltip :content="row.goodsName" :open-delay="500" placement="bottom" effect="light">
                <p class="shop-name">
                  {{ row.goodsName }}
                </p>
              </el-tooltip>

            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="empName"
          align="center"
          label="操作员工">
        </el-table-column>
        <el-table-column
          prop="date"
          width="180"
          align="center"
          label="领取时间">
        </el-table-column>
        <el-table-column
          width="180"
          prop="oriQuantity"
          align="right"
          label="领取前数量">
        </el-table-column>
        <el-table-column
          width="180"
          prop="quantity"
          align="right"
          label="领取数量">
        </el-table-column>
        <el-table-column
          width="180"
          align="right"
          prop="afterQuantity"
          label="领取后数量">
        </el-table-column>
      </el-table>
      <div class="table-footer">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-size.sync="formQuery.pageSize"
          :current-page.sync="formQuery.pageNum"
          :page-sizes="[10, 30, 90, 200]"
          layout="sizes,prev, pager, next"
          :pager-count="5"
          :total="total">
        </el-pagination>
      </div>
    </div>
</template>

<script>
import {pageDepositDrawRecord} from "@/api/business/business";

export default {
  name: "deposit-receive",
  data() {
    return {
      tableData: [],
      formQuery:{
        pageNum: 1,
        pageSize: 10
      },
      total: 0,
      loading: false
    }
  },
  props:{
    memberId: {
      type: [String,Number]
    }
  },
  watch:{
    memberId:{
      handler(v) {
        if (v) {
          this.handleList();
        }
      },
      immediate: true
    }
  },
  methods:{
    async handleList() {
      this.loading = true
      try {
        let res = await pageDepositDrawRecord({membersId: this.memberId, ...this.formQuery});
        if (res.code === 200) {
          res.rows.map(item=>{
            item.goodsLogo = item.logo
            item.date = this.$moment(item.timestamp).format('YYYY-MM-DD hh:mm')
            return item
          })
          this.tableData = res.rows
          this.total = res.total
          this.formQuery.pageNum = res.pageNum
        }
      } catch (e){}
      this.loading = false
    },
    handleSizeChange(val) {
      this.formQuery.pageNum = 1;
      this.formQuery.pageSize = val;
      // 获取列表
      this.handleList()
    },
    handleCurrentChange(val) {
      this.formQuery.pageNum = val;
      // 获取列表
      this.handleList()
    },
  }
}
</script>

<style lang="scss" scoped>
 .receive-info{
   overflow-y: hidden;
   height: calc(100vh - 214px);
   .shop-text{
     display: flex;
     align-items: center;
     position: relative;
   }
   .shop-name{
     margin-left: 10px;
     max-width: calc(100% - 50px);
     overflow: hidden;
     text-overflow: ellipsis;
     display: -webkit-box;
     line-clamp: 2;
     -webkit-line-clamp: 2;
     -webkit-box-orient: vertical;
     line-height: 1.5;
   }
 }
</style>
