package br.com.etecia.florsqliteapp;

public class PlantasCad {
    private int id;
    private String tipo;
    private String Nome;
    private String preco;
    private double dataRegistro;

    public PlantasCad(int anInt, String cursorPlantasString, String string, double aDouble, String id) {
    }

    public PlantasCad(int id, String tipo, String nome, String preco, double dataRegistro) {
        this.id = id;
        this.tipo = tipo;
        Nome = nome;
        this.preco = preco;
        this.dataRegistro = dataRegistro;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public double getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(double dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
