package argustags.argustags_phase_ii.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "image")
public class Image implements Serializable {
    @Id
    @GeneratedValue
    private  Long imgid;
    private String base64 ;

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "ImgID", nullable = false)
    private ArrayList<Tag> Tags;

    public Image(){

    }

    public Image(Long imgid,String base64,ArrayList<Tag> Tags){
        this.imgid = imgid;
        this.base64 = base64;
        this.Tags = Tags;
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

    public ArrayList<Tag> getTags() { return Tags; }

    public void setTags(ArrayList<Tag> Tags) { this.Tags = Tags; }
}
