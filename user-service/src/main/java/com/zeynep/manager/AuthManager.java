package com.zeynep.manager;

import com.zeynep.dto.request.UpdateEmailOrUsernameRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:7070/api/v1/auth", name = "userprofile-auth")
public interface AuthManager {
    @PutMapping("/update_email_or_username")//update metotlarında kullanılır
    public ResponseEntity<Boolean> updateEmailOrUsername(@RequestBody UpdateEmailOrUsernameRequestDto dto);
}
