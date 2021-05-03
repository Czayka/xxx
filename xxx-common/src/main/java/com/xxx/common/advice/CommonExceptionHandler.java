package com.xxx.common.advice;

import com.xxx.common.enums.ExceptionEnum;
import com.xxx.common.exception.GeneralException;
import com.xxx.common.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ExceptionResult> handlerException(GeneralException e){
        return ResponseEntity.status(e.getExceptionEnum().getCode())
                .body(new ExceptionResult(e.getExceptionEnum()));
    }
}
