package argustags.argustags_phase_ii.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "image")
public class Image implements Serializable {
    @Id
    @GeneratedValue
    private  Long imgid;
    private String base64 ;
    private List<Tag> Tags;

    public Image(){

    }

    public Long getId() {
        return imgid;
    }

    public void setId(Long imgid) {
        this.imgid = imgid;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public List<Tag> getTags() { return Tags; }

    public void setTags(List<Tag> Tags) { this.Tags = Tags; }
}