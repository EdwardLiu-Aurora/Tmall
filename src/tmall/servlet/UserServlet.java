package tmall.servlet;

import tmall.bean.User;
import tmall.util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Edward on 2018/7/12
 */

public class UserServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<User> us = userDAO.list(page.getStart(),page.getCount());
        int total = userDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("us", us);
        request.setAttribute("page", page);

        return "admin/listUser.jsp";
    }
}
