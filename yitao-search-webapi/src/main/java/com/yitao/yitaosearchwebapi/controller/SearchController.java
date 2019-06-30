package com.yitao.yitaosearchwebapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yitao.search.model.SearchRequest;
import com.yitao.search.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *     前台工程的启动:live-server --port=7093
 * </p>
 * @Package: com.yitao.yitaosearchwebapi.controller
 * @ClassName: SearchController
 * @Author: majiafei
 * @Description:
 * @Date: 2019/6/30 17:15
 */
@Controller
@RequestMapping("api/search")
public class SearchController {

    @Reference(check = false)
    private SearchService searchService;

    @PostMapping("/page")
    public ResponseEntity search(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(searchService.search(searchRequest));
    }

}
