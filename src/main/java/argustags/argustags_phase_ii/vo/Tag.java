package argustags.argustags_phase_ii.vo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tag")
public class Tag  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private  int tagid;
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

    public Tag(String tagcontent,String tagStart,String tagEnd,String workerName){
        this.tagcontent = tagcontent;
        this.tagStart = tagStart;
        this.tagEnd = tagEnd;
        this.workerName = workerName;
    }


    public int getId() {
        return tagid;
    }

    public void setId(int tagid) {
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

    public double[] getMiddle(){
        String[] start = tagStart.split(",");
        String[] end = tagEnd.split(",");
        double[] startNum = new double[2];
        startNum[0] = Double.parseDouble(start[0]);
        startNum[1] = Double.parseDouble(start[1]);
        double[] endNum = new double[2];
        endNum[0] = Double.parseDouble(end[0]);
        endNum[1] = Double.parseDouble(end[1]);
        double[] result = new double[2];
        result[0] = (startNum[0]+endNum[0])/2;
        result[1] = (startNum[1]+endNum[1])/2;
        return result;
    }
}
