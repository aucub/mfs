package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForwardMessage {
    private String publisher;
    private String consumer;
    private byte[] body;
}
