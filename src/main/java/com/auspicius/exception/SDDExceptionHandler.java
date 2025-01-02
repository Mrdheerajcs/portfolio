package com.auspicius.exception;


import com.auspicius.responce.ApiError;
import com.auspicius.responce.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class SDDExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SDDException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleSDDException(SDDException ex) {
        ApiResponse<ApiError> errorApiResponse = new ApiResponse<>();
        errorApiResponse.setMessage("Error occurred");
        errorApiResponse.setStatus(ex.getStatus());

        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());

        errorApiResponse.setResponse(apiError);

        return new ResponseEntity<>(errorApiResponse, HttpStatus.valueOf(ex.getStatus()));
    }
}
