package Modelo;

public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String usuario;
    private String password_hash;
    private String rol;
    private Integer id_admin;
    private int asignaciones;
    private int reparacionesCompletadas;

    // 🔹 Constructor vacío (JavaFX lo necesita)
    public Empleado() {
    }

    // 🔹 Constructor general con todos los campos
    public Empleado(int idEmpleado, String nombre, String usuario, String password_hash,
                    String rol, Integer id_admin, int asignaciones, int reparacionesCompletadas) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.usuario = usuario;
        this.password_hash = password_hash;
        this.rol = rol;
        this.id_admin = id_admin;
        this.asignaciones = asignaciones;
        this.reparacionesCompletadas = reparacionesCompletadas;
    }

    // 🔹 Constructor usado por la mayoría de tus controladores
    public Empleado(int idEmpleado, String nombre, String usuario, String rol) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.usuario = usuario;
        this.rol = rol;
    }

    // 🔹 Otros constructores útiles
    public Empleado(int idEmpleado, String nombre) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
    }

    public Empleado(String nombre, String usuario, String rol) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.rol = rol;
    }

    public Empleado(int idEmpleado, String nombre, String usuario) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.usuario = usuario;
    }

    // Getters y Setters
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

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getId_admin() {
        return id_admin;
    }

    public void setId_admin(Integer id_admin) {
        this.id_admin = id_admin;
    }

    public int getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(int asignaciones) {
        this.asignaciones = asignaciones;
    }

    public int getReparacionesCompletadas() {
        return reparacionesCompletadas;
    }

    public void setReparacionesCompletadas(int reparacionesCompletadas) {
        this.reparacionesCompletadas = reparacionesCompletadas;
    }

    @Override
    public String toString() {
        return nombre != null ? nombre : "Empleado";
    }
}
