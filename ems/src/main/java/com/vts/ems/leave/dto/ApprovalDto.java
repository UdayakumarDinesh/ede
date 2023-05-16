package com.vts.ems.leave.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApprovalDto {
        private String[] Approve;
        private String[] Reject;
        private String EmpNo;
        private String UserName;
        private String ApplId;
        private String Status;
        private String Type;
        private String Funds;
        private String Value;
}
