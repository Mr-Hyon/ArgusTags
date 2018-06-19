package argustags.argustags_phase_ii.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import argustags.argustags_phase_ii.vo.Tag;

@Entity
@Table(name = "image")
public class Image  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private  int imgid;
    @Column(nullable = false,length = 2147483647)
    private String base64 ;

    @Column(nullable = false)
    private String Tags ;

    public Image(){

    }

    public Image(int imgid,String base64,ArrayList<Integer> Tags){
        this.imgid = imgid;
        this.base64 = base64;
        String s = "";
        for(int i = 0;i<Tags.size();i++){
            int temp = Tags.get(i);
            s=s+temp;
            if(i==Tags.size()-1){
                ;
            }
            else{
                s=s+" ";
            }
        }

        this.Tags = s;

    }

    public int getId() {
        return imgid;
    }

    public void setId(int imgid) {
        this.imgid = imgid;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public ArrayList<Integer> getTags() {
        String[] s1 = Tags.split(" ");
        ArrayList<Integer> res = new ArrayList<>();
        for(int i = 0;i<s1.length;i++){
            res.add(Integer.parseInt(s1[i]));
        }
        return res;
    }

    public void setTags(ArrayList<Integer> Tags) {
        String s = "";
        for(int i = 0;i<Tags.size();i++){
            int temp = Tags.get(i);
            s=s+temp;
            if(i==Tags.size()-1){
                ;
            }
            else{
                s=s+" ";
            }
        }

        this.Tags = s;

    }
}
