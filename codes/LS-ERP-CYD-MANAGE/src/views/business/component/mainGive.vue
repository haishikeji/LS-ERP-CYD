<template>
  <div class="goods-container">
    <div class="header-title">
      <span class="title">赠送商品</span>
      <el-input class="input" v-model="form.select" placeholder="请输入商品名称">
        <el-button slot="append" icon="el-icon-search" @click="searchList"></el-button>
      </el-input>
    </div>
    <div class="content-goods" v-loading="loading">
      <div class="card" v-for="item in listGoods" :class="{'card-active':storeGoodsIds.indexOf(item.storeGoodsId)!==-1}" @click="currentGoods(item)">
        <div class="title-name">
          {{item.goodsName}}{{'\t'+item.goodsSpecification}}
        </div>
        <div class="box">
          <el-avatar :size="50" shape="square" :key="item.goodsLogo" :src="$imageView(item.goodsLogo)"/>
          <div class="text-box">
              <div class="desc-balance">
                <span>{{item.goodsSalePrice}}元 <span v-if="item.goodsUnitName">/{{item.goodsUnitName}}</span></span>
              </div>
              <div v-if="item.goodsUnitName2" class="desc-balance">
                <span>{{item.goodsSalePrice2}}元 <span>/{{item.goodsUnitName2}}</span></span>
              </div>
            <div class="desc-count">
              <span>库存：</span>
              <span>{{item.quantity}}</span>
              <span>{{item.goodsUnitName}}</span>
              <span>{{item.quantity2}}</span>
              <span>{{item.goodsUnitName2}}</span>
            </div>
          </div>
        </div>
      </div>
      <div style="width: 100%">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-size.sync="form.pageSize"
          :current-page.sync="form.pageNum"
          :page-sizes="[16, 36, 92, 200]"
          layout="sizes,prev, pager, next"
          :pager-count="5"
          :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import {handleStorePageGoods} from "@/utils/commonHandle";
import {mapGetters} from "vuex";

export default {
  name: "mainGoods",
  data() {
    return{
      form:{
        pageNum: 1,
        pageSize: 16,
        select: ''
      },
      total: 0,
      listGoods: [],
      loading:false
    }
  },
  computed: {
    ...mapGetters(['giveActive']),
    storeGoodsIds() {
      let ids = []
      this.giveActive.forEach(item=>{
        ids.push(item.storeGoodsId)
      })
      return ids
    }
  },
  mounted() {
    this.handleListGoods()
  },
  methods:{
    // 获取商品列表
    handleListGoods() {
      this.loading = true
      handleStorePageGoods(this.form).then(res=>{
        this.listGoods = res.rows
        this.form.pageNum = res.pageNum
        this.total = res.total
      }).finally(()=>{
        this.loading = false
      })
    },
    handleSizeChange(val) {
      this.form.pageNum = 1;
      this.form.pageSize = val;
      // 获取列表
      this.handleListGoods()
    },
    handleCurrentChange(val) {
      this.form.pageNum = val;
      // 获取列表
      this.handleListGoods()
    },
    searchList() {
      this.form.pageNum = 1
      this.handleListGoods()
    },
    // 选中商品
    currentGoods(item) {
      if (item.rate < 1) {
        this.$message.warning('商品规格有误')
        return
      }
      if (!item.goodsUnitName) {
        this.$message.warning('商品信息有误')
        return;
      }
      let indexs = ''
      this.giveActive.forEach((child,index)=>{
        if (child.storeGoodsId == item.storeGoodsId) {
          indexs = index
        }
      })
      if (indexs !== '') {
        this.$store.commit('SET_GIVE_ACTIVE_DEL', indexs);
      } else {
        this.$store.commit('SET_GIVE_ACTIVE',{
          ...item,
          goodsName: item.goodsName,
          goodsLogo: item.goodsLogo,
          goodsSalePrice: item.goodsSalePrice,
          goodsUnitName: item.goodsUnitName,
          quantity0: item.quantity,
          quantity3:0,
          quantity4:0,
          storeGoodsId: item.storeGoodsId,
        });
      }
      this.$emit("modalChange",3)
    }
  }
}
</script>

<style lang="scss" scoped>
.goods-container{
  padding: 15px 20px 0;
  flex: 1;

  .header-title{
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #eeeeee;

    .title{
      padding-bottom: 15px;
      color: #303133;
    }

    .input{
      width: 300px;
      margin-bottom: 15px;
    }
  }

  .content-goods{
    display: flex;
    flex-wrap: wrap;
    height: calc(100% - 56px);
    overflow-y: auto;
    padding-top: 15px;
    align-content: flex-start;

    .card{
      margin-right: 15px;
      margin-bottom: 15px;
      border: 1px solid #eeeeee;
      padding: 10px 15px;
      box-shadow: 0 0 1px 0 #eee;
      flex-basis: calc(100% / 4 - 15px);
      height: 118px;

      .title-name{
        font-size: 15px;
        color: #303133;
        font-weight: 700;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
        width: 100%;
        height: 40px;
        line-height: 19px;
      }

      .box{
        display: flex;

        .text-box{
          margin-left: 8px;
        }

        $font-desc:14px;
        $font-line: 1.5;
        .desc-balance{
          font-size: $font-desc;
          line-height: $font-line;
          color: #F56C6C;
        }
        .desc-count{
          font-size: $font-desc;
          line-height: $font-line;
          color: #606266;
        }
      }

      &:hover{
        background: #F2F6FC;
      }
      &:active{
        background: #EBEEF5;
      }
    }

    .card-active{
      background: #F2F6FC;
    }
  }
}
</style>
