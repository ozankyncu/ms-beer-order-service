package com.kyncu.beersystem.services;

import com.kyncu.beersystem.brewery.model.events.ValidateOrderResult;
import com.kyncu.beersystem.domain.BeerOrder;

import java.util.UUID;

public interface BeerOrderManager {
    BeerOrder newBeerOrder(BeerOrder beerOrder);

    void processValidationResult(UUID beerOrderID, Boolean isValid);
}
