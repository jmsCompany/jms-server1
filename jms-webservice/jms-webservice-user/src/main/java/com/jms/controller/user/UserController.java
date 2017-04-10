package com.jms.controller.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.jms.acl.SecuredObjectService;
import com.jms.domain.GroupTypeEnum;
import com.jms.domain.SandVikRoleEnum;
import com.jms.domain.db.Apps;
import com.jms.domain.db.GroupAuthorities;
import com.jms.domain.db.GroupMembers;
import com.jms.domain.db.Groups;
import com.jms.domain.db.PCPp;
import com.jms.domain.db.PCheckTime;
import com.jms.domain.db.PCppOp;
import com.jms.domain.db.PCppOpId;
import com.jms.domain.db.PRoutineDAtt;
import com.jms.domain.db.PRoutineDCategory;
import com.jms.domain.db.Roles;
import com.jms.domain.db.Users;
import com.jms.domain.ws.Valid;
import com.jms.domain.ws.WSAndriodMenuItem;
import com.jms.domain.ws.WSFileMeta;
import com.jms.domain.ws.WSMenu;
import com.jms.domain.ws.WSRoles;
import com.jms.domain.ws.WSSelectObj;
import com.jms.domain.ws.WSTableData;
import com.jms.domain.ws.WSUser;
import com.jms.domain.ws.WSUserProfile;
import com.jms.domain.ws.WSUserRoles;
import com.jms.domain.ws.WSUserPassword;
import com.jms.domain.ws.m.WSMachine;
import com.jms.domain.ws.p.WSPCppAndriod;
import com.jms.domain.ws.p.WSPCppOP;
import com.jms.domain.ws.p.WSPWo;
import com.jms.domainadapter.UserAdapter;
import com.jms.repositories.p.PCPpRepository;
import com.jms.repositories.p.PCppOpRepository;
import com.jms.repositories.system.AppsRepository;
import com.jms.repositories.user.GroupMemberRepository;
import com.jms.repositories.user.GroupRepository;
import com.jms.repositories.user.GroupTypeRepository;
import com.jms.repositories.user.RoleRepository;
import com.jms.repositories.user.UsersRepository;
import com.jms.service.user.GroupService;
import com.jms.service.user.UserService;
import com.jms.service.user.WSUsersValidator;
import com.jms.web.security.JMSUserDetails;
import com.jms.web.security.SecurityUtils;

@RestController
@Transactional(readOnly=true)
public class UserController {
	
	@Autowired @Qualifier("userService")
	private UserService userService;
	@Autowired 
	private GroupService groupService;
	
	@Autowired 
	private UserAdapter userAdapter;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private WSUsersValidator wsUsersValidator;
	@Autowired
	private AppsRepository appsRepository;
	@Autowired
	private SecuredObjectService securedObjectService;
	@Autowired
	private GroupMemberRepository groupMemberRepository;
	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	private PCPpRepository pCPpRepository;
	@Autowired
	private PCppOpRepository pCppOpRepository;
	
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private  GroupTypeRepository groupTypeRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
//	@Autowired
//	private GroupAuthoritiesRepository groupAuthoritiesRepository;
	
	/*
	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.addValidators(wsUsersValidator);
	}
	*/

	@Transactional(readOnly=false)
	@RequestMapping(value="/u/saveSelectedCppList", method=RequestMethod.POST)
	public WSUserProfile saveSelectedCppList(@RequestBody List<WSPCppOP> cppsOps) throws Exception {
	{

		List<PCPp> cppList = new ArrayList<PCPp>();
		for(WSPCppOP w :cppsOps)
		{
			PCppOpId id = new PCppOpId(); 
			id.setIdCpp(w.getIdCpp());
			id.setIdUser(securityUtils.getCurrentDBUser().getIdUser());
			PCppOp co = pCppOpRepository.findOne(id);
			if(w.isChecked())
			{
				if(co==null)
				{
					co = new PCppOp(id);
					pCppOpRepository.save(co);
				}
				cppList.add(pCPpRepository.findOne(w.getIdCpp()));
			}
			else 
			{
				if(co!=null)
				{
					pCppOpRepository.delete(co);
				}
			}
			

		}


		WSUserProfile userProfile = new WSUserProfile();
	
		Users u =securityUtils.getCurrentDBUser();
		userProfile.setLogin(u.getUsername());
		userProfile.setToken(u.getToken());
		userProfile.setIdUser(u.getIdUser());
		//userProfile.setLogoURL("www.sandvik.com");
		userProfile.setIdCompany(u.getCompany().getIdCompany());
		userProfile.setName(u.getName());
		userProfile.setDepartment("");
		Boolean isOP= false;
		userProfile.setIsAdmin(false);
		for(GroupMembers g:u.getGroupMemberses())
		{
			//System.out.println("g: " + g.getGroups().getGroupName());
			if(g.getGroups().getGroupName().equals("admin"))
			{
				userProfile.setIsAdmin(true);
			}
			
//			g.getGroups().getGroupType().getIdGroupType().equals(5l)&&
			if(g.getGroups().getGroupName().equals("OP"))
			{
				isOP=true;
				Long companyId= u.getCompany().getIdCompany();
				Long userId = u.getIdUser();
				
			
				List<WSPCppAndriod> cpps = new ArrayList<WSPCppAndriod>();
		
				
				List<PCppOp> cops = pCppOpRepository.getByUserId(securityUtils.getCurrentDBUser().getIdUser());
				//System.out.println("user id: " + securityUtils.getCurrentDBUser().getIdUser());
				List<PCPp> allCpps = new ArrayList<PCPp>();
               for(PCppOp o :cops)
               {
            	  // System.out.println("idCpp: " + o.getId().getIdCpp());
            	   PCPp c = pCPpRepository.getOne(o.getId().getIdCpp());
            	   if(c==null)
            		   continue;
            	   if(c.getActFt()==null)
            	   {
            		   allCpps.add(c);
            	   }
               }
				
				
				for( PCPp cpp:allCpps)
				{
					
					WSPCppAndriod m = new WSPCppAndriod();
					m.setChecklistId("001"); //需要修改
					m.setCppId(cpp.getIdCPp());
					if(cpp.getActFt()!=null)
					m.setActFt(cpp.getActFt().getTime());
					if(cpp.getActSt()!=null)
						m.setActSt(cpp.getActSt().getTime());
					
					//产品图纸
					if(cpp.getPRoutineD()!=null)
					{
						if(cpp.getPRoutineD().getPRoutine()!=null)
						{
							if(cpp.getPRoutineD().getPRoutine().getPDraw()!=null)
							{
								if(cpp.getPRoutineD().getPRoutine().getPDraw().getDrawAtt()!=null)
								{
									WSFileMeta f = new WSFileMeta();
									f.setName(cpp.getPRoutineD().getPRoutine().getPDraw().getDrawAtt());
									f.setDescription("产品图纸");
									m.getFiles().add(f);
									m.setDrawNo(cpp.getPRoutineD().getPRoutine().getPDraw().getDrawNo());
									m.setDrawVer(cpp.getPRoutineD().getPRoutine().getPDraw().getDrawVer());
								}
							}
							
						}
						
						
						//工种
						for(PRoutineDCategory pr:cpp.getPRoutineD().getPRoutineDCategories())
						{
							m.getCategories().add(pr.getClass().getSimpleName());
						}
					}
					
				
				 
					m.setFt(cpp.getPlanFt().getTime());
					m.setSt(cpp.getPlanSt().getTime());
					
				//	System.out.println("plan st: " + cpp.getPlanFt().getTime() +", plan ft: " + cpp.getPlanFt().getTime());
					
					if(cpp.getMMachine()!=null)
					{
						if(cpp.getMMachine().getPLine()!=null)
						{
							m.setLine(cpp.getMMachine().getPLine().getPline());
						}
						m.setmNo(cpp.getMMachine().getCode());
						m.setIdMachine(cpp.getMMachine().getIdMachine());
					}
				
				
		
					m.setOp(cpp.getUsers().getName());
					m.setpNo(cpp.getPWo().getSSo().getSMaterial().getPno());
					m.setQty(cpp.getQty());
					
					if(cpp.getPRoutineD()!=null)
					{
						m.setRoute(cpp.getPRoutineD().getRouteNo());
						//m.setRouteId(cpp.getPRoutineD().get);//图纸
						m.setRouteDes(cpp.getPRoutineD().getDes());
						//附件
						for(PRoutineDAtt pa:cpp.getPRoutineD().getPRoutineDAtts())
						{
							WSFileMeta f = new WSFileMeta();
							f.setDescription(pa.getPAttDraw().getName());
							f.setName(pa.getPAttDraw().getFilename());
							m.getFiles().add(f);
						}
						m.setStdWtLabor(cpp.getPRoutineD().getStdWtLabor());
						m.setStdWtMachine(cpp.getPRoutineD().getStdWtMachine());
						m.setStdWtSetup(cpp.getPRoutineD().getStdWtSetup());
					}
				
					m.setWoNo(cpp.getPWo().getWoNo());
					m.setShift(cpp.getPShiftPlanD().getShift());
					m.setSt(cpp.getPlanSt().getTime());
				
				
					
					int i=0;
					for(PCheckTime pc :cpp.getMMachine().getPCheckTimes())
					{
						if(i>0)
							break;
						Long checkInterval = pc.getInterval1();
						Long  intervalType = pc.getPUTime().getIdUTime(); //1分钟，2小时， 3天
						if(intervalType.equals(2l))
						{
							checkInterval=checkInterval*60;
						}
						else if(intervalType.equals(3l))
						{
							checkInterval=checkInterval*((cpp.getPlanFt().getTime()-cpp.getPlanSt().getTime())/1000*60);
						}
						m.setCheckInterval(checkInterval);
						m.setCheckIntervalType(intervalType);
						i++;
					}
			
					cpps.add(m);
				}
				userProfile.setPcppList(cpps);
			}
		}
		
		userProfile.setIsOP(isOP);
		List<Apps> appList =appsRepository.findAll();
		Map<Apps, String> smap= securedObjectService.getSecuredObjectsWithPermissions(u, appList);
		List<WSMenu> WSMenuList = new ArrayList<WSMenu>();
		for(Apps a : smap.keySet())
		{
			WSMenu item = new WSMenu();
			if(u.getLang()!=null&&u.getLang().equals("en"))
			{
				item.setGroup(a.getGroupsEn());
				item.setName(a.getAppNameEn());
			}
			else
			{
				item.setGroup(a.getGroups());
				item.setName(a.getAppName());
			}
			
			
			item.setId(a.getIdApp());
			item.setUrl(a.getUrl());
			item.setPermission(smap.get(a));
			WSMenuList.add(item);
		}
		userProfile.setWSMenuList(WSMenuList);
		return userProfile;
	}
	
	}
	

	@RequestMapping(value="/u/updateUserProfile", method=RequestMethod.GET)
	public WSUserProfile updateUserProfile() throws Exception {
		

		WSUserProfile userProfile = new WSUserProfile();
	
		Users u =securityUtils.getCurrentDBUser();
		userProfile.setLogin(u.getUsername());
		userProfile.setToken(u.getToken());
		userProfile.setIdUser(u.getIdUser());
		//userProfile.setLogoURL("www.sandvik.com");
		userProfile.setIdCompany(u.getCompany().getIdCompany());
		userProfile.setName(u.getName());
		userProfile.setDepartment("");
		Boolean isOP= false;
		userProfile.setIsAdmin(false);
		for(GroupMembers g:u.getGroupMemberses())
		{
			//System.out.println("g: " + g.getGroups().getGroupName());
			if(g.getGroups().getGroupName().equals("admin"))
			{
				userProfile.setIsAdmin(true);
			}
			
//			g.getGroups().getGroupType().getIdGroupType().equals(5l)&&
			if(g.getGroups().getGroupName().equals("OP"))
			{
				isOP=true;
				Long companyId= u.getCompany().getIdCompany();
				Long userId = u.getIdUser();
			
				List<WSPCppAndriod> cpps = new ArrayList<WSPCppAndriod>();
			//	System.out.println("取得日计划。。。。");

		int num=0;
				
				Map<Long,PCPp> cppFinalMap = new LinkedHashMap<Long,PCPp>();
				
				List<PCPp> cppList = pCPpRepository.getByCompanyIdAndUserId(companyId, userId);
				
				for(PCPp cpp:cppList)
				{
					num++;
					if(num>1&&cpp.getPlanSt().getTime()>(new Date().getTime())+86400000) //忽略大于明天的cpp
					{
						continue;
					}
					Long machineId =  cpp.getMMachine().getIdMachine();
					cppFinalMap.put(cpp.getIdCPp(), cpp);
					List<PCPp> others = pCPpRepository.getStartedCppBymachineId(machineId);
					for(PCPp o: others)
					{
						cppFinalMap.put(o.getIdCPp(), o);
					}
					
				}
				
				for(PCPp cpp:cppFinalMap.values())
				{
				
					Long machineId =  cpp.getMMachine().getIdMachine();
					cppFinalMap.put(cpp.getIdCPp(), cpp);
					List<PCPp> others = pCPpRepository.getStartedCppBymachineId(machineId);
					for(PCPp o: others)
					{
						cppFinalMap.put(o.getIdCPp(), o);
					}
					
				}
				
				
				for( PCPp cpp:cppFinalMap.values())
				{
					
					WSPCppAndriod m = new WSPCppAndriod();
					m.setChecklistId("001"); //需要修改
					m.setCppId(cpp.getIdCPp());
					if(cpp.getActFt()!=null)
					m.setActFt(cpp.getActFt().getTime());
					if(cpp.getActSt()!=null)
						m.setActSt(cpp.getActSt().getTime());
					
					//产品图纸
					if(cpp.getPRoutineD()!=null)
					{
						if(cpp.getPRoutineD().getPRoutine()!=null)
						{
							if(cpp.getPRoutineD().getPRoutine().getPDraw()!=null)
							{
								if(cpp.getPRoutineD().getPRoutine().getPDraw().getDrawAtt()!=null)
								{
									WSFileMeta f = new WSFileMeta();
									f.setName(cpp.getPRoutineD().getPRoutine().getPDraw().getDrawAtt());
									f.setDescription("产品图纸");
									m.getFiles().add(f);
									m.setDrawNo(cpp.getPRoutineD().getPRoutine().getPDraw().getDrawNo());
									m.setDrawVer(cpp.getPRoutineD().getPRoutine().getPDraw().getDrawVer());
								}
							}
							
						}
						
						
						//工种
						for(PRoutineDCategory pr:cpp.getPRoutineD().getPRoutineDCategories())
						{
							m.getCategories().add(pr.getClass().getSimpleName());
						}
					}
					
				
				 
					m.setFt(cpp.getPlanFt().getTime());
					m.setSt(cpp.getPlanSt().getTime());
					
				//	System.out.println("plan st: " + cpp.getPlanFt().getTime() +", plan ft: " + cpp.getPlanFt().getTime());
					
					if(cpp.getMMachine()!=null)
					{
						if(cpp.getMMachine().getPLine()!=null)
						{
							m.setLine(cpp.getMMachine().getPLine().getPline());
						}
						m.setmNo(cpp.getMMachine().getCode());
						m.setIdMachine(cpp.getMMachine().getIdMachine());
					}
				
				
		
					m.setOp(cpp.getUsers().getName());
					m.setpNo(cpp.getPWo().getSSo().getSMaterial().getPno());
					m.setQty(cpp.getQty());
					
					if(cpp.getPRoutineD()!=null)
					{
						m.setRoute(cpp.getPRoutineD().getRouteNo());
						//m.setRouteId(cpp.getPRoutineD().get);//图纸
						m.setRouteDes(cpp.getPRoutineD().getDes());
						//附件
						for(PRoutineDAtt pa:cpp.getPRoutineD().getPRoutineDAtts())
						{
							WSFileMeta f = new WSFileMeta();
							f.setDescription(pa.getPAttDraw().getName());
							f.setName(pa.getPAttDraw().getFilename());
							m.getFiles().add(f);
						}
						m.setStdWtLabor(cpp.getPRoutineD().getStdWtLabor());
						m.setStdWtMachine(cpp.getPRoutineD().getStdWtMachine());
						m.setStdWtSetup(cpp.getPRoutineD().getStdWtSetup());
					}
				
					m.setWoNo(cpp.getPWo().getWoNo());
					m.setShift(cpp.getPShiftPlanD().getShift());
					m.setSt(cpp.getPlanSt().getTime());
				
				
					
					int i=0;
					for(PCheckTime pc :cpp.getMMachine().getPCheckTimes())
					{
						if(i>0)
							break;
						Long checkInterval = pc.getInterval1();
						Long  intervalType = pc.getPUTime().getIdUTime(); //1分钟，2小时， 3天
						if(intervalType.equals(2l))
						{
							checkInterval=checkInterval*60;
						}
						else if(intervalType.equals(3l))
						{
							checkInterval=checkInterval*((cpp.getPlanFt().getTime()-cpp.getPlanSt().getTime())/1000*60);
						}
						m.setCheckInterval(checkInterval);
						m.setCheckIntervalType(intervalType);
						i++;
					}
			
					cpps.add(m);
				}
				userProfile.setPcppList(cpps);
			}
		}
		
		userProfile.setIsOP(isOP);
		List<Apps> appList =appsRepository.findAll();
		Map<Apps, String> smap= securedObjectService.getSecuredObjectsWithPermissions(u, appList);
		List<WSMenu> WSMenuList = new ArrayList<WSMenu>();
		for(Apps a : smap.keySet())
		{
			WSMenu item = new WSMenu();
			
    if(u.getLang()!=null&&u.getLang().equals("en"))
    {
    	item.setGroup(a.getGroupsEn());
		item.setName(a.getAppNameEn());
    }
    else
    {
		item.setGroup(a.getGroups());
		item.setName(a.getAppName());
    }
			item.setId(a.getIdApp());
			item.setUrl(a.getUrl());
			item.setPermission(smap.get(a));
			WSMenuList.add(item);
		}
		userProfile.setWSMenuList(WSMenuList);
		return userProfile;
	
		
	}
	
	
	@Transactional(readOnly=false)
	@RequestMapping(value="/login", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public WSUserProfile login(@RequestBody WSUser wsUser) throws Exception {
		WSUserProfile userProfile = new WSUserProfile();
		String login = wsUser.getLogin();
		System.out.println("user login: " +wsUser.getLogin() +", pass: "  +wsUser.getPassword());
		String token = userService.login(wsUser.getLogin(), wsUser.getPassword());
		if(token==null)
		{
			System.out.println("token is null????");
			return userProfile;
		}
		if(wsUser.getLang()!=null)
		{
			userProfile.setLang(wsUser.getLang());
		}
		Users u =usersRepository.findByUsernameOrEmailOrMobile(wsUser.getLogin());
		
		if(wsUser.getLang()!=null)
		{
			userProfile.setLang(wsUser.getLang());
			if(!wsUser.getLang().equals(u.getLang()))
			{
				u.setLang(wsUser.getLang());
				usersRepository.save(u);
			}
		}
		else
		{
			userProfile.setLang(u.getLang());
		}
		
		
		userProfile.setLogin(wsUser.getLogin());
		userProfile.setToken(token);
		userProfile.setIdUser(u.getIdUser());
		//userProfile.setLogoURL("www.sandvik.com");
		userProfile.setIdCompany(u.getCompany().getIdCompany());
		userProfile.setName(u.getName());
		userProfile.setDepartment("");
		
		Boolean isOP= false;
		userProfile.setIsAdmin(false);
		for(GroupMembers g:u.getGroupMemberses())
		{
			//System.out.println("g: " + g.getGroups().getGroupName());
			if(g.getGroups().getGroupName().equals("admin"))
			{
				userProfile.setIsAdmin(true);
			}
			
//			g.getGroups().getGroupType().getIdGroupType().equals(5l)&&
			if(g.getGroups().getGroupName().equals("OP"))
			{
				isOP=true;
				Long companyId= u.getCompany().getIdCompany();
				Long userId = u.getIdUser();
			
				List<WSPCppAndriod> cpps = new ArrayList<WSPCppAndriod>();
			//	System.out.println("取得日计划。。。。");

				int num=0;
				for( PCPp cpp:pCPpRepository.getByCompanyIdAndUserId(companyId, userId))
				{
					num++;
					if(num>1&&cpp.getPlanSt().getTime()>(new Date().getTime())+86400000) //忽略大于明天的cpp
					{
						continue;
					}
					WSPCppAndriod m = new WSPCppAndriod();
					m.setChecklistId("001"); //需要修改
					m.setCppId(cpp.getIdCPp());
					if(cpp.getActFt()!=null)
					m.setActFt(cpp.getActFt().getTime());
					if(cpp.getActSt()!=null)
						m.setActSt(cpp.getActSt().getTime());
					
					//产品图纸
					if(cpp.getPRoutineD()!=null)
					{
					
						if(cpp.getPRoutineD().getPRoutine()!=null)
						{
							if(cpp.getPRoutineD().getPRoutine().getPDraw()!=null)
							{
								if(cpp.getPRoutineD().getPRoutine().getPDraw().getDrawAtt()!=null)
								{
									WSFileMeta f = new WSFileMeta();
									f.setName(cpp.getPRoutineD().getPRoutine().getPDraw().getDrawAtt());
									f.setDescription("产品图纸");
									m.getFiles().add(f);
									m.setDrawNo(cpp.getPRoutineD().getPRoutine().getPDraw().getDrawNo());
									m.setDrawVer(cpp.getPRoutineD().getPRoutine().getPDraw().getDrawVer());
								}
							}
							
						}
						
						
						//工种
						for(PRoutineDCategory pr:cpp.getPRoutineD().getPRoutineDCategories())
						{
							m.getCategories().add(pr.getClass().getSimpleName());
						}
					}
					
				
				 if(cpp.getPlanFt()!=null)
				 {
						m.setFt(cpp.getPlanFt().getTime()); 
				 }
				if(cpp.getPlanSt()!=null)
				{
					m.setSt(cpp.getPlanSt().getTime());
				}
					
					
				//	System.out.println("plan st: " + cpp.getPlanFt().getTime() +", plan ft: " + cpp.getPlanFt().getTime());
					
					if(cpp.getMMachine()!=null)
					{
						if(cpp.getMMachine().getPLine()!=null)
						{
							m.setLine(cpp.getMMachine().getPLine().getPline());
						}
						m.setmNo(cpp.getMMachine().getCode());
						m.setIdMachine(cpp.getMMachine().getIdMachine());
					}
				
				
		
					m.setOp(cpp.getUsers().getName());
					
					if(cpp.getPWo()!=null)
					{
						if(cpp.getPWo().getSSo()!=null)
						{
							if(cpp.getPWo().getSSo().getSMaterial()!=null)
							{
								m.setpNo(cpp.getPWo().getSSo().getSMaterial().getPno());
							}
						}
					}
					
					m.setQty(cpp.getQty());
					
					if(cpp.getPRoutineD()!=null)
					{
						m.setRoute(cpp.getPRoutineD().getRouteNo());
						//m.setRouteId(cpp.getPRoutineD().get);//图纸
						m.setRouteDes(cpp.getPRoutineD().getDes());
						
						//附件
						for(PRoutineDAtt pa:cpp.getPRoutineD().getPRoutineDAtts())
						{
							WSFileMeta f = new WSFileMeta();
							f.setDescription(pa.getPAttDraw().getName());
							f.setName(pa.getPAttDraw().getFilename());
							m.getFiles().add(f);
						}
						if(cpp.getPRoutineD()!=null)
						{
							m.setStdWtLabor(cpp.getPRoutineD().getStdWtLabor());
							m.setStdWtMachine(cpp.getPRoutineD().getStdWtMachine());
							m.setStdWtSetup(cpp.getPRoutineD().getStdWtSetup());
						}
					
					}
				
					if(cpp.getPWo()!=null)
					{
						m.setWoNo(cpp.getPWo().getWoNo());
					}
				
					if(cpp.getPShiftPlanD()!=null)
					{
						m.setShift(cpp.getPShiftPlanD().getShift());
					}
					
					if(cpp.getPlanSt()!=null)
					{
						m.setSt(cpp.getPlanSt().getTime());
					}
					
				
				
					
					int i=0;
					for(PCheckTime pc :cpp.getMMachine().getPCheckTimes())
					{
						if(i>0)
							break;
						Long checkInterval = pc.getInterval1();
						Long  intervalType = pc.getPUTime().getIdUTime(); //1分钟，2小时， 3天
						if(intervalType.equals(2l))
						{
							checkInterval=checkInterval*60;
						}
						else if(intervalType.equals(3l))
						{
							checkInterval=checkInterval*((cpp.getPlanFt().getTime()-cpp.getPlanSt().getTime())/1000*60);
						}
						m.setCheckInterval(checkInterval);
						m.setCheckIntervalType(intervalType);
						i++;
					}
			
					cpps.add(m);
				}
				userProfile.setPcppList(cpps);
			}
		}
		
		userProfile.setIsOP(isOP);
		List<Apps> appList =appsRepository.findAll();
		Map<Apps, String> smap= securedObjectService.getSecuredObjectsWithPermissions(u, appList);
		List<WSMenu> WSMenuList = new ArrayList<WSMenu>();
		for(Apps a : smap.keySet())
		{
			WSMenu item = new WSMenu();
			
			if(u.getLang()!=null&&u.getLang().equals("en"))
			{
				item.setGroup(a.getGroupsEn());
				item.setName(a.getAppNameEn());
			}
			else
			{
				item.setGroup(a.getGroups());
				item.setName(a.getAppName());
			}
			
			item.setId(a.getIdApp());
			item.setUrl(a.getUrl());
			item.setPermission(smap.get(a));
			WSMenuList.add(item);
		}
		userProfile.setWSMenuList(WSMenuList);
		return userProfile;
	}
	
	
	@RequestMapping(value="/check/username", method=RequestMethod.GET)
	public Valid checkLogin(@RequestParam("login") String login,@RequestParam(required=false, value="idUser") Long idUser) throws Exception {
		Boolean returnVal = userService.checkLogin(login,idUser);
		System.out.println("check user name: " + login);
		Valid valid = new Valid();
		valid.setValid(returnVal);
		return valid;
	}
	
	@RequestMapping(value="/check/jmstoken", method=RequestMethod.GET)
	public Valid checkToken(@RequestParam("jmstoken") String jmstoken) throws Exception {
	//	System.out.println("check token: " + jmstoken);
		Boolean returnVal = userService.checkToken(jmstoken);
		Valid valid = new Valid();
		valid.setValid(returnVal);
		return valid;
	}
	
	
	@Transactional(readOnly=false)
	@RequestMapping(value="/user/save", method=RequestMethod.POST)
	public WSUser save(@RequestBody WSUser wsUser) throws Exception {
		return userService.save(wsUser);
	}
	
	
	
	@Transactional(readOnly=false)
	@RequestMapping(value="/user/updateInfo", method=RequestMethod.POST)
	public WSUser updateInfo(@RequestBody WSUser wsUser) throws Exception {
		return userService.updateInfo(wsUser);
	}
	
	@Transactional(readOnly=false)
	@RequestMapping(value="/user/updateUserPassword", method=RequestMethod.POST)
	public Valid updateUserPassword(@RequestBody WSUserPassword wsUserPassword) throws Exception {
		return userService.updateUserPassword(wsUserPassword);
	}
	
	

	@Transactional(readOnly=false)
	@RequestMapping(value="/user/updateUserPasswordByAdmin", method=RequestMethod.POST)
	public Valid updateUserPasswordByAdmin(@RequestBody WSUserPassword wsUserPassword) throws Exception {
		return userService.updateUserPasswordByAdmin(wsUserPassword);
	}
	
	
	@Transactional(readOnly=false)
	@RequestMapping(value="/getWSCppOPs", method=RequestMethod.GET)
	public List<WSPCppOP> getWSCppOPs(@RequestParam Long machineId) throws Exception
	{
		return userService.getWSCppOPs(machineId);
	}
	
	@Transactional(readOnly=true)
	@RequestMapping(value="/getAndroidMenu", method=RequestMethod.GET)
	public List<WSAndriodMenuItem> getAndroidMenu() throws Exception {

	
		List<WSAndriodMenuItem>  items = new ArrayList<WSAndriodMenuItem>();
		JMSUserDetails ud = securityUtils.getCurrentUser();
		//System.out.println(" get android menu: the token is:  " + ud.getToken());
		for(GrantedAuthority g: ud.getAuthorities())
		{
			Groups opGroup = groupRepository.findGroupByGroupNameAndCompany(SandVikRoleEnum.OP.name(), securityUtils.getCurrentDBUser().getCompany().getIdCompany(),GroupTypeEnum.Role.name());
			
			if(opGroup!=null&&g.getAuthority().equals(""+opGroup.getIdGroup()))
			{
				
			
				WSAndriodMenuItem i1 = new WSAndriodMenuItem("OP","操作员","MRstopforOP","MRstopforOP","需料停机");
				WSAndriodMenuItem i2 = new WSAndriodMenuItem("OP","操作员","Qtycheck","Qtycheck","到点检查");
				WSAndriodMenuItem i3 = new WSAndriodMenuItem("OP","操作员","Stopbyplan","Stopbyplan","计划停机");
				WSAndriodMenuItem i4 = new WSAndriodMenuItem("OP","操作员","Actualsetup","Actualsetup","实际装载");
				WSAndriodMenuItem i5 = new WSAndriodMenuItem("OP","操作员","Checklist","Checklist","非计划点料");
				WSAndriodMenuItem i6 = new WSAndriodMenuItem("OP","操作员","Eqstopforop","Eqstopforop","设备故障");
				//WSAndriodMenuItem i7 = new WSAndriodMenuItem("OP","操作员","Ehslogin","Ehslogin","EHS登录");
				WSAndriodMenuItem i8 = new WSAndriodMenuItem("OP","操作员","Thinkthroughthetask","Thinkthroughthetask","安全检查");
				WSAndriodMenuItem i9 = new WSAndriodMenuItem("OP","操作员","Maintenanceforop","Maintenanceforop","日常保养");
				items.add(i1);
				items.add(i2);
				items.add(i3);
				items.add(i4);
				items.add(i5);
				items.add(i6);
				//items.add(i7);
				items.add(i8);
				items.add(i9);
				break;
			}
			Groups equipmentGroup = groupRepository.findGroupByGroupNameAndCompany(SandVikRoleEnum.equipment.name(), securityUtils.getCurrentDBUser().getCompany().getIdCompany(),GroupTypeEnum.Role.name());
		//System.out.println("");
			
			if(equipmentGroup!=null&&g.getAuthority().equals(""+equipmentGroup.getIdGroup()))
			{
				//System.out.println(" get equipment: ");
				WSAndriodMenuItem i1 = new WSAndriodMenuItem("equipment","设备","Unplanned_equipment","Unplanned_equipment","设备停机");
				WSAndriodMenuItem i2 = new WSAndriodMenuItem("equipment","设备","Eqstopforeq","Eqstopforeq","设备维修");
				WSAndriodMenuItem i3 = new WSAndriodMenuItem("equipment","设备","Maintainance","Maintainance","设备保养");
				
				items.add(i1);
				items.add(i2);
				items.add(i3);
				
				break;
			}
			
			Groups qualityGroup = groupRepository.findGroupByGroupNameAndCompany(SandVikRoleEnum.quality.name(), securityUtils.getCurrentDBUser().getCompany().getIdCompany(),GroupTypeEnum.Role.name());
			if(qualityGroup!=null&&g.getAuthority().equals(""+qualityGroup.getIdGroup()))
			{
				//System.out.println(" get quality: ");
				WSAndriodMenuItem i1 = new WSAndriodMenuItem("quality","质量","Unplanned_quality","Unplanned_quality","质量停机");
				WSAndriodMenuItem i2 = new WSAndriodMenuItem("quality","质量","Problem_des","Problem_des","质量管理");
				items.add(i1);
				items.add(i2);
				break;
			}
			Groups warehouseGroup = groupRepository.findGroupByGroupNameAndCompany(SandVikRoleEnum.warehouse.name(), securityUtils.getCurrentDBUser().getCompany().getIdCompany(),GroupTypeEnum.Role.name());
			if(warehouseGroup!=null&&g.getAuthority().equals(""+warehouseGroup.getIdGroup()))
			{
				//System.out.println(" get warehouse: ");
				WSAndriodMenuItem i1 = new WSAndriodMenuItem("warehouse","仓库","Mrforwh","Mrforwh","需料配料");
				WSAndriodMenuItem i2 = new WSAndriodMenuItem("warehouse","仓库","Unplanned_ms","Unplanned_ms","缺料停机");
				WSAndriodMenuItem i3 = new WSAndriodMenuItem("warehouse","仓库","Msstopforwh","Msstopforwh","缺料配料");
				WSAndriodMenuItem i4 = new WSAndriodMenuItem("warehouse","仓库","Returnrm","Returnrm","手动流转");
				WSAndriodMenuItem i5 = new WSAndriodMenuItem("warehouse","仓库","Receiving2","Receiving2","新建来料");
				WSAndriodMenuItem i6 = new WSAndriodMenuItem("warehouse","仓库","Receiving3","Receiving3","检验入库");
				WSAndriodMenuItem i7 = new WSAndriodMenuItem("warehouse","仓库","Delivery","Delivery","出货管理");
				WSAndriodMenuItem i8 = new WSAndriodMenuItem("warehouse","仓库","RTV","RTV","采购退货");
				WSAndriodMenuItem i9 = new WSAndriodMenuItem("warehouse","仓库","RFC","RFC","销售退货");
				WSAndriodMenuItem i10 = new WSAndriodMenuItem("warehouse","仓库","Inventorysummary","Inventorysummary","库存汇总");
				WSAndriodMenuItem i11 = new WSAndriodMenuItem("warehouse","仓库","Inventorydetail","Inventorydetail","库存明细");
				WSAndriodMenuItem i12 = new WSAndriodMenuItem("warehouse","仓库","pmreport","pmreport","计划发料");
				items.add(i1);
				items.add(i2);
				items.add(i3);
				items.add(i4);
				items.add(i5);
				items.add(i6);
				items.add(i7);
				items.add(i8);
				items.add(i9);
				items.add(i10);
				items.add(i11);
				items.add(i12);
				break;
			}
			Groups supervisorGroup = groupRepository.findGroupByGroupNameAndCompany(SandVikRoleEnum.supervisor.name(), securityUtils.getCurrentDBUser().getCompany().getIdCompany(),GroupTypeEnum.Role.name());
			if(supervisorGroup!=null&&g.getAuthority().equals(""+supervisorGroup.getIdGroup()))
			{
				//System.out.println(" get supervisor: ");
				WSAndriodMenuItem i1 = new WSAndriodMenuItem("supervisor","主管","MRstopforOP","MRstopforOP","需料与停机（操作工人的主界面");
				WSAndriodMenuItem i2 = new WSAndriodMenuItem("supervisor","主管","Qtycheck","Qtycheck","到点检查已经生产的数量");
				WSAndriodMenuItem i3 = new WSAndriodMenuItem("supervisor","主管","Stopbyplan","Stopbyplan","按计划停机");
				WSAndriodMenuItem i4 = new WSAndriodMenuItem("supervisor","主管","Actualsetup","Actualsetup","实际装载时间记录");
				WSAndriodMenuItem i5 = new WSAndriodMenuItem("supervisor","主管","Checklist","Checklist","检查清单");
				WSAndriodMenuItem i6 = new WSAndriodMenuItem("supervisor","主管","Eqstopforop","Eqstopforop","由操作工人填写设备故障原因");
				WSAndriodMenuItem i7 = new WSAndriodMenuItem("supervisor","主管","Thinkthroughthetaskfordirector","Thinkthroughthetaskfordirector","检查操作工人的安全检查情况");
				
			    items.add(i1);
				items.add(i2);
				items.add(i3);
				items.add(i4);
				items.add(i5);
				items.add(i6);
				items.add(i7);
			
				break;
			}
		}
		return items;
	}
	

	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/group/op/members", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<WSSelectObj> findOPByCompanyId()
	{
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		List<Users> users = groupMemberRepository.findOPByCompanyId(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		for(Users u : users)
		{
			WSSelectObj w = new WSSelectObj(u.getIdUser(),u.getName());
			ws.add(w);
		}
		return ws;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/u/getUsersByQ", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<WSSelectObj> getUsersByQ(@RequestParam(value="q",required=false) String q)
	{
	    
		List<WSSelectObj> ws = new ArrayList<WSSelectObj>();
		List<Users> users;
		if(q==null)
		{
			users= usersRepository.findUsersByIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		}
		else
		{
			q = "%" +q+"%";
			users=usersRepository.findUsersByIdCompanyAndQ(securityUtils.getCurrentDBUser().getCompany().getIdCompany(), q);
		}
		for(Users u : users)
		{
			
		//	System.out.println("id: " + u.getIdUser() +", name: " + u.getName());
			WSSelectObj w = new WSSelectObj(u.getIdUser(),u.getName());
			
			ws.add(w);
		}
		return ws;
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/u/userList", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<WSUser> findUsersByCompanyId() throws Exception
	{
		List<WSUser> ws = new ArrayList<WSUser>();
		List<Users> users = usersRepository.findUsersByIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());
		for(Users u : users)
		{
			ws.add(userAdapter.toWSUser(u));
		}
		return ws;
	}
	

	
	@Transactional(readOnly = true)
	@RequestMapping(value="/u/userTable", method=RequestMethod.POST)
	public WSTableData  findUserTableByCompanyId(@RequestParam Integer draw,@RequestParam Integer start,@RequestParam Integer length) throws Exception {	   
		
		List<Users> users = usersRepository.findUsersByIdCompany(securityUtils.getCurrentDBUser().getCompany().getIdCompany());

		List<String[]> lst = new ArrayList<String[]>();
		int end=0;
		if(users.size()<start + length)
			end =users.size();
		else
			end =start + length;
		for (int i = start; i < end; i++) {
			Users w = users.get(i);
			String name = (w.getName()==null)?"":w.getName();
			String mobile = (w.getMobile()==null)?"":w.getMobile();
			String email = (w.getEmail()==null)?"":w.getEmail();
			String username = (w.getUsername()==null)?"":w.getUsername();
//			if(username.contains("@@"))
//			{
//				username = username.substring(0,username.indexOf("@@"));
//			}
			String gender = (w.getSysDicDByGender()==null)?"":w.getSysDicDByGender().getName();
				
			String[] d = {name,gender,username,mobile,email,""+w.getIdUser()};
			lst.add(d);

		}
		WSTableData t = new WSTableData();
		t.setDraw(draw);
		t.setRecordsTotal(users.size());
		t.setRecordsFiltered(users.size());
	    t.setData(lst);
	    return t;
	}
	
	
	
	@Transactional(readOnly = false)
	@RequestMapping(value="/u/addRoles", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public Valid addRolesforUser( @RequestBody WSUserRoles wsUserRoles) throws Exception
	{
		return userService.addRolesforUser(wsUserRoles);
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/u/getRolesByUserId", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<WSRoles> getRolesByUserId(@RequestParam Long userId) throws Exception
	{
		return userService.getRolesByUserId(userId);
	}
	
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/u/getWSMenuList", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<WSMenu> getWSMenuList() throws Exception
	{
		List<Apps> appList =appsRepository.findAll();
		Users u =securityUtils.getCurrentDBUser();
		List<WSMenu> wsMenuList = new ArrayList<WSMenu>();
		Map<Apps, String> smap= securedObjectService.getSecuredObjectsWithPermissions(u, appList);
		
	
		for(Apps a : smap.keySet())
		{
			WSMenu item = new WSMenu();
			if(u.getLang()!=null&&u.getLang().equals("en"))
			{
				item.setGroup(a.getGroupsEn());
				item.setName(a.getAppNameEn());
			}
			else
			{
				item.setGroup(a.getGroups());
				item.setName(a.getAppName());
			}
			
			item.setId(a.getIdApp());
			item.setUrl(a.getUrl());
		
			wsMenuList.add(item);
		}
		
		return wsMenuList;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/u/getWSMenuListByRoleId", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<WSMenu> getWSMenuListByRoleId(@RequestParam Long roleId) throws Exception
	{
		
		Roles r = roleRepository.findOne(roleId);
		Groups g = groupRepository.findGroupByGroupNameAndCompany(r.getRole(), securityUtils.getCurrentDBUser().getCompany().getIdCompany(), GroupTypeEnum.Role.name());
		Users u =securityUtils.getCurrentDBUser();
		List<Apps> appList =appsRepository.findAll();
		
		List<WSMenu> wsMenuList = new ArrayList<WSMenu>();
		Map<Apps, String> smap= securedObjectService.getSecuredObjectsWithPermissions(g, appList);
		
		for(Apps a : smap.keySet())
		{
			WSMenu item = new WSMenu();
			if(u.getLang()!=null&&u.getLang().equals("en"))
			{
				item.setGroup(a.getGroupsEn());
				item.setName(a.getAppNameEn());
			}
			else
			{
				item.setGroup(a.getGroups());
				item.setName(a.getAppName());
			}
			
			item.setId(a.getIdApp());
			item.setUrl(a.getUrl());
			item.setPermission(smap.get(a));
			wsMenuList.add(item);
		}
		
		return wsMenuList;
	}
	
	
	@Transactional(readOnly = true)
	@RequestMapping(value="/u/getUserByUserId", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public WSUser getUserByUserId(@RequestParam Long userId) throws Exception
	{
		return userService.getUsersByIdUser(userId);
	}
	
	
	


}