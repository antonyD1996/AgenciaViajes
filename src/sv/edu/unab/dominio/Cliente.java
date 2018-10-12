package sv.edu.unab.dominio;

import javax.persistence.*;
import java.io.Serializable;
import java.util.StringJoiner;

@Entity
@Table(schema = "avr",name = "clientes")
@SequenceGenerator(schema = "avr",sequenceName = "clientes_id_seq",name = "Cliente_seq_id",allocationSize = 1)
@NamedQueries({
        @NamedQuery(name = "Cliente.findAll",query = "SELECT c FROM Cliente c order by c.id")
})
public class Cliente implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE,generator = "Cliente_seq_id")
    @Column(name="id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,targetEntity = Persona.class,optional = false)
    @JoinColumn(name = "idpersona",referencedColumnName = "id",unique = true)
    private Persona datosPersonales;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof Cliente)) return false;

        Cliente cliente = (Cliente) o;

        return id.equals(cliente.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Cliente.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("datosPersonales=" + datosPersonales)
                .toString();
    }
}
