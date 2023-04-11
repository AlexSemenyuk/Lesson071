package org.itstep.servlet;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import org.itstep.dao.TaskDao;
import org.itstep.dao.impl.TaskDaoImpl;


public abstract class BaseServlet extends HttpServlet {
    protected TaskDao taskDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        String url = config.getServletContext().getInitParameter("url");
        String username = config.getServletContext().getInitParameter("username");
        String password = config.getServletContext().getInitParameter("password");
//        taskDao = new TaskDaoImpl(url, username, password);
        taskDao = new TaskDaoImpl("jdbc:mariadb://localhost/mynotes", "root", "");
    }

}
