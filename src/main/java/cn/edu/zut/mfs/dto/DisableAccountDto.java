package cn.edu.zut.mfs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisableAccountDto {
    private String userId;
    private long time;
}
