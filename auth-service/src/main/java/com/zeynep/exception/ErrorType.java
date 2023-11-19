package com.zeynep.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    INTERNAL_ERROR(5100,"Sunucu hatası...", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100,"Parametre hatası...", HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4110,"Kullanıcı adı veya sifre hatalı.", HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4111,"Kullanıcı adı kullanılmaktadır...",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4112,"Kulanici bulunamadi..." , HttpStatus.BAD_REQUEST ),
    ACTIVATION_CODE_ERROR(4113,"Aktivasyon kodu hatalidir..." , HttpStatus.BAD_REQUEST ),
    INVALID_TOKEN(4114,"Geçersiz token..." , HttpStatus.BAD_REQUEST ),
    TOKEN_NOT_CREATED(4115,"Token oluşturulamadı..." , HttpStatus.BAD_REQUEST ),
    ACCOUNT_NOT_ACTIVE(4116,"Kullanıcı aktif değil..." , HttpStatus.FORBIDDEN );

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
