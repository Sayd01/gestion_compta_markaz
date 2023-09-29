/*
 * Created on 2023-08-28 ( Time 13:26:33 )
 * Generator tool : Telosys Tools Generator ( version 3.3.0 )
 * Copyright 2023 O-sey. All Rights Reserved.
 */

package ci.saydos.markazcompta.utils.exception;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

import ci.saydos.markazcompta.utils.Status;


@Builder
@Data
public class ApiErrorResponse {
    private   Status            status;
    private   Integer           httpCode;
    protected boolean           hasError;
    protected LocalDateTime     timestamp;
}