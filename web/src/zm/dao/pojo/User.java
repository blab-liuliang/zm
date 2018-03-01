package zm.dao.pojo;

import javax.persistence.*;

@Entity
@Table(name="USER")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int      id;

    @Column(name = "uuid")
    private String uuid;

    public User(){
    }

    public int getId() {
        return id;
    }

    public void setId( int id){
        this.id = id;
    }

    public String getUuid(){
        return uuid;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }
}
