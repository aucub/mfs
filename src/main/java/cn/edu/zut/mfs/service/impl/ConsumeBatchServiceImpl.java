package cn.edu.zut.mfs.service.impl;

import cn.edu.zut.mfs.domain.Consume;
import cn.edu.zut.mfs.domain.ConsumeRecord;
import cn.edu.zut.mfs.service.ConsumeBatchService;
import cn.edu.zut.mfs.service.QuestService;
import cn.edu.zut.mfs.utils.MessageConverter;
import io.cloudevents.core.v1.CloudEventV1;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ConsumeBatchServiceImpl implements ConsumeBatchService {
    private RabbitTemplate rabbitTemplate;

    private QuestService questService;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setQuestService(QuestService questService) {
        this.questService = questService;
    }

    public Flux<CloudEventV1> consume(Consume consume) {
        return Flux.create(emitter -> {
            for (int i = 0; i < consume.getBatchSize(); i++) {
                Message message = rabbitTemplate.receive(consume.getQueue());
                emitter.next(MessageConverter.fromMessage(message));
                questService.consume(new ConsumeRecord(message.getMessageProperties().getMessageId(), 0, 0, consume.getQueue(), consume.getUserId()));
            }
            emitter.complete();
        });
    }
}
