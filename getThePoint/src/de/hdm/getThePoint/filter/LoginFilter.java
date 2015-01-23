package de.hdm.getThePoint.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.hdm.getThePoint.beans.UserBean;

/**
 * 
 * Dieser Loginfilter verhindert, dass unauthorisierte Benutzer auf die Seiten,
 * die in der Anwendung unter dem Pfad /secured liegen, erreichbar sind. Der
 * Benutzer wird dann direkt auf die Login-Seite (login.xhtml) umgeleitet.
 * 
 * Nach Beispiel auf http://www.itcuties.com/j2ee/jsf-2-login-filter-example/
 * umgesetzt.
 * 
 * @author Fabian
 *
 */
public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		UserBean userBean = (UserBean) ((HttpServletRequest) request)
				.getSession().getAttribute("userBean");

		if (userBean == null || !userBean.isLoggedIn()) {
			String contextPath = ((HttpServletRequest) request)
					.getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath
					+ "/login.xhtml");
		}

		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
