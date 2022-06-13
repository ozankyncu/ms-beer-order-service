package com.kyncu.msbeerorderservice.web.mappers;

import com.kyncu.msbeerorderservice.domain.BeerOrderLine;
import com.kyncu.msbeerorderservice.web.model.BeerOrderLineDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerOrderLineMapper {
    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);

    BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto);
}
