package com.vts.ems.property.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.property.dao.PropertyDao;
import com.vts.ems.property.model.PisImmovableProperty;
import com.vts.ems.property.model.PisImmovablePropertyTrans;

@Service
public class PropertyServiceImp implements PropertyService{

	@Autowired
	PropertyDao dao;

	@Override
	public List<Object[]> ImmPropDetails(String EmpNo) throws Exception {
		
		return dao.ImmPropDetails(EmpNo);
	}

	@Override
	public PisImmovableProperty getImmovablePropertyById(Long ImmPropertyId) throws Exception {
		
		return dao.getImmovablePropertyById(ImmPropertyId);
	}

	@Override
	public Long addImmovableProperty(PisImmovableProperty immovable) throws Exception {
		
		return dao.addImmovableProperty(immovable);
	}

	@Override
	public Long editImmovableProperty(PisImmovableProperty immovable) throws Exception {
		
		PisImmovableProperty imm = dao.getImmovablePropertyById(immovable.getImmPropertyId());
		
		imm.setPurpose(immovable.getPurpose());
		imm.setTransState(immovable.getTransState());
		imm.setTransDate(immovable.getTransDate());		
		imm.setMode(immovable.getMode());
		imm.setLocation(immovable.getLocation());
		imm.setDistrict(immovable.getDistrict());
		imm.setState(immovable.getState());
		imm.setPincode(immovable.getPincode());
		imm.setOwnership(immovable.getOwnership());
		imm.setDescription(immovable.getDescription());
		imm.setApplicantInterest(immovable.getApplicantInterest());
		imm.setPartialInterest(immovable.getPartialInterest());
		imm.setOsParticulars(immovable.getOsParticulars());
		imm.setOsShare(immovable.getOsShare());
		imm.setPrice(immovable.getPrice());
		imm.setFinanceSource(immovable.getFinanceSource());
		imm.setRequisiteSanction(immovable.getRequisiteSanction());
		imm.setPartyName(immovable.getPartyName());
		imm.setPartyAddress(immovable.getPartyAddress());
		imm.setTransArrangement(immovable.getTransArrangement());
		imm.setPartyRelated(immovable.getPartyRelated());
		imm.setRelationship(immovable.getRelationship());
		imm.setFutureDealings(immovable.getFutureDealings());
		imm.setDealingNature(immovable.getDealingNature());
		imm.setSanctionRequired(immovable.getSanctionRequired());
		imm.setRelavantFact(immovable.getRelavantFact());
		imm.setModifiedBy(immovable.getModifiedBy());
		imm.setModifiedDate(immovable.getModifiedDate());
		
		return dao.editImmovableProperty(imm);
	}

	@Override
	public Long addImmovablePropertyTransaction(PisImmovablePropertyTrans transaction) throws Exception {
		
		return dao.addImmovablePropertyTransaction(transaction);
	}

	@Override
	public List<Object[]> immmovablePropertyTransList(String immPropertyId) throws Exception {
		
		return dao.immmovablePropertyTransList(immPropertyId);
	}
}
