package com.vts.ems.circularorder.service;

import java.util.List;

import com.vts.ems.circularorder.dto.CircularUploadDto;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.master.model.CircularList;


public interface CircularService {
	

	public long CircularUpload(CircularUploadDto uploadcirdto ,EMSCircular circular)throws Exception;
	public List<Object[]> selectAllList() throws Exception;
	public List<Object[]> GetCircularList(String fromdate, String todate) throws Exception;
	public int CircularDelete(Long CircularId, String Username)throws Exception;
	public EMSCircular GetCircularDetailsToEdit(Long CircularId)throws Exception;

}
