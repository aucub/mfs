package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPermissionRelation {
    private String id;
    private String userId;
    private String permissionId;
    private int type;
}
