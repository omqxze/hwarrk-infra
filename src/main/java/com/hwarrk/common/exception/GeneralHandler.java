package com.hwarrk.common.exception;

import com.hwarrk.common.apiPayload.code.BaseCode;
import com.hwarrk.common.apiPayload.code.ResponseDTO;
import lombok.Getter;

@Getter
public class GeneralHandler extends RuntimeException {

    private BaseCode errorStatus;

    public GeneralHandler(BaseCode errorStatus) {
        super(errorStatus.getDto().getMessage());
        this.errorStatus = errorStatus;
    }

    public ResponseDTO getError() {
        return this.errorStatus.getDto();
    }

    public ResponseDTO getErrorHttpStatus() {
        return this.errorStatus.getHttpStatusDto();
    }
}
