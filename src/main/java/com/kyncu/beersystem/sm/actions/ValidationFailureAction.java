package com.kyncu.beersystem.sm.actions;

import com.kyncu.beersystem.domain.BeerOrderEventEnum;
import com.kyncu.beersystem.domain.BeerOrderStatusEnum;
import com.kyncu.beersystem.services.BeerOrderManagerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidationFailureAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        String beerOrderId = (String) stateContext.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        // In real system notify somewhere
        log.error("Compensating Transaction.... Validation failed: "+ beerOrderId);
    }
}
