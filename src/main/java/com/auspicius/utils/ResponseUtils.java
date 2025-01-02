package com.auspicius.utils;

import com.auspicius.responce.ApiResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class ResponseUtils {

        public static <T> ApiResponse<T> createSuccessResponse(T data) {
            ApiResponse<T> response = new ApiResponse<>();
            response.setResponse(data);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("success");
            return response;
        }

        public static <T> ApiResponse<T> createSuccessResponseWithCred(T data, String key, String salt) {
            ApiResponse<T> response = new ApiResponse<>();
            response.setResponse(data);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("success");
            response.setKey(key);
            response.setSalt(salt);
            response.setProduction(true);
            return response;
        }

        public static <T> ApiResponse<T> createFailureResponse(String msg, Integer status) {
            ApiResponse<T> response = new ApiResponse<>();
            response.setStatus(status);
            response.setMessage(msg);
            return response;
        }

        public static <T> ApiResponse<T> createNotFoundResponse(String msg) {
            ApiResponse<T> response = new ApiResponse<>();
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(msg);
            return response;
        }
    }

