package com.ndl.erp.dto;

import com.ndl.erp.domain.Inventario;

public class EntradasSalidasDTO {

    Inventario inventario;
    Integer costCenterId;
    Double cantidadEntrada;
    Double cantidadSalida;
    Double cantidadTotal;
    Double costoEntrada;
    Double costoSalida;
    Double costoTotal;
    Double totalEntrada;
    Double totalSalida;
    Double total;


    public EntradasSalidasDTO(Inventario inventario, Integer costCenterId, Double cantidadEntrada, Double cantidadSalida, Double cantidadTotal, Double costoEntrada, Double costoSalida, Double costoTotal, Double totalEntrada, Double totalSalida, Double total) {
        this.inventario = inventario;
        this.costCenterId = costCenterId == null ? 0 : costCenterId;
        this.cantidadEntrada = cantidadEntrada == null ? 0 : cantidadEntrada;
        this.cantidadSalida = cantidadSalida == null ? 0 : cantidadSalida;
        this.cantidadTotal = cantidadTotal == null ? 0 : cantidadTotal;
        this.costoEntrada = costoEntrada == null ? 0 : costoEntrada;
        this.costoSalida = costoSalida == null ? 0 : costoSalida;
        this.costoTotal = costoTotal == null ? 0 : costoTotal;
        this.totalEntrada = totalEntrada == null ? 0 : totalEntrada;
        this.totalSalida = totalSalida == null ? 0 : totalSalida;
        this.total = total == null ? 0 : total;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Integer getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(Integer costCenterId) {
        this.costCenterId = costCenterId;
    }

    public Double getCantidadEntrada() {
        return cantidadEntrada;
    }

    public void setCantidadEntrada(Double cantidadEntrada) {
        this.cantidadEntrada = cantidadEntrada;
    }

    public Double getCantidadSalida() {
        return cantidadSalida;
    }

    public void setCantidadSalida(Double cantidadSalida) {
        this.cantidadSalida = cantidadSalida;
    }

    public Double getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(Double cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public Double getCostoEntrada() {
        return costoEntrada;
    }

    public void setCostoEntrada(Double costoEntrada) {
        this.costoEntrada = costoEntrada;
    }

    public Double getCostoSalida() {
        return costoSalida;
    }

    public void setCostoSalida(Double costoSalida) {
        this.costoSalida = costoSalida;
    }

    public Double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Double getTotalEntrada() {
        return totalEntrada;
    }

    public void setTotalEntrada(Double totalEntrada) {
        this.totalEntrada = totalEntrada;
    }

    public Double getTotalSalida() {
        return totalSalida;
    }

    public void setTotalSalida(Double totalSalida) {
        this.totalSalida = totalSalida;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
