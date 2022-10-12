import java.util.*;

public class SlotItem{

    private List<String> slots = new ArrayList<String>();
    private List<SlotItem> SlotItems = new ArrayList<SlotItem>();

    private String title;
    private String type;

    private HashMap<String, Integer> values;
    private HashMap<String, Integer> totalValues = new HashMap<String, Integer>();

    //Constructors
    public SlotItem(String newTitle, String newType, String[] newSlots, HashMap<String, Integer> newValues){
        this.title = newTitle;
        this.type = newType;

        for (String slot: newSlots) {
            slots.add(slot);
        }
        
        values = newValues;
    }

    //Simple Getters, Setters
    public String getTitle(){
        return title;
    }

    public void setTitle(String newTitle){
        title = newTitle;
    }

    public String getType(){
        return type;
    }

    public void setType(String newType){
        type = newType;
    }


    //ToString
    public String toString(){
        String outString = "";
        outString += "Title: " +title + "\n";
        outString += "Type: " + type + "\n";

        outString += "Slots: ";
        for (String slot : slots) {
            outString += slot + " ";
        }




        return outString;
    }



    //Public Methods
    //Try to add Item to THIS item
    public void addItem(){

    }

    //Remove Item from THIS item
    public void removeItem(){

    }

    //Add Up total values and update
    public void calcTotalValues(){

    }

    public void getFreeSlots(){

    }




    public static void main(String[] args) {
        System.out.println("TESTING: \n");

        String[] slotInit1 = new String[]{"hilt", "guard", "pommel"};
        HashMap<String, Integer> valuesInit1 = new HashMap<String, Integer>();
        valuesInit1.put("Attack", 40);
        valuesInit1.put("Defense", 20);
        valuesInit1.put("Shield", 5);
        SlotItem S1 = new SlotItem("Holy Blade", "Sword Blade", slotInit1, valuesInit1);

        String[] slotInit2 = new String[]{"jewel"};
        HashMap<String, Integer> valuesInit2 = new HashMap<String, Integer>();
        valuesInit1.put("Magic", 40);
        valuesInit1.put("Attack", 20);
        valuesInit1.put("Agility", 5);
        SlotItem S2 = new SlotItem("Jeweled Pommel", "Pommel", slotInit1, valuesInit1);

        System.out.println(S1.toString());
        System.out.println(S2.toString());
    }
}