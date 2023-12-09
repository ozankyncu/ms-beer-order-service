package com.kyncu.beersystem.services.testcomponents;

import com.kyncu.beersystem.brewery.model.events.ValidateOrderRequest;
import com.kyncu.beersystem.brewery.model.events.ValidateOrderResult;
import com.kyncu.beersystem.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void list(Message message) {
        Boolean isValid = true;

        ValidateOrderRequest request = (ValidateOrderRequest) message.getPayload();
        if ("fail-validation".equals(request.getBeerOrder().getCustomerRef())) {
            isValid = false;
        }

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                ValidateOrderResult.builder()
                        .isValid(isValid)
                        .orderId(request.getBeerOrder().getId())
                        .build());
    }
}
