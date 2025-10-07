package Modelo;

public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String usuario;
    private String rol;

    public Empleado(int idEmpleado, String nombre, String usuario, String rol) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.usuario = usuario;
        this.rol = rol;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
