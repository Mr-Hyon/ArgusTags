package argustags.argustags_phase_ii.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    @Id
    @GeneratedValue
    private  Long tagid;
    private String tagcontent ;

    public Long getId() {
        return tagid;
    }

    public void setId(Long tagid) {
        this.tagid = tagid;
    }

    public String getTag() { return tagcontent; }

    public void setTag(String tagcontent) {
        this.tagcontent = tagcontent;
    }
}
