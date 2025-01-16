package cn.px.tea.modules.ycb.service.table.intel;

import cn.px.tea.modules.ycb.model.SysGoodsKind;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysGoodsKindService extends IService<SysGoodsKind> {

    List<SysGoodsKind> listByCondition(String goodsKindName, Integer status);

    SysGoodsKind getByPrimaryId(Long goodsKindId);

    int countByName(String goodsKindName);
}
