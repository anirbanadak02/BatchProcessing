package com.greater.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.greater.dto.CustomerDTO;

public class CSVReader {
	@Value("file:${TRANSACTION_PROCESSING}/pending/*.csv")
	  private Resource[] resources;
	
	@Bean
	public ItemReader<CustomerDTO> read() {
		MultiResourceItemReader multiResourceItemReader = new MultiResourceItemReader<CustomerDTO>();		
		multiResourceItemReader.setResources(resources);
		FlatFileItemReader<CustomerDTO> customerFileReader = new FlatFileItemReader<>();
		customerFileReader.setLinesToSkip(1);
		LineMapper<CustomerDTO> customerLineMapper = createCustomerLineMapper();
		customerFileReader.setLineMapper(customerLineMapper);
		multiResourceItemReader.setDelegate(customerFileReader);
		return multiResourceItemReader;
	}	
	
	private LineMapper<CustomerDTO> createCustomerLineMapper() {
		DefaultLineMapper<CustomerDTO> customerLineMapper = new DefaultLineMapper<>();
		LineTokenizer customerLineTokenizer = createCustomerLineTokenizer();
		customerLineMapper.setLineTokenizer(customerLineTokenizer);
		FieldSetMapper<CustomerDTO> customerInformationMapper = createCustomerInformationMapper();
		customerLineMapper.setFieldSetMapper(customerInformationMapper);
		return customerLineMapper;
	}

	private LineTokenizer createCustomerLineTokenizer() {
		DelimitedLineTokenizer customerLineTokenizer = new DelimitedLineTokenizer();
		customerLineTokenizer.setDelimiter(",");
		customerLineTokenizer.setNames(new String[] { "Customer Account", "Transaction Amount" });
		return customerLineTokenizer;
	}

	private FieldSetMapper<CustomerDTO> createCustomerInformationMapper() {
		BeanWrapperFieldSetMapper<CustomerDTO> customerInformationMapper = new BeanWrapperFieldSetMapper<>();
		customerInformationMapper.setTargetType(CustomerDTO.class);
		return customerInformationMapper;
	}
}
