<template>
  <div class="page-container page-mg">
    <div class="container">
      <el-button type="primary" size="small" class="add-btn" @click="add">新增房间</el-button>
      <div class="search">
        <el-form :inline="true"
                 :model="form"
                 ref="form"
                 @submit.native.prevent
                 label-width="120px">
          <el-form-item label="房间状态" prop="roomType">
            <el-select v-model="form.roomType" placeholder="请选择房间状态" size="small" clear>
              <el-option
                v-for="item in roomTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit" size="small">筛选</el-button>
            <el-button @click="formReset" size="small">重置</el-button>
          </el-form-item>
        </el-form>
      </div>

      <div class="table">
        <el-table
          :data="tableData"
          border
          v-loading="loading"
          header-cell-class-name="header-bg"
          style="width: 100%">
          <el-table-column
            prop="roomNo"
            align="left "
            label="房间编号"
            min-width="320">
          </el-table-column>
          <el-table-column
            prop="roomName"
            align="left "
            label="房间名称"
            min-width="320">
          </el-table-column>
          <el-table-column
            min-width="200"
            align="center"
            prop="roomTypeName"
            label="房间类型">
          </el-table-column>
          <el-table-column
            width="240px"
            align="center"
            label="操作">
            <template slot-scope="{row}">
              <el-button type="text" @click="edit(row)">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="text-align: center;margin-top: 15px">
          <el-button plain type="primary" @click="$router.back()">返回</el-button>
        </div>
      </div>
    </div>

    <el-dialog
      :title="isEdit?'编辑房间':'添加房间'"
      :visible.sync="dialogVisible"
      :before-close="visibleClose"
      v-loading="loadingRoom"
      width="600px">
      <el-form :model="formRoom"
               :rules="rules"
               ref="formRoom"
               label-width="150px">
        <el-form-item label="房间编号" prop="roomNo" v-if="!isEdit">
          <el-input v-model="formRoom.roomNo" style="width: 300px" placeholder="请输入房间编号"></el-input>
        </el-form-item>
        <el-form-item label="房间名称" prop="roomName">
          <el-input v-model="formRoom.roomName" style="width: 300px" placeholder="请输入房间名称"></el-input>
        </el-form-item>
        <el-form-item label="房间类型" prop="roomType">
          <el-radio v-model="formRoom.roomType" v-for="item in roomTypeList" :key="item.value" :label="item.value">
            {{item.label}}
          </el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {roomTypeList} from "@/utils/common";
import {listRoom,addRoom,editRoom} from "@/api/business/manage";

export default {
  name: "StoreRoom",
  data() {
    return {
      roomTypeList,
      form:{
        type: '',
        pageNum: 1,
        pageSize: 10,
        shopId: ''
      },
      total: 0,
      tableData: [],
      loading: false,
      loadingRoom: false,
      isEdit: false,
      formRoom:{
        roomId:"", //房间编号【must】,
        shopId: "",//门店编号【must】,
        roomNo: "",//房间编号【must】,
        roomName: "",//房间名称,
        roomType: "",//房间类型 1-大厅(散座) 2-包间【must】
      },
      dialogVisible: false,
      rules:{
        roomId: [
          {required: true,message: '请输入房间编号',trigger: 'blur'}
        ],
        roomNo: [
          {required: true,message: '请输入房间编号',trigger: 'blur'}
        ],
        roomName: [
          {required: true,message: '请输入房间名称',trigger: 'blur'}
        ],
        roomType: [
          {required: true,message: '请输入房间类型',trigger: 'change'}
        ],
      }
    }
  },
  mounted() {
    this.query()
    this.handleList()
  },
  methods:{
    query() {
      this.form.shopId = this.$route.query.id
    },
    // 提交
    onSubmit() {
      this.form.pageNum = 1
      this.handleList()
    },
    // 重置
    formReset() {
      this.$refs['form'].resetFields()
      this.handleList()
    },
    // 获取房间列表
    handleList() {
      let form = {
        roomType: this.form.roomType,
        pageNum: this.form.pageNum,
        pageSize: this.form.pageSize,
        shopId: this.form.shopId
      }
      this.loading = true
      listRoom(form).then(res=>{
        if (res.code === 200){
          res.data.map(item=>{
            let type = roomTypeList.filter(child=>child.value == item.roomType)
            if (type.length > 0) {
              item.roomTypeName = type[0].label
            }
          })
          this.tableData = res.data
        }
        // this.form.pageNum = res.pageNum
        // this.total = res.total
      }).finally(()=>{
        this.loading = false
      })
    },
    handleSizeChange(val) {
      this.form.pageNum = 1;
      this.form.pageSize = val;
      // 获取列表
      this.handleList()
    },
    handleCurrentChange(val) {
      this.form.pageNum = val;
      // 获取列表
      this.handleList()
    },
    // 取消
    cancel() {
      this.dialogVisible = false
      this.resetVisible()
    },
    // 新增房间
    add() {
      this.isEdit = false
      this.formRoom.shopId = this.form.shopId
      this.formRoom.roomId = ''
      this.formRoom.roomNo = ''
      this.dialogVisible = true
    },
    edit(row) {
      this.isEdit = true
      this.formRoom.shopId = this.form.shopId
      this.formRoom.roomId = row.roomId
      this.formRoom.roomName = row.roomName
      this.formRoom.roomType = row.roomType + ''
      this.dialogVisible = true
    },
    visibleClose(done) {
      done()
      this.resetVisible()
    },
    resetVisible() {
      this.$refs['formRoom'].resetFields()
    },
    // 保存
    save() {
      this.$refs['formRoom'].validate(vali=>{
        if (vali) {
          if (this.formRoom.roomId) {
            this.editRoom()
          } else {
            this.addRoom()
          }
        }
      })
    },
    // 添加房间
    addRoom() {
      let form = {
        "shopId": this.formRoom.shopId,
        "roomNo": this.formRoom.roomNo,
        "roomName": this.formRoom.roomName,
        "roomType": this.formRoom.roomType
      }
      this.loadingRoom = true
      addRoom(form).then(res=>{
        if (res.code === 200) {
          this.$message.success("添加成功")
          this.cancel()
          this.handleList()
        }
      }).finally(()=>{
        this.loadingRoom = false
      })
    },
    // 修改房间
    editRoom() {
      let form = {
        "shopId": this.formRoom.shopId,
        "roomId": this.formRoom.roomId,
        "roomName": this.formRoom.roomName,
        "roomType": this.formRoom.roomType
      }
      this.loadingRoom = true
      editRoom(form).then(res=>{
        if (res.code === 200) {
          this.$message.success("修改成功")
          this.cancel()
          this.handleList()
        }
      }).finally(()=>{
        this.loadingRoom = false
      })
    },
    // 获取详情
    infoInventory(row) {
      this.$router.push({
        path:"/warehouse/price-adjustment-info",
        query:{
          id: row.readjustId
        }
      })
    },
  }
}
</script>

<style lang="scss" scoped>
.search{
  display: flex;
  background: #F2F3F5;
  align-items: center;
  padding-top: 22px;
  margin: 15px 0;
}
.table-footer{
  text-align: right;
  margin-top: 15px;
}
.dialog-footer{
  text-align: center;
}
</style>

