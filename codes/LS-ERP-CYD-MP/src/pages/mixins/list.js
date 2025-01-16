import {ref} from 'vue'
import {onShow, onUnload} from "@dcloudio/uni-app";

export const listStorage = function (name) {
    let isScroll = ref(false)
    onShow(()=>{
        if (isScroll.value) {
            uni.pageScrollTo({scrollTop: 0})
            isScroll.value = false
        }
    })

    function on(callback) {
        uni.$on(name,({type,id}={type:'add',id: null})=>{
            let reload = false
            if (type === 'edit') {
                reload = false
            }else if (type === 'add') {
                isScroll.value = true
                reload = true
            }else if (type === 'cancel') {
                reload = false
            }else if (type === 'del'){
                reload = false
            }
            callback(reload,id,partialReplace)
        })

        /**
         * 下标替换无需赋值
         * @param list 列表
         * @param listRep 替换列表
         * @param field 替换字段
         */
        function partialReplace(list,listRep,field,size){
            // if (!list || list.lenght < 1) {
            //     return
            // }
            // // 当前列表不存在数量
            // let keys = {};
            // listRep.forEach(item=>{
            //     keys[item[field]] = item
            //     list.map(child=>{
            //         if (child[field] === item[field]) {
            //             child = Object.assign(child,{...item})
            //             delete keys[item[field]]
            //         }
            //         return child
            //     })
            // });
            // // 往后追加
            // if (Object.keys(keys).length > 0) {
            //     for (let key in keys) {
            //         list.push(keys[key])
            //     }
            // }
            let dict = {}
            listRep.forEach(item=>{
                dict[item[field]] = item
            })
            let indexs = []
            list.map((item,index)=>{
                if (list.length - (index+1) < size){ // 仅更新替换数据列
                    let info = dict[item[field]]
                    if (info) { // 合并信息
                        delete dict[item[field]]
                        item = Object.assign(item,{...info})
                    } else { // 记录不存数据
                        indexs.push(index)
                    }
                }
                return item
            })
            indexs.sort((a,b)=>b-a)
            // 删除不存在数据
            indexs.forEach(index=>{
                list.splice(index,1)
            })
            // 最加新数据
            for (let dictKey in dict) {
                list.push(dict[dictKey])
            }
        }
    }

    /**
     * 获取数据页
     * @param pageSize
     * @param field
     * @param id
     * @param list
     * @returns {number}
     */
    function getPage(pageSize,field,id,list) {
        let total = 0
        let pageNum = 1
        list.forEach((item,index)=>{
            if (item[field] == id) {
                total = index
            }
        })
        // 统计0为特殊
        if (total !== 0 && pageSize > 0) {
            let remainder = total % pageSize
            let intNum = Math.floor(total / pageSize)
            pageNum = intNum + (remainder >0 ? 1 : 0)
        }
        let spliceIndex = pageNum
        // 删除当前行
        list.splice(spliceIndex*pageSize,pageSize)
        return pageNum
    }

    onUnload(()=>{
        uni.$off(name)
    })

   return {on,getPage}
};