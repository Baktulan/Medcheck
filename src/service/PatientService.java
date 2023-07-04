package service;

import model.Patient;

import java.util.List;
import java.util.Map;

public interface PatientService extends GenericService<Patient> {
    String addPatientsToHospital(Long id, List<Patient> patients);
    Patient getPatientById(Long id);
    Map<Integer, Patient> getPatientByAge( int age);
    List<Patient> sortPatientsByAgeAscending(String ascending);
    List<Patient> sortPatientsByAgeDescending(String descending);
}
