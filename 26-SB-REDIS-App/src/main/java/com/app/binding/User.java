package com.app.binding;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class User implements Serializable{

	//private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private Long salary;
}
