package com.kyncu.beersystem.sm.actions;

import com.kyncu.beersystem.brewery.model.events.ValidateBeerOrderRequest;
import com.kyncu.beersystem.config.JmsConfig;
import com.kyncu.beersystem.domain.BeerOrder;
import com.kyncu.beersystem.domain.BeerOrderEventEnum;
import com.kyncu.beersystem.domain.BeerOrderStatusEnum;
import com.kyncu.beersystem.repositories.BeerOrderRepository;
import com.kyncu.beersystem.services.BeerOrderManagerImpl;
import com.kyncu.beersystem.web.mappers.BeerOrderMapper;
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
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        BeerOrder beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE,
                ValidateBeerOrderRequest.builder()
                        .beerOrder(beerOrderMapper.beerOrderToDto(beerOrder))
                        .build());

        log.debug("Sent Validation request to queue for order id " + beerOrderId);

    }
}
