package com.yitao.yitaouploadservice.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yitao.bo.UploadBo;
import com.yitao.common.FastdfsClient;
import com.yitao.common.exception.ServiceException;
import com.yitao.upload.service.UploadService;
import com.yitao.yitaouploadservice.config.UploadProerties;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @Package: com.yitao.yitaouploadservice.service.impl
 * @ClassName: UploadServiceImpl
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/26 20:50
 */

@Service
@Log4j2
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FastdfsClient fastdfsClient;

    @Autowired
    private UploadProerties uploadProerties;

    // TODO
    @Override
    public String upload(UploadBo uploadBo, NameValuePair[] metas) {
        return null;
    }

    @Override
    public String upload(UploadBo uploadBo) {
        String fileName = uploadBo.getFileName();

        if (StringUtils.isBlank(fileName)) {
            throw new ServiceException("filename is null or emptry");
        }

        if (!fileName.contains(".")) {
            throw new ServiceException("filename is not valid");
        }

        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(uploadBo.getBytes()));
        } catch (IOException e) {
            log.error("file upload failed", e);
            e.printStackTrace();
            throw new ServiceException("file upload failed");
        }

        // 扩展名
        String extName = fileName.substring(fileName.indexOf(".") + 1);
        String relativePath = fastdfsClient.upload(uploadBo.getBytes(), extName);

        if (relativePath == null) {
            throw new ServiceException("upload file failed");
        }

        return uploadProerties.getBaseUrl() + relativePath;
    }
}
