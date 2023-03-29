package cn.edu.zut.mfs;

import com.rabbitmq.stream.ConsumerBuilder;
import com.rabbitmq.stream.OffsetSpecification;
import org.springframework.rabbit.stream.listener.ConsumerCustomizer;

public class ConsumerCustomizerFor implements ConsumerCustomizer {

    @Override
    public void accept(String s, ConsumerBuilder consumerBuilder) {
        consumerBuilder.offset(OffsetSpecification.offset(1));
    }
}
