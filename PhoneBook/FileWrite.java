import java.io.*;

public class FileWrite extends NodeClass {

    FileWrite(String name,String num){
        setName(name);
        setNum(num);
    }
    FileWrite(){

    }
    public String toString(){
        return "Name = "+ getName() + "Phone number = " + getNum();
    }

    void writeJava(NodeClass temp){
        try{
            File fileCheck = new File("contact.txt");
            if(fileCheck.createNewFile()){
                System.out.println("New File Creted.");
            }
            try{
                ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream("contact.txt"));
                while(temp!=null){
                    FileWrite writeObj = new FileWrite(temp.getName(),temp.getNum());
                    obj.writeObject(writeObj);
                    temp = temp.next;
                }
                obj.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
}
