class Appointment {
    Patient patient;
    Doctor doctor;
    String date; 
    String time; 
    int duration; 

    public Appointment(Patient patient, Doctor doctor, String date, String time, int duration){
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public Boolean isValidDate(String date){
        if(date == null || date.length() != 10) {
            return false;
        }

        char d1 = date.charAt(0);
        char d2 = date.charAt(1);
        char dash1 = date.charAt(2);
        char d3 = date.charAt(3);
        char d4 = date.charAt(4);
        char dash2 = date.charAt(5);
        char d5 = date.charAt(6);
        char d6 = date.charAt(7);
        char d7 = date.charAt(8);
        char d8 = date.charAt(9);

        if(dash1 != '-' || dash2 != '-') {
            return false;
        }

        if(!Character.isDigit(d1) || !Character.isDigit(d2) || !Character.isDigit(d3) || !Character.isDigit(d4) || !Character.isDigit(d5) || !Character.isDigit(d6) || !Character.isDigit(d7) || !Character.isDigit(d8)) {
            return false;
        }

        int day = Integer.parseInt("" + d1 + d2);
        int month = Integer.parseInt("" + d3 + d4);
        int year = Integer.parseInt("" + d5 + d6 + d7 + d8);

        if(day < 1 || day > 31) {
            return false;
        }
        if(month < 1 || month > 12) {
            return false;
        }
        if(month == 4 || month == 6 || month == 9 || month == 11){
            if(day > 30) return false;
        }

        if(month == 2){
            boolean leap = false;
            if(year % 4 == 0){
                if(year % 100 != 0){ leap = true;
                }else if(year % 400 == 0) {
                    leap = true;
                }
            }

            if(leap){
                if(day > 29) {
                    return false;
                } else {
                    if(day > 28) {
                        return false;
                    }
                }
            }
        }
          return true;
    }

    public Boolean isValidTime(String time){
        if(time == null || time.length() != 8) {
            return false;
        }
        char h1 = time.charAt(0);
        char h2 = time.charAt(1);
        char colon = time.charAt(2);
        char m1 = time.charAt(3);
        char m2 = time.charAt(4);
        char space = time.charAt(5);
        char a1 = time.charAt(6);
        char a2 = time.charAt(7);

        if(colon != ':' || space != ' '){
           return false;
        }
        if(!Character.isDigit(h1) || !Character.isDigit(h2) || !Character.isDigit(m1) || !Character.isDigit(m2)){
            return false;
        }  
        if(!((a1=='a'||a1=='A'||a1=='p'||a1=='P') && (a2=='m'||a2=='M'))) {
            return false;
        }

        int hour = Integer.parseInt("" + h1 + h2);
        int minute = Integer.parseInt("" + m1 + m2);

        if(hour < 1 || hour > 12) {
            return false;
        }
        if(minute < 0 || minute > 59) {
            return false;
        }

        return true;
    }

    public Patient getPatient(){
        return patient;
    }
    public Doctor getDoctor(){ 
        return doctor; 
    }
    public String getDate(){
        return date; 
    }
    public String getTime(){
        return time;
    }
    public int getDuration(){ 
        return duration;
    }

    public void setPatient(Patient patient){
        if(patient != null) {
            this.patient = patient;
        }else{
             System.out.println("Invalid patient! Cannot be Empty.");
        }
    }

    public void setDoctor(Doctor doctor){
        if(doctor != null){ 
            this.doctor = doctor;
        }else{
             System.out.println("Invalid doctor! Cannot be Empty.");
        }
    }

    public void setDate(String date){
        if(isValidDate(date)) {
            this.date = date;
        }else{
            System.out.println("Invalid date! Format DD-MM-YYYY");
        }
    }

    public void setTime(String time){
        if(isValidTime(time)) {
            this.time = time;
        }else {
            System.out.println("Invalid time! Format HH:MM am/pm");
        }
    }

    public void setDuration(int duration){
        if(duration > 0) {
            this.duration = duration;
        }else {
            System.out.println("Invalid duration! Must be positive.");
        }
    }

    public String toString(){
        return "Appointment: " + date + " " + time + " (" + duration + " min) | Patient: " + patient.getName() + " | Doctor: " + doctor.getName();
    }
}