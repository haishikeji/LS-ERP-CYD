<template>
<div class="page-container page-mg">
  <div class="container">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" class="form">
        <div class="font-title-drive">
          新增店铺
        </div>
        <div class="page-body">
        <el-form-item label="门店名称" prop="shopName">
          <el-input v-model="form.shopName" style="width: 400px" placeholder="请输入门店名称"></el-input>
        </el-form-item>
        <el-form-item label="门店图片" prop="shopLogo">
          <el-upload
            class="avatar-uploader"
            :action="$uploadUrl"
            :show-file-list="false"
            :headers="{
              authorization: token
            }"
            :on-success="handleAvatarSuccess">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div class="tips">支持jpg、png格式上传或从图片文件选择</div>
        </el-form-item>
          <el-form-item label="门店地址" prop="goodsSupplierAddrBefore">
            <city-select style="width: 400px" @change="selectChange" placeholder="请选择门店地址"></city-select>
          </el-form-item>
          <el-form-item label="地址详情" prop="goodsSupplierAddrAfter">
            <el-input v-model="form.goodsSupplierAddrAfter" style="width: 400px" placeholder="请输入地址详情"></el-input>
          </el-form-item>
          <el-form-item label="营业时间" prop="openingHours">
            <el-time-picker
              is-range
              value-format="HH:mm"
              v-model="form.openingHours"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              placeholder="选择营业时间">
            </el-time-picker>
          </el-form-item>
        </div>
      </el-form>
  </div>

  <div class="footer">
    <el-button type="primary" @click="save">保存</el-button>
    <el-button type="primary" plain @click="cancel">返回</el-button>
  </div>
</div>
</template>

<script>
import {getToken} from "@/utils/auth";
import {addShop} from "@/api/business/manage";
import CitySelect from '@/components/CitySelect'

export default {
  name: "addGoods",
  components: {CitySelect},
  data() {
    debugger
    return {
      imageUrl: '',
      form:{
        shopName: '',
        shopLogo: '',
        goodsSupplierAddrBefore: '',
        goodsSupplierAddrAfter: '',
        openingHours: []
      },
      rules:{
        shopName:[
          { required: true, message: '请输入门店名称', trigger: 'blur' }
        ],
        shopLogo:[
          { required: true, message: '请上传门店图片', trigger: 'change' }
        ],
        goodsSupplierAddrBefore:[
          { required: true, message: '请选择门店地址', trigger: 'change' }
        ],
        goodsSupplierAddrAfter:[
          { required: true, message: '请选择门店详细地址', trigger: 'change' }
        ],
      }
    }
  },
  computed:{
    token() {
      return getToken()
    }
  },
  methods:{
    // 门店发生变化
    goodsSupplierChange(e) {
      let find = this.goodsSupplierList.filter(item=>{
        return item.value === e
      })
      try {
        let city  = JSON.parse(find[0].goodsSupplierArea);
        this.form.city = city.addrDetailed
      } catch (e){
        this.form.city = ''
      }
    },

    // 文件上传成功
    handleAvatarSuccess(res, file) {
      if (res.code == 200) {
        let shopLogo = res.data.sid;
        this.imageUrl = this.$imageView(shopLogo);
        this.form.shopLogo = shopLogo
      } else {
       this.$message.error(res.msg)
      }
    },
    // 保存
    save() {
      console.log("this.form.openingHours",this.form.openingHours)
      this.$refs['form'].validate(valid=>{
        if (valid) {
          let addr = {
            addrStandard: this.form.goodsSupplierAddrBefore,
            addrDetailed: this.form.goodsSupplierAddrAfter,
            addrCode: this.form.goodsSupplierAddrBefore.split(',')
          }
          let openingHours = ''
          if (this.form.openingHours && this.form.openingHours.length > 0) {
            openingHours = this.form.openingHours[0] + '~' + this.form.openingHours[1]
          }

          let form = {
            "shopName": this.form.shopName,
            "shopLogo": this.form.shopLogo,
            "shopAddr": JSON.stringify(addr),
            "openingHours":openingHours,
          };
          this.loading = true
          addShop(form).then(res=>{
            if (res.code === 200) {
              this.$message.success('添加门店成功')
              this.cancel()
            }
          }).finally(_=>{
            this.loading = false
          })
        }
      })
    },
    // 选中发生变化
    selectChange(value) {
      this.form.goodsSupplierAddrBefore = value
    },
    cancel() {
      this.$router.back()
    },
  }
}
</script>

<style lang="scss" scoped>
  .page-body{
    padding-top: 22px;
  }
.tips{
  color: #999999;
  line-height: 16px;
  font-size: 14px;
}
.form{
  padding-bottom: 100px;
}

.footer{
  background: #FFFFFF;
  height: 60px;
  box-shadow: 0 0 10px #eeeeee;
  position: fixed;
  width: 100%;
  bottom: 0;
  left: 0;
  z-index: 10;
  text-align: center;
  line-height: 60px;
}
</style>
