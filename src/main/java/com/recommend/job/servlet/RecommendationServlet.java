package com.recommend.job.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recommend.job.entity.Item;
import com.recommend.job.recommendation.Recommendation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RecommendationServlet", urlPatterns = "/recommendation")
public class RecommendationServlet extends BaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        auth(request, response);

        String userId = request.getParameter("user_id");
        double lat = Double.parseDouble(request.getParameter("lat"));
        double lon = Double.parseDouble(request.getParameter("lon"));

        Recommendation recommendation = new Recommendation();
        List<Item> items = recommendation.recommendItems(userId, lat, lon);
        mapper.writeValue(response.getWriter(), items);


    }
}
