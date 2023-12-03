package com.kyncu.beersystem.services;

import com.kyncu.beersystem.domain.BeerOrder;

public interface BeerOrderManager {
    BeerOrder newBeerOrder(BeerOrder beerOrder);
}
