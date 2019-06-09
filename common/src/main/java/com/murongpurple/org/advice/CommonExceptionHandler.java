package com.murongpurple.org.advice;

import com.murongpurple.org.myexception.BizException;
import com.murongpurple.org.response.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ResponseEntity<Result> handleException(BizException e){
        return ResponseEntity.status(e.getCodeMsgEnum().getCode())
                .body(Result.error(e.getCodeMsgEnum()));
    }
}
