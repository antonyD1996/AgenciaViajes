package sv.edu.unab.dominio;

import java.time.LocalDate;
import java.util.StringJoiner;

public class Empleado extends Persona {
    private String seguro;
    private String afp;

    public Empleado(String seguro, String afp) {
        this.seguro = seguro;
        this.afp = afp;
    }

    public Empleado(Long id, String seguro, String afp) {
        super(id);
        this.seguro = seguro;
        this.afp = afp;
    }

    public Empleado(Long id, String nombre, String apellidopaterno, String apellidomaterno, String dui, String nit, LocalDate fechaNacimiento, String telefono, String direccion, String email, String seguro, String afp) {
        super(id, nombre, apellidopaterno, apellidomaterno, dui, nit, fechaNacimiento, telefono, direccion, email);
        this.seguro = seguro;
        this.afp = afp;
    }

    public Empleado() {
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    public String getAfp() {
        return afp;
    }

    public void setAfp(String afp) {
        this.afp = afp;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Empleado.class.getSimpleName() + "[", "]")
                .add("seguro='" + seguro + "'")
                .add("afp='" + afp + "'")
                .toString();
    }
}
