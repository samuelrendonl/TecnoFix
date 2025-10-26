package Modelo;

public class DispositivoConBytes extends Dispositivo {

    private byte[] imagenBytes;  // 🔹 Imagen almacenada como bytes (desde la BD)

    // 🔸 Constructor vacío
    public DispositivoConBytes() {
        super();
    }

    // 🔸 Constructor con parámetros (puedes adaptarlo a tus campos)
    public DispositivoConBytes(int idReparacion, String nombreCliente, String nombreDispositivo,
                               String empleadoAsignado, String fechaRecepcion,
                               String estado, String descripcion, byte[] imagenBytes) {
        super(idReparacion, nombreCliente, nombreDispositivo, empleadoAsignado, fechaRecepcion, estado, descripcion);
        this.imagenBytes = imagenBytes;
    }

    public byte[] getImagenBytes() {
        return imagenBytes;
    }

    public void setImagenBytes(byte[] imagenBytes) {
        this.imagenBytes = imagenBytes;
    }

    public boolean tieneImagen() {
        return imagenBytes != null && imagenBytes.length > 0;
    }

    @Override
    public String toString() {
        return "DispositivoConBytes{" +
                "idReparacion=" + getIdReparacion() +
                ", nombreCliente='" + getNombreCliente() + '\'' +
                ", nombreDispositivo='" + getNombreDispositivo() + '\'' +
                ", estado='" + getEstado() + '\'' +
                ", tieneImagen=" + (tieneImagen() ? "Sí" : "No") +
                '}';
    }
}
