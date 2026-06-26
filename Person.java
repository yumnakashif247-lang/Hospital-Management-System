abstract class Person {
    protected String ID;
    protected String Name;

    public Person(String ID, String Name) {
        this.ID = ID; 
        setName(Name);
    }

    public String getName() {
        return Name;
    }

    public String getId() {
        return ID;
    }

    public Boolean isvalidName(String Name) {
        if (Name == null || Name.trim().isEmpty()) {
            return false;
        }
        for (int i = 0; i < Name.length(); i++) {
            char c = Name.charAt(i);
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }

    public void setName(String Name) {
        if (isvalidName(Name)) {
            this.Name = Name;
        } else {
            System.out.println("Invalid Input. Name can only contain Letters and spaces");
        }
    }

    abstract public String toString();
}