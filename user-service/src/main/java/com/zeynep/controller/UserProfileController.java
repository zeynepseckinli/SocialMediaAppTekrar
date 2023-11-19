package com.zeynep.controller;

import com.zeynep.dto.request.ActivateStatusRequestDto;
import com.zeynep.dto.request.UserCreateRequestDto;
import com.zeynep.dto.request.UserUpdateRequestDto;
import com.zeynep.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.zeynep.constants.RestApi.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping(CREATE)
    public ResponseEntity<Boolean>  createUser(@RequestBody UserCreateRequestDto dto){
        return ResponseEntity.ok(userProfileService.createUser(dto));
    }

    @GetMapping(ACTIVATESTATUS+"/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.activateStatus(authId));
    }

    @PostMapping(ACTIVATESTATUS2)
    public ResponseEntity<Boolean> activateStatus2(@RequestBody ActivateStatusRequestDto dto){
        return ResponseEntity.ok(userProfileService.activateStatus2(dto));
    }

    @PostMapping(UPDATE)
    public ResponseEntity<Boolean> updateUser(@RequestBody UserUpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.updateUser(dto));
    }
    @PostMapping(UPDATE+"/2")
    public ResponseEntity<Boolean> updateUser2(@RequestBody UserUpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.update(dto));
    }

    @DeleteMapping(DELETEBYID+"/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.deleteUser(authId));
    }

}
