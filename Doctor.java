class Doctor extends Person {
    private String Specialization;
    private String shiftStart;
    private String shiftEnd;

    public Doctor(String ID, String Name, String Specialization, String shiftStart, String shiftEnd) {
        super(ID, Name);
        setID(ID);
        setSpecialization(Specialization);
        setShiftStart(shiftStart);
        setShiftEnd(shiftEnd);
    }

    public Boolean isvalidID(String ID) {
        if (ID == null || ID.length() != 5) {
            return false;
        }
        char First = ID.charAt(0);
        char dash = ID.charAt(1);
        char d1 = ID.charAt(2);
        char d2 = ID.charAt(3);
        char d3 = ID.charAt(4);

        if (!Character.isUpperCase(First)) return false;
        if (dash != '-') return false;
        if (!Character.isDigit(d1) || !Character.isDigit(d2) || !Character.isDigit(d3)) return false;

        return true;
    }

    public void setID(String ID) {
        if (isvalidID(ID)) {
            this.ID = ID;
        } else {
            System.out.println("Invalid Doctor ID! Format A-000");
        }
    }

    public String getShiftStart() {
        return shiftStart;
    }

    public String getShiftEnd() {
        return shiftEnd;
    }

    public Boolean isValidTime(String time) {
        if (time == null || time.length() != 8) return false;

        char h1 = time.charAt(0);
        char h2 = time.charAt(1);
        char colon = time.charAt(2);
        char m1 = time.charAt(3);
        char m2 = time.charAt(4);
        char space = time.charAt(5);
        char a1 = time.charAt(6);
        char a2 = time.charAt(7);

        if (colon != ':' || space != ' ') return false;
        if (!Character.isDigit(h1) || !Character.isDigit(h2) ||
            !Character.isDigit(m1) || !Character.isDigit(m2)) return false;

        if (!((a1 == 'a' || a1 == 'A' || a1 == 'p' || a1 == 'P') &&
              (a2 == 'm' || a2 == 'M'))) return false;

        int hour = Integer.parseInt("" + h1 + h2);
        int minute = Integer.parseInt("" + m1 + m2);

        if (hour < 1 || hour > 12) return false;
        if (minute < 0 || minute > 59) return false;

        return true;
    }

    public void setShiftStart(String shiftStart) {
        if (isValidTime(shiftStart)) {
            this.shiftStart = shiftStart;
        } else {
            System.out.println("Invalid time! Format HH:MM am/pm");
        }
    }

    public void setShiftEnd(String shiftEnd) {
        if (isValidTime(shiftEnd)) {
            this.shiftEnd = shiftEnd;
        } else {
            System.out.println("Invalid time! Format HH:MM am/pm");
        }
    }

    public String getSpecialization() {
        return Specialization;
    }

    public Boolean isValidSpecialization(String Specialization) {
        if (Specialization == null || Specialization.trim().isEmpty()) return false;
        for (int i = 0; i < Specialization.length(); i++) {
            char c = Specialization.charAt(i);
            if (!Character.isLetter(c) && c != ' ') return false;
        }
        return true;
    }

    public void setSpecialization(String Specialization) {
        if (isValidSpecialization(Specialization)) {
            this.Specialization = Specialization;
        } else {
            System.out.println("Invalid Specialization!");
        }
    }

    public String toString() {
        return "Doctor ID: " + ID + " | Name: " + Name +
               " | Specialization: " + Specialization +
               " | Shift: " + shiftStart + " to " + shiftEnd;
    }
}