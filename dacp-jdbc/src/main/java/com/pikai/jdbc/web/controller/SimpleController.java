package com.pikai.jdbc.web.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SimpleController {
	/**
	 * 用于映射静态页面
	 * 
	 * @param request
	 * @param response
	 * @param ftl
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = { "/page/{ftl:.*}" }, method = { RequestMethod.POST, RequestMethod.GET })
	public String add(HttpServletRequest request, HttpServletResponse response, @PathVariable("ftl") String ftl, ModelMap modelMap) {
		Map paramMap = getParameterMap(request);
		Iterator it = paramMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			request.setAttribute((String) entry.getKey(), (String) entry.getValue() + "");
		}
		if (StringUtils.isNotBlank(ftl)) {
			return "/" + ftl.replace(".", "/");
		}
		return "/index";
	}

	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();

		String name = "";
		String value = "";
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if ((valueObj instanceof String[])) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
}
