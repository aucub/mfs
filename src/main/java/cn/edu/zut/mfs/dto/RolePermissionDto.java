package cn.edu.zut.mfs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionDto {
    private String roleId;
    private List<String> permissionIds;
}
