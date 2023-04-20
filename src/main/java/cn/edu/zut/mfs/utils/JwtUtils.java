package cn.edu.zut.mfs.utils;

import cn.edu.zut.mfs.dto.JwtDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class JwtUtils {

    private final static Algorithm algorithm = Algorithm.HMAC256("JAC1O17W1F3QB9E8B4B1MT6QKYOQB36V");

    /**
     * 生成JWT
     *
     * @param jwtDto
     * @return
     */
    public static String generate(JwtDto jwtDto) {
        return JWT.create()
                .withJWTId(jwtDto.getJwtId())
                .withIssuer(jwtDto.getIssuer())
                .withSubject(jwtDto.getSubject())
                .withExpiresAt(jwtDto.getExpiresAt())
                .withAudience(jwtDto.getAudience())
                .withClaim("scope", jwtDto.getAuthorities())
                .sign(algorithm);
    }

    public static DecodedJWT decode(String token) {
        return JWT.decode(token);
    }
}
