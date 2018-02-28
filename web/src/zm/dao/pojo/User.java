package zm.dao.pojo;

import javax.persistence.*;

@Entity
@Table(name="User")
public class User {

    @Id @GeneratedValue
    @Column(name = "id")
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
