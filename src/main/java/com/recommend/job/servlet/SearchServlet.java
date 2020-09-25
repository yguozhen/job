package com.recommend.job.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recommend.job.db.MySQLConnection;
import com.recommend.job.entity.Item;
import com.recommend.job.external.GitHubClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet(name = "SearchServlet", urlPatterns = "/search")
public class SearchServlet extends BaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        auth(request,response);
        ObjectMapper mapper = new ObjectMapper();
        String userId = request.getParameter("user_id");
        double lat = Double.parseDouble(request.getParameter("lat"));
        double lon = Double.parseDouble(request.getParameter("lon"));

        MySQLConnection connection = new MySQLConnection();
        Set<String> favoritedItemIds = connection.getFavoriteItemIds(userId);
        connection.close();

        GitHubClient client = new GitHubClient();
        response.setContentType("application/json");
        List<Item> items = client.search(lat, lon, null);

        for(Item item : items){
            item.setFavorite(favoritedItemIds.contains(item.getId()));
        }

        response.getWriter().print(mapper.writeValueAsString(items));
    }
}
