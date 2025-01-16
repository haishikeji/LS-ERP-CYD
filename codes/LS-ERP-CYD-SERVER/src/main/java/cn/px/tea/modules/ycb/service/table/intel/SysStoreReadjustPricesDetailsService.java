package cn.px.tea.modules.ycb.service.table.intel;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.px.tea.modules.ycb.model.SysStoreReadjustPricesDetails;

import java.util.List;

public interface SysStoreReadjustPricesDetailsService extends IService<SysStoreReadjustPricesDetails> {
    /**
     * 调价单详情列表
     */
    List<SysStoreReadjustPricesDetails> listReadjustPricesDetails(Long readjustId);

    List<SysStoreReadjustPricesDetails> listByInventoryIds(List<Long> listReadjustId);
}
