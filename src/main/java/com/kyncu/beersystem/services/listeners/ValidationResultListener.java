package com.kyncu.beersystem.services.listeners;

import com.kyncu.beersystem.brewery.model.events.ValidateOrderResult;
import com.kyncu.beersystem.config.JmsConfig;
import com.kyncu.beersystem.services.BeerOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class ValidationResultListener {

    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE)
    public void listen(ValidateOrderResult validateOrderResult) {
        final UUID beerOrderId = validateOrderResult.getOrderId();

        log.debug("Validation result for Order Id: "+ beerOrderId);

        beerOrderManager.processValidationResult(validateOrderResult.getOrderId(), validateOrderResult.getIsValid());
    }
}
