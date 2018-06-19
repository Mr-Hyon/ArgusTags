package argustags.argustags_phase_ii.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "image")
public class Image  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private  Long imgid;
    @Column(nullable = false,length = 2147483647)
    private String base64 ;
    @OneToMany
    @JoinColumn(name = "imageid")
    private List<Tag> Tags = new ArrayList<Tag>();

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

    public List<Tag> getTags() { return Tags; }

    public void setTags(ArrayList<Tag> Tags) { this.Tags = Tags; }
}
