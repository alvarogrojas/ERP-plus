package com.ndl.erp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndl.erp.domain.TerminalUsuario;

public class ErpApplication11 {

	public static void main(String[] args) {


		ObjectMapper mapper = new ObjectMapper();
		TerminalUsuario tu = new TerminalUsuario();
		String jsonString = null;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tu);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(jsonString);

	}

}
