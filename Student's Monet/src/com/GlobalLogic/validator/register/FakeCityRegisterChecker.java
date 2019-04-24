package com.GlobalLogic.validator.register;

import com.GlobalLogic.domain.Adult;
import com.GlobalLogic.domain.Child;
import com.GlobalLogic.domain.register.CityRegisterResponse;
import com.GlobalLogic.domain.Person;
import com.GlobalLogic.exception.CityRegisterException;
import com.GlobalLogic.exception.TransportException;

public class FakeCityRegisterChecker implements CityRegisterChecker {

    public static final String GOOD1 = "1000";
    public static final String GOOD2 = "2000";
    public static final String BAD1 = "1001";
    public static final String BAD2 = "2001";
    public static final String ERROR1 = "1002";
    public static final String ERROR2 = "2002";
    public static final String ERRORTRANSPORT1 = "1003";
    public static final String ERRORTRANSPORT2 = "2003";

    public CityRegisterResponse checkPerson(Person person)
            throws CityRegisterException, TransportException {

        CityRegisterResponse cityRegisterCheckerResponse = new CityRegisterResponse();

        if(person instanceof Adult){
            Adult t = (Adult)person;
            String passportSeria = t.getPassportSeria();
            if(passportSeria.equals(GOOD1) || passportSeria.equals(GOOD2)){
                cityRegisterCheckerResponse.setExist(true);
                cityRegisterCheckerResponse.setTemporal(false);
            }
            if(passportSeria.equals(BAD1) || passportSeria.equals(BAD2)){
                cityRegisterCheckerResponse.setExist(true);
            }
            if(passportSeria.equals(ERROR1)|| passportSeria.equals(ERROR2)){
                CityRegisterException exception = new CityRegisterException("1","GRN ERROR " + passportSeria);
                throw exception;
            }
            if(passportSeria.equals(ERRORTRANSPORT1)|| passportSeria.equals(ERRORTRANSPORT2)){
                TransportException exception = new TransportException("Transport ERROR " + passportSeria);
                throw exception;
            }
        }

        if(person instanceof Child){
            cityRegisterCheckerResponse.setExist(true);
            cityRegisterCheckerResponse.setTemporal(true);
        }

        System.out.println(cityRegisterCheckerResponse);

        return cityRegisterCheckerResponse;
    }
}