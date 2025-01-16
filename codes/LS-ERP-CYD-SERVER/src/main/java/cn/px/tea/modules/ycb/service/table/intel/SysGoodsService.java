package cn.px.tea.modules.ycb.service.table.intel;

import cn.px.tea.modules.ycb.model.SysGoods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysGoodsService extends IService<SysGoods> {

    SysGoods getByPrimaryId(Long goodsId);

    List<SysGoods> listByCondition(String goodsCode, String goodsName, Integer goodsTypeSort, Long goodsType, Long goodsKind, String goodsBatch, Long goodsSupplierId, Integer status);

    SysGoods getByPrimary(String goodsName, Long goodsType, Long goodsKind, Long goodsUnit, Long goodsSupplierId, String goodsSpecification);

    List<SysGoods> listByGoodsId(List<Long> listGoodsId);

    List<SysGoods> listGoods(Integer goodsType, Integer goodsKind, String select);

    List<SysGoods> listByCondition(String select, Integer status);

    SysGoods getByGoodsCode(String goodsCode);

    List<SysGoods> listByValid();
}
