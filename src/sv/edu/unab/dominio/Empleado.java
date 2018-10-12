package sv.edu.unab.dominio;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.StringJoiner;

@Entity
@Table(schema = "avr",name = "empleados")
@SequenceGenerator(schema = "avr",sequenceName = "empleados_id_seq",name = "Empleado_seq_id",allocationSize = 1)
@NamedQueries({
        @NamedQuery(name = "Empleado.findAll",query = "SELECT e FROM Empleado e order by e.id")
})

public class Empleado implements Serializable{
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "Empleado_seq_id")
    @Column(name="id")
    private Long id;
    @NotNull
    @Column(name = "seguro")
    private String isss;
    @NotNull
    @Column(name = "afp")
    private String afp;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,targetEntity = Persona.class,optional = false)
    @JoinColumn(name = "idpersona",referencedColumnName = "id",unique = true)
    private Persona datosPersonales;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsss() {
        return isss;
    }

    public void setIsss(String isss) {
        this.isss = isss;
    }

    public String getAfp() {
        return afp;
    }

    public void setAfp(String afp) {
        this.afp = afp;
    }

    public Persona getDatosPersonales() {
        return datosPersonales;
    }

    public void setDatosPersonales(Persona datosPersonales) {
        this.datosPersonales = datosPersonales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empleado)) return false;

        Empleado empleado = (Empleado) o;

        return id.equals(empleado.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Empleado.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("isss='" + isss + "'")
                .add("afp='" + afp + "'")
                .add("datosPersonales=" + datosPersonales)
                .toString();
    }
}
