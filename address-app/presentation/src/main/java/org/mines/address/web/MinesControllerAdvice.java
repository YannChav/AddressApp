package org.mines.address.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = {"org.mines.address"})
public class MinesControllerAdvice {

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
