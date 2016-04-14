package com.stobinski.bottlecaps.ejb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 3640526000383206159L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/index.html").forward(request, response);			
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
