package com.alnt.map.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alnt.map.CustomHashMapImpl;

/**
 * @author vikas.singla
 *
 */
@RestController
@RequestMapping("/map")
public class HashMapController {

	CustomHashMapImpl<Object, Object> requestMapper = new CustomHashMapImpl<Object, Object>();

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	@ResponseBody
	public String create(HttpServletRequest request) {
		String token = request.getSession().getId() + Math.random();
		CustomHashMapImpl<Object, Object> obj = new CustomHashMapImpl<Object, Object>();
		requestMapper.put(token, obj);
		return token;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/put", method = RequestMethod.POST, consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void put(HttpServletRequest request,
			@RequestParam("key") Object key, @RequestParam("value") Object value) {
		String token = request.getHeader("token");
		if (token == null)
			throw new NullPointerException("SESSION ID is required");

		CustomHashMapImpl<Object, Object> obj = (CustomHashMapImpl<Object, Object>) requestMapper
				.get(token);
		obj.put(key, value);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object get(HttpServletRequest request,
			@RequestParam("key") Object key) {
		String token = request.getHeader("token");
		if (token == null)
			throw new NullPointerException("SESSION ID is required");

		CustomHashMapImpl<Object, Object> obj = (CustomHashMapImpl<Object, Object>) requestMapper
				.get(token);
		return obj.get(key);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/remove", method = RequestMethod.POST, consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void remove(HttpServletRequest request,
			@RequestParam("key") Object key) {
		String token = request.getHeader("token");
		if (token == null)
			throw new NullPointerException("SESSION ID is required");

		CustomHashMapImpl<Object, Object> obj = (CustomHashMapImpl<Object, Object>) requestMapper
				.get(token);
		obj.remove(key);
	}
}
