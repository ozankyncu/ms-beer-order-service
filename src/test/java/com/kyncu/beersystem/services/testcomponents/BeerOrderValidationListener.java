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
        boolean isValid = true;
        boolean sendResponse = true;

        ValidateOrderRequest request = (ValidateOrderRequest) message.getPayload();

        if(request.getBeerOrder().getCustomerRef() != null) {
            if (request.getBeerOrder().getCustomerRef().equals("fail-validation")) {
                isValid = false;
            } else if (request.getBeerOrder().getCustomerRef().equals("dont-validate")) {
                sendResponse = false;
            }
        }

        if (sendResponse) {
            jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                    ValidateOrderResult.builder()
                            .isValid(isValid)
                            .orderId(request.getBeerOrder().getId())
                            .build());
        }

    }
}
