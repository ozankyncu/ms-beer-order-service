package com.kyncu.beersystem.brewery.model.events;

import com.kyncu.beersystem.brewery.model.BeerOrderDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateOrderRequest {
    BeerOrderDto beerOrder;
}
