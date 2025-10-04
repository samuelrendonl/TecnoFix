/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Mi PC
 */
public class Dispositivo {
    private int idDispositivo;
    
    private String NombreCliente;
    private String NombreDispositivo;
    private String MarcaDispositivo;
    private String DañoDispositivo;
    private String EmpleadoAsignado;

    public Dispositivo(int idDispositivo, String NombreCliente, String NombreDispositivo, String MarcaDispositivo, String DañoDispositivo, String EmpleadoAsignado) {
        this.idDispositivo = idDispositivo;
        this.NombreCliente = NombreCliente;
        this.NombreDispositivo = NombreDispositivo;
        this.MarcaDispositivo = MarcaDispositivo;
        this.DañoDispositivo = DañoDispositivo;
        this.EmpleadoAsignado = EmpleadoAsignado;
    }

    public int getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }

    public String getNombreDispositivo() {
        return NombreDispositivo;
    }

    public void setNombreDispositivo(String NombreDispositivo) {
        this.NombreDispositivo = NombreDispositivo;
    }

    public String getMarcaDispositivo() {
        return MarcaDispositivo;
    }

    public void setMarcaDispositivo(String MarcaDispositivo) {
        this.MarcaDispositivo = MarcaDispositivo;
    }

    public String getDañoDispositivo() {
        return DañoDispositivo;
    }

    public void setDañoDispositivo(String DañoDispositivo) {
        this.DañoDispositivo = DañoDispositivo;
    }

    public String getEmpleadoAsignado() {
        return EmpleadoAsignado;
    }

    public void setEmpleadoAsignado(String EmpleadoAsignado) {
        this.EmpleadoAsignado = EmpleadoAsignado;
    }

    

}



