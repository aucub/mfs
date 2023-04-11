package cn.edu.zut.mfs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtDto {
    private String jwtId;
    private String issuer;//签发者
    private String subject;//主题
    private Date expiresAt;//过期时间,Date.from(Instant.now().plus(30, ChronoUnit.MINUTES))
    private String audience;//接收人
    private String scope;//授权范围

}
