import java.io.*;
import java.util.Scanner;
class MainClass extends NodeClass{

    MainClass(String name,String number){
        this.setName(name);
        this.setNum(number);
        this.next = null;
    }
    
    MainClass(){
        
    }
    @Override
    public String toString(){
        return "Name = "+ getName() + "Phone number = " + getNum();
    }

    String checkNumber(Scanner scan){
        String number = null;
        do {
            System.out.print("Enter your number:");
            number = scan.nextLine();
            if(start == null){
                return number;
            }
            else{
                NodeClass temp = start;
                while(temp!=null){
                    if(number.equals(temp.getNum())){
                        System.out.println("The number already exist.");
                        number = null;
                        break;
                    }
                    temp = temp.next;
                }
            }
        } while (number == null);
        return number;
    }

    void readRecord(){
        try (ObjectInputStream output = new ObjectInputStream(new FileInputStream("Contact.txt"));) {
            while (true) {
                try {
                    FileWrite readObj = (FileWrite) output.readObject();
                    MainClass object = new MainClass(readObj.getName(), readObj.getNum());
                    if (start == null) {
                        start = object;
                        object.prev = null;
                    } else {
                        NodeClass temp = start;
                        while (temp.next != null) {
                            temp = temp.next;
                        }
                        temp.next = object;
                        object.prev = temp;
                    }
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Errors for class");
                }
            }
            output.close();
        } catch (IOException e) {
        }
    }

    void write(NodeClass temp){
        FileWrite obj = new FileWrite();
        obj.writeJava(temp);
    }

    void insert(Scanner scan){
        System.out.print("Enter your name:");
        String name = scan.nextLine();
        String number = checkNumber(scan);
        NodeClass obj = new MainClass(name, number);
        if(start == null){
            start = obj;
            start.prev = null;
        }
        else{
            NodeClass temp = start;
            while(temp.next!=null)  
                temp = temp.next;
            temp.next = obj;
            obj.prev = temp;
        }
        System.out.println("Insertion successfull\n");
        write(start);
    }

    void display(){
        if(start == null){
            System.out.println("No data is currently in the record.");
        }
        else{
            NodeClass temp = start;
            System.out.println("==============================");
            System.out.println("Name\t\t Phone Number");
            System.out.println("==============================");
            while(temp!=null){
                System.out.printf("%-20s %-20s\n",temp.getName(),temp.getNum());
                temp = temp.next;
            }
        }
    }

    void delete(Scanner scan){
        boolean found = false;
        System.out.print("Enter your Phone number(must be 10):");
        String number =scan.nextLine();
        NodeClass temp = start;
        while(temp!=null){
            if(number.equals(temp.getNum())){
                found = true;
                String name = temp.getName();
                if(temp.next == null && temp.prev == null ){ // only node
                    start = null;
                }
                else if(temp.next == null){ // last node
                    NodeClass temp2 = temp.prev;
                    temp2.next = null;
                    temp.next = null;
                }
                else if(temp.prev == null){ // first node
                    start = start.next;
                    start.prev = null;
                    temp.next = null;
                }else{ // specific node
                    NodeClass temp2 = temp.prev;
                    temp2.next = temp.next;
                    temp.next=null;
                    temp.prev=null;
                    temp = temp2.next;
                    temp.prev= temp2;
                }
                System.out.println("The record of " + name + " has been deleted.");
                break;
            }
            temp = temp.next;
        }
        if(!found)
            System.out.print("The data could not be found\n");
        write(start);
    }
    
    int searchMenu(Scanner scan){
        int choice;
        System.out.println("1.Search by name\n2.Search by number");
        System.out.print("Enter your choice:");
        choice = scan.nextInt();
        scan.nextLine();
        switch(choice){
            case 1:
                return 1;
            case 2:
                return 0;
            default:
                System.out.println("Invalid choice Please try again!!");
                return 2;
        }
    }

    void search(Scanner scan){
        boolean found = false;
        int ch = searchMenu(scan);
        if(ch == 0){
            System.out.print("Enter Phone number(must be 10):");
            String number =scan.nextLine();
            NodeClass temp = start;
            while(temp!=null){ 
                if(temp.getNum().equals(number)){
                    found = true;
                    System.out.printf("%-10s %-10s\n",temp.getName(),temp.getNum());
                }
                temp = temp.next;
            }
            if(!found){
                System.out.print("The data could not be found");
            }
        }else{
            System.out.print("Enter name:");
            String name =scan.nextLine();
            NodeClass temp = start;
            while(temp!=null){ 
                if(temp.getName().equals(name)){
                    found = true;
                    System.out.printf("%-10s %-10s\n",temp.getName(),temp.getNum());
                }
                temp = temp.next;
            }
            if(!found){
                System.out.print("The data could not be found");
            }
        }
    }

    public static void main(String[] args) {
        int choice;
        MainClass obj = new MainClass();
        Scanner scan = new Scanner(System.in);
        obj.readRecord();
        do
        {   
            System.out.println("================================");
            System.out.println("\tPhone Book Menu");
            System.out.println("================================");
            System.out.println("1.Insert phone number\n2.Display\n3.Delete phone number\n4.Search\n5.exit");
            System.out.print("Enter your choice:");
            choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1:
                    obj.insert(scan);
                    break;
                case 2:
                    obj.display();
                    break;
                case 3:
                    obj.delete(scan);
                    break;
                case 4:
                    obj.search(scan);
                    break;
                case 5:
                    obj.write(obj.start);
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!!/nPlease try agaub");
                    break;
            }
        System.out.print("do you want to continue:");
        }while(scan.next().equalsIgnoreCase("y"));
        obj.write(obj.start);
    }
}