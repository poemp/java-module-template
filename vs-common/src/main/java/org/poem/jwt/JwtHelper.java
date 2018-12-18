package org.poem.jwt;

import io.jsonwebtoken.*;
import org.apache.shiro.codec.Base64;
import org.poem.Constant;
import org.poem.jwt.result.CheckResult;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SignatureException;
import java.util.*;

/**
 * @author poem
 */
public class JwtHelper {


    /**
     * 创建
     *
     * @param claims
     * @param ttlMillis
     * @return
     */
    public static String createJWT(Map<String, Object> claims, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                //签发者
                .setIssuer("performer")
                //签发时间
                .setIssuedAt(now)
                //签名算法以及密匙
                .signWith(signatureAlgorithm, secretKey);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            //过期时间
            builder.setExpiration(expDate);
        }
        return "Banner " + builder.compact();
    }

    /**
     * 验证JWT
     *
     * @param jwtStr
     * @return
     */
    public static CheckResult validateJWT(String jwtStr) {
        CheckResult checkResult = new CheckResult();
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setErrCode(Constant.JWT_ERRCODE_EXPIRE);
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            checkResult.setErrCode(Constant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrCode(Constant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    /**
     * @return
     */
    private static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(Constant.JWT_SECERT);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 解析JWT字符串
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    private static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }


    /**
     * 利用jwt解析token信息.
     *
     * @param token 要解析的token信息
     * @return
     * @throws Exception
     */
    public static Optional<Claims> getClaimsFromToken(String token) throws Exception {
        Claims claims;
        SecretKey secretKey = generalKey();
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return Optional.of(claims);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof ExpiredJwtException){
                throw e;
            }else if(e instanceof SignatureException){
                throw e;
            }
            return Optional.empty();
        }
    }

    /**
     * 获取tooken中的参数值
     *
     * @param token 要解析的token信息
     * @return
     * @throws Exception
     */
    public static Map<String, Object> extractInfo(String token) throws Exception {
        token = token.replaceAll("Banner ", "");
        Optional<Claims> claims = getClaimsFromToken(token);
        if (claims.isPresent()) {
            Map<String, Object> info = new HashMap<String, Object>(100);
            Set<String> keySet = claims.get().keySet();
            //通过迭代，提取token中的参数信息
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = claims.get().get(key);
                info.put(key, value);

            }
            return info;
        }
        return null;
    }
}
