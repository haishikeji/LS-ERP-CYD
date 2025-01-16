<template>
    <view class="container">
        <view class="form">
            <u-form labelPosition="left"
                     :model="form"
                     ref="formRef"
                     :rules="rules"
                    :error-type="['toast']"
                     label-width="auto"
                     :labelStyle="{...formLabelStyle}">
                <view class="form-group">
                    <u-form-item prop="cardName" :borderBottom="true" label="店铺图片">
                      <u-upload ref="Upload"
                                :auto-upload="false"
                                class="form-upload"
                                :maxCount="1"
                                width="150rpx"
                                height="150rpx"
                                @on-remove="removeImage"
                                @on-choose-complete="afterRead"
                                :file-list="fileList"
                      >
                      </u-upload>
                    </u-form-item>
                </view>
                <view class="form-group">
                    <u-form-item prop="storeName" :required="true" :borderBottom="true" label="店铺名称">
                        <u-input v-model="form.storeName"
                                 :border="false"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 disabledColor="#ffffff"
                                 placeholder="请输入">
                        </u-input>
                    </u-form-item>
                    <u-form-item
                                 prop="businessHours"
                                 :borderBottom="true"
                                 label="营业时间">
                        <u-input v-model="form.businessHours"
                                 :border="false"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 :disabled="true"
                                 disabledColor="#ffffff"
                                 placeholder="请输入">
                        </u-input>
                        <template #right>
                            <u-icon
                                name="arrow-right"
                            ></u-icon>
                        </template>
                      <view class="date-after" @click="openRangeTime"></view>
                    </u-form-item>
<!--                    <u-form-item prop="businessHours" :borderBottom="true" label="工作日" v-if="!edit">-->
<!--                        <u-input v-model="form.day"-->
<!--                                 :border="false"-->
<!--                                 inputAlign="right"-->
<!--                                 :customStyle="{...inputCustom}"-->
<!--                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"-->
<!--                                 fontSize="32rpx"-->
<!--                                 disabledColor="#ffffff"-->
<!--                                 placeholder="请输入">-->
<!--                        </u-input>-->
<!--                    </u-form-item>-->
                    <u-form-item prop="addr" :borderBottom="true" label="店铺地址"  class="custon-item">
                        <custom-multpicker
                            idField="code"
                            nameField="name"
                            parentField="parentCode"
                            :selected="form.addrCode"
                            @confirm="cityChange"
                        ></custom-multpicker>
<!--                        <u-input v-model="form.addrStandard"-->
<!--                                 :border="false"-->
<!--                                 inputAlign="right"-->
<!--                                 :customStyle="{...inputCustom}"-->
<!--                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"-->
<!--                                 fontSize="32rpx"-->
<!--                                 :disabled="true"-->
<!--                                 disabledColor="#ffffff"-->
<!--                                 placeholder="请选择">-->
<!--                        </u-input>-->
                        <template #right>
                            <u-icon
                                name="arrow-right"
                            ></u-icon>
                        </template>
                    </u-form-item>
                    <u-form-item prop="addr" :borderBottom="true" label="详细地址">
                        <u-input v-model="form.addrDetailed"
                                 :border="false"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 @input="addrChange"
                                 disabledColor="#ffffff"
                                 placeholder="请输入">
                        </u-input>
                    </u-form-item>
                </view>
                <view v-if="!edit" class="form-group">
                    <u-form-item prop="staffId"
                                 :borderBottom="true"
                                 label="店长名称">
                        <u-input v-model="form.staffIdName"
                                 :border="false"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 placeholder="请输入">
                        </u-input>
                    </u-form-item>
                    <u-form-item prop="phone" :borderBottom="true" label="联系电话">
                        <u-input v-model="form.phone"
                                 :border="false"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 placeholder="请输入">
                        </u-input>
                    </u-form-item>
                </view>
                <view v-if="edit" class="form-group">
                    <u-form-item prop="staffId"
                                 :borderBottom="true"
                                 label="店长名称">
                        <u-input v-model="form.staffIdName"
                                 :border="false"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 disabledColor="#ffffff"
                                 placeholder="请输入">
                        </u-input>
                        <template #right>
                            <u-icon
                                name="arrow-right"
                            ></u-icon>
                          <view class="date-after" @click="staffShow = true"></view>
                        </template>
                    </u-form-item>
                    <u-form-item prop="phone" :borderBottom="true" label="联系电话">
                        <u-input v-model="form.phone"
                                 :border="false"
                                 inputAlign="right"
                                 :customStyle="{...inputCustom}"
                                 :placeholderStyle="{...formPlaceHolderStyle}.toString()"
                                 fontSize="32rpx"
                                 disabledColor="#ffffff"
                                 placeholder="请输入">
                        </u-input>
                    </u-form-item>
                </view>
            </u-form>
        </view>

        <!-- 员工选择-->
        <custom-employee-select
            v-model="staffShow"
            :showSelect="false"
            :showChange="true"
            :shopId="shopId"
            @selected="select"
        ></custom-employee-select>

<!--        <custom-popup-select-->
<!--            v-model="staffShow"-->
<!--            placeholder="输入员工名称或员工号搜索"-->
<!--            @select="select"-->
<!--            @search="search"-->
<!--            @scrollBottom="scrollBottom"-->
<!--            :list="staffLists">-->
<!--            <template  #item="{item}">-->
<!--                <image class="popup-icon" :src="viewImage+item.avatar+fileViewAfter"></image>-->
<!--                <view class="popup-content">-->
<!--                    <view class="popup-content-name">{{ item.cardName }}</view>-->
<!--                    <view class="popup-content-phone">{{ item.phone }}</view>-->
<!--                    <view class="popup-content-number">{{ item.empNo }}</view>-->
<!--                </view>-->
<!--            </template>-->
<!--        </custom-popup-select>-->

        <custom-range-time
            ref="timeslot"
            title='预定时间段'
            @confirm="confirmRange"
        />

        <u-modal v-model="modalShow"
            @confirm="confirmTip"
            @cancel="cancelTip"
            :showCancelButton="true"
            :title="title + '提示'"
            :content="'当前为'+title+'操作，确认要继续操作？'"></u-modal>

        <view class="footer">
            <u-button
                @click="modalChange"
                type="primary"
                :customStyle="{borderRadius: '10rpx'}">
                <view class="primary-text">
                    <text v-if="!edit">提交</text>
                    <text v-else>修改</text>
                </view>
            </u-button>
        </view>
    </view>
</template>
<script setup lang="ts">
    import {viewImage,fileViewAfter} from '@/env'
    import {useFormStyle,userBasePage} from '@/pages/index/mixins'
    import {tempUpload} from '@/lib/system/control'
    import {onLoad, onReady, onShow} from "@dcloudio/uni-app";
    import {reactive,ref} from 'vue'
    import http from "@/lib/request";
    import {routeParams} from "@/router";
    import {cityFormatter} from "@/lib/system/control";
    import {debounce} from "@/util/common";

    const {inputCustom,formLabelStyle,formPlaceHolderStyle} = useFormStyle()
    let  modalShow = ref(false)
    let staffShow = ref(false)

    let formRef = ref(null)
    let timeslot = ref(null)

    // const {baseParams} = userBasePage()
    //
    // const constructorBase = new baseParams()
    // let staffParams = reactive(Object.assign({select: '',post: '2'},{...constructorBase}))


    let title = ref('')
    let edit = ref(true)
    let shopId = ref(null)
    let fileList = ref([])
    // let staffLists = ref([])
    let key = ref(0)

    let form = reactive({
        avatar: '',
        storeName: '',
        businessHours: '',
        day: '',
        addr:'',
        addrDetailed: '',
        addrStandard: '',
        addrCode: [],
        staffId: '',
        staffIdName: '',
        phone: ''
    })

    // let labels = reactive({
    //     staffIdName: ''
    // })

    let rules = reactive({
        storeName: {
            type: 'string',
            required: true,
            message: '请填写店铺名称',
            trigger: ['blur']
        },
        // addr: {
        //     type: 'string',
        //     required: true,
        //     message: '请填写地址',
        //     trigger: ['blur','change']
        // }
        staffIdName: {
            type: 'string',
            required: true,
            message: '请选择店长姓名',
            trigger: ['blur']
        },
        phone:{
            type: 'string',
            required: true,
            message: '请填写店长手机号',
            trigger: ['blur']
        }
    })


    // 删除图片
    function deletePic(event) {
        fileList.value.splice(event.index, 1)
        key.value = Date.now()
    }
    //删除
    function removeImage(){
      fileList.value.splice(0,fileList.value.length)
      form.avatar = ''
    }
    // 新增图片
    async function afterRead(event) {
        // 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
        let lists = [].concat(event[0].file)
        lists.map((item) => {
            // fileList.value.push({
            //     ...item,
            //     url: item.path,
            //     status: 'uploading',
            //     message: '上传中'
            // })
            item.url = item.path
          return item
        })
        let fileListLen = fileList.value.length

        for (let i = 0; i < lists.length; i++) {
            tempUpload(lists[i].url).then(url=>{
              // let itemResult = {
              //   status: 'success',
              //   message: '',
              //   url: viewImage + url + fileViewAfter
              // }
                fileList.value.splice(fileListLen-1,1,{
                    status: 'success',
                    message: '',
                    // url: viewImage + url + fileViewAfter
                    url: viewImage + url
                  })
                if (typeof url === "string") {
                    form.avatar = url
                }
                key.value = Date.now()
            })
        }
    }

    // 地区发生变化 label value
    function cityChange(confirm) {
        form.addrStandard = confirm.label
        form.addrCode = confirm.value
        form.addr = JSON.stringify({
            addrStandard: form.addrStandard,
            addrDetailed: form.addrDetailed,
            addrCode: form.addrCode
        })
        console.log(form.addr, 'addr')
    }

    // 地区内容值
    function addrChange(value) {
        setTimeout(()=>{
            form.addrDetailed = value
            form.addr = JSON.stringify({
                addrStandard: form.addrStandard,
                addrDetailed: form.addrDetailed,
                addrCode: form.addrCode
            })
        },0)
    }

    // 模态框发生变化
    function modalChange() {
        if (edit.value) {
            title.value = '修改';
        } else {
            title.value = '新增';
        }
        formRef.value.validate(vaild=>{
          if (vaild) {
            modalShow.value = true
          }
        })
    }

    // 提示确认
    // function confirmTip() {
    const confirmTip = debounce(300,()=>{
        submit()
        cancelTip()
    })
    // 提示取消
    function cancelTip() {
        modalShow.value = false
    }

    function openRangeTime() {
        timeslot.value.open()
    }

    function confirmRange(value) {
        const start = value.start
        const end = value.end
        form.businessHours = start.hour > end.hour ?
            `${end.hour}:${end.min}~${start.hour}:${start.min}`:
            `${start.hour}:${start.min}~${end.hour}:${end.min}`
    }

    // // 搜索
    // function search(type,value,controlName) {
    //     staffParams.pageNum = 1
    //     staffParams.select = value
    //     staffList(true)
    // }
    // 选中
    function select(item, controlName) {
        form.staffId = item.empId+''
        form.staffIdName = item.cardName
        form.phone = item.phone
    }

    // 滚动
    // function scrollBottom(e,controlName) {
    //     if (isScroll(staffParams.pageNum, staffParams.pageSize, staffParams.total)) {
    //         staffParams.pageNum++;
    //         staffList()
    //     }
    //     // 是否滚动
    //     function isScroll(pageNum,pageSize,total) {
    //         let number = pageNum * pageSize
    //         return total > number
    //     }
    //
    // }

    // // 获取员工列表
    // function staffList(bool = false) {
    //     if (bool) {
    //         staffLists.value = []
    //     }
    //     http('staff.pageEmpAll',staffParams).then(res=>{
    //         if (res.code === 200) {
    //             if (res.rows.length > 0) {
    //                 staffLists.value = [...staffLists.value,...res.rows];
    //             }
    //             staffParams.pageNum = res.pageNum
    //             staffParams.total = res.total
    //         }
    //     });
    // }


    // 提交
    function submit() {
        let vo = {
            "shopName": form.storeName,
            "shopLogo": form.avatar,
            "shopAddr": form.addr,
            "managerId": form.staffId,
            "managerName":form.staffIdName,
            "managerPhone": form.phone,
            "openingHours": form.businessHours
        }
        if (!edit.value) {
            handleAdd(vo);
        } else {
            handleEdit(vo)
        }
    }

    function handleEdit(vo) {
        http('stores.editShow',{shopId: shopId.value,...vo},'店铺修改中...').then(res=>{
            if (res.code === 200) {
                uni.showToast({
                    title: '店铺修改成功',
                    icon: 'none',
                    duration: 200
                })
                setTimeout(()=>{
                    uni.navigateBack()
                })
            }
        })
    }

    // 新增
    function handleAdd(vo) {
        http('stores.addShop',vo,'店铺添加中...').then(res=>{
            if (res.code === 200) {
                uni.showToast({
                    title: '店铺新增成功',
                    icon: 'none',
                    duration: 200
                })
                setTimeout(()=>{
                    uni.navigateBack()
                })
            }
        })
    }

    // 获取info信息
    function handleInfo() {
        http('stores.getShopInfo',{shopId:shopId.value}).then(res=>{
            if (res.code === 200) {
                form.avatar = res.data.shopLogo
                fileList.value.push({url: viewImage + res.data.shopLogo + fileViewAfter})

                form.storeName = res.data.shopName
                form.staffId = res.data.managerId+''
                form.staffIdName = res.data.managerName
                form.phone = res.data.managerPhone
                form.businessHours = res.data.openingHours
                form.addr = res.data.shopAddr

                let vo = cityFormatter(res.data.shopAddr)
                form = Object.assign(form,{...vo})
                key.value = Date.now()
            }
        })
    }

    onLoad((params)=>{
        switch (params.type +'') {
            case 'add':
                uni.setNavigationBarTitle({
                    title: '新增店铺'
                });
                edit.value = false
                break;
            case 'edit':
                uni.setNavigationBarTitle({
                    title: '修改店铺'
                });
                // edit.value = true
                let param = routeParams()
                shopId.value = params.shopId
                if (params) {
                    fileList.value = []
                    handleInfo()
                }
                break;
        }
        // staffList(true)
    })

    onReady(()=>{
      setFormRule()
    })

    function setFormRule() {
      formRef.value.setRules(rules);
    }

    // onShow(()=>{
    //     if (edit.value) {
    //         let params = routeParams()
    //         shopId.value = params.shopId
    //         if (params) {
    //             fileList.value = []
    //             handleInfo()
    //         }
    //     }
    // })
</script>
<style lang="scss" scoped>
    @import "~@/pages/index/form.scss";
    @import "~@/pages/index/popup-select.scss";

    .container{
        padding-bottom: 130rpx;
    }

    .form-upload{
        ::v-deep .u-upload{
            justify-content: flex-end;

            .u-upload__button{
                margin: 0;
                height: 150rpx!important;
                width: 150rpx!important;
            }

            .u-upload__wrap__preview__image{
                margin: 0;
                height: 150rpx!important;
                width: 150rpx!important;
            }
        }
    }
    .custon-item ::v-deep .u-form-item__body__right__content__slot{
        justify-content: flex-end;
    }
    .custon-item ::v-deep .u-form-item__body__right{
        padding: 20rpx 0;
    }

    .footer {
        box-sizing: border-box;
        width: 100%;
        background: #ffffff;

        // #ifndef MP-WEIXIN
        height: 130rpx;
        line-height: 130rpx;
        align-items: center;
        padding: 10rpx 30rpx 30rpx;
        display: flex;
        // #endif

        // #ifdef MP-WEIXIN
        padding: 10rpx 30rpx 30rpx;
        // #endif

        position: fixed;
        z-index: 10;
        left: 0;
        bottom: 0;

        .primary-text{
            display: flex;
            align-items: center;
            text{
                line-height: 1;
                font-family: Hiragino Sans GB;
                font-weight: normal;
                color: #FFFFFF;
                font-size: 32rpx;
                margin-left: 16rpx;
            }
        }
    }
</style>
