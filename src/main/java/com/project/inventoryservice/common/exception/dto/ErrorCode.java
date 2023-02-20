package com.project.inventoryservice.common.exception.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
    ENTITY_NOT_FOUND(400, "E003", "err.entity.not.found"),
    NOT_FOUND(404, "E010", "err.page.not.found"),
    INTERNAL_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR"),
    METHOD_NOT_ALLOWED(405, "E011", "err.method.not.allowed"),
    BUSINESS_CUSTOM_MESSAGE(400, "B001", ""),
    ;

    private int status;
    private String code;
    private String message;

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
