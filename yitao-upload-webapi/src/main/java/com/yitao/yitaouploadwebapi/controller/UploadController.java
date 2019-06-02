package com.yitao.yitaouploadwebapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.org.apache.regexp.internal.RE;
import com.yitao.bo.UploadBo;
import com.yitao.upload.service.UploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Package: com.yitao.yitaouploadwebapi.controller
 * @ClassName: UploadController
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/26 20:16
 */
@Controller
public class UploadController {

    @Reference(check = false)
    private UploadService uploadService;

    @PostMapping("/api/upload/image")
    public ResponseEntity<String> upload(MultipartFile file) throws IOException {
        UploadBo uploadBo = new UploadBo();
        uploadBo.setBytes(file.getBytes());
        uploadBo.setContentType(file.getContentType());
        uploadBo.setFileName(file.getOriginalFilename());
        return ResponseEntity.ok(uploadService.upload(uploadBo));
    }

    /**
     * 删除图片
     * @param path
     * @return
     */
    @GetMapping("/api/upload/delete")
    public ResponseEntity<Integer> delete(@RequestParam("path") String path) {
        return ResponseEntity.ok(uploadService.deleteFile(path));
    }

}
