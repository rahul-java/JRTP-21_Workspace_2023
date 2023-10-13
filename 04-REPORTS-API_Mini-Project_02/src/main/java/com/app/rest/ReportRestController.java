package com.app.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.request.SearchRequest;
import com.app.response.SearchResponse;
import com.app.service.ReportService;

@RestController
@CrossOrigin
public class ReportRestController {

	@Autowired
	private ReportService reportService;
	
	@GetMapping("/plans")
	public ResponseEntity<List<String>> getPlanNames()
	{
		List<String> uniquePlanNames = reportService.getUniquePlanNames();
		return new ResponseEntity<>(uniquePlanNames, HttpStatus.OK);
	}
	
	@GetMapping("/statuses")
	public ResponseEntity<List<String>> getPlanStatuses()
	{
		List<String> uniquePlanStatuses = reportService.getUniquePlanStatuses();
		return new ResponseEntity<>(uniquePlanStatuses,HttpStatus.OK);
	}
	
	@PostMapping("/search")
	public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest request)
	{
		List<SearchResponse> searchResponse = reportService.search(request);
		System.out.println(searchResponse.toString());
		return new ResponseEntity<>(searchResponse,HttpStatus.OK);
	}
	
	@GetMapping("/excel")
	public void excelReport(HttpServletResponse response) throws Exception
	{
		response.setContentType("application/octet-stream");
		
		String headerKey="Content-Disposition";
		String headerValue="attachment;filename=data.xls";
		
		response.setHeader(headerKey, headerValue);
		reportService.generateExcel(response);
	}
	
	@GetMapping("/pdf")
	public void pdfReport(HttpServletResponse response) throws Exception
	{
		response.setContentType("application/pdf");
		
		String headerKey="Content-Disposition";
		String headerValue="attachment;filename=data.pdf";
		
		response.setHeader(headerKey, headerValue);
		reportService.generatePdf(response);
	}
}
