package org.itstep.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.itstep.data.Category;
import org.itstep.data.Condition;
import org.itstep.data.Priority;
import org.itstep.data.Task;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class DoneServlet extends BaseServlet {

    private List<Task> tasks = new CopyOnWriteArrayList<>();
    private Map<Integer, String> mapCategories = new HashMap<>();
    private Map<Integer, String> mapPriorities = new HashMap<>();
    private Map<Integer, String> mapConditions = new HashMap<>();

    public static String TEMPLATE;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // 1. Загрузка TEMPLATE
        ServletContext servletContext = config.getServletContext();

        try (InputStream in = servletContext.getResourceAsStream("/WEB-INF/template/done.html");
             BufferedReader rdr = new BufferedReader(new InputStreamReader(in))) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = rdr.readLine()) != null) {
                stringBuilder.append(line);
            }
            TEMPLATE = stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. Удаление
        String delete = req.getParameter("delete");
        System.out.println("deleteDone = " + delete);

        if (delete != null && !delete.isBlank()) {
            taskDao.deleteTask(Integer.parseInt(delete));
        }

        // 2. Изменение condition (состояние)
        String condition = req.getParameter("condition");
        System.out.println("conditionDone = " + condition);

        if (condition != null && !condition.isBlank()) {
            taskDao.changeCondition(condition);
        }

        // 3. Сортировка и вывод HTML
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        // 3.1 Получение параметров сортировки
        String sort = req.getParameter("sort");
        System.out.println("sort = " + sort);

        // 3.2 Считывание с DB в зависимости от сортировки
        if (sort != null && !sort.isBlank()) {
            tasks = taskDao.findAllBySort(sort);
        } else if (sort == null) {
            tasks = taskDao.findAllBySort("id");
        }
//        System.out.println("HomeServlet - tasks");
//        tasks.stream().forEach(task -> System.out.println(task.toString()));

        // 3.2 Отбор tasks соответствующих Done
        List<Task> tasksDone = new CopyOnWriteArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getCondition().condition().equals("Done")) {
                tasksDone.add(tasks.get(i));
            }
        }
        System.out.println("HomeServlet - tasksActive");
        tasksDone.stream().forEach(task -> System.out.println(task.toString()));

        // 3.3 Печать tasks соответствующих Active
        PrintWriter writer = resp.getWriter();
        String tasksDoneString = "<ul class='list'>" +
                                 tasksDone.stream().map(task ->
                                                 "<li>" + task.toStringForDone() + "</li>")
                                         .collect(Collectors.joining())
                                 + "</ul>";
//        System.out.println("tasksActiveString = " + tasksActiveString);
        writer.printf(TEMPLATE, tasksDoneString);
//        writer.printf(TEMPLATE, "Hello");
    }


}



