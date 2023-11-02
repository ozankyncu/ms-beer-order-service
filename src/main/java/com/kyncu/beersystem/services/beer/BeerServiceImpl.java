package com.kyncu.beersystem.services.beer;

import com.kyncu.beersystem.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@ConfigurationProperties(prefix = "kyncu.beer", ignoreUnknownFields = false)
@Component
public class BeerServiceImpl implements BeerService{
    public static final String BEER_BY_ID_PATH = "/api/v1/beer/";
    public static final String BEER_BY_UPC_PATH = "/api/v1/beerUpc/";

    private final RestTemplate restTemplate;

    private String beerServiceHost;

    public void setBeerServiceHost(String beerServiceHost) {
        this.beerServiceHost = beerServiceHost;
    }

    public BeerServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Optional<BeerDto> getBeerById(UUID id) {
        log.debug("Calling Beer Service by Id");

        return Optional.ofNullable(restTemplate.getForObject(beerServiceHost + BEER_BY_ID_PATH +
                id.toString(), BeerDto.class));
    }

    @Override
    public Optional<BeerDto> getBeerByUpc(String upc) {
        return Optional.ofNullable(restTemplate.getForObject(beerServiceHost + BEER_BY_UPC_PATH +
                upc, BeerDto.class));
    }
}
