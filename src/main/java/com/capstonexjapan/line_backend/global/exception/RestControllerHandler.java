//package com.capstonexjapan.line_backend.global.exception;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class RestControllerHandler {
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
//        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
//        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", ex.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    // 다른 예외 처리 메서드를 추가할 수 있습니다.
//
//    @Setter
//    @Getter
//    public static class ErrorResponse {
//        private String errorCode;
//        private String errorMessage;
//
//        public ErrorResponse(String errorCode, String errorMessage) {
//            this.errorCode = errorCode;
//            this.errorMessage = errorMessage;
//        }
//
//    }
//}
