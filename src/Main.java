import db.DataBase;
import enums.Gender;
import model.Department;
import model.Doctor;
import model.Hospital;
import model.Patient;
import service.impl.DepartmentServiceImpl;
import service.impl.DoctorServiceImpl;
import service.impl.HospitalServiceImpl;
import service.impl.PatientServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        DataBase dataBase = new DataBase();
        HospitalServiceImpl hospitalService = new HospitalServiceImpl(dataBase);
        DoctorServiceImpl doctorService = new DoctorServiceImpl(dataBase);
        PatientServiceImpl patientService = new PatientServiceImpl(dataBase);
        DepartmentServiceImpl departmentService = new DepartmentServiceImpl(dataBase);

        List<Patient> patients = new ArrayList<>();


        Scanner scanner = new Scanner(System.in);
        int num;
        while (true) {
            System.out.println("""
                    1.Add hospital                   | 15.Add department to Hospital
                    2.Add second hospital            | 16.Add second department to Hospital
                    3.Get all hospital               | 17.Remove department by ID
                    4.Find hospital by ID            | 18.Get all department by Hospital ID
                    5.Get all patient from hospital  | 19.Find department by name
                    6.Delete hospital by ID          | 20.Update department by ID
                    7.Get all hospital by address    | 21.Add doctor to Hospital by Hospital's ID
                    8.Add patient to hospital        | 22.Add second doctor to Hospital
                    9.Remove patient by ID           | 23.Remove doctor by ID
                    10.Update patient by ID          | 24.Update doctor by ID
                    11.Get patient by ID             | 25.Find doctor by ID
                    12.Get patient by age            | 26.Assign doctor to Department by Department's ID
                    13.Sort patient by age ASCENDING | 27.Get all doctor by hospital ID
                    14.Sort patient by age DESCENDING| 28.Get all doctor by department ID
                    """);
            num = scanner.nextInt();

            switch (num) {
                case 1: {
                    System.out.println(hospitalService.addHospital(new Hospital(null, "Health", "Chui 24", new ArrayList<>(), new ArrayList<>(), new ArrayList<>())));
                    break;
                }
                case 2: {
                    System.out.println(hospitalService.addHospital(new Hospital(null, "KyrgyzNational", "Moskovskaya 23", new ArrayList<>(), new ArrayList<>(), new ArrayList<>())));
                    break;
                }
                case 3: {
                    System.out.println(hospitalService.getAllHospital());
                    break;
                }
                case 4: {
                    System.out.println(hospitalService.findHospitalById(1L));
                    break;
                }
                case 5: {
                    System.out.println(hospitalService.getAllPatientFromHospital(1L));
                    break;
                }
                case 6: {
                    System.out.println(hospitalService.deleteHospitalById(3L));
                    break;
                }
                case 7: {
                    System.out.println(hospitalService.getAllHospitalByAddress("chui 24"));
                    break;
                }
                case 8: {
                    System.out.println(patientService.addPatientsToHospital
                            (2L, List.of(new Patient(null, "Asel", "Tashmatova", 23, Gender.FEMALE),
                                    new Patient(null, "Asan", "Asanov", 24, Gender.MALE))));
                    break;
                }
                case 9: {
                    patientService.removeById(2L);
                    break;
                }
                case 10: {
                    patientService.updateById(2L, new Patient(null, "Uson", "Usonov", 26, Gender.MALE));
                    break;
                }
                case 11: {
                    System.out.println(patientService.getPatientById(3L));
                    break;
                }
                case 12: {
                    System.out.println(patientService.getPatientByAge(23));
                    break;
                }
                case 13: {
                    System.out.println(patientService.sortPatientsByAgeAscending("Ascending"));
                    break;
                }
                case 14: {
                    System.out.println(patientService.sortPatientsByAgeDescending("Descending"));
                    break;
                }
                case 15: {
                    System.out.println(departmentService.add(1L, new Department(null, "Cardiology", new ArrayList<>())));
                    break;
                }
                case 16: {
                    System.out.println(departmentService.add(1L, new Department(null, "Endocrinology", new ArrayList<>())));
                    break;
                }
                case 17: {
                    departmentService.removeById(3L);
                    break;
                }
                case 18: {
                    System.out.println(departmentService.getAllDepartmentByHospital(1L));
                    break;
                }
                case 19: {
                    System.out.println(departmentService.findDepartmentByName("Cardiology"));
                    break;
                }
                case 20: {
                    System.out.println(departmentService.updateById(2L, new Department(null, "NeiroHirur", new ArrayList<>())));
                }
                case 21: {
                    System.out.println(doctorService.add(1L, new Doctor(null, "Gulum", "Amatova", Gender.FEMALE, 10)));
                    break;
                }
                case 22: {
                    System.out.println(doctorService.add(1L, new Doctor(null, "Baktulan", "Nazirbek uulu", Gender.MALE, 8)));
                    break;
                }
                case 23: {
                    doctorService.removeById(1L);
                    break;
                }
                case 24: {
                    System.out.println(doctorService.updateById(1L, new Doctor(null, "Zamir", "Zamirov", Gender.MALE, 5)));
                    break;
                }
                case 25:{
                    System.out.println(doctorService.findDoctorById(2L));
                    break;
                } case 26:{
                    System.out.println(doctorService.assignDoctorToDepartment(2L,List.of(new Doctor(null, "Gulum", "Amatova", Gender.FEMALE, 10 ),new Doctor(null, "Baktulan", "Nazirbek uulu", Gender.MALE, 8))));
                    break;
                } case 27:{
                    System.out.println(doctorService.getAllDoctorsByHospitalId(1L));
                    break;
                } case 28:{
                    System.out.println(doctorService.getAllDoctorsByDepartmentId(2L));
                    break;
                }
            }
        }

    }
}