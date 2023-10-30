package com.example.javafx;

public class Valute {
    private int numCode;
    private String charCode;
    private int Nominal;
    private String name;
    private double value;
    private double vunitrate;

    public Valute(int numCode, String charCode, int nominal, String name, double value, double vunitrate) {
        this.numCode = numCode;
        this.charCode = charCode;
        Nominal = nominal;
        this.name = name;
        this.value = value;
        this.vunitrate = vunitrate;
    }

    public double getVunitrate() {
        return vunitrate;
    }

    public void setVunitrate(double vunitrate) {
        this.vunitrate = vunitrate;
    }

    @Override
    public String toString() {
        return "Valute{" +
                "numCode=" + numCode +
                ", charCode='" + charCode + '\'' +
                ", Nominal=" + Nominal +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public int getNumCode() {
        return numCode;
    }

    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public int getNominal() {
        return Nominal;
    }

    public void setNominal(int nominal) {
        Nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
