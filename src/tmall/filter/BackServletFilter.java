package tmall.filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Edward on 2018/7/7
 */

// 判断请求是否为后台管理的过滤器
public class BackServletFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        // 获取 Web 应用程序环境路径
        String contextPath = request.getServletContext().getContextPath();
        // 获取 Web 请求的 uri 串
        String uri = request.getRequestURI();
        // 将 Web 环境路径去除掉，只剩下最后一段
        uri = StringUtils.remove(uri, contextPath);

        // 如果最后一段 URI 以 admin_ 开头，则表示这是后台管理的访问
        if(uri.startsWith("/admin_")){
            // 将头尾去掉，拼接成对应的 Servlet 名称
            String servletPath = StringUtils.substringBetween(uri,"_","_") + "Servlet";
            // 最后一部分的字符串代表需要访问的方法
            String method = StringUtils.substringAfterLast(uri,"_");
            // 在请求中设置参数
            request.setAttribute("method",method);
            // 获取转发器，将请求转发到对应的 servlet 处
            servletRequest.getRequestDispatcher("/"+servletPath).forward(request,response);
            return;
        }

        // 如果后面还定义了其他过滤器，则执行，否则执行 Servlet 对应的 service()
        filterChain.doFilter(request,response);
    }

    public void init(FilterConfig arg0) throws ServletException {

    }

    public void destroy() {

    }
}
