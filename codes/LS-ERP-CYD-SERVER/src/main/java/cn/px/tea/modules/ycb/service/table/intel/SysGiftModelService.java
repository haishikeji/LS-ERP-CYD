package cn.px.tea.modules.ycb.service.table.intel;

import cn.px.tea.modules.ycb.model.SysGiftModel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysGiftModelService extends IService<SysGiftModel> {

    SysGiftModel getByGiftModelId(Long giftModelId);
    SysGiftModel getByGiftModelId(Long giftModelId, Integer status);

    List<SysGiftModel> listByModelId(List<Long> listModelId, Integer status);
}
