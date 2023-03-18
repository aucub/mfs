package cn.edu.zut.mfs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RolePermissionRelation {
    private String id;
    private String roleId;
    private String permissionId;
}
