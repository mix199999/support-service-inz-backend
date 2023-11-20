package com.shopsupport.supportservice.mappers;

import com.shopsupport.supportservice.dto.OrderDTO;
import com.shopsupport.supportservice.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO orderToDTO(Order order);
}
