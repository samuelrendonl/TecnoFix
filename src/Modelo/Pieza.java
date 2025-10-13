package Modelo;

public class Pieza {
    private int id;
    private String nombre;
    private String tipo;
    private int precio;
    private int cantidad;
    private byte[] imagen;

    public Pieza(int id, String nombre, String tipo, int precio, int cantidad, byte[] imagen) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }

    public Pieza(String nombre, String tipo, int precio, int cantidad, byte[] imagen) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }

    // Getters y setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
    public byte[] getImagen() { return imagen; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setPrecio(int precio) { this.precio = precio; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setImagen(byte[] imagen) { this.imagen = imagen; }
}
