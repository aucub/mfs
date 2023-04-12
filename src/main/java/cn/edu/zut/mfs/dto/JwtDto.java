package cn.edu.zut.mfs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtDto {
    private String jwtId;
    private String issuer;//签发人
    private String subject;//主题，用户
    private Instant expiresAt;//过期时间
    private String audience;//接收人
    private Instant notBefore;//生效时间
    private Instant issuedAt;//签发时间
    private List<String> authorities;//授权
}
