import java.util.*;

/*
TODO Add recursive adds and removes
TODO Fix some warnings
TODO Consider/Add Core Tag
TODO Add check for completeness

 */

public class SlotItem{

    private List<String> slots = new ArrayList<String>();
    private List<SlotItem> slotItems = new ArrayList<SlotItem>();

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
        
        this.values = newValues;

        for (String key : values.keySet()) {
            totalValues.put(key, values.get(key));
        }
    }

    //Simple Getters, Setters
    public String getTitle(){
        return title;
    }

    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String newType){
        this.type = newType;
    }

    public HashMap<String, Integer> getValues(){
        return this.values;
    }

    public HashMap<String, Integer> getTotalValues(){
        return this.totalValues;
    }



    //ToString
    public String toString(){
        String outString = "";
        outString += "Title: " +title + "\n";
        outString += "Type: " + type + "\n";

        outString += ("Slots:       " + this.slots + "\n");
        outString += ("FreeSlots:   " + this.getThisFreeSlots() + "\n");

        outString += "SubItems: \n";
        for (SlotItem item : slotItems) {
            outString += "\t" + item.toStringSimple() + "\n";
        }

        outString += ("Values:      " + this.values + "\n");
        outString += ("TotalValues: " + this.totalValues + "\n");

        return outString;
    }

    public String toStringSimple(){
        String outString = "";

        outString += "Item: " + this.title + ", " + this.type;

        return outString;
    }



    //Public Methods
    //Try to add Item to THIS item if slot is available
    public SlotItem addItem(SlotItem newItem){
        if(this.getThisFreeSlots().contains(newItem.type)) {
            this.slotItems.add(newItem);
        }

        calcTotalValues();
        return newItem;
    }

    //Remove Item from THIS item
    public void removeItem(SlotItem item){
        if(this.slotItems.contains(item))
        {
            this.slotItems.remove(item);
        }
        else{
            //throw error?
        }

        calcTotalValues();
    }

    //Add Up total values and update
    public void calcTotalValues(){
        //Reset this items total values
        this.totalValues.clear();

        //Repopulate from values
        for (String key : values.keySet()) {
            totalValues.put(key, values.get(key));
        }

        //Calc totals on each subitem
        for (SlotItem item: this.slotItems) {
            //Recurse on each subitem
            item.calcTotalValues();

            //Total from subitem
            for (String key : item.totalValues.keySet()) {
                //Subitem has a duplicate key, sum and replace
                if(values.containsKey(key)){
                    totalValues.replace(key, totalValues.get(key) + item.getTotalValues().get(key));
                }
                //No duplicate, just place in list
                else{
                    totalValues.put(key, item.getTotalValues().get(key));
                }
            }
        }

    }

    //Returns a list containing all available slots on THIS item
    public List<String> getThisFreeSlots(){
        List<String> checkSlots = new ArrayList<String>();
        for (String slot : this.slots) {
            checkSlots.add(slot);
        }

        for (SlotItem item: this.slotItems) {
            if(checkSlots.contains(item.type)){
                checkSlots.remove(item.type);
            }
        }

        return checkSlots;
    }




    public static void main(String[] args) {
        System.out.println("TESTING: \n");

        HashMap<String, Integer> valuesInit1 = new HashMap<String, Integer>();
        valuesInit1.put("Attack", 40);
        valuesInit1.put("Defense", 20);
        valuesInit1.put("Shield", 5);
        SlotItem S1 = new SlotItem("SwordOne", "SwordBase", new String[]{"hilt", "guard", "pommel"}, valuesInit1);


        HashMap<String, Integer> valuesInit2 = new HashMap<String, Integer>();
        valuesInit2.put("Magic", 40);
        valuesInit2.put("Attack", 20);
        valuesInit2.put("Agility", 5);
        SlotItem S2 = new SlotItem("Jeweled Pommel", "pommel", new String[]{"jewel"}, valuesInit2);


        System.out.println(S1.toString() + "\n");
        System.out.println(S2.toString() + "\n");

        //Test adding and recalc of total values
        System.out.println("Test adding and recalc of total values");
        System.out.println(S1.toString());
        System.out.println(S2.toString());
        S1.addItem(S2);
        System.out.println(S1.toString());
        S1.removeItem(S2);
        System.out.println(S1.toString());



    }
}