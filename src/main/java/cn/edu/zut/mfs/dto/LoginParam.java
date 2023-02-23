package cn.edu.zut.mfs.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginParam {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
