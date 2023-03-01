package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FanoutMessage {
    private String fanoutExchangeName;
    private Boolean autoDelete;
    private List<String> consumers;
}
