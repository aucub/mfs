package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessage {
    private String publisher;
    private List<String> consumer;
    private byte[] body;
}
