package cn.edu.zut.mfs.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindPageVo {
    private String keyword;
    private Integer pageSize;
    private Integer pageNum;
}
