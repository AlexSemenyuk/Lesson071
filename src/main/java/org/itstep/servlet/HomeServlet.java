package org.itstep.servlet;

import jakarta.servlet.annotation.WebServlet;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

//@WebServlet(name = "home", urlPatterns = "/")
public class HomeServlet extends BaseServlet {
    private static SimpleDateFormat dateTemplate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private List<Task> tasks = new CopyOnWriteArrayList<>();


    public static String TEMPLATE;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // 1. Загрузка TEMPLATE
        ServletContext servletContext = config.getServletContext();
        try (var in = servletContext.getResourceAsStream("/WEB-INF/template/home.html");
             var rdr = new BufferedReader(new InputStreamReader(in))) {
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
        System.out.println("deleteHome = " + delete);

        if (delete != null && !delete.isBlank()) {
            taskDao.deleteTask(Integer.parseInt(delete));
        }

        // 2. Изменение condition (состояние)
        String condition = req.getParameter("condition");
        System.out.println("conditionHome = " + condition);

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

        // 3.2 Отбор tasks соответствующих Active
        List<Task> tasksActive = new CopyOnWriteArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getCondition().condition().equals("Active")) {
                tasksActive.add(tasks.get(i));
            }
        }
//        System.out.println("HomeServlet - tasksActive");
//        tasksActive.stream().forEach(task -> System.out.println(task.toString()));

        // 3.3 Печать tasks соответствующих Active
        PrintWriter writer = resp.getWriter();
        String tasksActiveString = "<ul class='list'>" +
                                   tasksActive.stream().map(task ->
                                                   "<li>" + task.toStringForHome() + "</li>")
                                           .collect(Collectors.joining())
                                   + "</ul>";
//        System.out.println("tasksActiveString = " + tasksActiveString);
        writer.printf(TEMPLATE, tasksActiveString);
//        writer.printf(TEMPLATE, "Hello");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. Добавление нового задания (по кнопке submit)
        // 1.1 Данные с формы
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String category = req.getParameter("category");
        String deadline = req.getParameter("deadline");
        String priority = req.getParameter("priority");
//        System.out.println("name = " + name);
//        System.out.println("description = " + description);
//        System.out.println("categoryTMP = " + categoryTMP);
//        System.out.println("deadline = " + deadline);
//        System.out.println("priorityTMP = " + priorityTMP);
//
        if (name != null && !name.isBlank() &&
            description != null && !description.isBlank() &&
            category != null && !category.isBlank() &&
            deadline != null && !deadline.isBlank() &&
            priority != null && !priority.isBlank()) {

            try {
                taskDao.saveTaskToDB(name, description, category, deadline, priority);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
//        doGet(req, resp);
        resp.sendRedirect("/Lesson071/");
    }
}
