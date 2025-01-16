<template>
  <div class="page-container page-mg">
    <div class="container">
      <div class="font-title-drive">
        新增出库单
      </div>
        <setting-out-store></setting-out-store>
    </div>

  </div>
</template>
<script>
import {handleStoreList} from "@/utils/commonHandle";
import {listShelf} from "@/api/business/storage";
import SelectPage from "@/components/SelectPage"
import settingOutStore from "./component/settingOutStore";

export default {
  components:{settingOutStore},
}
</script>
<style lang="scss" scoped>
.search{
  padding: 0 15px;
  font-size: 14px;
  color: #606266;
  margin-bottom: 22px;
}
.container-component{
  padding: 20px 0!important;
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
