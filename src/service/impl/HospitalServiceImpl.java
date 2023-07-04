package service.impl;

import db.DataBase;
import excepticons.MyException;
import model.Hospital;
import model.Patient;
import service.HospitalService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HospitalServiceImpl implements HospitalService {
    private DataBase dataBase;
    public static Long hosId = 1L;

    public HospitalServiceImpl(DataBase dataBase) {
        this.dataBase = dataBase;
    }


    @Override
    public String addHospital(Hospital hospital) {
        hospital.setId(hosId++);
        dataBase.getHospitals().add(hospital);
        return "Successfully added!";
    }

    @Override
    public Hospital findHospitalById(Long id) {
        return dataBase.getHospitals().stream()
                .filter(hospital -> hospital.getId().equals(id))
                .findFirst()
                .orElse(null);

    }

    @Override
    public List<Hospital> getAllHospital() {
        return dataBase.getHospitals();
    }

    @Override
    public List<Patient> getAllPatientFromHospital(Long id) {
        return dataBase.getHospitals().stream()
                .filter(hospital -> hospital.getId().equals(id))
                .flatMap(hospital -> hospital.getPatients().stream()).collect(Collectors.toList());

    }

    @Override
    public String deleteHospitalById(Long id) {
        try {
            boolean idfound = false;
            for (int i = 0; i < dataBase.getHospitals().size(); i++) {
                List<Hospital> hospitals = dataBase.getHospitals();
                for (int j = 0; j < hospitals.size(); j++) {
                    if (hospitals.get(j).getId().equals(id)) {
                        hospitals.remove(j);
                        idfound = true;
                        return "Successfully deleted!";
                    }
                }
                if (!idfound) {
                    throw new MyException("Not found Hospital id");
                }
            }
        } catch (MyException myException) {
            System.out.println(myException.getMessage());
        }

        return null;
    }

    @Override
    public Map<String, Hospital> getAllHospitalByAddress(String address) {
        Map<String,Hospital>hospitalMap= new HashMap<>();
        for (int i = 0; i < dataBase.getHospitals().size(); i++) {
            if (address.equalsIgnoreCase(dataBase.getHospitals().get(i).getAddress())){
                hospitalMap.put(dataBase.getHospitals().get(i).getAddress(),dataBase.getHospitals().get(i));
            }
        }
        return hospitalMap;
    }
}
