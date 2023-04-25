package cn.edu.zut.mfs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindPageDto {
    private String keyword;
    private Integer pageSize;
    private Integer pageNum;
}
