package com.greater.writer;

import java.io.File;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;

import com.greater.BatchProcessing.BatchProcesingUtil;
import com.greater.dto.CustomerDTO;

public class CSVWriter {

	@Autowired
	BatchProcesingUtil batchProcesingUtil;
	@Value("${TRANSACTION_PROCESSING}")
	private String filePath;
	
	public FlatFileItemWriter<CustomerDTO> write() {
	
		FlatFileItemWriter<CustomerDTO> writer = new FlatFileItemWriter<CustomerDTO>();
		try
		{
		writer.setResource(new FileSystemResource(
				new File(filePath + "/processed/finance_customer_transactions-"+batchProcesingUtil.getDateandTime()+".csv")));
		DelimitedLineAggregator<CustomerDTO> delLineAgg = new DelimitedLineAggregator<CustomerDTO>();
		delLineAgg.setDelimiter(",");
		BeanWrapperFieldExtractor<CustomerDTO> fieldExtractor = new BeanWrapperFieldExtractor<CustomerDTO>();
		fieldExtractor.setNames(new String[] { "customerAccount", "transactionalAmount" });
		delLineAgg.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(delLineAgg);			
		
		}
		catch(Exception e)
		{
		e.printStackTrace();	
		}return writer;
	}
}
