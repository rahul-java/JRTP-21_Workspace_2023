package com.app.service;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.aspectj.apache.bcel.classfile.Module.Open;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.app.entity.EligibilityDetails;
import com.app.repo.EligibilityDetailsRepo;
import com.app.request.SearchRequest;
import com.app.response.SearchResponse;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private EligibilityDetailsRepo eligRepo;
	
	@Override
	public List<String> getUniquePlanNames() {
		
		return eligRepo.findPlanNames();
	}

	@Override
	public List<String> getUniquePlanStatuses() {
		
		return eligRepo.findPlanStatuses();
	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {
		
		List<SearchResponse> srList=new ArrayList<>();
		
		EligibilityDetails queryBuilder = new EligibilityDetails();
		
		String planName = request.getPlanName();
		if(planName!=null && !planName.equals(""))
		{
			queryBuilder.setPlanName(planName);
		}
		
		String planStatus = request.getPlanStatus();
		if(planStatus!=null && !planStatus.equals(""))
		{
			queryBuilder.setPlanStatus(planStatus);
		}
		
		LocalDate planStartDate = request.getPlanStartDate();
		if(planStartDate!=null)
		{
			queryBuilder.setPlanStartDate(planStartDate);
		}
		
		LocalDate planEndDate = request.getPlanEndDate();
		if(planEndDate!=null)
		{
			queryBuilder.setPlanEndDate(planEndDate);
		}
		
		Example<EligibilityDetails> example = Example.of(queryBuilder);
		
		List<EligibilityDetails> entities = eligRepo.findAll(example);
		
		for(EligibilityDetails entity:entities)
		{
			SearchResponse sr = new SearchResponse();
			BeanUtils.copyProperties(entity, sr);
			srList.add(sr);
		}
		return srList;
	}

	@Override
	public void generateExcel(HttpServletResponse response) throws Exception {
		
		List<EligibilityDetails> entities = eligRepo.findAll();
		
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow headerRow = sheet.createRow(0);
		
		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(1).setCellValue("EMAIL");
		headerRow.createCell(2).setCellValue("Mobile");
		headerRow.createCell(3).setCellValue("Gender");
		headerRow.createCell(4).setCellValue("SSN");
		
		int i=1;
		for(EligibilityDetails entity:entities){
			
			HSSFRow dataRow = sheet.createRow(i);
			dataRow.createCell(0).setCellValue(entity.getName());
			dataRow.createCell(1).setCellValue(entity.getEmail());
			dataRow.createCell(2).setCellValue(entity.getMobile());
			dataRow.createCell(3).setCellValue(String.valueOf(entity.getGender()));
			dataRow.createCell(4).setCellValue(entity.getSsn());
			i++;
		}
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}

	@Override
	public void generatePdf(HttpServletResponse response) throws Exception {
		
		List<EligibilityDetails> entities = eligRepo.findAll();
		Document document =new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		
		Paragraph paragraph = new Paragraph("List of Users",font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(paragraph);
		
		PdfPTable pdfPTable = new PdfPTable(5);
		pdfPTable.setWidthPercentage(100f);
		pdfPTable.setWidths(new float[] {1.5f,3.5f,3.0f,1.5f,3.0f});
		pdfPTable.setSpacingBefore(10);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		cell.setPhrase(new Phrase("Name",font));
		pdfPTable.addCell(cell);
		
		cell.setPhrase(new Phrase("Email",font));
		pdfPTable.addCell(cell);
		
		cell.setPhrase(new Phrase("PhoneNo",font));
		pdfPTable.addCell(cell);
		
		cell.setPhrase(new Phrase("Gender",font));
		pdfPTable.addCell(cell);
		
		cell.setPhrase(new Phrase("SSN",font));
		pdfPTable.addCell(cell);
		
		for(EligibilityDetails entity:entities)
		{
			pdfPTable.addCell(entity.getName());
			pdfPTable.addCell(entity.getEmail());
			pdfPTable.addCell(String.valueOf(entity.getMobile()));
			pdfPTable.addCell(String.valueOf(entity.getGender()));
			pdfPTable.addCell(String.valueOf(entity.getSsn()));
			
		}
		document.add(pdfPTable);
		document.close();

	}

}
