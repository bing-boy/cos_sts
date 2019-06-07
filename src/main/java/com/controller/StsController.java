package com.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.servic.GetCredential;

@Controller
@RequestMapping("/")
public class StsController {

	@Autowired
	GetCredential getCre;

	@RequestMapping("getSts")
	@ResponseBody
	public String getSts(HttpServletRequest request) {
		System.out.println("StsController.getSts");
		String bucket = request.getParameter("bucket");
		String region = request.getParameter("region");
		String exp = request.getParameter("exp");
		String secId = request.getParameter("secId");
		String secKey = request.getParameter("secKey");
		String policy = request.getParameter("policy");
		System.out.println("bucket:" + bucket + ", region:" + region + ", exp:" + exp + ", secId:" + secId + ", secKey:"
				+ secKey + ", policy:" + policy);
		Map<String,String> map = new HashMap<>();
		map.put("bucket", bucket);
		map.put("region", region);
		map.put("exp", exp);
		map.put("secId", secId);
		map.put("secKey", secKey);
		map.put("policy", policy);
		JSONObject json = getCre.getCredential(map);
		return json.toString();
	}

	@RequestMapping("home")
	public String index() {
		System.out.println("StsController.index");
		return "index";
	}
}
