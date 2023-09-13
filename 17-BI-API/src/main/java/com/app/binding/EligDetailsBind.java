package com.app.binding;

import lombok.Data;

@Data
public class EligDetailsBind {

	private Long caseNum;
	private String holderName;
	private Long holderSsn;
	private String planName;
	private String planStatus;
	private Double benefitAmt;
	
	private String bankName;
	private String bankAccountNo;
	
	
}
