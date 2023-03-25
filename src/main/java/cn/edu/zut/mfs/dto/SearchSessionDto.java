package cn.edu.zut.mfs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SearchSessionDto {
    private int start;
    private int size;
}
