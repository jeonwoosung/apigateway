package com.hlicp.clp.authorized;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/login")
	public String login(LoginDTO logInfo, HttpSession session) {
		logger.debug("login started, id:" + logInfo.getId() + " pwd: " + logInfo.getPwd() + " sessionId: " + session.getId());

		boolean result = loginCheck(logInfo.getId(), logInfo.getPwd());

		if (result) {
			session.setAttribute("authorized", true);
		}

		logger.debug("login ended, result: " + result);

		return result ? "logged in" : "login info is invalid";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		logger.debug("logout started, id:" + session.getId());

		session.removeAttribute("authorized");

		return "logged out";
	}

	private boolean loginCheck(String id, String pwd) {
		boolean result = false;
		if(id !=null && pwd != null){
			result = id.equals("1111111") && pwd.equals("1111");
		}
		
		return result;
	}

	@RequestMapping(value = "/sessionCheck")
	public String checkedOut(HttpSession session) {
		String result = "authorized"; 
		Object authObj = session.getAttribute("authorized");
		if (authObj == null || !(boolean) authObj) {
			result = "not authorized";
		}
		
		return result ;
	}

}
