package com.GlobalLogic.validator;

import com.GlobalLogic.domain.student.StudentAnswer;
import com.GlobalLogic.domain.student.StudentOrder;

public class StudentValidator {

    public StudentAnswer checkStudent(StudentOrder studentOrder){
        System.out.println("checkStudent is checking!" );
        return new StudentAnswer();
    }
}
