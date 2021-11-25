package com.ndl.erp.util;

public class ObligadoTributario {
	public static final String TIPO_PERSONA_FISICA = "01";
	public static final String TIPO_PERSONA_JURIDICA = "02";
	public ObligadoTributario() {}
	public ObligadoTributario(String tipoIdentificacion, String numeroIdentificacion) {
		super();
		this.tipoIdentificacion = tipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion;
	}
	private String tipoIdentificacion;
	private String numeroIdentificacion;
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
}
