package cn.edu.zut.mfs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindUserPageDto {
    private String keyword;
    private String roleId;
    private Integer pageSize;
    private Integer pageNum;
}
