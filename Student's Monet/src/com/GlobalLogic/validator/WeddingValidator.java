package com.GlobalLogic.validator;

import com.GlobalLogic.domain.student.StudentOrder;
import com.GlobalLogic.domain.WeddingAnswer;

public class WeddingValidator {

    public WeddingAnswer checkWedding(StudentOrder studentOrder){
        System.out.println("Wedding in process!");
        return new WeddingAnswer();
    }
}
