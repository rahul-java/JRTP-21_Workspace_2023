package com.app.service;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.binding.CoResponse;
import com.app.entity.CitizenAppEntity;
import com.app.entity.CoTriggerEntity;
import com.app.entity.DcCaseEntity;
import com.app.entity.EligDtlsEntity;
import com.app.repository.CitizenAppRepository;
import com.app.repository.CoTriggerRepository;
import com.app.repository.DcCaseRepository;
import com.app.repository.EligDtlsRepository;
import com.app.utils.EmailUtils;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class CoServiceImpl implements CoService {

	@Autowired
	private CoTriggerRepository coTriggerRepository;
	@Autowired
	private EligDtlsRepository eligDtlsRepository;
	@Autowired
	private CitizenAppRepository citizenAppRepository;
	@Autowired
	private DcCaseRepository dcCaseRepository;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public CoResponse processPendingTriggers() {

		CitizenAppEntity citizenAppEntity = null;
		CoResponse response = new CoResponse();
		Long failed=0l;
		Long success=0l;
		
		// fetch all pending triggers

		List<CoTriggerEntity> pendingTriggers = coTriggerRepository.findByTrgStatus("PENDING");
		
		response.setTotalTriggers(Long.valueOf(pendingTriggers.size()));

		// process each pending triggers

		for (CoTriggerEntity entity : pendingTriggers) {
			// get eligibility data based on caseNum
			EligDtlsEntity eligDtlsEntity = eligDtlsRepository.findByCaseNum(entity.getCaseNum());

			// get citizen data based on caseNum
			Optional<DcCaseEntity> dcCaseEntityOptional = dcCaseRepository.findById(entity.getCaseNum());
			if (dcCaseEntityOptional.isPresent()) {
				DcCaseEntity dcCaseEntity = dcCaseEntityOptional.get();
				Integer appId = dcCaseEntity.getAppId();
				Optional<CitizenAppEntity> citizenAppEntityOptional = citizenAppRepository.findById(appId);
				if (citizenAppEntityOptional.isPresent()) {
					citizenAppEntity = citizenAppEntityOptional.get();

				}
			}

			// generate pdf with elig details
			// send pdf to citizen mail
			// store pdf and update trigger as complete

			try {
				generatePdf(eligDtlsEntity, citizenAppEntity);
				success++;
			} catch (Exception e) {
				failed++;
				e.printStackTrace();
			}

			
		}

		// return summary
		response.setFailedTriggers(failed);
		response.setSuccTriggers(success);

		return response;
	}

	private void generatePdf(EligDtlsEntity elig, CitizenAppEntity citizen) throws Exception {
		Document document = new Document(PageSize.A4);

		File file = new File(elig.getCaseNum() + ".pdf");

		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		PdfWriter.getInstance(document, fileOutputStream);

		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph paragraph = new Paragraph("Eligibility Report", font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);

		PdfPTable pdfPTable = new PdfPTable(7);
		pdfPTable.setWidthPercentage(100f);
		pdfPTable.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 1.5f, 3.0f, 1.5f, 3.0f });
		pdfPTable.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Citizen Name", font));
		pdfPTable.addCell(cell);

		cell.setPhrase(new Phrase("Plan Name", font));
		pdfPTable.addCell(cell);

		cell.setPhrase(new Phrase("Plan Status", font));
		pdfPTable.addCell(cell);

		cell.setPhrase(new Phrase("Plan Start Date", font));
		pdfPTable.addCell(cell);

		cell.setPhrase(new Phrase("Plan End Date", font));
		pdfPTable.addCell(cell);

		cell.setPhrase(new Phrase("Benefit Amount", font));
		pdfPTable.addCell(cell);

		cell.setPhrase(new Phrase("Denial Reason", font));
		pdfPTable.addCell(cell);

		pdfPTable.addCell(citizen.getFullname());
		pdfPTable.addCell(elig.getPlanName());
		pdfPTable.addCell(elig.getPlanStatus());
		pdfPTable.addCell(elig.getPlanStartDate() + "");
		pdfPTable.addCell(elig.getPlanEndDate() + "");
		pdfPTable.addCell(elig.getBenefitAmt() + "");
		pdfPTable.addCell(elig.getDenialReason());

		document.add(pdfPTable);
		document.close();

		// send pdf to citizen mail

		String subject = "His Eligibility Info";
		String body = "His Eligibility Info";
		emailUtils.sendEmail(citizen.getEmail(), body, subject, file);

		updateTrigger(elig.getCaseNum(), file);
		
		file.delete();
	}

	private void updateTrigger(Long caseNum, File file) throws Exception {
		
		CoTriggerEntity coTriggerEntity = coTriggerRepository.findByCaseNum(caseNum);

		byte[] byteArr = new byte[(byte) file.length()];
		
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(byteArr);
		
		coTriggerEntity.setCoPdf(byteArr);
		coTriggerEntity.setTrgStatus("Completed");
		
		coTriggerRepository.save(coTriggerEntity);
		fileInputStream.close();

	}
}
