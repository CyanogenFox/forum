package fox.forum.accounting.security.filters;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import fox.forum.accounting.dao.AccountingRepository;
import fox.forum.accounting.model.User;

@Service
@Order(10)
public class AuthenticationFilter implements Filter {

	@Autowired
	AccountingRepository repository;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		if (checkEndPoint(req.getMethod(), req.getServletPath())) {
			String token = req.getHeader("Authorization");
			String[] details;
			try {
				token = token.split(" ")[1];
				String decode = new String(Base64.getDecoder().decode(token));
				details = decode.split(":");
			} catch (Exception e) {
				resp.sendError(401, "Token not valid");
				return;
			}
			User user = repository.findById(details[0]).orElse(null);
			if (user.equals(null) || !BCrypt.checkpw(details[1], user.getPassword())) {
				resp.sendError(401, "User or password not valid");
				return;
			}
			req = new WrappedRequest(req, user.getLogin());
		}
		chain.doFilter(req, resp);
	}

	private boolean checkEndPoint(String method, String servletPath) {
		return !("POST".equalsIgnoreCase(method) && servletPath.matches("/account/register/?"));
	}

	private class WrappedRequest extends HttpServletRequestWrapper {
		String login;

		public WrappedRequest(HttpServletRequest request, String login) {
			super(request);
			this.login = login;
		}

		@Override
		public Principal getUserPrincipal() {
			return () -> login;
		}
	}

}
