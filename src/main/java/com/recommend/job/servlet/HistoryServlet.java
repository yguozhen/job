package com.recommend.job.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recommend.job.db.MySQLConnection;
import com.recommend.job.entity.HistoryRequestBody;
import com.recommend.job.entity.Item;
import com.recommend.job.entity.ResultResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet(name = "HistoryServlet", urlPatterns = {"/history"})
public class HistoryServlet extends BaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();

        auth(request, response);

        HistoryRequestBody body = mapper.readValue(request.getReader(), HistoryRequestBody.class);
        MySQLConnection connection = new MySQLConnection();
        connection.setFavoriteItems(body.userId, body.favorite);
        connection.close();

        ResultResponse resultResponse = new ResultResponse("SUCCESS");
        mapper.writeValue(response.getWriter(), resultResponse);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        auth(request, response);

        String userId = request.getParameter("user_id");

        MySQLConnection connection = new MySQLConnection();
        Set<Item> items = connection.getFavoriteItems(userId);
        connection.close();
        mapper.writeValue(response.getWriter(), items);
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        auth(request, response);
        response.setContentType("application/json");
        HistoryRequestBody body = mapper.readValue(request.getReader(), HistoryRequestBody.class);

        MySQLConnection connection = new MySQLConnection();
        connection.unsetFavoriteItems(body.userId, body.favorite.getId());
        connection.close();

        ResultResponse resultResponse = new ResultResponse("SUCCESS");
        mapper.writeValue(response.getWriter(), resultResponse);
    }

}
