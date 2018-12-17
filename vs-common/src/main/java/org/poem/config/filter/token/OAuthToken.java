package org.poem.config.filter.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author poem
 */
public class OAuthToken implements AuthenticationToken {

    private String token;

    public OAuthToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
