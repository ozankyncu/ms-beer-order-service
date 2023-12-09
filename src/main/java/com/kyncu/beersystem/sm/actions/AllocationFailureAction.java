package com.kyncu.beersystem.sm.actions;

import com.kyncu.beersystem.brewery.model.events.AllocationFailureEvent;
import com.kyncu.beersystem.config.JmsConfig;
import com.kyncu.beersystem.domain.BeerOrderEventEnum;
import com.kyncu.beersystem.domain.BeerOrderStatusEnum;
import com.kyncu.beersystem.services.BeerOrderManagerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllocationFailureAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_FAILURE_QUEUE, AllocationFailureEvent.builder()
                .orderId(UUID.fromString(beerOrderId))
                .build());

        log.debug("Sent Allocation failure message to queue for order id " + beerOrderId);
    }
}
