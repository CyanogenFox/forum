package fox.forum.forum.security.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public class AddFilter implements Filter {
	/*
	 * Add post or add comment filter
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		if (checkEndPoint(httpServletRequest.getMethod(), httpServletRequest.getServletPath())) {
			if (!checkAuth(httpServletRequest)) {
				httpServletResponse.setStatus(403);
				return;
			}
		}
		chain.doFilter(httpServletRequest, httpServletResponse);
	}

	private boolean checkAuth(HttpServletRequest httpServletRequest) {
		String loginString = httpServletRequest.getUserPrincipal().getName();
		return httpServletRequest.getServletPath().contains(loginString);
	}

	private boolean checkEndPoint(String method, String path) {
		return ("PUT".equalsIgnoreCase(method) && path.matches("/forum/post/\\w+/comment/\\w+/?"))
				|| ("POST".equalsIgnoreCase(method) && path.matches("/forum/post/\\w+/?"));
	}

}
