package com.shopsupport.supportservice.mappers;


import com.shopsupport.supportservice.dto.MessageDTO;
import com.shopsupport.supportservice.entities.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDTO toMessageDTO(Message message);
}
