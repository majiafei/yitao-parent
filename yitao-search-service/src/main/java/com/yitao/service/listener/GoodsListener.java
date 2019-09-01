package com.yitao.service.listener;

import com.yitao.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: house
 * @Package: com.yitao.service.listener
 * @ClassName: GoodsListener
 * @Author: majiafei
 * @Description:
 * @Date: 2019/7/1 21:57
 */
@Component
public class GoodsListener {

    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "yt.search.insert.queue", durable = "true"),
            exchange = @Exchange(name = "yt.item.exchange",
                    type = ExchangeTypes.TOPIC,
                    ignoreDeclarationExceptions = "true"),
            key = {"item.insert", "item.update"}
    ))
    public void listenInsert(Long id) {
        //监听新增或更新
        if (id != null) {
            System.out.println("处理insert/update消息");
            searchService.insertOrUpdateGoods(id);
        }
    }

}
