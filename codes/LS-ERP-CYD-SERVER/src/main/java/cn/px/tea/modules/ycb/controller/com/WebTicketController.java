package cn.px.tea.modules.ycb.controller.com;

import cn.px.tea.common.domain.AjaxResult;
import cn.px.tea.modules.ycb.pojo.bo.base.ShopConsumeInfo;
import cn.px.tea.modules.ycb.pojo.rf.business.OrderPayIdRf;
import cn.px.tea.modules.ycb.pojo.rf.room.RoomUserRf;
import cn.px.tea.modules.ycb.service.base.inter.TicketService;
import cn.px.tea.security.aspect.Log;
import cn.px.tea.security.define.SystemDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 公共-票剧打印
 */
@RestController
@RequestMapping("/web/ticket")
public class WebTicketController {

    @Autowired
    private TicketService ticketService;

    /**
     * 获取付款单票剧
     * @param rf
     */
    @GetMapping("getOrderPay")
    @Log(modelTitle = "公共-票剧打印-获取付款单票剧" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<ShopConsumeInfo> getOrderPay(@Validated OrderPayIdRf rf) {
        return AjaxResult.success(ticketService.getOrderPay(rf.getOrderPayId()));
    }

    /**
     * 打印付款单票剧
     * @param rf
     */
    @PostMapping("printOrderPay")
    @Log(modelTitle = "公共-票剧打印-打印付款单票剧" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult printOrderPay(@Validated @RequestBody OrderPayIdRf rf) {
        ticketService.printOrderPay(rf.getOrderPayId());
        return AjaxResult.success();
    }

    /**
     * 获取付款单票剧
     * @param rf
     */
    @GetMapping("getRoom")
    @Log(modelTitle = "公共-票剧打印-获取付款单票剧" , interfaceType = SystemDefine.REQUEST_TYPE_GET)
    public AjaxResult<ShopConsumeInfo> getRoom(@Validated RoomUserRf rf) {
        return AjaxResult.success(ticketService.getRoom(rf.getRoomUseId()));
    }

    /**
     * 打印包间/桌子消费单票剧
     * @param rf
     */
    @PostMapping("printRoom")
    @Log(modelTitle = "公共-票剧打印-打印包间/桌子消费单票剧" , interfaceType = SystemDefine.REQUEST_TYPE_POST)
    public AjaxResult printRoom(@Validated @RequestBody RoomUserRf rf) {
        ticketService.printRoom(rf.getRoomUseId());
        return AjaxResult.success();
    }

}
