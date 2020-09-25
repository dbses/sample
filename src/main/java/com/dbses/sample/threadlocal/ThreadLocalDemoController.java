package com.dbses.sample.threadlocal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "ThreadLocal测试")
@RequestMapping(value = "/demo/threadlocal", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThreadLocalDemoController {

    @PostMapping("/memory")
    @ApiOperation(value = "测试接口", notes = "测试接口")
    public ResponseEntity<String> ping() {
        int length = 10240;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = new Byte("0");
        }
        String request = new String(bytes);
        SessionContext.setDbName(request);
        return ResponseEntity.ok("success");
    }

}
