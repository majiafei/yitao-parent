package com.yitao.common;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * fastdfs工具类
 * @Package: com.yitao.common
 * @ClassName: FastdfsClient
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/26 18:28
 */
public class FastdfsClient {

    private Logger logger = LoggerFactory.getLogger(FastdfsClient.class);

    private StorageClient1 storageClient = null;

    public FastdfsClient() {

    }

    public FastdfsClient(String configPath) throws IOException, MyException {
        Assert.notNull(configPath, "configPath can't null");
        if (configPath.startsWith("classpath:")) {
            configPath = configPath.replace("classpath:", this.getClass().getResource("/").getPath());
        }
        // 初始化配置文件
        ClientGlobal.init(configPath);

        // 创建trackerclient
        TrackerClient trackerClient = new TrackerClient();
        // 获取TrackerServcer
        TrackerServer connection = trackerClient.getConnection();

        if (connection == null) {
            throw new IllegalStateException("traker server is null");
        }

        // 声明StorageServer
        StorageServer storageServer = null;
        // 创建TrackerClient
        storageClient = new StorageClient1(connection, storageServer);
    }

    public String upload(byte[] bytes, String extName) {
        return upload(bytes, extName, null);
    }

    /**
     * 上传文件
     * @param bytes 字节数组
     * @param extName 扩展名
     * @param metas 元数据
     * @return
     */
    public String upload(byte[] bytes, String extName, @NotNull NameValuePair[] metas) {
        try {
            return storageClient.upload_file1(bytes, extName, metas);
        } catch (IOException e) {
            logger.error("upload faild", e);
        } catch (MyException e) {
            e.printStackTrace();
            logger.error("upload faild", e);
        } catch (Exception e) {
            logger.error("upload faild", e);
        }

        return null;
    }

    public String upload(String file, String extName) {
        return upload(file, extName, null);
    }

    public String upload(String file, String extName, @NotNull NameValuePair[] metas) {
        try {
            String[] paths = storageClient.upload_file(file, extName, metas);
            if (paths == null) {
                return null;
            }
            return paths[0] + "/" + paths[1];
        } catch (IOException e) {
            logger.error("upload failed", e);
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
            logger.error("upload failed", e);
        } catch (Exception e) {
            logger.error("upload failed", e);
        }

        return null;
    }

    public int deleteFile(String path) throws IOException, MyException {
        return storageClient.delete_file1(path);
    }

}
