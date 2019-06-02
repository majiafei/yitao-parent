package com.yitao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.service.SpecParamService;
import com.yitao.vo.SpecParamVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Package: com.yitao.controller
 * @ClassName: SpecParamController
 * @Author: majiafei
 * @Description:
 * @Date: 2019/6/1 17:50
 */

@Controller
@RequestMapping("/api/item/spec")
public class SpecParamController {

    @Reference(check = false)
    private SpecParamService specParamService;

    @GetMapping("/params")
    public ResponseEntity<List<SpecParamVO>> getSpecParamListByCid(@RequestParam("cid") Long cid) {
        return ResponseEntity.ok(specParamService.querySpecPramListByCid(cid));
    }

}
