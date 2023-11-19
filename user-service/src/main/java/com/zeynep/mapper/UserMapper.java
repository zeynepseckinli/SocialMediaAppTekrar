package com.zeynep.mapper;

import com.zeynep.dto.request.UserCreateRequestDto;
import com.zeynep.dto.request.UserUpdateRequestDto;
import com.zeynep.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserProfile fromCreateRequestToUser(UserCreateRequestDto dto);
    UserProfile fromUpdateRequestToUser(UserUpdateRequestDto dto);
}
