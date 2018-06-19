package argustags.argustags_phase_ii.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tag")
public class Tag  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private  Long tagid;
    @Column(nullable = false)
    private String tagcontent ;
    @Column(nullable = false)
    private String tagStart;
    @Column(nullable = false)
    private String tagEnd;
    @Column(nullable = false)
    private String workerName;

    public Tag(){

    }


    public Long getId() {
        return tagid;
    }

    public void setId(Long tagid) {
        this.tagid = tagid;
    }

    public String getTagStart(){
        return tagStart;
    }

    public void setTagStart(String startCoord){
        this.tagStart = startCoord;
    }

    public String getTagEnd(){
        return tagEnd;
    }

    public void setTagEnd(String endCoord){
        this.tagEnd = endCoord;
    }

    public String getTag() {
        return tagcontent;
    }

    public void setTag(String tagcontent) {
        this.tagcontent = tagcontent;
    }

    public String getWorkerName(){
        return workerName;
    }

    public void setWorkerName(String workername){
        this.workerName = workername;
    }
}
