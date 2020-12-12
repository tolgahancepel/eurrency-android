package com.cepel.dovizapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rates {

    @SerializedName("USD")
    @Expose
    private Double uSD;
    @SerializedName("GBP")
    @Expose
    private Double gBP;
    @SerializedName("CAD")
    @Expose
    private Double cAD;
    @SerializedName("CHF")
    @Expose
    private Double cHF;
    @SerializedName("INR")
    @Expose
    private Double iNR;
    @SerializedName("TRY")
    @Expose
    private Double tRY;


    public Double getUSD() {
        return uSD;
    }

    public void setUSD(Double uSD) {
        this.uSD = uSD;
    }

    public Double getGBP() {
        return gBP;
    }

    public void setGBP(Double gBP) {
        this.gBP = gBP;
    }

    public Double getCAD() {
        return cAD;
    }

    public void setCAD(Double cAD) {
        this.cAD = cAD;
    }

    public Double getCHF() {
        return cHF;
    }

    public void setCHF(Double cHF) { this.cHF = cHF; }

    public Double getINR() {
        return iNR;
    }

    public void setINR(Double iNR) { this.iNR = iNR; }

    public Double getTRY() { return tRY; }

    public void setTRY(Double tRY) { this.tRY = tRY; }
}

