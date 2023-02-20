package com.project.inventoryservice.common.exception;

import com.project.inventoryservice.common.exception.dto.ErrorCode;

public class BusinessException extends RuntimeException{

    private String customMessage;
    private ErrorCode errorCode;

    /**
     * 사용자 정의 메시지를 받아 처리하는 경우
     *
     * @param errorCode 400 에러
     * @param customMessage 사용자에게 표시할 메시지
     */
    public BusinessException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
        this.customMessage = customMessage;
    }

    /**
     * 사전 정의된 에러코드 객체를 넘기는 경우
     *
     * @param message 서버에 남길 메시지
     * @param errorCode 사전 정의된 에러코드
     */
    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getCustomMessage() {
        return customMessage;
    }

}
