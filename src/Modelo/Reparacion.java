package Modelo;

public class Reparacion {

    private int idReparacion;
    private String fechaRecepcion;
    private String descripcion;
    private String estado;
    private String nombreCliente;
    private String nombreDispositivo;
    private String nombreEmpleado;

    // ====== CONSTRUCTORES ======
    public Reparacion() {
    }

    public Reparacion(int idReparacion, String fechaRecepcion, String descripcion, String estado,
                      String nombreCliente, String nombreDispositivo, String nombreEmpleado) {
        this.idReparacion = idReparacion;
        this.fechaRecepcion = fechaRecepcion;
        this.descripcion = descripcion;
        this.estado = estado;
        this.nombreCliente = nombreCliente;
        this.nombreDispositivo = nombreDispositivo;
        this.nombreEmpleado = nombreEmpleado;
    }

    // ====== GETTERS Y SETTERS ======
    public int getIdReparacion() {
        return idReparacion;
    }

    public void setIdReparacion(int idReparacion) {
        this.idReparacion = idReparacion;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    // ====== TO STRING (opcional, útil para depuración) ======
    @Override
    public String toString() {
        return "Reparacion{" +
                "idReparacion=" + idReparacion +
                ", fechaRecepcion='" + fechaRecepcion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", nombreDispositivo='" + nombreDispositivo + '\'' +
                ", nombreEmpleado='" + nombreEmpleado + '\'' +
                '}';
    }
}
