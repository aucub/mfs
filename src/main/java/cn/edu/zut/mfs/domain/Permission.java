package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Permission {
    private String id;
    private String pid;
    private String name;
    private String value;
    private int status;
}
