package com.greater.processor;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.greater.BatchProcessing.BatchProcesingUtil;
import com.greater.dto.ReportCustomerDTO;
import com.greater.rowmapper.SkipRowMapper;

public class ReportCustomerDetailsItemProcessor implements ItemProcessor<ReportCustomerDTO, ReportCustomerDTO>{

	@Autowired
	DataSource dataSource;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	public SkipRowMapper skipRowMapper;
	@Autowired
	BatchProcesingUtil batchProcesingUtil;
	@Override
	public ReportCustomerDTO process(ReportCustomerDTO item) throws Exception {
		item.setSkippedTransactions("Skipped Transactions: "+String.valueOf(getSkippedRecord()));
		item.setFileName("File Processed: finance_customer_transactions-"+batchProcesingUtil.getDateandTime()+".csv");
		return item;
	}
	
	private Integer getSkippedRecord()
	{		
		JdbcTemplate select = new JdbcTemplate(dataSource);
        return select.queryForObject("SELECT COUNT(*) FROM skip_data",skipRowMapper);       
		
		
	}

}
