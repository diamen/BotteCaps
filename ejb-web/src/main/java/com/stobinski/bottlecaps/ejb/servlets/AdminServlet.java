package com.stobinski.bottlecaps.ejb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 6860476647099641842L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/index.jsp").forward(request, response);			
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
