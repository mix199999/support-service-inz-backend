package com.shopsupport.supportservice.mappers;

import com.shopsupport.supportservice.dto.ProductDto;
import com.shopsupport.supportservice.dto.UserDto;
import com.shopsupport.supportservice.entities.Product;
import com.shopsupport.supportservice.entities.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")

public interface ProductMapper {
    ProductDto toProductDto(Product product);
}
