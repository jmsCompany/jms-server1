package com.jms.web.security;

public interface TokenUtils {
    String getToken(JMSUserDetails userDetails);
    String getToken(JMSUserDetails userDetails, Long expiration);
    boolean validate(String token);
    JMSUserDetails getUserFromToken(String token);
}