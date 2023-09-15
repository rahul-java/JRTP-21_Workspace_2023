package com.app.service;

import org.springframework.stereotype.Service;

import com.app.exception.NoProductFoundException;

@Service
public class ProductService {

	public String getProductName(Integer pid)
	{
		
		if(pid==100)
		{
			return "Mouse";
		}
		else if(pid==101)
		{
			return "Keyboard";
		}
		else 
		{
			//throw exception
			throw new NoProductFoundException("Invalid Product Id");
		}
		
		
	}
}
