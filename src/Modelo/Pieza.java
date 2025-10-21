package Modelo;

public class Pieza {
    private int id;
    private String nombre;
    private String tipo;
    private double precio;
    private int cantidad;
    private byte[] imagen;
    private String proveedor;
    private String marca;
    private int usadas; // cantidad usada en reparaciones

    public Pieza(int id, String nombre, String tipo, double precio, int cantidad, byte[] imagen) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }
    
    public Pieza(int id, String nombre, String tipo, int precio, int cantidad, String proveedor, String marca, byte[] imagen) {
    this.id = id;
    this.nombre = nombre;
    this.tipo = tipo;
    this.precio = precio;
    this.cantidad = cantidad;
    this.proveedor = proveedor;
    this.marca = marca;
    this.imagen = imagen;
}


    public Pieza(String nombre, String tipo, double precio, int cantidad, byte[] imagen) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }

    public Pieza(int id, String nombre, String tipo, double precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Pieza() {}

    // ======== GETTERS Y SETTERS ========
    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public byte[] getImagen() { return imagen; }
    public void setImagen(byte[] imagen) { this.imagen = imagen; }

    public int getUsadas() { return usadas; }
    public void setUsadas(int usadas) { this.usadas = usadas; }
}
