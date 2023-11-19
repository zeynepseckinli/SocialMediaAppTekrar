package com.zeynep.mapper;

import com.zeynep.dto.request.RegisterRequestDto;
import com.zeynep.dto.request.UserCreateRequestDto;
import com.zeynep.dto.response.RegisterResponseDto;
import com.zeynep.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);
    Auth fromRegisterRequestToAuth(RegisterRequestDto dto);
    RegisterResponseDto fromAuthToRegisterResponse(Auth auth);
    @Mapping(source = "id", target = "authId")
    UserCreateRequestDto fromAuthToUserCreateRequestDto(Auth auth);
}
