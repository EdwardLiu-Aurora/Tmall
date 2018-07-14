<%--
  Created by IntelliJ IDEA.
  User: Edwar
  Date: 2018/7/12
  Time: 18:22
  左侧竖状分类导航
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<div class="categoryMenu">
    <c:forEach items="${cs}" var="c">
        <div cid="${c.id}" class="eachCategory">
            <span class="glyphicon glyphicon-link"></span>
            <a href="forecategory?cid=${c.id}">
                    ${c.name}
            </a>
        </div>
    </c:forEach>
</div>
