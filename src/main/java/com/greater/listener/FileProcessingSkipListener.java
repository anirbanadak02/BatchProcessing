package com.greater.listener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.core.SkipListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.greater.dto.CustomerDTO;

public class FileProcessingSkipListener implements SkipListener<CustomerDTO, CustomerDTO>{
	
	@Autowired
	DataSource dataSource;

	@Override
	public void onSkipInProcess(CustomerDTO customerDTO, Throwable arg1) {
		// Skipped data goes into database in different transaction which will be helpful to calculate the number of skipped transactions
		System.out.println("Skipped Process");
		log(customerDTO);
	}

	@Override
	public void onSkipInRead(Throwable arg0) {
		
		System.out.println();
	}

	@Override
	public void onSkipInWrite(CustomerDTO customerDTO, Throwable arg1) {	
		// Skipped data goes into database in different transaction which will be helpful to calculate the number of skipped transactions 
		System.out.println("Skipped Write");
		log(customerDTO);
	}
	
	private void log(CustomerDTO customerDTO)
	{
		String sql = "INSERT INTO greater.skip_data " +
				"(filename) VALUES (?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
		
			ps.setString(1, customerDTO.getDateTime());

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

}
