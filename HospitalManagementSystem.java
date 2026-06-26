import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class HospitalManagementSystem {
  Hospital hospital;

    JTextArea doctorList;
    JTextArea patientList;
    JTextArea appList;

    public HospitalManagementSystem() {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {}

        hospital = new Hospital();

        JFrame frame = new JFrame("Hospital Management System");
        frame.setSize(1100, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.BOTH;

        JLabel title = new JLabel(" Hospital Management System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(40, 110, 180));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        frame.add(title, gbc);
        gbc.gridwidth = 1;

        JPanel sidePanel = new JPanel(new GridLayout(10, 1, 8, 8));
        sidePanel.setBackground(new Color(230, 240, 255));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addDoc = createButton("Add Doctor");
        JButton addPat = createButton("Add Patient");
        JButton removeDoc = createButton("Remove Doctor");
        JButton removePat = createButton("Remove Patient");
        JButton assignDoc = createButton("Assign Doctor");
        JButton bookApp = createButton("Book Appointment");
        JButton generateBill = createButton("Generate Bill");
        JButton searchDoc = createButton("Search Doctor");
        JButton searchPat = createButton("Search Patient");
        JButton exit = createButton("Exit");

        JButton[] buttons = { addDoc, addPat, removeDoc, removePat, assignDoc, bookApp, generateBill,searchDoc, searchPat, exit};

        for (JButton b : buttons) {
            sidePanel.add(b);
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        frame.add(sidePanel, gbc);
        gbc.gridheight = 1;

        doctorList = createTextArea();
        patientList = createTextArea();
        appList = createTextArea();

        JScrollPane docScroll = new JScrollPane(doctorList);
        JScrollPane patScroll = new JScrollPane(patientList);
        JScrollPane appScroll = new JScrollPane(appList);

        JLabel docLabel = sectionLabel("Doctors");
        JLabel patLabel = sectionLabel("Patients");
        JLabel appLabel = sectionLabel("Appointments");

        gbc.gridx = 1; gbc.gridy = 1;
        frame.add(docLabel, gbc);
        gbc.gridx = 2;
        frame.add(patLabel, gbc);
        gbc.gridx = 3;
        frame.add(appLabel, gbc);

        gbc.gridy = 2;
        gbc.gridx = 1;
        frame.add(docScroll, gbc);
        gbc.gridx = 2;
        frame.add(patScroll, gbc);
        gbc.gridx = 3;
        frame.add(appScroll, gbc);

         refreshLists();

        generateBill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pid = JOptionPane.showInputDialog("Enter Patient ID for Billing:");
                Patient p = hospital.findPatientById(pid);
                if(p!=null){ 
                     double amount;
                     amount = Math.random() * 5000 + 1000;
                     amount = Math.round(amount * 100) / 100.0;
                     String message = "------- BILL GENERATED ------\n" + "Patient Name : " + p.getName() + "\n"+ "Disease      : " + p.getDisease() + "\n" + "Total Amount : Rs. " + amount + "\n"+ "----------------------------";
                     JOptionPane.showMessageDialog(null, message, "Billing", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                  JOptionPane.showMessageDialog(frame,"Patient not found!");
                }
            }
        });
       
        addDoc.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            String id = "";
            while(true) {
             id = JOptionPane.showInputDialog("Enter Doctor ID (e.g., A-001):");
             if(id == null) {
                return;
             }
             Doctor temp = new Doctor(id,"","","","");
             if(!temp.isvalidID(id)) {
                JOptionPane.showMessageDialog(frame, "Invalid Doctor ID! Format A-001");
                continue;
             }
             if(hospital.findDoctorById(id) != null) { 
                JOptionPane.showMessageDialog(frame, "Doctor ID already exists!");
                continue;
             }
                break;
            }

             String name = "";
             while(true) {
              name = JOptionPane.showInputDialog("Enter Doctor Name:");
              if(name == null) {
                return;
              }
              Doctor temp = new Doctor("",name,"","","");
              if(!temp.isvalidName(name)) {
                JOptionPane.showMessageDialog(frame, "Invalid Name! Only letters and spaces allowed.");
                continue;
              }
                break;
             } 

             String spec = "";
             while(true) {
              spec = JOptionPane.showInputDialog("Enter Doctor Specialization:");
              if(spec == null) {
                return;
              }
              Doctor temp = new Doctor("","",spec,"","");
              if(spec.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Specialization cannot be empty!");
                continue;
              }
              if(!temp.isValidSpecialization(spec)) {
                JOptionPane.showMessageDialog(frame, "Invalid Specialization! Only letters and spaces allowed.");
                continue;
              }
                break;
             }

             String shiftStart = "";
             while(true) {
              shiftStart = JOptionPane.showInputDialog("Enter Shift Start (HH:MM am/pm):");
              if(shiftStart == null) {
                return;
              }
              Doctor temp = new Doctor("","","",shiftStart,"");
              if(!temp.isValidTime(shiftStart)) {
                JOptionPane.showMessageDialog(frame, "Invalid time!Enter again. ");
                continue;
              }
                break;
             }

             String shiftEnd = "";
             while(true) {
              shiftEnd = JOptionPane.showInputDialog("Enter Shift End (HH:MM am/pm):");
              if(shiftEnd == null) {
                return;
              }
              Doctor temp = new Doctor("","","","",shiftEnd);
              if(!temp.isValidTime(shiftEnd)) {
                JOptionPane.showMessageDialog(frame, "Invalid time! Enter again.A");
                continue;
              }
                break;
             }
             Doctor d = new Doctor(id, name, spec, shiftStart, shiftEnd);
             hospital.doctors.add(d);
             hospital.saveDoctorsToFile();
             doctorList.append(d + "\n");
           }
        });

        addPat.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {

           String id = "";
           while(true) {
             id = JOptionPane.showInputDialog("Enter Patient ID (Format 000-A):");
             if(id == null) {
                return;
             }
             Patient temp = new Patient(id,"","",null,"");
             if(!temp.isValidPatientID(id)) {
                JOptionPane.showMessageDialog(frame,"Invalid ID format! (Format 000-A):");
                continue;
             }
             if(hospital.findPatientById(id) != null) {
                JOptionPane.showMessageDialog(frame, "Patient with this ID already exists! Try another one.");
                continue;
             }
                break;
           }

            String name = "";
            while(true) {
             name = JOptionPane.showInputDialog("Enter Patient Name:");
             if(name == null) {
                return;
             }
             Patient temp = new Patient("",name,"",null,"");
             if(!temp.isvalidName(name)) {
                JOptionPane.showMessageDialog(frame, "Invalid name! Only letters and spaces allowed.");
                continue;
             }
                break;
            }

            String gender = "";
            while(true) {
             gender = JOptionPane.showInputDialog("Enter Gender (Male/Female):");
             if(gender == null) {
                return;
             }
             Patient temp = new Patient("","", "", null, gender);
             if(!temp.isValidGender(gender)) {
                JOptionPane.showMessageDialog(frame, "Invalid input! Gender must be 'Male' or 'Female'.");
                continue;
             }
             gender = gender.substring(0,1).toUpperCase() + gender.substring(1).toLowerCase();
                break;
            }

            String disease = "";
            while(true) {
             disease = JOptionPane.showInputDialog("Enter Disease:");
             if(disease == null) {
                return;
             }
             Patient temp = new Patient("", "", disease, null, "");
             if(!temp.isValidDisease(disease)) {
                JOptionPane.showMessageDialog(frame, "Invalid disease name! Only letters and spaces allowed.");
                continue;
             }
                break;
            }

            String docId = JOptionPane.showInputDialog("Enter Assigned Doctor ID (or leave blank):");
            Doctor assignedDoctor = null;
            if(docId != null && !docId.trim().isEmpty()) {
            assignedDoctor = hospital.findDoctorById(docId);
            if(assignedDoctor == null) {
                JOptionPane.showMessageDialog(frame, "Doctor ID not found. Assigning None.");
            }
            }

          Patient p = new Patient(id, name, disease, assignedDoctor, gender);
          hospital.patients.add(p);
          hospital.savePatientsToFile();
          patientList.append(p + "\n");
         }
        });
        
        removeDoc.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
          String dID = JOptionPane.showInputDialog("Enter Doctor ID to remove:");
          if(dID == null) {
            return;
          }
          Doctor d = hospital.findDoctorById(dID);
          if(d != null) {
            for(int i = 0; i < hospital.patients.size(); i++) {
                Patient p = hospital.patients.get(i);
                if(p.getAssignedDoctor() != null && 
                   p.getAssignedDoctor().getId().equals(dID)) {
                    p.setAssignedDoctor(null);
                }
            }
            for(int i = hospital.appointments.size() - 1; i >= 0; i--) {
                Appointment a = hospital.appointments.get(i);
                if (a.getDoctor() != null && a.getDoctor().getId().equals(dID)) {
                    hospital.appointments.remove(i);
                }
            }
            hospital.doctors.remove(d);
            hospital.saveDoctorsToFile();
            hospital.savePatientsToFile();
            hospital.saveAppointmentsToFile();
            doctorList.setText("");
            for(int i = 0; i < hospital.doctors.size(); i++) {
                doctorList.append(hospital.doctors.get(i) + "\n");
            }
            patientList.setText("");
            for(int i = 0; i < hospital.patients.size(); i++) {
                patientList.append(hospital.patients.get(i) + "\n");
            }
            appList.setText("");
              for (int i = 0; i < hospital.appointments.size(); i++) {
                appList.append(hospital.appointments.get(i) + "\n");
            }
            JOptionPane.showMessageDialog(frame, "Doctor removed successfully!");
          } else {
            JOptionPane.showMessageDialog(frame, "Doctor not found!");
          }
         }
       });
        
        removePat.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            String id = JOptionPane.showInputDialog("Enter Patient ID to remove:");
            if(id == null) {
                return;
            }
            Patient p = hospital.findPatientById(id);
            if(p != null) {
              for(int i = hospital.appointments.size() - 1; i >= 0; i--) {
                Appointment a = hospital.appointments.get(i);
                if(a.getPatient().getId().equals(id)) {
                    hospital.appointments.remove(i);
                }
               }
              hospital.patients.remove(p);
              hospital.savePatientsToFile();
              hospital.saveAppointmentsToFile();
              patientList.setText("");
              for(int i = 0; i < hospital.patients.size(); i++) {
                patientList.append(hospital.patients.get(i) + "\n");
              }
              appList.setText("");
              for (int i = 0; i < hospital.appointments.size(); i++) {
                appList.append(hospital.appointments.get(i) + "\n");
              }
               JOptionPane.showMessageDialog(frame, "Patient removed successfully!");
            } else {
               JOptionPane.showMessageDialog(frame, "Patient not found!");
            }
         }
        });


        assignDoc.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            String pid = "";
            while(true) {
             pid = JOptionPane.showInputDialog("Enter Patient ID:");
             if(pid == null) {
                return;
             }
             Patient temp = new Patient(pid, "", "", null, "");
             if(!temp.isValidPatientID(pid)) {
                JOptionPane.showMessageDialog(frame, "Invalid Patient ID format!");
                continue;
             }

             if(hospital.findPatientById(pid) == null) {
                JOptionPane.showMessageDialog(frame, "Patient not found! Enter again.");
                continue;
             }
                break;
             }
            Patient p = hospital.findPatientById(pid);
            if(p.getAssignedDoctor() != null) {
               JOptionPane.showMessageDialog(frame, 
               "Patient already has a doctor assigned (" + p.getAssignedDoctor().getName() + ").\nCannot reassign.");
               return;
            }
            String did = "";
            while(true) {
             did = JOptionPane.showInputDialog("Enter Doctor ID:");
             if(did == null) return;
             Doctor tempD = new Doctor(did, "", "", "", "");
             if(!tempD.isvalidID(did)) {
                JOptionPane.showMessageDialog(frame, "Invalid Doctor ID format!");
                continue;
             }
             if(hospital.findDoctorById(did) == null) {
                JOptionPane.showMessageDialog(frame, "Doctor not found! Enter again.");
                continue;
             }
                break;
            }
             Doctor d = hospital.findDoctorById(did);
             p.setAssignedDoctor(d);
             hospital.savePatientsToFile();
             patientList.setText("");
             for(int i = 0; i < hospital.patients.size(); i++) {
             patientList.append(hospital.patients.get(i).toString() + "\n");
             }

             JOptionPane.showMessageDialog(frame, "Doctor assigned successfully!");
         }
        });
         
       
        searchPat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String id = JOptionPane.showInputDialog(null, "Enter Patient ID to search:");
                if (id == null || id.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid input!");
                return;
                }
                Patient p = hospital.findPatientById(id);
                if (p != null) {
                JOptionPane.showMessageDialog(null, p.toString());
                }else {
                JOptionPane.showMessageDialog(null, "Patient not found!");
                }
            }
        });

        bookApp.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
             try {
               String pid = JOptionPane.showInputDialog(null, "Enter Patient ID:");
               if (pid == null) {
                return;
               }
               Patient p = hospital.findPatientById(pid);
               if (p == null) {
                 JOptionPane.showMessageDialog(null, "Patient not found.");
                 return;
                }
               Doctor d = p.getAssignedDoctor();
               if (d == null) {
                 JOptionPane.showMessageDialog(null, "This patient has no assigned doctor.");
                 return;
                }
               String s = d.getShiftStart();
               String e2 = d.getShiftEnd();
               int sH = Integer.parseInt(s.substring(0, 2));
               int sM = Integer.parseInt(s.substring(3, 5));
               String sAMPM = s.substring(6).toLowerCase();
               if (sAMPM.equals("pm") && sH != 12) {
                sH += 12;
               }
               if (sAMPM.equals("am") && sH == 12) {
                sH = 0;
               }
               int startMin = sH * 60 + sM;
               int eH = Integer.parseInt(e2.substring(0, 2));
               int eM = Integer.parseInt(e2.substring(3, 5));
               String eAMPM = e2.substring(6).toLowerCase();
               if (eAMPM.equals("pm") && eH != 12) {
                eH += 12;
               }
               if (eAMPM.equals("am") && eH == 12) {
                eH = 0;
               }
               int endMin = eH * 60 + eM;
               JOptionPane.showMessageDialog(frame, "Booking appointment with assigned doctor: " + d.getName());
               String date = JOptionPane.showInputDialog(null, "Enter appointment date (DD-MM-YYYY):");
               if (date == null) {
                return;
               }
               Appointment temp = new Appointment(null, null, "", "", 30);
               while (!temp.isValidDate(date)) {
                   date = JOptionPane.showInputDialog(null, "Invalid Date!\nEnter again (DD-MM-YYYY):");
                   if (date == null) return;
                }
               String time = JOptionPane.showInputDialog(null, "Enter appointment time (HH:MM am/pm)\nDoctor Shift: " + d.getShiftStart() + " to " + d.getShiftEnd());
               if (time == null) {
                return;
               }
               while (true) {
                 while (!temp.isValidTime(time)) {
                    time = JOptionPane.showInputDialog(null, "Invalid Time Format!\nEnter again (HH:MM am/pm):");
                    if (time == null) {
                        return;
                    }
                 }
                 int h = Integer.parseInt(time.substring(0, 2));
                 int m = Integer.parseInt(time.substring(3, 5));
                 String ampm = time.substring(6).toLowerCase();
                 if (ampm.equals("pm") && h != 12) {
                    h += 12;
                 }
                 if (ampm.equals("am") && h == 12) {
                    h = 0;
                 }
                 int userMin = h * 60 + m;
                 if (userMin < startMin || userMin > endMin) {
                    time = JOptionPane.showInputDialog(null, "Error! Time outside doctor's shift.\nShift: " + d.getShiftStart() + " to " + d.getShiftEnd() + "\nEnter time again:");
                    if (time == null) {
                        return;
                    }
                    continue;
                 }
                 boolean clash = false;
                 for (int i = 0; i < hospital.appointments.size(); i++) {
                    Appointment ap = hospital.appointments.get(i);
                    if (ap.getDoctor().getId().equals(d.getId()) &&
                       ap.getDate().equals(date)) {
                        String tt = ap.getTime();
                        int bh = Integer.parseInt(tt.substring(0, 2));
                        int bm = Integer.parseInt(tt.substring(3, 5));
                        String bap = tt.substring(6).toLowerCase();
                        if (bap.equals("pm") && bh != 12) {
                            bh += 12;
                        }
                        if (bap.equals("am") && bh == 12) {
                            bh = 0;
                        }
                        int bookedMin = bh * 60 + bm;
                        int bookedEnd = bookedMin + ap.getDuration();
                        int userEnd = userMin + 30;
                        if (!(userEnd <= bookedMin || userMin >= bookedEnd)) {
                            clash = true;
                            break;
                        }
                    }
                 }
                 if (clash) {
                    time = JOptionPane.showInputDialog(null, "This time is already booked for Doctor " + d.getName() + "\nEnter another time:");
                    if (time == null) {
                        return;
                    }
                    continue;
                 }
                 break;
                }
                Appointment a = new Appointment(p, d, date, time, 30);
                hospital.appointments.add(a);
                hospital.saveAppointmentsToFile();
                appList.setText("");
                for (int i = 0; i < hospital.appointments.size(); i++) {
                    appList.append(hospital.appointments.get(i) + "\n");
                }
                doctorList.setText("");
                for (int i = 0; i < hospital.doctors.size(); i++) {
                    doctorList.append(hospital.doctors.get(i) + "\n");
                }
                patientList.setText("");
                for (int i = 0; i < hospital.patients.size(); i++) {
                    patientList.append(hospital.patients.get(i) + "\n");
                }
                JOptionPane.showMessageDialog(null, "Appointment booked successfully!");
               } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error while booking appointment!");
               }
            }
        });

        searchDoc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String id = JOptionPane.showInputDialog(null, "Enter Doctor ID to search:");
                if (id == null || id.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid input!");
                return;
                }
                Doctor d = hospital.findDoctorById(id);
                if (d != null) {
                JOptionPane.showMessageDialog(null, d.toString());
                }     
                else {
                JOptionPane.showMessageDialog(null, "Doctor not found!");
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                hospital.savePatientsToFile();
                hospital.saveDoctorsToFile();
                hospital.saveAppointmentsToFile(); 
                JOptionPane.showMessageDialog(null, "Data saved. Exiting program...");
                System.exit(0); 
            }
        });

     frame.setLocationRelativeTo(null); 
     frame.setVisible(true);
    }

    
    private JButton createButton(String text) {
       JButton b = new JButton(text); 
       b.setFont(new Font("Segoe UI", Font.BOLD, 14));
       b.setBackground(new Color(180, 210, 255));
       b.setFocusPainted(false);
       b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 130, 200)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
       ));
       b.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            b.setBackground(new Color(150, 190, 255));
        }

        public void mouseExited(MouseEvent e) {
            b.setBackground(new Color(180, 210, 255));
        }
      });
      return b;
    }


    private JTextArea createTextArea() {
        JTextArea t = new JTextArea(15, 25);
        t.setEditable(false);
        t.setFont(new Font("Consolas", Font.PLAIN, 13));
        t.setBackground(new Color(250, 250, 250));
        t.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return t;
    }

    private JLabel sectionLabel(String text) {
        JLabel l = new JLabel(text, JLabel.CENTER);
        l.setFont(new Font("Segoe UI", Font.BOLD, 16));
        l.setOpaque(true);
        l.setBackground(new Color(200, 230, 250));
        l.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        return l;
    }

    private void refreshLists() {
    doctorList.setText("");
    for (Doctor d : hospital.doctors) {
        doctorList.append(d + "\n");
    }

    patientList.setText("");
    for (Patient p : hospital.patients) {
        patientList.append(p + "\n");
    }

    appList.setText("");
    for (Appointment a : hospital.appointments) {
        appList.append(a + "\n");
    }
    }
}