<%@ page import="ru.javawebinar.topjava.model.UserMeal" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.Month" %>
<%@ page import="ru.javawebinar.topjava.model.UserMealWithExceed" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="static ru.javawebinar.topjava.util.UserMealsUtil.getFilteredWithExceeded" %><%--
  Created by IntelliJ IDEA.
  User: Жека
  Date: 07.06.2016
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals List</title>
</head>
<body>
    <h1><u>Meals</u></h1>
    <hr>
    <% List<UserMeal> mealList = Arrays.asList(
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );
        List<UserMealWithExceed> filteredMealsWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(23, 0), 2000);%>
    <table border="1">
        <caption>Meals</caption>
        <tbody>
        <tr>
            <th>Дата</th>
            <th>Еда</th>
            <th>Каллории</th>
        </tr>
    <% for (int i = 0; i < filteredMealsWithExceeded.size(); i++) {
        UserMealWithExceed userMealWithExceed = filteredMealsWithExceeded.get(i);
    %>
        <tr>
            <th><%= userMealWithExceed.getDateTime().toString().replace("T", " ")%></th>
            <th><%= userMealWithExceed.getDescription()%></th>
            <% if(userMealWithExceed.isExceed()) {%>
                <th style="color: red">
            <% } else { %>
                <th>
            <%} %> <%= userMealWithExceed.getCalories()%></th>
        </tr>
        <%
    }%>
        </tbody>
    </table>
</body>
</html>
