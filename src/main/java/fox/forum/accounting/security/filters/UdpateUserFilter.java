package fox.forum.accounting.security.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(31)
public class UdpateUserFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		if (checkEndPoint(httpServletRequest.getMethod(), httpServletRequest.getServletPath())) {
			if (!httpServletRequest.getUserPrincipal().getName().equals(getNameFromRequest(httpServletRequest))) {
				httpServletResponse.setStatus(403);
				return;
			}
		}
		chain.doFilter(httpServletRequest, httpServletResponse);
	}

	private String getNameFromRequest(HttpServletRequest req) {
		return req.getServletPath().split("/account/user/")[1].split("/")[0];
	}

	private boolean checkEndPoint(String method, String servletPath) {
		return "PUT".equalsIgnoreCase(method) && servletPath.matches("/account/user/\\w+/?");
	}

}
