package com.yitao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.domain.SpecParam;
import com.yitao.dto.SpecParamDTO;
import com.yitao.service.SpecParamService;
import com.yitao.vo.SpecParamVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<SpecParamVO>> getSpecParamListByCondition(SpecParamDTO specParamDTO) {
        return ResponseEntity.ok(specParamService.querySpecParamListByCondition(specParamDTO));
    }

    @PostMapping("/param")
    public ResponseEntity<Void> saveSpecParam(@RequestBody SpecParamDTO specParamDTO) {
        specParamService.saveSpecParam(specParamDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/param/{specParamId}")
    public ResponseEntity<Void> deleteSpecParamById (@PathVariable("specParamId") Long specParamId) {
        specParamService.deleteSpecParam(specParamId);
        return ResponseEntity.ok().build();
    }

}
