package com.yitao.yitaouploadservice.config;

import com.yitao.common.FastdfsClient;
import com.yitao.common.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.apache.zookeeper.Op;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Package: com.yitao.yitaouploadservice.config
 * @ClassName: FastdfsClientConfig
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/26 21:05
 */

@Configuration
@Log4j2
public class FastdfsClientConfig {

    @Autowired
    private UploadProerties uploadProerties;

    @Bean
    public FastdfsClient fastdfsClient() throws IOException, MyException {
        FastdfsClient fastdfsClient = null;
        try {
            fastdfsClient = new FastdfsClient(uploadProerties.getConfigPath());
        } catch (IOException e) {
            log.error("read fastdfs config path failed", e);
            throw new IOException("read fastdfs config path failed");
        }
        return fastdfsClient;
    }

}

