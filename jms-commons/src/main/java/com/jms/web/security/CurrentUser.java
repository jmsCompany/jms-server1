package com.jms.web.security;

import org.springframework.security.core.authority.AuthorityUtils;

import com.jms.domain.db.Users;


public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private Users user;

    public CurrentUser(Users user,String userName) {
    	
        super(userName, user.getPassword(), SecurityUtils.getAuthorities(userName));
        this.user = user;
        System.out.println("usrnameL : " +userName+", passwordL :" + user.getPassword());
    }

    public Users getUser() {
        return user;
    }

    public Integer getId() {
        return user.getIdUser();
    }
/**
    public Role getRole() {
        return user.getRole();
    }
**/
    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}