package com.vts.ems.chss.dto;

import lombok.Data;

@Data
public class CHSSIPDPackageDto {

	private String CHSSBillPkgId;
	private String billid;
	private String pkg_id;
	private String pkg_total_cost;
	private String[] pkgitem_id;
	private String[] pkgitem_cost;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
	
}
