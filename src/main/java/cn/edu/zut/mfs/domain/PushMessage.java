package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushMessage {
    private String userId;
    private String route;
    private byte[] body;
    private String id = UUID.randomUUID().toString();
}
