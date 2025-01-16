package cn.px.tea.modules.ycb.service.com.inter;

import cn.px.tea.modules.ycb.model.SysGoods;

public interface BaseGoodsService {

    boolean syncGoods(SysGoods sysGoods);

    boolean syncGoodsBig(SysGoods sysGoods);
}
