import java.util.*;

/*
TODO Add recursive adds and removes
TODO Fix some warnings
TODO Consider/Add Core Tag
TODO Add check for completeness
TODO Add recursive total slots

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


    //Public Specific Methods
    //Try to add Item to THIS item if slot is available
    public boolean addItem(SlotItem newItem){
        //Check if slot is available
        if(this.getThisFreeSlots().contains(newItem.type)) {
            this.slotItems.add(newItem);
        }
        else{
            return false;
        }

        calcTotalValues();
        return true;
    }

    //Remove Item from THIS item
    public boolean removeItem(SlotItem item){
        if(this.slotItems.contains(item))
        {
            this.slotItems.remove(item);
        }
        else{
            return false;
        }

        calcTotalValues();
        return true;
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

    //Public Specific Methods Deep
    //Try to add Item to this or any subitem if slot is available
    public boolean addItemDeep(SlotItem newItem){
        //Check if slot is available and add to THIS item
        if(this.getThisFreeSlots().contains(newItem.type)) {
            this.slotItems.add(newItem);
            calcTotalValues();
            return true;
        }
        //Try to add to all subitems
        else{
            for (SlotItem item: this.slotItems) {
                if (item.addItemDeep(newItem)) {
                    calcTotalValues();
                    return true;
                }
            }
        }
        return false;
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

        HashMap<String, Integer> valuesInit3 = new HashMap<String, Integer>();
        valuesInit3.put("Health", 40);
        valuesInit3.put("Attack", 10);
        valuesInit3.put("Style", 8);
        SlotItem S3 = new SlotItem("Rambunctious Ruby", "jewel", new String[]{}, valuesInit3);


        System.out.println(S1 + "\n");
        System.out.println(S2 + "\n");
        System.out.println(S3 + "\n");

        //Test adding and recalc of total values
        System.out.println("Test adding and recalc of total values");
        System.out.println(S1);
        System.out.println(S2);
        S1.addItem(S2);
        System.out.println(S1);
        S1.removeItem(S2);
        System.out.println(S1);

        //Test adding deeply
        System.out.println("Test adding and removing deeply");
        S1.addItem(S2);
        System.out.println(S1.addItemDeep(S3));

        System.out.println(S1 + "\n");
        System.out.println(S2 + "\n");
        System.out.println(S3 + "\n");



    }
}