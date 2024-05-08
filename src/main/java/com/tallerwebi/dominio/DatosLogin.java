package com.tallerwebi.dominio;

public class DatosLogin {
    private String email;
    private String password;
    private String sexo;
    private String nivelDeActividad;
    private Double peso;
    private Double altura;
    private Integer edad;

    public DatosLogin() {
    }


    public Integer getEdad() {return edad;}

    public void setEdad(Integer edad) {this.edad = edad;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getSexo() {return sexo;}

    public void setSexo(String sexo) {this.sexo = sexo;}

    public String getNivelDeActividad() {return nivelDeActividad;}

    public void setNivelDeActividad(String nivelDeActividad) {this.nivelDeActividad = nivelDeActividad;}

    public Double getPeso() {return peso;}

    public void setPeso(Double peso) {this.peso = peso;}

    public Double getAltura() {return altura;}

    public void setAltura(Double altura) {this.altura = altura;}

}


