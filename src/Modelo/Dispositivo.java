package Modelo;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import javafx.scene.image.Image;

public class Dispositivo implements Serializable {

    private int idDispositivo;
    private int idReparacion;
    private String nombreCliente;
    private String nombreDispositivo;
    private String marcaDispositivo;
    private String dañoDispositivo;
    private String empleadoAsignado;
    private String fechaRecepcion;
    private String estado;
    private String descripcion;
    private byte[] imagenDispositivo; 
    
    public Dispositivo(int idDispositivo, int idReparacion, String nombreCliente, String nombreDispositivo,
                       String marcaDispositivo, String dañoDispositivo, String empleadoAsignado,
                       String fechaRecepcion, String estado, String descripcion) {
        this.idDispositivo = idDispositivo;
        this.idReparacion = idReparacion;
        this.nombreCliente = nombreCliente;
        this.nombreDispositivo = nombreDispositivo;
        this.marcaDispositivo = marcaDispositivo;
        this.dañoDispositivo = dañoDispositivo;
        this.empleadoAsignado = empleadoAsignado;
        this.fechaRecepcion = fechaRecepcion;
        this.estado = estado;
        this.descripcion = descripcion;
    }
  public Dispositivo(int idDispositivo, int idReparacion, String nombreCliente, String nombreDispositivo,
                       String marcaDispositivo, String dañoDispositivo, String empleadoAsignado,
                       String fechaRecepcion, String estado, String descripcion, byte[] imagenDispositivo) {
        this(idDispositivo, idReparacion, nombreCliente, nombreDispositivo,
             marcaDispositivo, dañoDispositivo, empleadoAsignado, fechaRecepcion, estado, descripcion);
        this.imagenDispositivo = imagenDispositivo;
    }

   public Dispositivo(int idDispositivo, String nombreCliente, String nombreDispositivo,
                       String marcaDispositivo, String dañoDispositivo, String empleadoAsignado) {
        this.idDispositivo = idDispositivo;
        this.nombreCliente = nombreCliente;
        this.nombreDispositivo = nombreDispositivo;
        this.marcaDispositivo = marcaDispositivo;
        this.dañoDispositivo = dañoDispositivo;
        this.empleadoAsignado = empleadoAsignado;
    }
    public Dispositivo(int idReparacion, String nombreCliente, String nombreDispositivo,
                       String empleadoAsignado, String fechaRecepcion, String estado, String descripcion) {
        this.idReparacion = idReparacion;
        this.nombreCliente = nombreCliente;
        this.nombreDispositivo = nombreDispositivo;
        this.empleadoAsignado = empleadoAsignado;
        this.fechaRecepcion = fechaRecepcion;
        this.estado = estado;
        this.descripcion = descripcion;
    }

  
    public Dispositivo(int idDispositivo, String nombreCliente, String nombreDispositivo,
                       String marcaDispositivo, String dañoDispositivo, String empleadoAsignado,
                       String fechaRecepcion, String estado) {
        this.idDispositivo = idDispositivo;
        this.nombreCliente = nombreCliente;
        this.nombreDispositivo = nombreDispositivo;
        this.marcaDispositivo = marcaDispositivo;
        this.dañoDispositivo = dañoDispositivo;
        this.empleadoAsignado = empleadoAsignado;
        this.fechaRecepcion = fechaRecepcion;
        this.estado = estado;
    }


    public Dispositivo(int idReparacion, String nombreCliente, String nombreDispositivo,
                       String empleadoAsignado, String fechaRecepcion, String estado,
                       String descripcion, byte[] imagenDispositivo) {
        this(idReparacion, nombreCliente, nombreDispositivo, empleadoAsignado, fechaRecepcion, estado, descripcion);
        this.imagenDispositivo = imagenDispositivo;
    }

    public Dispositivo() {
    }

    public int getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public int getIdReparacion() {
        return idReparacion;
    }

    public void setIdReparacion(int idReparacion) {
        this.idReparacion = idReparacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreDispositivo() {
        return nombreDispositivo;
    }

    public void setNombreDispositivo(String nombreDispositivo) {
        this.nombreDispositivo = nombreDispositivo;
    }

    public String getMarcaDispositivo() {
        return marcaDispositivo;
    }

    public void setMarcaDispositivo(String marcaDispositivo) {
        this.marcaDispositivo = marcaDispositivo;
    }

    public String getDañoDispositivo() {
        return dañoDispositivo;
    }

    public void setDañoDispositivo(String dañoDispositivo) {
        this.dañoDispositivo = dañoDispositivo;
    }

    public String getEmpleadoAsignado() {
        return empleadoAsignado;
    }

    public void setEmpleadoAsignado(String empleadoAsignado) {
        this.empleadoAsignado = empleadoAsignado;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Imagen en bytes (BLOB)
    public byte[] getImagenDispositivo() {
        return imagenDispositivo;
    }

    public void setImagenDispositivo(byte[] imagenDispositivo) {
        this.imagenDispositivo = imagenDispositivo;
    }


    public Image getImagenFX() {
        if (imagenDispositivo != null && imagenDispositivo.length > 0) {
            try {
                return new Image(new ByteArrayInputStream(imagenDispositivo));
            } catch (Exception e) {
                
                return null;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Dispositivo{" +
                "idDispositivo=" + idDispositivo +
                ", idReparacion=" + idReparacion +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", nombreDispositivo='" + nombreDispositivo + '\'' +
                ", empleadoAsignado='" + empleadoAsignado + '\'' +
                '}';
    }
}
