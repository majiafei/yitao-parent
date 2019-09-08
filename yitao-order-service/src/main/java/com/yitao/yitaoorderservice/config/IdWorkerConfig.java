package com.yitao.yitaoorderservice.config;

import com.yitao.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: house
 * @Package: com.yitao.yitaoorderservice.config
 * @ClassName: IdWorkerConfig
 * @Author: majiafei
 * @Description:
 * @Date: 2019/9/8 19:43
 */
@Configuration
public class IdWorkerConfig {

    @Value("${yt.idworker.workerId}")
    private Long workerId;

    @Value("${yt.idworker.datacenterId}")
    private Long datacenterId;

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(workerId, datacenterId);
    }

}
