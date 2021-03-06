package jpa.collectionmappings.order_by;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by john_000 on 11/4/2016.
 */
@Entity
public class MeteringUnit {

    @GeneratedValue
    @Id
    private long id;

    @OneToMany(mappedBy = "meteringUnit", fetch = FetchType.EAGER,
    cascade = { CascadeType.ALL})
    @OrderBy("value DESC") // ASC is the default
    private List<Sensor> sensors = new ArrayList<>();


    public MeteringUnit() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MeteringUnit{");
        sb.append("id=").append(id);
        sb.append(", sensors=").append(getSensors());
        sb.append('}');
        return sb.toString();
    }
}
