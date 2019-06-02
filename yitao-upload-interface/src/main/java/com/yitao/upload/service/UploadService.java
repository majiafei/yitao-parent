package com.yitao.upload.service;

import com.yitao.bo.UploadBo;
import org.omg.CORBA.NameValuePair;

/**
 * @Package: com.yitao.upload.service
 * @ClassName: UploadService
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/26 20:49
 */
public interface UploadService {

    /**
     * 上传图片
     * @param uploadBo
     * @param metas
     * @return
     */
    String upload(UploadBo uploadBo, NameValuePair[] metas);

    /**
     * 上传图片
     * @param uploadBo
     * @return
     */
    String upload(UploadBo uploadBo);

    int deleteFile(String path);

}
