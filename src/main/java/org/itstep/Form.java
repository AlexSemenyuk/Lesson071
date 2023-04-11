package org.itstep;

import org.itstep.data.Category;
import org.itstep.data.Condition;
import org.itstep.data.Priority;
import org.itstep.data.Task;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static java.lang.Class.forName;

public class Form {
    private static SimpleDateFormat dateTemplate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public Form() throws SQLException {
    }

    public static void main(String[] args) throws ParseException {
        Task task = new Task(55, "Buy milk", "1 bottle", Category.SHOPPING, (java.sql.Date) dateTemplate.parse("2023-04-10"), Priority.NORMAL, Condition.ACTIVE);
        String templateIntoTask = """
                INSERT INTO tasks (name, description, category_id, deadline, priority_id, condition_id) VALUES ("
                %s
                ");
                """;
//        dateTemplate.format(task.getDeadline());
        String tmp = "'" + task.getName() +
                     "', '" + task.getDescription() +
                     "', '" + task.getCategory().category() +
                     "', '" + dateTemplate.format(task.getDeadline()) +
                     "', '" + task.getPriority().priority() +
                     "', '" + task.getCondition().condition() + "'";
        System.out.println(templateIntoTask.formatted(tmp));
    }


}
