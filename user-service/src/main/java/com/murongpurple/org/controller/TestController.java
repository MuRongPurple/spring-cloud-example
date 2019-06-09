package com.murongpurple.org.controller;

import com.murongpurple.org.enums.CodeMsgEnum;
import com.murongpurple.org.myexception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/user")
public class TestController {

    @ResponseBody
    @GetMapping("/index2")
    public Object index2() throws InterruptedException {

        // 为了演示超时现象，我们在这里然线程休眠,时间随机 0~2000毫秒
        Thread.sleep(5000);

        System.out.println("方法2执行");
        log.info("hello murongpurple");
        return "hello murongpurple index2";
    }

    @ResponseBody
    @GetMapping("/index3")
    public Object index3(int id) throws InterruptedException {

       if (id % 2 == 0){
           throw new RuntimeException("异常");
       }
       return "hello murongpurple index3";
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<String> userInfo(@PathVariable("id") Long id) throws InterruptedException {

        if (id % 2 == 0){
            throw new BizException(CodeMsgEnum.USERNUM_CANNOT_BE_EVEN);
        }
        return ResponseEntity.ok("hello murongpurple user");
    }
}
