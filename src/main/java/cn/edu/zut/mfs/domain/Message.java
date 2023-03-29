package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Message {
    private String id;
    private String client;
    private String topic;
    //private Date timeStamp;

}
