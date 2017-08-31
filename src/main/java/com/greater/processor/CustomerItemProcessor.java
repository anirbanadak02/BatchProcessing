package com.greater.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.greater.BatchProcessing.BatchProcesingUtil;
import com.greater.dto.CustomerDTO;

public class CustomerItemProcessor implements ItemProcessor<CustomerDTO, CustomerDTO>{

	@Autowired
	ApplicationContext a;
	@Autowired
	BatchProcesingUtil batchProcesingUtil;
	@Override
	public CustomerDTO process(CustomerDTO customerDTO) throws Exception {
	customerDTO.setDateTime("finance_customer_transactions-"+batchProcesingUtil.getDateandTime());
	return customerDTO;
	}

}
