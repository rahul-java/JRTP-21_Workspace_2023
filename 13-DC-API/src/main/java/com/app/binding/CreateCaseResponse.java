package com.app.binding;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class CreateCaseResponse {

	private Long caseNum;
	private Map<Integer,String> planName;
}
