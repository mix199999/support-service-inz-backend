package com.shopsupport.supportservice.mappers;


import com.shopsupport.supportservice.dto.UserDto;
import com.shopsupport.supportservice.entities.User;
import org.mapstruct.Mapper;


import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);



}