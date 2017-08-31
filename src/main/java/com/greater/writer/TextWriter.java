package com.greater.writer;

import java.io.File;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;

import com.greater.BatchProcessing.BatchProcesingUtil;
import com.greater.dto.ReportCustomerDTO;

public class TextWriter {
	
	@Autowired
	BatchProcesingUtil batchProcesingUtil;
	@Value("${TRANSACTION_PROCESSING}")
	private String filePath;
	
	public FlatFileItemWriter<ReportCustomerDTO> write() {
		FlatFileItemWriter<ReportCustomerDTO> writer = new FlatFileItemWriter<ReportCustomerDTO>();
	
		writer.setResource(new FileSystemResource(
				new File(filePath + "/reports/finance_customer_transactions-"+batchProcesingUtil.getDateandTime()+".txt")));

		DelimitedLineAggregator<ReportCustomerDTO> delLineAgg = new DelimitedLineAggregator<ReportCustomerDTO>();
		delLineAgg.setDelimiter(",");
		BeanWrapperFieldExtractor<ReportCustomerDTO> fieldExtractor = new BeanWrapperFieldExtractor<ReportCustomerDTO>();
		fieldExtractor.setNames(new String[] { "fileName","noofAccounts", "credit", "debit","skippedTransactions" });
		delLineAgg.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(delLineAgg);		
		writer.setLineSeparator("\n");
		return writer;
	}
}
