package com.yitao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.dto.SpecGroupDTO;
import com.yitao.service.SpecGroupService;
import com.yitao.vo.SpecGroupVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Package: com.yitao.controller
 * @ClassName: SpecGroupController
 * @Author: majiafei
 * @Description:
 * @Date: 2019/5/26 9:43
 */

@Controller
@RequestMapping("/api/item/spec")
public class SpecGroupController {

    @Reference(check = false)
    private SpecGroupService specGroupService;

    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroupVO>> getSpecGroupList(@PathVariable("cid") Long cid) {
        List<SpecGroupVO> specGroupVOList = specGroupService.querySpecGroupListByCid(cid);
        return ResponseEntity.ok(specGroupVOList);
    }

    @PostMapping("/group")
    public ResponseEntity<Void> saveSpecGroup(@RequestBody SpecGroupDTO specGroupDTO) {
        specGroupService.saveSpecGroup(specGroupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/group")
    public ResponseEntity<Void> updateSpecGroup(@RequestBody SpecGroupDTO specGroupDTO) {
        specGroupService.updateSpectGroup(specGroupDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteSpecGroup(@PathVariable("id") Long id) {
        specGroupService.deleteSpecGroup(id);
        return ResponseEntity.ok().build();
    }

}
