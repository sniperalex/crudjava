package com.crud.veiculos;

public class Vehicle {
    private Integer id;
    private String marca;
    private String modelo;
    private String placa;
    private Integer ano;

    public Vehicle(Integer id, String marca, String modelo, String placa, Integer ano) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.ano = ano;
    }

    public Vehicle(String marca, String modelo, String placa, Integer ano) {
        this(null, marca, modelo, placa, ano);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    @Override
    public String toString() {
        return String.format("ID=%d | %s %s | placa=%s | ano=%d", id, marca, modelo, placa, ano);
    }
}
