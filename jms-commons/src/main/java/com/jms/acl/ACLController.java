package com.jms.acl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jms.repositories.user.UsersRepository;

@RestController
@Transactional
public class ACLController {
	private static Logger logger = LoggerFactory.getLogger(ACLController.class);


	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private SecuredObjService securedObjService;
	
	@Autowired
	private SecuredObjDAO securedObjDAO;
    
    public static Permission getPermissionFromNumber(int permNum) {
    	switch(permNum) {
    	case 0: return BasePermission.READ; //1
    	case 1: return BasePermission.WRITE; //2
    	case 2: return BasePermission.CREATE; //4
    	case 3: return BasePermission.DELETE; //8
    	case 4: return BasePermission.ADMINISTRATION; //16
    	default: return null;
    	}
    }
	


}
