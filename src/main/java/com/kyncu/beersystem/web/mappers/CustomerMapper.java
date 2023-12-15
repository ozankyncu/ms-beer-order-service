package com.kyncu.beersystem.web.mappers;

import com.kyncu.beersystem.brewery.model.CustomerDto;
import com.kyncu.beersystem.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {

    CustomerDto customerToDto(Customer customer);

    Customer dtoToCustomer(CustomerDto dto);
}

