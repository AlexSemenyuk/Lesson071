package org.itstep.dao;

import org.itstep.data.Category;
import org.itstep.data.Condition;
import org.itstep.data.Priority;
import org.itstep.data.Task;

import java.text.ParseException;
import java.util.Date;

public interface TaskDao extends GenericDao<Task, Integer, String> {
    void saveTaskToDB(String name, String description, String category, String deadline, String priority) throws ParseException;

}



