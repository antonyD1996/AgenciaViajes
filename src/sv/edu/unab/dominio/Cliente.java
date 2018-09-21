package sv.edu.unab.dominio;

import java.time.LocalDate;

public class Cliente extends Persona {
    public Cliente() {
    }

    public Cliente(Long id) {
        super(id);
    }

    public Cliente(Long id, String nombre, String apellidopaterno, String apellidomaterno, String dui, String nit, LocalDate fechaNacimiento, String telefono, String direccion, String email) {
        super(id, nombre, apellidopaterno, apellidomaterno, dui, nit, fechaNacimiento, telefono, direccion, email);
    }

}
