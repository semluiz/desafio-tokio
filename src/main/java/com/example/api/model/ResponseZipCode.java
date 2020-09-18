package com.example.api.model;


public class ResponseZipCode {

    private String cep;

    private String bairro;

    private String cidade;

    private String log;


    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public ResponseZipCode(String cep, String bairro, String cidade, String log) {
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.log = log;
    }

    public ResponseZipCode() {
    }
}

