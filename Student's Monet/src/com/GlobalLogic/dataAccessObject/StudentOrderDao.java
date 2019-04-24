package com.GlobalLogic.dataAccessObject;

import com.GlobalLogic.domain.student.StudentOrder;

public interface StudentOrderDao {

    Long saveStudentOrder(StudentOrder studentOrder) throws DaoException;
}
