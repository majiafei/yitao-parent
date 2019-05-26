package com.yitao.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 上传文件BO
 * @Package: com.yitao.bo
 * @ClassName: UploadBo
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/26 20:45
 */
@Getter
@Setter
public class UploadBo implements Serializable {

    // 字节数组
    private byte[] bytes;
    // 文件名称
    private String fileName;
    // 内容类型
    private String contentType;

}
