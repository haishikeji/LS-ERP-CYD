package cn.px.tea.modules.ycb.service.table.intel;

import cn.px.tea.modules.ycb.model.SysStoreGoodsUnpack;
import com.baomidou.mybatisplus.extension.service.IService;

import java.sql.Date;
import java.util.List;

public interface SysStoreGoodsUnpackService extends IService<SysStoreGoodsUnpack> {

    SysStoreGoodsUnpack getByUnpackId(Long unpackId);

    List<SysStoreGoodsUnpack> listByCondition(Long shopId, Long goodsId, String goodsName, Integer type, Date sDate, Date eDate);
}
