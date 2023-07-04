package service.impl;

import db.DataBase;
import excepticons.MyException;
import model.Hospital;
import model.Patient;
import service.PatientService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PatientServiceImpl implements PatientService {
    private DataBase dataBase;
    public  static Long patId=1L;

    public PatientServiceImpl(DataBase dataBase) {
        this.dataBase=dataBase;
    }

    @Override
    public String add(Long hospitalId, Patient patient) {
        patient.setId(patId++);
        dataBase.getHospitals().stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .findFirst().filter(hospital -> hospital.getPatients().add(patient));
        return "Successfully added!";
    }

    @Override
    public void removeById(Long id) {
        try{
            boolean isFound=false;
            for (int i = 0; i < dataBase.getHospitals().size(); i++) {
                Hospital hospital=dataBase.getHospitals().get(i);
                for (int j = 0; j < hospital.getPatients().size(); j++) {
                    if (hospital.getPatients().get(j).getId().equals(id)) {
                        hospital.getPatients().remove(j);
                        isFound=true;
                        System.out.println("Successfully deleted");

                    }
                }
        }if (!isFound){
                throw new MyException("Not found id");
            }

        }catch (MyException  myException){
            System.out.println(myException.getMessage());
        }


    }

    @Override
    public String updateById(Long id, Patient patient) {
        try{
           boolean isFound=false;
            for (int i = 0; i < dataBase.getHospitals().size(); i++) {
                Hospital hospital=dataBase.getHospitals().get(i);
                for (int j = 0; j < hospital.getPatients().size(); j++) {
                    if (hospital.getPatients().get(j).getId().equals(id)){
                        hospital.getPatients().get(j).setFirstName(patient.getFirstName());
                        hospital.getPatients().get(j).setLastName(patient.getLastName());
                        hospital.getPatients().get(j).setAge(patient.getAge());
                        hospital.getPatients().get(j).setGender(patient.getGender());
                        isFound=true;
                        System.out.println("Successfully updated!");

                    }
                }
            }if(!isFound){
                throw  new MyException("Not found ID");
            }
        }catch (MyException myException){
            System.out.println(myException.getMessage());
        }
        return null;
    }

    @Override
    public String addPatientsToHospital(Long id, List<Patient> patients) {
        try{
            boolean isFound=false;
            for (int i = 0; i < dataBase.getHospitals().size(); i++) {
                if (id.equals(dataBase.getHospitals().get(i).getId())){
                    Hospital hospital=dataBase.getHospitals().get(i);
                    for (Patient p :patients) {
                        p.setId(patId++);
                    }
                    hospital.getPatients().addAll(patients);
                    isFound=true;
                    break;
                }
            }if (!isFound){
                throw  new MyException("Not found ID");
            }
            else {
                return "Patients successfully added to "+id +" ID's Hospital";
            }
        }catch (MyException myException){
            System.out.println(myException.getMessage());
        }

      return null;

    }

    @Override
    public Patient getPatientById(Long id) {
      return   dataBase.getHospitals().stream()
                .flatMap(hospital -> hospital.getPatients().stream())
                .filter(patient -> patient.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public Map<Integer, Patient> getPatientByAge(int age) {
        try {
            boolean isFound = false;
            Map<Integer, Patient> patientMap = new HashMap<>();
            for (int i = 0; i < dataBase.getHospitals().size(); i++) {
                Hospital hospital = dataBase.getHospitals().get(i);
                for (int j = 0; j < hospital.getPatients().size(); j++) {
                    if (age == hospital.getPatients().get(j).getAge()) {
                        patientMap.put(hospital.getPatients().get(j).getAge(), hospital.getPatients().get(j));
                        isFound = true;
                        return patientMap;
                    }
                }
            }
            if (!isFound) {
                throw new MyException("Not found Patient age ");
            }
        } catch (MyException myException) {
            System.out.println(myException.getMessage());
        } return null;
    }

    @Override
    public List<Patient> sortPatientsByAgeAscending(String ascending) {
      return   dataBase.getHospitals().stream()
                .flatMap(hospital -> hospital.getPatients().stream())
                .sorted(Comparator.comparing(Patient::getAge)).collect(Collectors.toList());

    }

    @Override
    public List<Patient> sortPatientsByAgeDescending(String descending) {

        return   dataBase.getHospitals().stream()
                .flatMap(hospital -> hospital.getPatients().stream())
                .sorted(Comparator.comparing(Patient::getAge).reversed()).collect(Collectors.toList());
    }

}
