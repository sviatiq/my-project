package com.GlobalLogic;

import com.GlobalLogic.domain.*;
import com.GlobalLogic.domain.children.ChildrenAnswer;
import com.GlobalLogic.domain.register.CityRegisterAnswer;
import com.GlobalLogic.domain.student.StudentAnswer;
import com.GlobalLogic.domain.student.StudentOrder;
import com.GlobalLogic.mail.MailSender;
import com.GlobalLogic.validator.ChildrenValidator;
import com.GlobalLogic.validator.CityValidator;
import com.GlobalLogic.validator.StudentValidator;
import com.GlobalLogic.validator.WeddingValidator;

import java.util.LinkedList;
import java.util.List;

public class StudentOrderValidator {
    private CityValidator cityValidator;
    private StudentValidator studentValidator;
    private ChildrenValidator childrenValidator;
    private WeddingValidator weddingValidator;
    private MailSender mailSender;

    public StudentOrderValidator(){
        cityValidator = new CityValidator();
        studentValidator = new StudentValidator();
        childrenValidator = new ChildrenValidator();
        weddingValidator = new WeddingValidator();
        mailSender = new MailSender();

    }
    public static void main(String[] args) {
        StudentOrderValidator studentOrderValidator = new StudentOrderValidator();
        studentOrderValidator.checkAll();
    }

    public void checkAll() {
        List<StudentOrder> studentOrderList = readStudentOrders();

          for(StudentOrder so: studentOrderList){
              checkOneOrder(so);
          }
    }
    public List<StudentOrder> readStudentOrders(){
        List<StudentOrder> studentOrderList = new LinkedList<>();
                                                               
        for(int i = 0; i<5;i++){
           StudentOrder studentOrder = SaveStudentOrder.buildStudentOrder(i);
            studentOrderList.add(studentOrder);
        }                                                      
                                                               
        return studentOrderList;
    }                                                          

        public void checkOneOrder(StudentOrder so){
        CityRegisterAnswer cityRegisterAnswer = checkCityRegister(so);
//        StudentAnswer studentAnswer = checkStudent(so);
//        ChildrenAnswer childrenAnswer = checkChildren(so);
//        WeddingAnswer weddingAnswer = checkWedding(so);
//
//             sendMail(so);


    }
    public WeddingAnswer checkWedding(StudentOrder so){
        return weddingValidator.checkWedding(so);
    }
    public ChildrenAnswer checkChildren(StudentOrder so){
        return childrenValidator.checkChildren(so);
    }
    public StudentAnswer checkStudent(StudentOrder so){
        return studentValidator.checkStudent(so);
    }
    public CityRegisterAnswer checkCityRegister(StudentOrder so){
        return cityValidator.checkCityRegister(so);
    }
    public void sendMail(StudentOrder so){
        mailSender.sendMail(so);
    }
    }
