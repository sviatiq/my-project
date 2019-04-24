package com.GlobalLogic.validator;

import com.GlobalLogic.domain.children.ChildrenAnswer;
import com.GlobalLogic.domain.student.StudentOrder;

public class ChildrenValidator {

    public ChildrenAnswer checkChildren(StudentOrder studentOrder){
        System.out.println("checkChildren starts checking!");
        return new ChildrenAnswer();
    }
}
