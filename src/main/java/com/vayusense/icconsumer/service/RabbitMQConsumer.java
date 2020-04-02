package com.vayusense.icconsumer.service;

import com.rabbitmq.client.Channel;
import com.vayusense.icconsumer.dto.StateDto;
import com.vayusense.icconsumer.entities.MachineLearningLog;
import com.vayusense.icconsumer.entities.Unit;
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

    private final TevaService tevaService;

    @RabbitListener(containerFactory = "rabbitListenerContainerState", queues="stateteva")
    public void recieveState(@Payload StateDto state, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)throws Exception{
        try {
                 Mono<String> monoState = tevaService.createState(state);
                 log.info("Recieved Message From RabbitMQ for teva state : " + monoState.block());
                 if (!monoState.block().isBlank()){
                     channel.basicAck(tag,false);
                 }
        }catch (Exception e){
                log.error("there is a error while recieved a Message From RabbitMQ for teva state  : " + state);
                channel.basicReject(tag, true);
        }

    }

    @RabbitListener(containerFactory = "rabbitListenerContainerlog", queues="logteva")
    public void recievedLog(@Payload MachineLearningLog mlLog, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)throws Exception{
        try{
              Mono<String> monoMLlog = tevaService.createMLLog(mlLog);
               log.info("Recieved Message From RabbitMQ for teva log : " + mlLog);
                if (!monoMLlog.block().isBlank()){
                    channel.basicAck(tag,false);
                }
        }catch (Exception e){
                log.error("there is a error while recieved a Message From RabbitMQ for teva log : " + mlLog);
                channel.basicReject(tag, true);
        }

    }

    @RabbitListener(containerFactory = "rabbitListenerContainerUnit", queues="unitteva")
    public void recievedUnit(@Payload Unit unit, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)throws Exception{
        try{
                Mono<String> monoUnit = tevaService.createUnit(unit);
                log.info("Recieved Message From RabbitMQ: " + unit);
                if (!monoUnit.block().isBlank()){
                    channel.basicAck(tag,false);
                }
        }catch (Exception e){
            log.error("there is a error while recieved a Message From RabbitMQ for teva unit : " + unit);
            channel.basicReject(tag, true);
        }

    }


}
