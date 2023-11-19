package com.zeynep.service;

import com.zeynep.dto.request.ActivateStatusRequestDto;
import com.zeynep.dto.request.UpdateEmailOrUsernameRequestDto;
import com.zeynep.dto.request.UserCreateRequestDto;
import com.zeynep.dto.request.UserUpdateRequestDto;
import com.zeynep.exception.ErrorType;
import com.zeynep.exception.UserManagerException;
import com.zeynep.manager.AuthManager;
import com.zeynep.mapper.UserMapper;
import com.zeynep.repository.UserRepository;
import com.zeynep.repository.entity.UserProfile;
import com.zeynep.utility.JwtTokenManager;
import com.zeynep.utility.ServiceManager;
import com.zeynep.utility.enums.EStatus;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {

    private final UserRepository userRepository;
    private final JwtTokenManager tokenManager;
    private final AuthManager authManager;

    public UserProfileService(UserRepository userRepository, JwtTokenManager tokenManager, AuthManager authManager) {
        super(userRepository);
        this.userRepository=userRepository;
        this.tokenManager = tokenManager;
        this.authManager = authManager;
    }

    public Boolean createUser(UserCreateRequestDto dto){
        try {
            save(UserMapper.INSTANCE.fromCreateRequestToUser(dto));
            return true;
        }catch (Exception e){
            throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }
    }

    public Boolean activateStatus(Long authId) {
        Optional<UserProfile> userProfile = userRepository.findOptionalByAuthId(authId);
        if(userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }else {
            userProfile.get().setStatus(EStatus.ACTIVE);
            update(userProfile.get());
            return true;
        }
    }

    public Boolean activateStatus2(ActivateStatusRequestDto dto) {
        Optional<UserProfile> userProfile = userRepository.findOptionalByAuthId(dto.getAuthId());
        if(userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }else {
            userProfile.get().setStatus(EStatus.ACTIVE);
            update(userProfile.get());
            return true;
        }
    }

    public Boolean updateUser(UserUpdateRequestDto dto) {
        try {
            update(UserMapper.INSTANCE.fromUpdateRequestToUser(dto));
            return true;
        } catch (Exception e){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
    }

    public Boolean update(UserUpdateRequestDto dto){
        Optional<Long> authId = tokenManager.getIdFromToken(dto.getToken());
        if (authId.isEmpty()){
            throw new UserManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = userRepository.findOptionalByAuthId(authId.get());
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        //auth isteği yolla
        if (!dto.getUsername().equals(userProfile.get().getUsername())|| !dto.getEmail().equals(userProfile.get().getEmail())){
            userProfile.get().setUsername(dto.getUsername());
            userProfile.get().setEmail(dto.getEmail());
            UpdateEmailOrUsernameRequestDto updateEmailOrUsernameRequestDto = UpdateEmailOrUsernameRequestDto.builder()
                    .username(userProfile.get().getUsername())
                    .email(userProfile.get().getEmail())
                    .id(userProfile.get().getAuthId())
                    .build();
            authManager.updateEmailOrUsername(updateEmailOrUsernameRequestDto);
        }
        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setAvatarUrl(dto.getAvatarUrl());
        userProfile.get().setAddress(dto.getAddress());
        userProfile.get().setAbout(dto.getAbout());
        update(userProfile.get());
        return true;
    }

    public Boolean deleteUser(Long authId) {
        Optional<UserProfile> userProfile = userRepository.findOptionalByAuthId(authId);
        if(userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }else {
            userProfile.get().setStatus(EStatus.DELETED);
            update(userProfile.get());
            return true;
        }
    }
}
