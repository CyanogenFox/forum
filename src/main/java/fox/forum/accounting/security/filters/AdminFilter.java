package fox.forum.accounting.security.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import fox.forum.accounting.dao.AccountingRepository;
import fox.forum.accounting.model.User;

@Service
@Order(20)
public class AdminFilter implements Filter {
	// TODO remove admin checking from DeleteFilter and do it here
	@Autowired
	AccountingRepository repository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			User userAccount = repository.findById(request.getUserPrincipal().getName()).get();
			if (!userAccount.getRoles().contains("Administrator".toUpperCase())) {
				response.sendError(403);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String path) {

		return path.matches("/account/user/\\w+/role/\\w+/?");
	}

}
