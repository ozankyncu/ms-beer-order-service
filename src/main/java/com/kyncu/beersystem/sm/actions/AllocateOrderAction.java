package com.kyncu.beersystem.sm.actions;

import com.kyncu.beersystem.brewery.model.events.AllocateOrderRequest;
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

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);

        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_QUEUE,
                    AllocateOrderRequest.builder()
                            .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
                            .build());
            log.debug("Sent Allocation Request for order id: " + beerOrderId);
        }, () -> log.error("Beer Order Not Found!"));

    }
}
