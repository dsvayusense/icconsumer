package com.vayusense.icconsumer.service;

import com.rabbitmq.client.Channel;
import com.vayusense.icconsumer.dto.StateDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
@Slf4j
public class RabbitMQConsumer  {

    private final StateService stateService;

    @RabbitListener(containerFactory = "rabbitListenerContainerFirst", queues="app1-queue" )
    public void listen(@Payload StateDto state, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)throws Exception{
        try {
                 Mono<StateDto> monoState = stateService.SwitchMethodByConsume(state);
                 log.info("Recieved Message From RabbitMQ app1: " + monoState.block());
                 if(monoState.block() != null){
                     channel.basicAck(tag,false);
                 }
        }catch (Exception e){
                channel.basicReject(tag, true);
        }

    }

    @RabbitListener(containerFactory = "rabbitListenerContainerSecond", queues="app2-queue")
    public void recieved(@Payload StateDto state, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)throws Exception{
        try{
                log.info("Recieved Message From RabbitMQ: " + state);
                if (state != null){
                    channel.basicAck(tag,false);
                }
        }catch (Exception e){
                channel.basicReject(tag, true);
        }

    }



}
