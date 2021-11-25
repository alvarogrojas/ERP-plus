package com.ndl.erp.domain;

import javax.persistence.*;
import java.io.Serializable;

//@Data
//@Builder
//@AllArgsConstructor
@Entity
@Table(name="system_role")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	public Role() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column
	private String name;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
