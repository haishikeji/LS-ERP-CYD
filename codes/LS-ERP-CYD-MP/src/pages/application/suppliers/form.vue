<template>
  <view class="container">
    <view class="form">
      <u-form labelPosition="left"
               :model="form"
               :rules="rules"
               :error-type="['toast']"
               label-width="auto"
               :label-style="{...formLabelStyle}"
               ref="formRef">
        <view class="form-group">
          <u-form-item :borderBottom="false" label="供应商图标" v-if="pageType !== 'show'">
<!--            <u-upload-->
<!--                :key="key"-->
<!--                @afterRead="afterRead"-->
<!--                @delete="deletePic"-->
<!--                :deletable="edit"-->
<!--                :disabled="!edit"-->
<!--                accept="image"-->
<!--                width="75px"-->
<!--                height="75px"-->
<!--                :maxCount="1"-->
<!--                :fileList="fileList"-->
<!--            >-->
<!--              <image-->
<!--                  :src="image.plugIcon.upLoadphotoIcon"-->
<!--                  :style="{ width: '75px', height: '75px' }"-->
<!--                  mode="widthFix"></image>-->
<!--            </u-upload>-->
            <u-upload ref="uUpload"
                      :auto-upload="false"
                      class="form-upload"
                      :maxCount="1"
                      width="150rpx"
                      height="150rpx"
                      @on-remove="deletePic"
                      @on-choose-complete="afterRead"
                      :file-list="fileList"
            >
            </u-upload>
          </u-form-item>

          <view class="goodsLogo" v-else>
            <view class="title-text">
              <text>供应商图标</text>
            </view>
<!--            :src=" getViewImage() + form.goodsSupplierLogo + fileViewAfter"-->
            <u-avatar
                class="avatar-icon"
                :text="form.goodsSupplierLogo ? '' : '商品'"
                fontSize="18"
                :src=" getViewImage() + form.goodsSupplierLogo"
                size="150"
                mode="square"
                randomBgColor
            ></u-avatar>
          </view>

        </view>
        <view class="form-group">
          <u-form-item prop="goodsSupplierName" :borderBottom="true" label="供应商名称" :required="!!edit">
            <u-input v-model="form.goodsSupplierName"
                     :border="false"
                     inputAlign="right"
                     :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                     fontSize="15px"
                     disabledColor="#ffffff"
                     :disabled="!edit"
                     :placeholder="pageType !== 'show' ? '请输入': '未填写'">
            </u-input>
          </u-form-item>

          <u-form-item prop="goodsSupplierBrand" :borderBottom="true" label="供应商所属品牌">
            <u-input v-model="form.goodsSupplierBrand"
                     :border="false"
                     inputAlign="right"
                     :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                     fontSize="15px"
                     disabledColor="#ffffff"
                     :disabled="!edit"
                     :placeholder="pageType !== 'show' ? '请输入': '未填写'">
            </u-input>
          </u-form-item>

          <u-form-item prop="goodsSupplierAddr" :borderBottom="true" label="供应商地址" class="custon-item">
<!--            <u-input v-model="form.addr"-->
<!--                     :border="false"-->
<!--                     inputAlign="right"-->
<!--                     :placeholderStyle="{...formPlaceHolderStyle}.toString()"-->
<!--                     fontSize="15px"-->
<!--                     disabledColor="#ffffff"-->
<!--                     :disabled="!edit"-->
<!--                     placeholder="请选择">-->
<!--            </u-input>-->
            <custom-multpicker
                :key="statusName"
                idField="code"
                nameField="name"
                parentField="parentCode"
                :disabled="!edit"
                :selected="form.addrCode"
                :placeholder="pageType !== 'show' ? '请选择': '未填写'"
                @confirm="cityChange"
            ></custom-multpicker>
            <template #right>
              <view class="icon" v-if="pageType !== 'show'">
                <u-icon class="arrow-right" name="arrow-right" color="#999999" size="18"></u-icon>
              </view>
            </template>
          </u-form-item>

          <u-form-item prop="goodsSupplierAddr" :borderBottom="true" label="详细地址">
            <u-input v-model="form.addrDetailed"
                     :border="false"
                     inputAlign="right"
                     :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                     @input="addrChange"
                     fontSize="15px"
                     disabledColor="#ffffff"
                     :disabled="!edit"
                     :placeholder="pageType !== 'show' ? '请输入': '未填写'">
            </u-input>
          </u-form-item>

          <u-form-item prop="sort" :borderBottom="true" label="排序号">
            <u-input v-model="form.sort"
                     :border="false"
                     inputAlign="right"
                     :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                     fontSize="15px"
                     disabledColor="#ffffff"
                     :disabled="!edit"
                     :placeholder="pageType !== 'show' ? '请输入': '未填写'">
            </u-input>
          </u-form-item>

          <u-form-item prop="remark" :borderBottom="false" label="备注">
            <u-input v-model="form.remark"
                     :border="false"
                     inputAlign="right"
                     :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                     fontSize="15px"
                     disabledColor="#ffffff"
                     :disabled="!edit"
                     :placeholder="pageType !== 'show' ? '请输入': '未填写'">
            </u-input>
          </u-form-item>
        </view>

        <view class="form-group" v-if="pageType == 'show'">
          <u-form-item :borderBottom="true" label="供应商状态">
            <u-input v-model="statusName"
                     :border="false"
                     inputAlign="right"
                     :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                     fontSize="15px"
                     color="#8B6445"
                     disabledColor="#ffffff"
                     :disabled="!edit">
            </u-input>
          </u-form-item>

<!--          <u-form-item prop="createTime" :borderBottom="true" label="加入时间">-->
<!--            <u-input v-model="form.createTime"-->
<!--                     :border="false"-->
<!--                     inputAlign="right"-->
<!--                     fontSize="15px"-->
<!--                     :disabled="!edit"-->
<!--                     disabledColor="#ffffff">-->
<!--            </u-input>-->
<!--          </u-form-item>-->
        </view>

          <view class="form-group" v-if="!edit">
              <u-form-item prop="goodsSupplierArea"
                           :borderBottom="true"
                           label="生产基地"
                           class="custom-input">
                  <view class="form-item-inner" @click="toSetFactoryAddr">
                      <view class="input-outer">
                          <view class="input__outer">
                              <view class="not-input">
                                  <view class="not-input__inner">
                                      <text class='not-input__placeholder'>
                                          配置生产基地
                                      </text>
                                  </view>
                              </view>
                              <view>
                                  <view>
                                      <u-icon name="arrow-right" size="20px"></u-icon>
                                  </view>
                              </view>
                          </view>
                      </view>
                  </view>
                  <!--            <u-input v-model="form.goodsSupplierArea"-->
                  <!--                     :border="false"-->
                  <!--                     inputAlign="right"-->
                  <!--                     :placeholderStyle="{...formPlaceHolderStyle}.toString()"-->
                  <!--                     fontSize="28rpx"-->
                  <!--                     color="#8B6445"-->
                  <!--                     disabledColor="#ffffff"-->
                  <!--                     disabled-->
                  <!--                     placeholder="请选择默认生产基地">-->
                  <!--            </u-input>-->
              </u-form-item>
          </view>
      </u-form>
    </view>
    <view class="footer1 footer"  v-if="edit">
      <u-button
          type="primary"
          :customStyle="{...btnStyle1,}"
          @click="submit">
        提交
      </u-button>
    </view>

    <view class="footer2 footer" v-else-if="pageType == 'show' && statusName !== '删除'">
      <view class="btn1 btn" v-if="statusName !== '禁用'">
        <u-button
            :plain="true"
            @click="supplierStop(goodsSupplierId)"
            class="content-button"
            :customStyle="{...btnStyle2,}">
          <text class="content-button-text">禁用</text>
        </u-button>
      </view>
      <view class="btn2 btn" v-if="statusName !== '正常'">
        <u-button
            :plain="true"
            @click="supplierOpen(goodsSupplierId)"
            class="content-button"
            :customStyle="{...btnStyle2,}">
          <text class="content-button-text">启用</text>
        </u-button>
      </view>
      <view class="btn0 btn" v-if="statusName !== '删除'">
        <u-button
            :plain="true"
            @click="supplierDel(goodsSupplierId)"
            class="content-button"
            :customStyle="{...btnStyle2,}">
          <text class="content-button-text">删除</text>
        </u-button>
      </view>
      <view class="btn3 btn">
        <u-button
            :plain="true"
            @click="toUpdate(goodsSupplierId)"
            class="content-button"
            :customStyle="{...btnStyle2,}">
          <text class="content-button-text">修改</text>
        </u-button>
      </view>
    </view>

    <u-modal
        :showCancelButton="true"
        v-model="show"
        :title="title"
        :content='content'
        @confirm="confirmModal"
        @cancel="releaseModal"
    ></u-modal>
  </view>
</template>

<script setup>
import {ref, reactive, watch, nextTick} from 'vue'
  import {useFormStyle} from '@/pages/index/mixins'
  import { goodsStatus } from "@/lib/system/dict";
  import {onLoad, onReady, onShow} from "@dcloudio/uni-app";
  import http from "../../../lib/request";
  import dayjs from 'dayjs'
  import {getViewImage,fileViewAfter} from '@/env'
  import {tempUpload} from '@/lib/system/control'
  import {routeParams} from "@/router";
  import {getUrlWithParams} from "@/util/url";
  import {storage} from "../../../lib/system/storage";
import {debounce} from '@/util/common'

  const {formLabelStyle, formPlaceHolderStyle} = useFormStyle()
  let btnStyle1 = reactive({borderRadius: '16rpx'})
  let btnStyle2 = reactive({borderRadius: '10rpx',border: '1rpx solid #CCCCCC',background: '#FFFFFF'})

  const fileList = ref([])

  const form = reactive({
    goodsSupplierLogo: '', // 供应商图标
    goodsSupplierName: '', // 供应商名称
    // py: '', // 简拼码
    goodsSupplierBrand: '', // 供应商所属品牌
    goodsSupplierAddr:'', // 地址
    addrStandard: '', // 规范住址
    addrCode: [],
    goodsSupplierArea: '', // 生产基地
    addrDetailed: '', // 详细住址
    status: '', // 状态
    createTime: '',
    sort: "" , // 排序号
    remark: "" // 备注
  })

  const rules = reactive({
    // goodsSupplierLogo: {
    //   type: 'string',
    //   required: true,
    //   message: '上传图片',
    //   trigger: ['blur','change']
    // },
    goodsSupplierName: {
      type: 'string',
      required: true,
      message: '请输入供应商名称',
      trigger: ['blur', 'change']
    },
    // goodsSupplierBrand: {
    //   type: 'string',
    //   required: true,
    //   message: '请输入供应商所属品牌',
    //   trigger: ['blur','change']
    // },
    // py: {
    //   type: 'string',
    //   required: true,
    //   message: '请输入供应商简拼码',
    //   trigger: ['blur', 'change']
    // },
    // goodsSupplierAddr: {
    //   validator(rule, value, callback){
    //     if (form.goodsSupplierAddr=='') {
    //       callback(new Error('请选择地址'));
    //     }
    //     const addr = JSON.parse(form.goodsSupplierAddr)
    //     if (!addr.addrStandard) {
    //       callback(new Error('请选择地址'));
    //     }else if (!addr.addrDetailed){
    //       callback(new Error('请输入详细地址'));
    //     }else{
    //       callback();
    //     }
    //   },
    // }
  })

  let area = ref('')
  let dataId = ref('')

  let formRef = ref(null)

  let key = ref(null)
  let statusName = ref('')
  let goodsSupplierId = ref('')
  let edit = ref(null)
  let pageType = ref('')

  //显示
  let show = ref(false)
  // 标题
  let title = ref('')
  // 内容
  let content = ref('')
  // 类型
  let modalType = ref('')
  // 选中对象
  let modalCurrent = ref({})

  // 地区发生变化 label value
  function cityChange(confirm) {
    form.addrStandard = confirm.label
    form.addrCode = confirm.value
    form.goodsSupplierAddr = JSON.stringify({
      addrStandard: form.addrStandard,
      addrDetailed: form.addrDetailed,
      addrCode: form.addrCode
    })
  }
  // 地区内容值
  function addrChange() {
    setTimeout(()=>{
      form.goodsSupplierAddr = JSON.stringify({
        addrStandard: form.addrStandard,
        addrDetailed: form.addrDetailed,
        addrCode: form.addrCode
      })
    },0)
  }
  // 删除图片
  function deletePic(event) {
    fileList.value.splice(0, fileList.value.length)
   form.goodsSupplierLogo = ''
  }
  // 新增图片
  async function afterRead(event) {
    // 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
    let lists = [].concat(event[0].file)
    lists.map((item) => {
      // fileList.value.push({
      //   ...item,
      //   status: 'uploading',
      //   message: '上传中'
      // }
      item.url = item.path
      return item
    })
    let fileListLen = fileList.value.length
    uni.showLoading({
      title: '上传中...'
    })
    try {
      for (let i = 0; i < lists.length; i++) {
        let item = lists[i]
        tempUpload(lists[i].url).then(url => {
          // fileList.value.splice(fileListLen - 1, 1, Object.assign(item, {
          //   status: 'success',
          //   message: '',
          //   url: getViewImage() + url + fileViewAfter
          // }))
          // key.value = Date.now()
          fileList.value.splice(fileListLen-1,1, {
              status: 'success',
              message: '',
              // url: getViewImage() + url + fileViewAfter
              url: getViewImage() + url
            })

          if (typeof url === "string" && url !== '') {
            uni.hideLoading()
            form.goodsSupplierLogo = url
            // onEditAvatar()
          }
          key.value = Date.now()
        })
      }
    } catch (e){
      uni.hideLoading()
    }
  }

  // 供应商禁用
  function supplierStop(item,type='confirm') {
    if (type === 'confirm') {
      modalType.value = 'stop'
      confirmShow('供应商禁用提示',`供应商：${form.goodsSupplierName}，禁用此供应商，是否确认？`,item)
      return
    }
    http('supplier.setStatusGoodsSupplier',{primaryId: goodsSupplierId.value,status:0},'供应商禁用中...').then(res=>{
      if (res.code === 200) {
        toastShow()
        uni.showToast({
          title: '供应商禁用成功',
          icon: "success"
        })
        setTimeout(()=>{
          uni.navigateBack()
        },150)
      }
    })
  }

  // 供应商启用
  function supplierOpen(item,type='confirm') {
    if (type === 'confirm') {
      modalType.value = 'open'
      confirmShow('供应商启用提示',`供应商：${form.goodsSupplierName}，启用此供应商，是否确认？`,item)
      return
    }
    http('supplier.setStatusGoodsSupplier',{primaryId: goodsSupplierId.value,status:1},'供应商启用中...').then(res=>{
      if (res.code === 200) {
        toastShow()
        uni.showToast({
          title: '供应启用成功',
          icon: "success"
        })
        setTimeout(()=>{
          uni.navigateBack()
        },150)
      }
    })
  }

  // 供应商删除
  function supplierDel(item,type='confirm') {
    if (type === 'confirm') {
      modalType.value = 'delete'
      confirmShow('供应商删除提示',`供应商：${form.goodsSupplierName}，删除此供应商，是否确认？`,item)
      return
    }
    http('supplier.setStatusGoodsSupplier',{primaryId: goodsSupplierId.value,status:2},'供应商删除中...').then(res=>{
      if (res.code === 200) {
        toastShow()
        uni.showToast({
          title: '供应删除成功',
          icon: "success"
        })
        setTimeout(()=>{
          uni.navigateBack()
        },150)
      }
    })
  }

  // 修改资料
  function toUpdate(id) {
    toForm(id,'edit')
  }

  // 跳转设置默认生产基地
  function toSetFactoryAddr() {
    uni.navigateTo({
      url: getUrlWithParams('/pages/application/suppliers/factory-addr/index',{
        goodsSupplierId:goodsSupplierId.value
      })
    })
  }

  // 跳转
  function toForm(id,type) {
    const url  = getUrlWithParams('/pages/application/suppliers/form',{
      type,
      id
    })

    uni.navigateTo({url})
  }

  // 提示框显示
  function confirmShow(name,context,item) {
    show.value = true
    title.value = name
    content.value = context
    modalCurrent = Object.assign(modalCurrent,{...item})
  }
  // 提示框隐藏
  // function confirmModal() {
  const confirmModal = debounce(300,()=>{
    switch (modalType.value) {
      case "stop":
        supplierStop(modalCurrent,'action')
        break;
      case "open":
        supplierOpen(modalCurrent, 'action')
        break;
      case "delete":
        supplierDel(modalCurrent, 'action')
        break;
    }
    releaseModal()
  })
  // 释放
  function releaseModal() {
    show.value = false
    modalType.value = ''
    modalCurrent = Object.assign({})
  }

  // 显示提示
  function toastShow() {
    uni.showToast({
      duration: 500,
      icon: 'none',
      mask: true,
      title: '操作成功'
    })
  }

  // 提交
  // function submit() {
  const submit = debounce(300,()=>{
    formRef.value.validate(vaild=>{
      if (vaild) {
        if (goodsSupplierId.value!=='') {
          updateSupplier();
        } else {
          addSupplier();
        }
      }

    })
  })

  // 新增供应商
  function addSupplier() {
    // let dataDetial = {
    //   goodsSupplierName : form.goodsSupplierName
    // }
    http('supplier.addSupplier', {...form},'供应商新增中...').then(res=>{
      if (res.code == 200) {
        uni.showToast({
          title: '供应商新增完成',
          icon: "success"
        })
        setTimeout(()=>{
          uni.navigateBack()
        },150)
      }
    })
  }
  function updateSupplier() {
    http('supplier.editSupplier',{...form,goodsSupplierId: goodsSupplierId.value},'更新供应商中...').then(res=>{
      if (res.code == 200) {
        uni.showToast({
          title: '供应商资料更新成功',
          icon: "success"
        })
        setTimeout(()=>{
          uni.navigateBack()
        },150)
      }
    });
  }

  // 获取供应商信息
  function getSupplierInfo(id,callback = false) {
    fileList.value = []
    http('supplier.getSupplierInfo',{primaryId: id},'供应商信息读取中...').then(res=>{
      if (res.code === 200) {
        if (res.data.goodsSupplierLogo) {
          form.goodsSupplierLogo = res.data.goodsSupplierLogo;
          // fileList.value.push({url: getViewImage() + form.goodsSupplierLogo + fileViewAfter});
          fileList.value.push({url: getViewImage() + form.goodsSupplierLogo});
        }
        key.value = Date.now()
        form.goodsSupplierName = res.data.goodsSupplierName || ''
        form.goodsSupplierBrand = res.data.goodsSupplierBrand || ''
        // form.py = res.data.py || ''
        // form.addr = res.data.addr || ''
        // form.detialAddr = res.data.detialAddr || ''
        // form.goodsSupplierArea = area.value || ''
        // form.goodsSupplierArea  = res.data.goodsSupplierArea || ''
        form.createTime = dayjs(res.data.createTime).format('YYYY-MM-DD HH:mm:ss') || ''
        form.sort = res.data.sort + '' || ''
        form.remark = res.data.remark || ''
        if (res.data.goodsSupplierAddr) {
          form.goodsSupplierAddr = res.data.goodsSupplierAddr

          const addr = JSON.parse(res.data.goodsSupplierAddr);
          form.addrDetailed = addr.addrDetailed;
          form.addrStandard = addr.addrStandard;
          form.addrCode = addr.addrCode
        } else {
          form.addrDetailed = '';
          form.addrStandard = '';
        }
        statusName.value = goodsStatus[res.data.status]['name'];
        // if (res.data.goodsSupplierArea !== '') {
        //   const addr = JSON.parse(JSON.stringify(res.data.goodsSupplierArea))
        //   form.goodsSupplierArea = addr.addrCode[2].slice(0,2)
        // }
        if (callback) {
          callback()
        }
      }
    })
  }

  onLoad((params)=>{
    storage.clearData()
    if (params) {
        goodsSupplierId.value = params.id
        console.log(params, 'params')
        switch (params.type) {
            case 'show':
                uni.setNavigationBarTitle({
                    title: '供应商详情'
                });
                getSupplierInfo(params.id, () => {
                    edit.value = false
                    pageType.value = 'show'

                })
                break
            case 'edit':
                uni.setNavigationBarTitle({
                    title: '编辑供应商资料'
                });
                getSupplierInfo(params.id, () => {
                    edit.value = true
                    pageType.value = 'edit'

                })
                break
            default:
                uni.setNavigationBarTitle({
                    title: '新增供应商'
                });
                edit.value = true
                pageType.value = 'default'
        }
    }
  })

  // // 页面加载时
  // onShow(()=>{
  //   let params = routeParams()
  //   console.log(params,'params')
  //
  // })

  onReady(()=>{
    setFormRule()
  })

  function setFormRule() {
    formRef.value.setRules(rules);
  }
</script>

<style lang="scss" scoped>
    @import "~@/pages/index/form.scss";

  .container {
    // #ifdef H5 || MP-WEIXIN
    min-height: calc(100vh - var(--window-top));
    // #endif
    // #ifdef APP-PLUS
    min-height: 100vh;
    // #endif
    background: #EDEFF3;
    padding: 20rpx 20rpx 30rpx;
    position: relative;

    .form {
      padding-bottom: 120rpx;

      .form-group {
        background: #ffffff;
        border-radius: 20rpx;
        padding: 0 30rpx;
        margin-bottom: 20rpx;
      }
    }

    .goodsLogo {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
      height: 190rpx;

      .title-text {
        font-size: 15px;
        font-family: Hiragino Sans GB;
        font-weight: normal;
        color: #666666;
        line-height: 40rpx;
      }

      .supplier-code {
        height: 100rpx;
      }
    }

    .form-group-item {
      //border-bottom: 1rpx solid #EEEEEE;

      .form-group-item-header {
        display: flex;
        justify-content: space-between;
        border-bottom: 1rpx solid #EEEEEE;
        .item-header-title {
          font-weight: bold;
          padding: 30rpx 0;
        }
        .item-header-option {
          padding: 29rpx 0 29rpx 29rpx;
          color: #999999;
          font-size: 35rpx;
          display: flex;
          align-items: center;
          .item-option-del {
            font-size: 25rpx;
          }
        }
        .item-header-option:active {
          font-size: 40rpx;
          .item-option-del:active {
            font-size: 30rpx;
          }
        }
      }
    }

    .add-btn {
      margin-bottom: 30rpx;
    }

    .footer {
      position: fixed;
      width: 100%;
      /*display: flex;*/
      /*align-items: center;*/
      /*height: 110rpx;*/
      /*padding: 10rpx 30rpx;*/
      // #ifndef MP-WEIXIN
      height: 130rpx;
      line-height: 130rpx;
      align-items: center;
      padding: 20rpx 30rpx 30rpx;
      display: flex;
      // #endif

      // #ifdef MP-WEIXIN
      padding:20rpx 30rpx 30rpx;
      // #endif
      bottom: 0;
      left: 0;
      z-index: 10;
      background: #ffffff;
      box-sizing: border-box;
    }

    .footer2 {
      position: fixed;
      display: inline-flex;
      justify-content: flex-end;
      align-items: center;

      .btn {
        margin-left: 24rpx;
      }

      .content-button {
        width: 180rpx;
        height: 80rpx;
        font-size: 15px;
        font-family: Hiragino Sans GB;
        font-weight: normal;
        color: #202020;
        line-height: 36rpx;
      }

      .btn1 {
        margin-left: 0;
      }
    }
  }

  .custon-item ::v-deep .u-form-item__body__right__content__slot{
    justify-content: flex-end;
    //line-height: ;
  }
    ::v-deep .u-upload__wrap {
        justify-content: flex-end;
    }
    .not-input__placeholder{
        color: $uni-color-primary!important;
        font-size: 15px;
    }
</style>
