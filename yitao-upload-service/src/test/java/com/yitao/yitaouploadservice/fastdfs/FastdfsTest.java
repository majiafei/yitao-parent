package com.yitao.yitaouploadservice.fastdfs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.bo.UploadBo;
import com.yitao.common.FastdfsClient;
import com.yitao.upload.service.UploadService;
import com.yitao.yitaouploadservice.YitaoUploadServiceApplicationTests;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.Cleaner;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: house
 * @Package: com.yitao.yitaouploadservice.fastdfs
 * @ClassName: FastdfsTest
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/26 18:16
 */
public class FastdfsTest extends YitaoUploadServiceApplicationTests {

    @Autowired
    private FastdfsClient fastdfsClient;

    @Reference
    private UploadService uploadService;

    @Test
    public void testUpload() throws IOException, MyException {
        ClientGlobal.init("F:\\myproject\\idea\\yitao_02\\yitao-parent\\yitao-upload-service\\src\\main\\resources\\fastdfs\\fdfs_client.conf");
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        String[] paths = storageClient.upload_appender_file("C:\\Users\\pc\\Desktop\\yitao\\8e7968901ac93ef9.png", "png", null);
        System.out.println(Arrays.toString(paths));
    }

    @Test
    public void testFastdfsCleint() throws IOException, MyException {
//        FastdfsClient client = new FastdfsClient("F:\\myproject\\idea\\yitao_02\\yitao-parent\\yitao-upload-service\\src\\main\\resources\\fastdfs\\fdfs_client.conf");
//        FastdfsClient client = new FastdfsClient("classpath:fastdfs/fdfs_client.conf");
//        String path = fastdfsClient.upload("C:\\Users\\pc\\Desktop\\yitao\\8e7968901ac93ef9.png", "png");
//        System.out.println(path);

        File file = new File("C:\\Users\\pc\\Desktop\\yitao\\8e7968901ac93ef9.png");
        byte[] bytes = new byte[1024];
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (fileInputStream.read(bytes) != -1) {
            byteArrayOutputStream.write(bytes);
        }
//        String png = fastdfsClient.upload(byteArrayOutputStream.toByteArray(), "png");
        UploadBo uploadBo = new UploadBo();
        uploadBo.setFileName(".png");
        uploadBo.setContentType("png");
        uploadBo.setBytes(byteArrayOutputStream.toByteArray());
        String upload = uploadService.upload(uploadBo);
        System.out.println(upload);
    }

}
