package org.poem;

/**
 * @author poem
 */
public class Constant {

    /**
     * Token过期
     */
    public static final int JWT_ERRCODE_EXPIRE = 4001;

    /**
     * 验证不通过
     */
    public static final int JWT_ERRCODE_FAIL = 4002;

    /**
     * JWT
     * 密匙
     */
    public static final String JWT_SECERT = "GNIOADGM43413RMLKADFI4SFNK43MKL14353M3KL5J353=";

    /**
     * token有效时间,10分钟
     */
    public static final long JWT_TTL = 10 * 60 * 1000;

    /**
     * user key
     */
    public static final String JWT_CLAIM_KEY = "USER";


    /**
     * 权限的key
     */
    public static final String AUTHORIZATION = "Authorization";

}
