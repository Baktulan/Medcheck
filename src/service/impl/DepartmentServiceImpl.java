package service.impl;

import db.DataBase;
import excepticons.MyException;
import model.Department;
import model.Hospital;
import service.DepartmentService;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentServiceImpl implements DepartmentService {
    private DataBase dataBase;
    public static Long depID=1L;

    public DepartmentServiceImpl(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public String add(Long hospitalId, Department department) {
        try{
            department.setId(depID++);
            boolean isFound=false;
            for (int i = 0; i < dataBase.getHospitals().size(); i++) {
                if (hospitalId.equals(dataBase.getHospitals().get(i).getId())){
                    dataBase.getHospitals().get(i).getDepartments().add(department);
                   isFound=true;
                   return " Department Successfully added to Hospital!";
                }
            }
            if (!isFound){
                throw  new MyException("Not found Hospital ID");
            }
        }catch (MyException myException){
            System.out.println(myException.getMessage());
        }

        return null;
    }

    @Override
    public void removeById(Long id) {
        try{
            boolean isfound=false;
            for (int i = 0; i < dataBase.getHospitals().size(); i++) {
                Hospital hospital=dataBase.getHospitals().get(i);
                for (int j = 0; j < hospital.getDepartments().size(); j++) {
                    if (hospital.getDepartments().get(j).getId().equals(id)){
                        hospital.getDepartments().remove(j);
                        isfound=true;
                        System.out.println("Successfully deleted!");
                        break;
                    }
                }
            }if (!isfound){
                throw new MyException("Not found id");
            }
        }catch (MyException myException){
            System.out.println(myException.getMessage());
        }
    }

    @Override
    public String updateById(Long id, Department department) {
        dataBase.getHospitals().stream()
                .flatMap(hospital -> hospital.getDepartments().stream())
                .filter(department1 -> department1.getId().equals(id))
                .findFirst()
                .ifPresent(department1 -> {department1.setDepartmentName(department.getDepartmentName());department1.setDoctors(department1.getDoctors());});
        return "Successfully updated!";
    }

    @Override
    public List<Department> getAllDepartmentByHospital(Long id) {
       return dataBase.getHospitals().stream()
                .filter(hospital -> hospital.getId().equals(id))
                .flatMap(hospital -> hospital.getDepartments().stream()).collect(Collectors.toList());

    }

    @Override
    public Department findDepartmentByName(String name) {
        try{
            for (int i = 0; i < dataBase.getHospitals().size(); i++) {
                boolean isFound=false;
                if (name.equalsIgnoreCase(dataBase.getHospitals().get(i).getDepartments().get(i).getDepartmentName())){
                    isFound=true;
                    return dataBase.getHospitals().get(i).getDepartments().get(i);
                }if (!isFound){
                    throw  new MyException("Not found Department name!");
                }
            }
        }catch (MyException myException){
            System.out.println(myException.getMessage());
        }

        return null;
    }
}
