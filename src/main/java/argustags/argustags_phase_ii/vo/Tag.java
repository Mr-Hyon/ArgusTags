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
    private String tagStart;
    private String tagEnd;
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
