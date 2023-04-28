package com.vts.ems.config;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {
	@RequestMapping("/error")
	public String handleError(HttpServletRequest req) {
		Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				req.setAttribute("ErrorMsg", "Error 404");
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				req.setAttribute("ErrorMsg", "Error 500");
			} else {
				req.setAttribute("ErrorMsg", "Error 500");
			}
		}
		return "static/Error";
	}

	public String getErrorPath() {
		return "/error";
	}
	
	
	@RequestMapping("UnderConstruction.htm")
	public String UnderConstruction(HttpServletRequest req) {
		Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		
		return "static/UnderConstruction";
	}
}