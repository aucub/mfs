package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForwardMessage {
    private String queue;
    private String exchange;
    private String routingKey;
    private String content;
    private Boolean autoDelete;
    private List<String> consumers;
}
