package com.app.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CitizenApp {

	private String fullname;
	private String email;
	private Long phoneNo;
	private String gender;
	private Long ssn;
	private LocalDate dob;
	
	
}
