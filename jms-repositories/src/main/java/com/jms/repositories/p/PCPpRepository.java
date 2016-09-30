package com.jms.repositories.p;


import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jms.domain.db.PCPp;


@Repository
public interface PCPpRepository extends JpaRepository<PCPp, Long>{

    @Query("select p from PCPp p where p.company.idCompany=?1 order by p.planSt desc")
	public List<PCPp> getByCompanyId(Long companyId);
    

    @Query("select p from PCPp p where p.company.idCompany=?1 and p.PWo.idWo in ?2 order by p.PWo.idWo desc,p.PRoutineD.idRoutineD asc,p.planSt desc")
	public List<PCPp> getByCompanyIdAndWoIds(Long companyId,List<Long> woIds);
    
//    DATE(p.planSt)=CURDATE()
//    and now()+86400000>p.plantSt 
//   DATE_ADD(now(), INTERVAL 1 DAY)>  明天
    @Query("select p from PCPp p where p.company.idCompany=?1 and p.users.idUser=?2 and p.actFt is null order by p.planSt")
	public List<PCPp> getByCompanyIdAndUserId(Long companyId,Long userId);
    
    
    @Query("select p from PCPp p where p.MMachine.idMachine=?1 and p.actFt is null order by p.planSt")
 	public List<PCPp> getNotFinishedCppsByMachineId(Long machineId);
    
    
    @Query("select p from PCPp p where p.MMachine.idMachine=?1 and p.actSt is not null and p.actFt is null order by p.planSt")
	public List<PCPp> getStartedCppBymachineId(Long machineId);
    
    
    @Query("select p from PCPp p where p.PWo.idWo=?1 and p.planSt<now()")
	public List<PCPp> getShouldStartedByWoId(Long woId);
    
    @Query("select p from PCPp p where p.PRoutineD.idRoutineD=?1 and p.actFt is not null")
  	public List<PCPp> getFinishedCppByRoutineDId(Long routineDId);
    
    @Query("select p from PCPp p where  p.company.idCompany=?1 and p.planSt>?2 and p.planFt<?3 order by p.PWo.idWo")
  	public List<PCPp> getByFromDateToDate(Long companyId,Date st,Date ft);
    
    @Query("select p from PCPp p where p.company.idCompany=?1 and p.planSt>?2 and p.planFt<?3 and  p.MMachine.idMachine=?4 and p.PWo.SSo.SMaterial.idMaterial=?5")
  	public List<PCPp> getByFromDateToDateAndMachineIdAndMaterialId(Long companyId,Date st,Date ft,Long machineId,Long materialId);
    
    @Query("select p from PCPp p where p.company.idCompany=?1 and p.MMachine.idMachine=?2 and p.planSt>?3 and p.planSt<?4")
  	public List<PCPp> getByMachineIdAndDate(Long companyId,Long machineId,Date st, Date ft);
  
    @Query("select p from PCPp p where p.MMachine.idMachine=?1 and p.actSt<?2 and p.actFt is null")
   	public List<PCPp> getByMachineIdAndTime(Long machineId,Date t);
    
    
    @Query("select p from PCPp p where p.company.idCompany=?1 and p.planSt>?2 and p.planFt<?3 and  p.MMachine.idMachine=?4")
  	public List<PCPp> getByFromDateToDateAndMachineId(Long companyId,Date st,Date ft,Long machineId);
    
    
    
    @Query("select p from PCPp p where p.company.idCompany=?1 and p.actSt>?2 and p.actFt<?3 and  p.MMachine.idMachine=?4")
  	public List<PCPp> getActualByFromDateToDateAndMachineId(Long companyId,Date st,Date ft,Long machineId);
    
    
    
    @Query("select p from PCPp p where p.PWo.SSo.SMaterial.idMaterial=?1 order by p.idCPp desc")
  	public List<PCPp> getByMaterialId(Long materialId);
 
   
    
}
