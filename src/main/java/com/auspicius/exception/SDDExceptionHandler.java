package com.auspicius.exception;

import com.auspicius.responce.ApiError;
import com.auspicius.responce.ApiResponse;
import com.auspicius.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class SDDExceptionHandler {

    @ExceptionHandler(SDDException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleSDDException(SDDException ex) {
        log.error("Error: {}", ex.getMessage());
        ApiResponse<ApiError> errorResponse = new ApiResponse<>();
        errorResponse.setMessage("Validation Error");
        errorResponse.setStatus(ex.getStatus());

        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());
        apiError.setField(ex.getField()); // This works now
        errorResponse.setResponse(apiError);

        return ResponseEntity.status(HttpStatus.valueOf(ex.getStatus())).body(errorResponse);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleRecordNotFoundException(RecordNotFoundException ex) {
        log.error("Record not found: {}", ex.getMessage());
        ApiResponse<String> response = ResponseUtils.createNotFoundResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage());
        ApiResponse<String> response = new ApiResponse<>();
        response.setMessage("Internal Server Error");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
