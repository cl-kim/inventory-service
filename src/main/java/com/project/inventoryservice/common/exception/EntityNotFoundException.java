package com.project.inventoryservice.common.exception;

import com.project.inventoryservice.common.exception.dto.ErrorCode;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException{
    private ErrorCode errorCode;

    public EntityNotFoundException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
