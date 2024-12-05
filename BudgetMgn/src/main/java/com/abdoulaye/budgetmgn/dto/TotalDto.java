package com.abdoulaye.budgetmgn.dto;

import lombok.Builder;


public class TotalDto {
    private Double totalRevenu;
    private Double totalDepense;
    private Double restant;

    public Double getTotalRevenu() {
        return totalRevenu;
    }

    public void setTotalRevenu(Double totalRevenu) {
        this.totalRevenu = totalRevenu;
    }

    public Double getTotalDepense() {
        return totalDepense;
    }

    public void setTotalDepense(Double totalDepense) {
        this.totalDepense = totalDepense;
    }

    public Double getRestant() {
        return restant;
    }

    public void setRestant(Double restant) {
        this.restant = restant;
    }

    public TotalDto(Double totalRevenu, Double totalDepense, Double restant) {
        this.totalRevenu = totalRevenu;
        this.totalDepense = totalDepense;
        this.restant = restant;
    }
}
