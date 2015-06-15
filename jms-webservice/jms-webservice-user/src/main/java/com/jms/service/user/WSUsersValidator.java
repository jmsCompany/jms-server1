package com.jms.service.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.jms.domain.ws.WSUser;

@Component
public class WSUsersValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		 return WSUser.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		WSUser u = (WSUser) target;
		String email = u.getEmail();
		String username= u.getUsername();
		String mobile = u.getMobile();
		if ((email==null||email.isEmpty())
				&&(username==null||username.isEmpty())
				&&(mobile==null||mobile.isEmpty())) {
			errors.rejectValue("username", "error","至少一项不能为空！");
		}
	}

}
