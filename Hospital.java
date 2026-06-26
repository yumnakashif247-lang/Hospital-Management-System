import java.util.*;
import java.io.*;

class Hospital {
    ArrayList<Doctor> doctors = new ArrayList<>();
    ArrayList<Patient> patients = new ArrayList<>();
    ArrayList<Appointment> appointments = new ArrayList<>();

    public Hospital() {
        loadDoctorsFromFile();
        loadPatientfromFile();
        loadAppointmentsFromFile();
    }

   public void saveDoctorsToFile() {
        try (PrintWriter dw = new PrintWriter(new FileWriter("doctors.txt"))) {
            for (int i = 0; i < doctors.size(); i++) {
                Doctor d = doctors.get(i);
                dw.println(d.getId() + "|" + d.getName() + "|" + d.getSpecialization()+ "|" + d.getShiftStart() + "|" + d.getShiftEnd());
            }
        } catch (IOException e) {
            System.out.println("Error saving doctor data.");
        }
    }

    public void savePatientsToFile() {
      try (PrintWriter pw = new PrintWriter(new FileWriter("patients.txt"))) {
         for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            String docId;
            if (p.getAssignedDoctor() != null) {
                docId = p.getAssignedDoctor().getId();
            } else {
                docId = "-1";
            }
            pw.println(p.getId() + "|" + p.getName() + "|" + p.getGender() + "|" + p.getDisease() + "|" + docId);
         }
         } catch (IOException e) {
         System.out.println("Error saving patient data.");
        }
    }
     
    public void loadDoctorsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("doctors.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                int first = line.indexOf('|');
                int second = line.indexOf('|', first + 1);
                int third = line.indexOf('|',second+1);
                int fourth = line.indexOf('|', third+1);
                
                if (first < 0 || second < 0 || third < 0 || fourth < 0) {
                continue;  
                }
    
                String id = line.substring(0, first);
                String name = line.substring(first + 1, second);
                String spec = line.substring(second + 1, third);
                String shiftStart = line.substring(third + 1, fourth);
                String shiftEnd = line.substring(fourth + 1);
                Doctor d = new Doctor(id, name, spec, shiftStart, shiftEnd);
                if (!d.isValidTime(shiftStart)) {
                     d.setShiftStart("00:00 am");
                }
                if (!d.isValidTime(shiftEnd)) {
                     d.setShiftEnd("00:00 am");
                }
             doctors.add(d);
            }
        } catch (IOException e) {
            System.out.println("No existing doctor data found.");
        } 
    }
     
    public void loadPatientfromFile() {
      try (BufferedReader br = new BufferedReader(new FileReader("patients.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
           
            int first = line.indexOf('|');                       
            int second = line.indexOf('|', first + 1);          
            int third = line.indexOf('|', second + 1);          
            int fourth = line.indexOf('|', third + 1);        

            if (first >= 0 && second >= 0 && third >= 0 && fourth >= 0) {
                String id = line.substring(0, first);
                String name = line.substring(first + 1, second);
                String gender = line.substring(second + 1, third); 
                String disease = line.substring(third + 1, fourth);
                String docId = line.substring(fourth + 1);

                Doctor assignedDoctor = null;
                for (int i = 0; i < doctors.size(); i++) {
                    if (doctors.get(i).getId().equals(docId)) {
                        assignedDoctor = doctors.get(i);
                        break;
                    }
                }
                patients.add(new Patient(id, name, disease, assignedDoctor, gender));
            } 
        }
      } catch (IOException e) {
        System.out.println("No existing patient data found.");
      }
    }

    public void saveAppointmentsToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("appointments.txt"))) {
            for (int i = 0; i < appointments.size(); i++) {
                Appointment a = appointments.get(i);
               pw.println(a.getPatient().getId() + "|" +  a.getDoctor().getId() + "|" + a.getDate() + "|" + a.getTime() + "|" +a.getDuration());
            }
        } catch (IOException e) {
            System.out.println("Error saving appointments!");
        }
    }

    public void loadAppointmentsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("appointments.txt"))) {
         String line;
          while ((line = br.readLine()) != null) {
            int first = line.indexOf('|');
            int second = line.indexOf('|', first + 1);
            int third = line.indexOf('|', second + 1);
            int fourth = line.indexOf('|', third + 1);

            if(first >= 0 && second >= 0 && third >= 0 && fourth >= 0) {
                String pid = line.substring(0, first);
                String did = line.substring(first + 1, second);
                String date = line.substring(second + 1, third);
                String time = line.substring(third + 1, fourth);
                int duration = 30;
                try {
                    duration = Integer.parseInt(line.substring(fourth + 1));
                } catch(Exception e){
                    duration = 30; 
                }

                Patient p = findPatientById(pid);
                Doctor d = findDoctorById(did);
                if(p != null && d != null){
                    appointments.add(new Appointment(p, d, date, time, duration));
                }
            }      
          }
        } catch(IOException e) {
        System.out.println("No existing appointment data found.");
        }
    }


    public Doctor findDoctorById(String id) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getId().equals(id)) {
                return doctors.get(i);
            }
        }
        return null;
    }

    public Patient findPatientById(String id) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId().equals(id)) return patients.get(i);
        }
        return null;
    }   
}
