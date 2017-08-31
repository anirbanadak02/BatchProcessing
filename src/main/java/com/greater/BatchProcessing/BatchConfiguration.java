package com.greater.BatchProcessing;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.greater.dto.CustomerDTO;
import com.greater.dto.ReportCustomerDTO;
import com.greater.listener.FileProcessingSkipListener;
import com.greater.processor.CustomerItemProcessor;
import com.greater.processor.ReportCustomerDetailsItemProcessor;
import com.greater.reader.CSVReader;
import com.greater.reader.DatabaseReader;
import com.greater.rowmapper.CustomerRowMapper;
import com.greater.writer.CSVWriter;
import com.greater.writer.TextWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	@Autowired
	public FileProcessingSkipListener fileProcessingSkipListener;
	@Autowired
	public CustomerRowMapper customerRowMapper;
	@Autowired
	CustomerItemProcessor customerItemProcessor;
	@Autowired
	DeletingTasklet deletingTasklet;
	@Autowired
	ReportCustomerDetailsItemProcessor reportCustomerDetailsItemProcessor;
	@Autowired
	public DataSource dataSource;
	@Autowired
	BatchProcesingUtil batchProcesingUtil;
	@Value("${TRANSACTION_PROCESSING}")
	private String filePath;
	@Autowired
	private CSVReader csvReader;
	@Autowired 
	DatabaseReader databaseReader;
	
	@Autowired
	CSVWriter csvWriter;
	@Autowired
	TextWriter textWriter;


	@Bean
	public DataSource datasource() {
		final DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setUrl("jdbc:mysql://localhost:3306/greater");
		datasource.setUsername("root");
		datasource.setPassword("root");
		return datasource;
	}
	
	@Bean
	public JdbcBatchItemWriter<CustomerDTO> write() {
		JdbcBatchItemWriter<CustomerDTO> writer = new JdbcBatchItemWriter<CustomerDTO>();
		try
		{		
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<CustomerDTO>());
		writer.setSql(
				"insert into customer (transactionalAmount,Filename,customeraccount)values (:transactionalAmount,:dateTime,:customerAccount)");
		writer.setDataSource(dataSource);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return writer;
	}
	
	/*
	 * Read from csv, put in database
	 */
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<CustomerDTO, CustomerDTO>chunk(1).reader(csvReader.read())
				.processor(customerItemProcessor)
				.faultTolerant().skipLimit(10).
				skip(Exception.class).listener(fileProcessingSkipListener)
				.writer(write())
				.faultTolerant().skipLimit(10)
				.skip(Exception.class).listener(fileProcessingSkipListener)
				.build();
	}

	/*
	 * Copies csv from pending to processed
	 */
	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2").<CustomerDTO, CustomerDTO>chunk(1).reader(csvReader.read())
				.writer(csvWriter.write()).faultTolerant().skipLimit(10)
				.skip(Exception.class).listener(fileProcessingSkipListener).build();
	}
	/*
	 * Reads from database and makes the report
	 */
	@Bean
	public Step step3() {
		return stepBuilderFactory.get("step3").<ReportCustomerDTO, ReportCustomerDTO>chunk(1).reader(databaseReader.read())
				.processor(reportCustomerDetailsItemProcessor).writer(textWriter.write()).build();
	}
	/*
	 * Deletes the files from pending directory and clears the database tables (customer and skip_data)
	 */
	@Bean
	public Step step4() {
		return stepBuilderFactory.get("step4").tasklet(deletingTasklet).build();
	}

	@Bean
	public Job buildCustomerJob() {
		return jobBuilderFactory.get("importCustomerJob").incrementer(new RunIdIncrementer()).start(step1())
				.next(step2()).next(step3()).next(step4()).build();
	}
}
