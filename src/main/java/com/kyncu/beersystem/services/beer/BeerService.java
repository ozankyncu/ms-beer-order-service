package com.kyncu.beersystem.services.beer;

import com.kyncu.beersystem.brewery.model.BeerDto;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDto> getBeerById(UUID id);

    Optional<BeerDto> getBeerByUpc(String upc);
}
