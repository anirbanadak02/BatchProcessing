package com.greater.reader;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import com.greater.dto.ReportCustomerDTO;
import com.greater.rowmapper.CustomerRowMapper;

public class DatabaseReader {
	@Autowired
	DataSource dataSource;
	@Autowired
	public CustomerRowMapper customerRowMapper;
	
	public JdbcCursorItemReader<ReportCustomerDTO> read() {
		JdbcCursorItemReader dbReader = new JdbcCursorItemReader<ReportCustomerDTO>();
		dbReader.setDataSource(dataSource);
		dbReader.setSql(
				"select count(distinct(customeraccount)) as account,sum(case when transactionalAmount > 0 then transactionalAmount end) as credit,sum(case when transactionalAmount < 0 then transactionalAmount end) as debit from greater.customer");
		dbReader.setRowMapper(customerRowMapper);
		return dbReader;
	}
}
