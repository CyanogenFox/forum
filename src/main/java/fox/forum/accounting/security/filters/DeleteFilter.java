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
@Order(30)
public class DeleteFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		if (checkEndPoint(req.getMethod(), req.getServletPath())) {
			if (!req.getUserPrincipal().getName().equals(getNameFromRequest(req))) {
				if (!req.getUserPrincipal().getName().equals("admin")) { //check tag instead
					resp.setStatus(403);
					return;
				}
			}
		}
		chain.doFilter(req, resp);
	}

	private String getNameFromRequest(HttpServletRequest req) {
		return req.getServletPath().split("/account/user/")[1].split("/")[0];
	}

	private boolean checkEndPoint(String method, String servletPath) {
		return "DELETE".equalsIgnoreCase(method) && servletPath.matches("/account/user/\\w+/?");
	}

}
