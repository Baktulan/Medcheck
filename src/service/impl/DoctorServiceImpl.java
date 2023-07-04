package service.impl;

import db.DataBase;
import excepticons.MyException;
import model.Department;
import model.Doctor;
import model.Hospital;
import service.DoctorService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorServiceImpl implements DoctorService {
    private DataBase dataBase;

    public static Long docID=1L;
    public DoctorServiceImpl(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public String add(Long hospitalId, Doctor doctor) {
        doctor.setId(docID++);
        dataBase.getHospitals().stream()
                .filter(hospital -> hospital.getId().equals(hospitalId))
                .findFirst().filter(hospital -> hospital.getDoctors().add(doctor));
        return "Doctor successfully added to Hospital";
    }

    @Override
    public void removeById(Long id) {
        try{
            boolean isFound=false;
            for (int i = 0; i < dataBase.getHospitals().size(); i++) {
                Hospital hospital=dataBase.getHospitals().get(i) ;
                for (int j = 0; j < hospital.getDoctors().size(); j++) {
                    if (hospital.getDoctors().get(j).getId().equals(id)){
                        hospital.getDoctors().remove(j);
                        isFound= true;
                        System.out.println("Successfully deleted!");
                    }
                }
            }if (!isFound){
                throw  new MyException("Not found ID");
            }
        }catch (MyException myException){
            System.out.println(myException.getMessage());
        }

    }

    @Override
    public String updateById(Long id, Doctor doctor) {
        try{
            boolean isFound=false;
            for (int i = 0; i < dataBase.getHospitals().size(); i++) {
                Hospital hospital= dataBase.getHospitals().get(i);
                for (int j = 0; j < hospital.getDoctors().size(); j++) {
                    if (hospital.getDoctors().get(j).getId().equals(id)){
                        hospital.getDoctors().get(j).setFirstName(doctor.getFirstName());
                        hospital.getDoctors().get(j).setLastName(doctor.getLastName());
                        hospital.getDoctors().get(j).setGender(doctor.getGender());
                        hospital.getDoctors().get(j).setExperienceYear(doctor.getExperienceYear());
                        isFound=true;
                        return "Successfully updated!";
                    }
                }
            } if (!isFound){
                throw  new MyException("Not found ID");
            }
        }catch (MyException myException){
            System.out.println(myException.getMessage());
        }
        return null;
    }

    @Override
    public Doctor findDoctorById(Long id) {
        try{
            boolean isFound=false;
            for (int i = 0; i < dataBase.getHospitals().size(); i++) {
                Hospital hospital=dataBase.getHospitals().get(i);
                for (int j = 0; j < hospital.getDoctors().size(); j++) {
                    if (id.equals(hospital.getDoctors().get(j).getId())){
                        isFound=true;
                        return hospital.getDoctors().get(j);

                    }
                }
            }if(!isFound){
                throw new MyException("Not found Doctor with "+id+ " ID");
            }
        }catch (MyException myException){
            System.out.println(myException.getMessage());
        }
        return null;
    }

    @Override
    public String assignDoctorToDepartment(Long departmentId, List<Doctor> doctorsId) {
        try{
            boolean isFound=false;
            for (int i = 0; i < doctorsId.size(); i++) {
                doctorsId.get(i).setId(docID++);
            }
            for (int i = 0; i < dataBase.getHospitals().size(); i++) {
                Hospital hospital= dataBase.getHospitals().get(i);
                for (int j = 0; j < hospital.getDepartments().size(); j++) {
                    if (hospital.getDepartments().get(j).getId().equals(departmentId)){
                        isFound =true;
                        hospital.getDepartments().get(j).setDoctors(doctorsId);
                        return "Successfully assigned to Department!";
                    }
                }
            }if(!isFound){
                throw new MyException("Not found ID");
            }
        }catch (MyException myException){
            System.out.println(myException.getMessage());
        }
        return null;
    }

    @Override
    public List<Doctor> getAllDoctorsByHospitalId(Long id) {
      return   dataBase.getHospitals().stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst()
                .map(Hospital::getDoctors)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Doctor> getAllDoctorsByDepartmentId(Long id) {
     return    dataBase.getHospitals().stream()
                .flatMap(hospital -> hospital.getDepartments().stream())
                .filter(department -> department.getId().equals(id))
                .findFirst()
                .map(Department::getDoctors)
                .orElse(Collections.emptyList());

    }
}
