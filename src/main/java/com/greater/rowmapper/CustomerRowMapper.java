package com.greater.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.greater.dto.CustomerDTO;
import com.greater.dto.ReportCustomerDTO;

public class CustomerRowMapper implements RowMapper<ReportCustomerDTO>{

	@Override
	public ReportCustomerDTO mapRow(ResultSet rs, int arg1) throws SQLException {
		ReportCustomerDTO reportCustomerDTO = new ReportCustomerDTO();
		reportCustomerDTO.setNoofAccounts("Total Accounts: "+String.valueOf(rs.getLong(1)));
		reportCustomerDTO.setCredit("Total Credits : "+String.valueOf(rs.getLong(2)));
		reportCustomerDTO.setDebit("Total Debits : "+String.valueOf(rs.getLong(3)));		
		return reportCustomerDTO;
	}

}
