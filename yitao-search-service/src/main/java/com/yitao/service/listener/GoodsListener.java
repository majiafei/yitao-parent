package com.yitao.service.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.search.service.SearchService;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 *     监听商品的增加删除和修改
 * </p>
 * @ClassName: GoodsListener
 * @Auther: admin
 * @Date: 2019/7/1 17:41
 * @Description:
 */
@Component
@Log4j2
public class GoodsListener {

    @Reference(check = false)
    private SearchService searchService;

    /**
     * 监听商品的添加和修改
     * @param spuId
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "yt.item.instert.queue", durable = "true"),
                        exchange = @Exchange(name = "yt.item.exchange", type = ExchangeTypes.TOPIC,
                        ignoreDeclarationExceptions = "true"), key = {"item.insert", "item.update"}))
    public void listenerGoodsInsterOrUpdate(Long spuId) {
        if (log.isDebugEnabled()) {
            log.debug("将新增加或者更新的商品同步到索引库");
        }
        searchService.insertOrUpdateGoods(spuId);
    }

}
