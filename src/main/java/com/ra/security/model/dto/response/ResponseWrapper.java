package com.ra.security.model.dto.response;

import com.ra.security.constants.EHttpStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseWrapper<T> {
    EHttpStatus ehttpStatus;
    int statusCode;
    T data;
}
