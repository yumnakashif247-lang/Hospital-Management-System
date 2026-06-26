class Patient extends Person {
    private String Disease;
    private Doctor AssignedDoctor;
    private String Gender;

    public Patient(String ID, String Name, String Disease, Doctor AssignedDoctor, String Gender) {
        super(ID, Name);
        setID(ID);
        setDisease(Disease);
        this.AssignedDoctor = AssignedDoctor;
        setGender(Gender);
    }

    public Boolean isValidPatientID(String ID) {
        if (ID == null || ID.length() != 5) return false;

        char d1 = ID.charAt(0);
        char d2 = ID.charAt(1);
        char d3 = ID.charAt(2);
        char dash = ID.charAt(3);
        char last = ID.charAt(4);

        if (!Character.isDigit(d1) || !Character.isDigit(d2) || !Character.isDigit(d3)) return false;
        if (dash != '-') return false;
        if (!Character.isUpperCase(last)) return false;

        return true;
    }

    public void setID(String ID) {
        if (isValidPatientID(ID)) {
            this.ID = ID;
        } else {
            System.out.println("Invalid Patient ID! Format 000-A");
        }
    }

    public Boolean isValidGender(String gender) {
        if (gender == null) return false;
        gender = gender.trim().toLowerCase();
        return gender.equals("male") || gender.equals("female");
    }

    public void setGender(String gender) {
        if (isValidGender(gender)) {
            this.Gender = gender.substring(0, 1).toUpperCase() +
                          gender.substring(1).toLowerCase();
        } else {
            System.out.println("Invalid Gender!");
        }
    }

    public String getGender() {
        return Gender;
    }

    public String getDisease() {
        return Disease;
    }

    public Doctor getAssignedDoctor() {
        return AssignedDoctor;
    }

    public Boolean isValidDisease(String Disease) {
        if (Disease == null || Disease.trim().isEmpty()) return false;
        for (int i = 0; i < Disease.length(); i++) {
            char c = Disease.charAt(i);
            if (!Character.isLetter(c) && c != ' ') return false;
        }
        return true;
    }

    public void setDisease(String Disease) {
        if (isValidDisease(Disease)) {
            this.Disease = Disease;
        } else {
            System.out.println("Invalid Disease!");
        }
    }

    public void setAssignedDoctor(Doctor d) {
        this.AssignedDoctor = d;
    }

    public String toString() {
        String docName = (AssignedDoctor != null) ? AssignedDoctor.getName() : "Not Assigned";
        return "Patient ID: " + ID + " | Name: " + Name +
               " | Disease: " + Disease +
               " | Doctor: " + docName +
               " | Gender: " + Gender;
    }
}
