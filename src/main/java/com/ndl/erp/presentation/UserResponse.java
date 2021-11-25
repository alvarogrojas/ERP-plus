package com.ndl.erp.presentation;

//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;

import java.io.Serializable;

//@Data
//@Builder
//@AllArgsConstructor
public class UserResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
