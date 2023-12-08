package com.kyncu.beersystem.services.testcomponents;

import com.kyncu.beersystem.brewery.model.events.AllocateOrderRequest;
import com.kyncu.beersystem.brewery.model.events.AllocateOrderResult;
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
public class BeerOrderAllocationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    private void listen(Message message) {

        AllocateOrderRequest request = (AllocateOrderRequest) message.getPayload();

        request.getBeerOrderDto().getBeerOrderLines().forEach(beerOrderLineDto -> {
            beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
        });

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE,
                AllocateOrderResult.builder().beerOrderDto(request.getBeerOrderDto())
                        .pendingInventory(false)
                        .allocationError(false)
                        .build());

    }
}
