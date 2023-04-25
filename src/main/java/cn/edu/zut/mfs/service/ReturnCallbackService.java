package cn.edu.zut.mfs.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReturnCallbackService implements RabbitTemplate.ReturnsCallback {

    @Override
    public void returnedMessage(@NotNull ReturnedMessage returned) {
        log.info("returnedMessage={}", returned);
    }
}
