package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleRelation {
    private String id;
    private String userId;
    private String roleId;
}
