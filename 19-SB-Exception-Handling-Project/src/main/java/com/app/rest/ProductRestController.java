package com.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.ProductService;

@RestController
public class ProductRestController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/product/{pid}")
	public String gtProduct(@PathVariable Integer pid)
	{
		return productService.getProductName(pid);
	}
}
