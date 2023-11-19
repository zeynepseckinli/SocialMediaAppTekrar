package com.zeynep.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    INTERNAL_ERROR(5100,"Sunucu hatası...", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4200,"Parametre hatası...", HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4210,"Kullanıcı adı kullanılmaktadır...",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4211,"Kulanici bulunamadi..." , HttpStatus.BAD_REQUEST ),
    USER_NOT_CREATED(4212,"Kulanici profili oluşturulamadi..." , HttpStatus.BAD_REQUEST ),
    INVALID_TOKEN(4114,"Geçersiz token..." , HttpStatus.BAD_REQUEST );

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
