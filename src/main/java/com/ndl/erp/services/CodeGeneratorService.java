package com.ndl.erp.services;

import com.ndl.erp.domain.CostCenter;
import com.ndl.erp.domain.PurchaseOrderProvider;
import com.ndl.erp.repository.CentroCostosRepository;
import com.ndl.erp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;

@Component
public class CodeGeneratorService {
	
	String START_NEW_CODE_POSFIX = "0001";

    static final int POSIX_REQUIRED = 1;

	@Autowired
	private CentroCostosRepository costsCenterRepository;

	@Transactional
	public synchronized String generateCode(String type) {
		String code = "";
		int id = this.costsCenterRepository.getMaxId(type);
		//Calendar currentDate = Calendar.getInstance();
		Calendar currentDate = DateUtil.getCurrentCalendar();
		if (id == -1) {
			code = generateValueFormatted(getYearFormatted(currentDate.get(Calendar.YEAR))) + generateValueFormatted(currentDate.get(Calendar.MONTH) + POSIX_REQUIRED)
					+ START_NEW_CODE_POSFIX;
		} else {
			String consecutive = "";
			int currentMonth = currentDate.get(Calendar.MONTH) + POSIX_REQUIRED;
			try {
				Optional<CostCenter> oc = this.costsCenterRepository.findById(id);
				if (oc==null) {
					return null;
				}
				CostCenter costsCenter = oc.get();
				int year = Integer.parseInt(costsCenter.getCode().substring(0,2));
				int month = getMonthFormatted(costsCenter.getCode().substring(2, 4));
				//int month = getMonth(Integer.parseInt(costsCenter.getCode().substring(2, 4));
				if (year != getYearFormatted(currentDate.get(Calendar.YEAR))) {
					consecutive = START_NEW_CODE_POSFIX;
				} else if (month != currentMonth) {
					consecutive = START_NEW_CODE_POSFIX;
				} else {
					String currentConsecutive = costsCenter.getCode().substring(costsCenter.getCode().length() - 4);
					int c = Integer.parseInt(currentConsecutive);
					c ++;
					consecutive = generateConsecutive(c);
				}
			} catch(Exception e) {
				consecutive = generateConsecutive(600);
			}
			code = generateValueFormatted(getYearFormatted(currentDate.get(Calendar.YEAR)))
					+ generateValueFormatted(currentMonth) + consecutive;
		}
		return code;
	}

	private String generateConsecutive(int c) {
		String consecutive = "";
		if (c < 10) {
			consecutive = "000"+ c;
		} else if (c < 100) {
			consecutive = "00"+ c;
		} else if (c < 1000) {
			consecutive = "0"+ c;
		} else {
			consecutive = Integer.toString(c);
		}
		return consecutive;
	}

	private String generateValueFormatted(int value) {
		String valueFormatted = "";
		if (value < 10) {
			valueFormatted = "0"+ value;
		} else {
			valueFormatted = Integer.toString(value);
		}
		return valueFormatted;
	}

	private int getYearFormatted(int year) {
		String f = Integer.toString(year);
		String vy = f.substring(2);
		return Integer.parseInt(vy);
	}
	private int getMonthFormatted(String month) {
		if (month == null || month.isEmpty()) {
			return POSIX_REQUIRED;
		}
		Integer mInt = Integer.parseInt(month);
		if (mInt <=0) {
			mInt += POSIX_REQUIRED;
		}
		return mInt;
	}


}
