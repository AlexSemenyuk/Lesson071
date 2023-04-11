package org.itstep.dao;

import java.util.List;

// Data Access Object
public interface GenericDao<T, I, S> {

    //        void saveTaskToDB(S, S, S, );
    void changeCondition(S condition);

    void deleteTask(I id);

    List<T> findAllBySort(String sort);
}


