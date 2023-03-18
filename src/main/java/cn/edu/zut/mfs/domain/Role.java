package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Role {
    private String id;
    private String name;
    private String description;
    private int status;
}
