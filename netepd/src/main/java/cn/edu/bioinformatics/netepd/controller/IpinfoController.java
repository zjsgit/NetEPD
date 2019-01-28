package cn.edu.bioinformatics.netepd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
public class IpinfoController {
	
	@RequestMapping(value="/ipsave")
	public @ResponseBody String saveIP(HttpServletRequest request, HttpSession session){
		String ip = request.getParameter("ip");
		//System.out.println("ipvalue:"+ip);
		JSONObject json = new JSONObject();
		if (ip == null || "".equals(ip)) {
			json.put("status", "shit");
			return json.toJSONString();
		}
		session.setAttribute("ipvalue", ip);
		json.put("status", "ok");
		return json.toString();
	}
	
}
