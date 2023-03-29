package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ForwardMessage {
    private String id;
    private String client;
    private String topic;
    //private Date timeStamp;

}
