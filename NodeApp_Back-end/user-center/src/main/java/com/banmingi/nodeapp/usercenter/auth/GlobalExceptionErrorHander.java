package com.banmingi.nodeapp.usercenter.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @auther 半命i 2020/4/22
 * @description
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionErrorHander {
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorBody> error(SecurityException e) {
        log.warn("发生Security异常",e);
       return new ResponseEntity<ErrorBody>(
                ErrorBody.builder()
                        .body(e.getMessage())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ErrorBody {
    private String body;
    private int status;
}
