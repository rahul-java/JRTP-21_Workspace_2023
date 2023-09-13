package com.app.service;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.binding.EligDetailsBind;
import com.app.entity.EligDtlsEntity;
import com.app.repository.EligDtlsRepository;
import com.opencsv.CSVWriter;

@Service
public class BiServiceImpl implements BiService {

	@Autowired
	private EligDtlsRepository eligDtlsRepository;
	
	
	@Override
	public void createCSVFile() {

		List<EligDtlsEntity> eligDtlsEntitiesList = eligDtlsRepository.findByPlanStatus("AP");
		List<EligDetailsBind> eligDtlsBindList = new ArrayList<>();
		
		for(EligDtlsEntity eligDetails:eligDtlsEntitiesList)
		{
			EligDetailsBind eligDetailsBind = new EligDetailsBind();
			BeanUtils.copyProperties(eligDetails, eligDetailsBind);
			eligDetailsBind.setBankAccountNo("9638527410123");
			eligDetailsBind.setBankName("CITI BANK");
			
			eligDtlsBindList.add(eligDetailsBind);
		}
		
		String path="C:\\Users\\hp\\Downloads\\csv.txt";
		
		File file = new File(path);
		
		try {
			
			FileWriter fileWriter = new FileWriter(file);
			CSVWriter csvWriter = new CSVWriter(fileWriter);
			
			List<String[]> data =new ArrayList<>();
			
			data.add(new String[] {"CaseNum","Name","SSN","BenefitAmt","Bank","AccountNo"});
			
			for(EligDetailsBind elgDtlsBind :eligDtlsBindList)
			{
				data.add(new String[] {
						
						String.valueOf(elgDtlsBind.getCaseNum()),
						String.valueOf(elgDtlsBind.getHolderName()),
						String.valueOf(elgDtlsBind.getHolderSsn()),
						String.valueOf(elgDtlsBind.getBenefitAmt()),
						String.valueOf(elgDtlsBind.getBankName()),
						String.valueOf(elgDtlsBind.getBankAccountNo())
						
						});
			}
			
			csvWriter.writeAll(data);
			csvWriter.close();
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
