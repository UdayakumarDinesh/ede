package com.vts.ems.master.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity( name="master_edit")
public class MasterEdit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long MasterEditId;
	private String TableName;
	private Long TableRowId;
	private String Comments;
	private String FilePath;
	private String OriginalName;
	private String CreatedBy;
	private String CreatedDate;
}
