import java.io.Serializable;

public class NodeClass implements Serializable{
    NodeClass start = null;
    private String name;
    private String number;
    NodeClass next;
    NodeClass prev;
    String getNum(){
        return number;
    }

    void setNum(String number){
        this.number = number;
    }

    String getName(){
        return name;
    }

    void setName(String name){
        this.name = name;
    }
}
