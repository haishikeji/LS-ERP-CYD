<template>
    <el-card class="form-container" shadow="never">
        <div v-for="(cate,index) in allResourceCate" :class="index===0?'top-line':null" :key="'cate'+cate.id">
            <el-row class="table-layout" style="background: #F2F6FC;">
                <el-checkbox v-model="cate.checked"
                             :indeterminate="isIndeterminate(cate)"
                             @change="handleCheckAllChange(cate)">
<!--                             :indeterminate="isIndeterminate(cate)"-->
                    {{cate.permName}}
                </el-checkbox>
            </el-row>
            <el-row class="table-layout">
<!--                <el-col :span="8" v-for="resource in getResourceByCate(cate.id)" :key="resource.id" style="padding: 4px 0">-->
                <div class="table-item" v-for="resource in cate.children" :key="resource.id" style="padding: 4px 0">
                    <el-checkbox v-model="resource.checked" @change="handleCheckChange(resource,cate)">
                        {{resource.permName}}
                    </el-checkbox>
                </div>
            </el-row>
        </div>
        <div style="margin-top: 20px" align="center">
            <el-button type="primary" @click="handleSave()">保存</el-button>
            <el-button @click="handleClear()">清空</el-button>
        </div>

    </el-card>
</template>

<script>
// import {fetchAllResourceList} from '@/api/resource';
// import {listAllCate} from '@/api/resourceCategory';
import {allocResource,listResourceByRole} from '@/api/role';
import {treePerm} from '@/api/appWebRole'

export default {
  name: "allPermis",
  data() {
    return {
      roleId: null,
      allResource: null,
      allResourceCate: null
    };
  },
  created() {
    this.roleId = this.$route.query.roleId;
    this.getAllResourceCateList();
  },
  methods: {
    // getAllResourceList() {
    //   fetchAllResourceList().then(response => {
    //     this.allResource = response.data;
    //     for (let i = 0; i < this.allResource.length; i++) {
    //       this.allResource[i].checked = false;
    //     }
    //     this.getResourceByRole(this.roleId);
    //   });
    // },
    // 获取资源
    getAllResourceCateList() {
      // listAllCate().then(response => {
      //   this.allResourceCate = response.data;
      //   for (let i = 0; i < this.allResourceCate.length; i++) {
      //     this.allResourceCate[i].checked = false;
      //   }
      //   this.getAllResourceList();
      // });
      treePerm().then(res=>{
        if(res.code == 200){
          this.allResourceCate = res.data
          this.allResourceCate && this.allResourceCate.map(item=>{
            item.permName = item.categoryName
            item.checked = false
            return item
          })
        }
      })
    },
    // getResourceByCate(permId) {
    //   let cateResource = [];
    //   if (this.allResource == null) return null;
    //   for (let i = 0; i < this.allResource.length; i++) {
    //     let resource = this.allResource[i];
    //     if (resource.categoryId === permId) {
    //       cateResource.push(resource);
    //     }
    //   }
    //   return cateResource;
    // },
    // getResourceByRole(roleId){
    //   listResourceByRole(roleId).then(response=>{
    //     let allocResource = response.data;
    //     this.allResource.forEach(item=>{
    //       item.checked = this.getResourceChecked(item.id,allocResource);
    //     });
    //     this.allResourceCate.forEach(item=>{
    //       item.checked = this.isAllChecked(item.id);
    //     });
    //     this.$forceUpdate();
    //   });
    // },
    getResourceChecked(resourceId,allocResource){
      if(allocResource==null||allocResource.length===0) return false;
      for(let i=0;i<allocResource.length;i++){
        if(allocResource[i].id===resourceId){
          return true;
        }
      }
      return false;
    },
    isIndeterminate(cate) {
      let cateResources = cate.children;
      if (cateResources == null) return false;
      // 统计当前组选中数量
      let checkedCount = 0;
      for (let i = 0; i < cateResources.length; i++) {
        if (cateResources[i].checked === true) {
          checkedCount++;
        }
      }
      // 是否全选
      return !(checkedCount === 0 || checkedCount === cateResources.length);
    },
    isAllChecked(permId,cate) {
      // let cateResources = this.getResourceByCate(permId);
      let cateResources = cate.children;
      if (cateResources == null) return false;
      let checkedCount = 0;
      for (let i = 0; i < cateResources.length; i++) {
        if (cateResources[i].checked === true) {
          checkedCount++;
        }
      }
      if(checkedCount===0){
        return false;
      }
      return checkedCount === cateResources.length;
    },
    handleSave() {
      this.$confirm('是否分配权限？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let checkedResourceIds = new Set();
        console.log(this.allResourceCate, 'allResourceCate')
        recursion(this.allResourceCate,checkedResourceIds)
        // if (this.allResource != null && this.allResource.length > 0) {
        //   this.allResource.forEach(item => {
        //     if (item.checked) {
        //       checkedResourceIds.add(item.id);
        //     }
        //   });
        // }
        let params = new URLSearchParams();
        params.append("roleId", this.roleId);
        params.append("resourceIds", Array.from(checkedResourceIds));
        console.log( params, 'params')
        // allocResource(params).then(response => {
        //   this.$message({
        //     message: '分配成功',
        //     type: 'success',
        //     duration: 1000
        //   });
        //   this.$router.back();
        // })
      })
      // 递归
      function recursion(resource,listSet){
        resource.forEach(item=>{
          if(item.checked && item.permId){
            listSet.add(item.permId)
          }
          if(item.children){
            recursion(item.children,listSet)
          }
        })
      }
    },
    handleClear() {
      this.allResourceCate.forEach(item => {
        item.checked = false;
      });
      this.allResource.forEach(item => {
        item.checked = false;
      });
      this.$forceUpdate();
    },
    handleCheckAllChange(cate) {
      // let cateResources = this.getResourceByCate(cate.permId);
      // 子集
      let cateResources = cate.children
      for (let i = 0; i < cateResources.length; i++) {
        cateResources[i].checked = cate.checked;
      }
      this.$forceUpdate();
    },
    handleCheckChange(resource,cate) {
      // this.allResourceCate.forEach(item=>{
      //   if(item.id===resource.permId){
      //     item.checked = this.isAllChecked(resource.permId,cate);
      //   }
      // });
      cate.checked = this.isAllChecked(resource,cate)
      this.$forceUpdate();
    }
  }
}
</script>

<style lang="scss" scoped>
    .table-layout {
        padding: 20px;
        border-left: 1px solid #DCDFE6;
        border-right: 1px solid #DCDFE6;
        border-bottom: 1px solid #DCDFE6;
        &:nth-child(n+2) {
            display: flex;
            flex-wrap: wrap;
            justify-content: flex-start;
        }

        .table-item{
            margin-right: 10px;
        }
    }

    .top-line {
        border-top: 1px solid #DCDFE6;
    }
</style>
