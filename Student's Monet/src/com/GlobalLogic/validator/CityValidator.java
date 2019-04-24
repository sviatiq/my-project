package com.GlobalLogic.validator;

import com.GlobalLogic.domain.Child;
import com.GlobalLogic.domain.Person;
import com.GlobalLogic.domain.register.AnswerCityRegisterItem;
import com.GlobalLogic.domain.register.CityRegisterAnswer;
import com.GlobalLogic.domain.register.CityRegisterResponse;
import com.GlobalLogic.domain.student.StudentOrder;
import com.GlobalLogic.exception.CityRegisterException;
import com.GlobalLogic.exception.TransportException;
import com.GlobalLogic.validator.register.CityRegisterChecker;
import com.GlobalLogic.validator.register.FakeCityRegisterChecker;

public class CityValidator {
    private static final String NonGRN = "NO_GRN";
    private CityRegisterChecker personChecker;

    public CityValidator(){
        personChecker = new FakeCityRegisterChecker();
}

     public CityRegisterAnswer checkCityRegister(StudentOrder studentOrder){
         CityRegisterAnswer cityRegisterAnswer = new CityRegisterAnswer();

         cityRegisterAnswer.addItem(checkPerson(studentOrder.getHusband()));
         cityRegisterAnswer.addItem(checkPerson(studentOrder.getWife()));
             for(Child child: studentOrder.getChildren()){
                 cityRegisterAnswer.addItem(checkPerson(child));
             }
         return cityRegisterAnswer;
     }
     private AnswerCityRegisterItem checkPerson(Person person){
            AnswerCityRegisterItem.CityStatus status = null;
            AnswerCityRegisterItem.CityError error = null;
         try {
                CityRegisterResponse tmp = personChecker.checkPerson(person);
                status = tmp.isExist() ?
                        AnswerCityRegisterItem.CityStatus.YES:
                        AnswerCityRegisterItem.CityStatus.NO;
         }catch (CityRegisterException ex) {
             ex.printStackTrace(System.out);
             status = AnswerCityRegisterItem.CityStatus.ERROR;
             error = new AnswerCityRegisterItem.CityError(ex.getCode(), ex.getMessage());
         }catch (TransportException ex) {
             ex.printStackTrace(System.out);
             status = AnswerCityRegisterItem.CityStatus.ERROR;
             error = new AnswerCityRegisterItem.CityError(NonGRN, ex.getMessage());
         }catch(Exception ex){
             ex.printStackTrace(System.out);
             status = AnswerCityRegisterItem.CityStatus.ERROR;
             error = new AnswerCityRegisterItem.CityError(NonGRN, ex.getMessage());
         }

             AnswerCityRegisterItem answerCityRegisterItem = new AnswerCityRegisterItem(status,person,error);

         return answerCityRegisterItem;
     }
}
