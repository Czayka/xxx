package com.xxx.common.exception;

import com.xxx.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralException extends RuntimeException {

    private ExceptionEnum exceptionEnum;
}
