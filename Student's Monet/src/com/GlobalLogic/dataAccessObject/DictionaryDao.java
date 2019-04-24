package com.GlobalLogic.dataAccessObject;
import com.GlobalLogic.domain.CountryArea;
import com.GlobalLogic.domain.PassportOffice;
import com.GlobalLogic.domain.RegisterOffice;
import com.GlobalLogic.domain.Street;
import java.util.List;

public interface DictionaryDao {

   List<Street> findStreets(String pattern) throws DaoException;
   List<PassportOffice> findPassportOffices(String areaId) throws DaoException;
   List<RegisterOffice> findRegisterOffices(String areaId) throws DaoException;
   List<CountryArea> findAreas(String areaId) throws DaoException;
}
