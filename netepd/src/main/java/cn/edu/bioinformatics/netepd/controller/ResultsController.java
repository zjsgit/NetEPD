package cn.edu.bioinformatics.netepd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.bioinformatics.netepd.entity.FormInfo;
import cn.edu.bioinformatics.netepd.service.FormInfoService;

@Controller
public class ResultsController {
	
	@Autowired
	private FormInfoService formInfoService;
	
	@RequestMapping(value="/result")
	public String getResult(HttpServletRequest request, HttpSession session){
		
		String forminfo = request.getParameter("forminfo");
		String jobid = request.getParameter("jobid");
		
		int infoId = Integer.parseInt(forminfo);
		FormInfo aform = formInfoService.getById(infoId);
		System.out.println(aform);
		//验证用户信息是否正确
		if (!aform.getJobId().equals(jobid)) {
			return "error";
		}
		
		session.setAttribute("infoId", forminfo);
		session.setAttribute("taxId", aform.getTaxid());
		return "result";
	}
}
