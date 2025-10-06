
package Modelo;

public class Dispositivo {
    private int idDispositivo;
    private String nombreCliente;
    private String nombreDispositivo;
    private String marcaDispositivo;
    private String dañoDispositivo;
    private String empleadoAsignado;

    public Dispositivo(int idDispositivo, String nombreCliente, String nombreDispositivo, String marcaDispositivo, String dañoDispositivo, String empleadoAsignado) {
        this.idDispositivo = idDispositivo;
        this.nombreCliente = nombreCliente;
        this.nombreDispositivo = nombreDispositivo;
        this.marcaDispositivo = marcaDispositivo;
        this.dañoDispositivo = dañoDispositivo;
        this.empleadoAsignado = empleadoAsignado;
    }

    // Getters y setters
    public int getIdDispositivo() { return idDispositivo; }
    public void setIdDispositivo(int idDispositivo) { this.idDispositivo = idDispositivo; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getNombreDispositivo() { return nombreDispositivo; }
    public void setNombreDispositivo(String nombreDispositivo) { this.nombreDispositivo = nombreDispositivo; }

    public String getMarcaDispositivo() { return marcaDispositivo; }
    public void setMarcaDispositivo(String marcaDispositivo) { this.marcaDispositivo = marcaDispositivo; }

    public String getDañoDispositivo() { return dañoDispositivo; }
    public void setDañoDispositivo(String dañoDispositivo) { this.dañoDispositivo = dañoDispositivo; }

    public String getEmpleadoAsignado() { return empleadoAsignado; }
    public void setEmpleadoAsignado(String empleadoAsignado) { this.empleadoAsignado = empleadoAsignado; 
    
    }}
