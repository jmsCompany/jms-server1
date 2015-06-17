package com.jms.domainadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jms.domain.EnabledEnum;
import com.jms.domain.db.Company;
import com.jms.domain.ws.WSCompany;
import com.jms.domain.ws.WSSysDicD;
import com.jms.repositories.system.DistrictRepository;
import com.jms.repositories.system.SysDicDRepository;

@Service
@Transactional(readOnly = true)
public class CompanyAdapter {

	@Autowired
	private UserAdapter userAdapter;

	@Autowired
	private SysDicDRepository sysDicDRepository;

	@Autowired
	private SysDicDAdapter sysDicDAdapter;

	@Autowired
	private ProvinceAdapter provinceAdapter;

	@Autowired
	private CityAdapter cityAdapter;
	@Autowired
	private DistrictAdapter districtAdapter;
	@Autowired
	private DistrictRepository districtRepository;

	public Company toDBCompany(WSCompany wsCompany,Company company) throws Exception {
		if (wsCompany == null)
			return null;
		Company c = (Company) BeanUtil.shallowCopy(wsCompany, Company.class,company);

		if (wsCompany.getEnabledEnum() != null)
			c.setEnabled(wsCompany.getEnabledEnum().getStatusCode());
		else
			c.setEnabled(EnabledEnum.ENABLED.getStatusCode());

		if (wsCompany.getCompanyNature() != null) {
			c.setSysDicDByCompanyNature(sysDicDRepository.findOne(wsCompany
					.getCompanyNature().getIdDic()));
		}
		if (wsCompany.getCompanySize() != null) {
			c.setSysDicDByCompanyNature(sysDicDRepository.findOne(wsCompany
					.getCompanyNature().getIdDic()));
		}
		if (wsCompany.getCompanyType() != null) {
			c.setSysDicDByCompanyType(sysDicDRepository.findOne(wsCompany
					.getCompanyType().getIdDic()));
		}

		if (wsCompany.getWsDistrict() != null) {
			c.setDistrict(districtRepository.findOne(wsCompany.getWsDistrict()
					.getIdDistrict()));
		}
		return c;

	}

	public WSCompany toWSCompany(Company company) throws Exception {
		if (company == null)
			return null;
		WSCompany wsc = (WSCompany) BeanUtil.shallowCopy(company,
				WSCompany.class,null);
		wsc.setEnabledEnum(findEnabledEnumByStatusCode(company.getEnabled()));
		wsc.setWsUsers(userAdapter.toWSUser(company.getUsers()));
		if (company.getSysDicDByCompanySize() != null) {
			WSSysDicD companySize = sysDicDAdapter.toWSSysDicD(company
					.getSysDicDByCompanySize());
			wsc.setCompanySize(companySize);
		}
		if (company.getSysDicDByCompanyNature() != null) {
			WSSysDicD companyNature = sysDicDAdapter.toWSSysDicD(company
					.getSysDicDByCompanyNature());
			wsc.setCompanyNature(companyNature);
		}
		if (company.getSysDicDByCompanyType() != null) {
			WSSysDicD companyType = sysDicDAdapter.toWSSysDicD(company
					.getSysDicDByCompanyType());
			wsc.setCompanyType(companyType);
		}

		if (company.getDistrict() != null) {
			wsc.setWsProvince(provinceAdapter.toWSProvince(company
					.getDistrict().getCity().getProvince()));
			wsc.setWsCity(cityAdapter.toWSCity(company.getDistrict().getCity()));
			wsc.setWsDistrict(districtAdapter.toWSDistrict(company
					.getDistrict()));
		}

		return wsc;
	}

	private EnabledEnum findEnabledEnumByStatusCode(int statusCode) {
		for (EnabledEnum e : EnabledEnum.values()) {
			if (e.getStatusCode() == statusCode)
				return e;
		}
		return null;
	}

}
