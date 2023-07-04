package service;

import model.Doctor;

import java.util.List;

public interface DoctorService extends GenericService<Doctor> {
    Doctor findDoctorById(Long id);
    String assignDoctorToDepartment(Long departmentId, List<Doctor> doctorsId);
    List<Doctor> getAllDoctorsByHospitalId(Long id);
    List<Doctor> getAllDoctorsByDepartmentId(Long id);
}
