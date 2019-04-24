package com.GlobalLogic.validator.register;

import com.GlobalLogic.domain.register.CityRegisterResponse;
import com.GlobalLogic.domain.Person;
import com.GlobalLogic.exception.CityRegisterException;
import com.GlobalLogic.exception.TransportException;

public class RealCityRegisterChecker implements CityRegisterChecker{

    public CityRegisterResponse checkPerson(Person person)
            throws CityRegisterException, TransportException {
        return  null;
    }


}

