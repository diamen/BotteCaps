package com.stobinski.bottlecaps.ejb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stobinski.bottlecaps.ejb.security.CsrfSessionCacheBean;

@WebServlet(name = "loginServlet", urlPatterns = { "/admin/login" })
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 3640526000383206159L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		String hash = (String) req.getAttribute(CsrfSessionCacheBean.SALT);
		resp.setHeader("XSRF-TOKEN", hash);
		req.getRequestDispatcher("/index.html").forward(req, resp);
    }
	
}
