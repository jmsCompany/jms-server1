package com.jms.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.db.GroupMembers;
import com.jms.domain.db.Users;
import com.jms.repositories.user.UsersRepository;


@Service
public class SecurityUtils {
	@Autowired
	private  UsersRepository usersRepository;
	private static final Logger logger = LogManager.getLogger(SecurityUtils.class.getCanonicalName());
	
	public String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof UserDetails) {
            return ((JMSUserDetails) auth.getPrincipal()).getLogin();
        } else {
            return auth.getPrincipal().toString();
        }
    }
	public Users getCurrentDBUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userid;
        if (auth.getPrincipal() instanceof UserDetails) {
        	userid =((JMSUserDetails) auth.getPrincipal()).getIdUser();
        } else {
        	userid = null;
        }
        
       return usersRepository.findOne(userid);
    }
	public JMSUserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            return ((JMSUserDetails) auth.getPrincipal());
        } else {
            return null;
        }
    }

	
	@Transactional(readOnly=true)
	public  Collection<GrantedAuthority> getAuthorities(Long idUser) {
		
		List<GrantedAuthority> l = new ArrayList<GrantedAuthority>();
		Users user = usersRepository.findOne(idUser);
		if(user == null) {
			return l;
		}
		
		Map<String,String> roles = new HashMap<String,String>();
		Map<Long,String> groups = new HashMap<Long,String>();
		//todo:
	   for(final GroupMembers gm : user.getGroupMemberses()) {
		   
		   if(groups.containsKey(gm.getId().getIdGroup()))
			   continue;
		   groups.put(gm.getId().getIdGroup(), ""+gm.getId().getIdGroup());
			l.add( new GrantedAuthority() {
				private static final long serialVersionUID = 1L;
				
				@Override
				public String getAuthority() {
					return ""+gm.getId().getIdGroup();
				}
				
				@Override
				public String toString() {
					return getAuthority();
				}
			});
			String role = gm.getRoles().getRole();
			if(!roles.containsKey(role))
			{
				roles.put(role, role);
				l.add( new GrantedAuthority() {
					private static final long serialVersionUID = 1L;
					
					@Override
					public String getAuthority() {
						return role;
					}
					
					@Override
					public String toString() {
						return getAuthority();
					}
				});
			}
		
		}
		
		return l;		
	}	
	
	
    
}