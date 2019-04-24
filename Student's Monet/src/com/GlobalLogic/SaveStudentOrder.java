package com.GlobalLogic;

import com.GlobalLogic.dataAccessObject.DictionaryDaoImpl;
import com.GlobalLogic.dataAccessObject.StudentDaoImpl;
import com.GlobalLogic.dataAccessObject.StudentOrderDao;
import com.GlobalLogic.domain.*;
import com.GlobalLogic.domain.student.StudentOrder;

import java.time.LocalDate;
import java.util.List;


public class SaveStudentOrder {


    public static void main(String[] args) throws Exception {

//        List<Street> d = new DictionaryDaoImpl().findStreets("про");
//        for(Street s: d){
//            System.out.println(s.getStreetName());
//        }
//        List<PassportOffice> p = new DictionaryDaoImpl().findPassportOffices("010020000000");
//        for(PassportOffice po: p){
//            System.out.println(po.getOfficeName());
//        }
//
//        List<RegisterOffice> ro = new DictionaryDaoImpl().findRegisterOffices("010010000000");
//        for(RegisterOffice r: ro){
//            System.out.println(r.getOfficeName());
//        }
//        List<CountryArea> ca1 = new DictionaryDaoImpl().findAreas("");
//        for(CountryArea c: ca1){
//            System.out.println(c.getAreaId()+":"+c.getAreaName());
//        }
//        System.out.println("----------------------->");
//        List<CountryArea> ca2 = new DictionaryDaoImpl().findAreas("02000000");
//        for(CountryArea c: ca2){
//            System.out.println(c.getAreaId()+":"+c.getAreaName());
//        }
//        System.out.println("-------------------->");
//        List<CountryArea> ca3 = new DictionaryDaoImpl().findAreas("02002000");
//        for(CountryArea c: ca3){
//            System.out.println(c.getAreaId()+":"+c.getAreaName());
//        }

        StudentOrder studentOrder = buildStudentOrder(10);
        StudentOrderDao dao = new StudentDaoImpl();
        Long id = dao.saveStudentOrder(studentOrder);
        System.out.println(id);

//      StudentOrder so = new StudentOrder();wife_address
//      long answer = saveStudentOrder(so);
//      System.out.println(answer);

    }

    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.println("Save student order: ");
        return answer;
    }

    public static StudentOrder buildStudentOrder(long id) {
        StudentOrder so = new StudentOrder();
        so.setStudentOrderId(id);
        so.setMarriageCertificateId("" + (1234560000+id));
        so.setMarriageDate(LocalDate.of(2017,4,2));
        RegisterOffice registerOffice1 = new RegisterOffice(1L,"","");
        so.setMarriageOffice(registerOffice1);
        Street street = new Street(1L,"Podol");
        Address address = new Address("31000", street, "12","","142");

        //husband
        Adult husband = new Adult("Krivonosenko","Vasiliy","Gennadievich", LocalDate.of(1998,4,12));
        husband.setPassportSeria(""+(1_000 + id));
        husband.setPassportNumber(""+(10_000 + id));
        husband.setIssueDate(LocalDate.of(2015,9,15));
        PassportOffice passportOffice1 = new PassportOffice(1L,"","");
        husband.setIssueDepartment(passportOffice1);
        husband.setStudentId("" + (100_000 + id));
        husband.setAddress(address);
        //wife
        Adult wife = new Adult("Krivonosenko", "Elena", "Sergeevna", LocalDate.of(1999, 4, 12));
        wife.setPassportSeria("" + (2_000 + id));
        wife.setPassportNumber("" + (20_000 + id));
        wife.setIssueDate(LocalDate.of(2016, 2, 5));
        PassportOffice passportOffice2 = new PassportOffice(1L,"","");
        wife.setIssueDepartment(passportOffice2);
        wife.setStudentId("" + (200_000 + id));
        wife.setAddress(address);
        //child
        Child child = new Child("Krivonosenko", "Dmitriy", "Vasilievich", LocalDate.of(2018, 3, 7));
        child.setCertification(""+(300_000+id));
        child.setIssueDate(LocalDate.of(2019,4,2));
        RegisterOffice registerOffice2 = new RegisterOffice(2L, "","");
        child.setIssueDepartment(registerOffice2);
        child.setAddress(address);

//        Child child2 = new Child("Krivonosenko", "Olga", "Vasilievna", LocalDate.of(2018, 6, 30));
//        child2.setCertification(""+(400_000+id));
//        child2.setIssueDate(LocalDate.of(2019,4,2));
//        RegisterOffice registerOffice3 = new RegisterOffice(3L,"","");
//        child2.setIssueDepartment(registerOffice3);
//        child2.setAddress(address);

        so.setHusband(husband);
        so.setWife(wife);
        so.addChild(child);
        //so.addChild(child2);

        return so;
    }
}
