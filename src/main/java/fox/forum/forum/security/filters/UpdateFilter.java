package fox.forum.forum.security.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fox.forum.dao.ForumRepository;
import fox.forum.model.Post;

@Service
public class UpdateFilter implements Filter {

	@Autowired
	ForumRepository repository;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		if (checkEndPoint(httpServletRequest.getMethod(), httpServletRequest.getServletPath())) {
			Post post = repository.findById(getIDfromRequest(httpServletRequest)).orElse(null);
			if (!post.getAuthor().equals(httpServletRequest.getUserPrincipal().getName())) {
				httpServletResponse.setStatus(403);
				return;
			}
		}
		chain.doFilter(httpServletRequest, httpServletResponse);
	}

	private String getIDfromRequest(HttpServletRequest httpServletRequest) {
		return httpServletRequest.getServletPath().split("/forum/post/")[1].split("/")[0];
	}

	private boolean checkEndPoint(String method, String servletPath) {
		return "PUT".equalsIgnoreCase(method) && servletPath.matches("/forum/post/\\w+/?");
	}

}
