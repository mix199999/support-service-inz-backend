package com.shopsupport.supportservice.mappers;

import com.shopsupport.supportservice.dto.OrderDetailsDTO;
import com.shopsupport.supportservice.entities.OrderDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface OrderDetailsMapper {
    OrderDetailsDTO toOrderDetailsDTO(OrderDetails orderDetails);
}
