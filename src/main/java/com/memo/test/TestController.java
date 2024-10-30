package com.memo.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memo.post.mapper.PostMapper;

@Controller
public class TestController {
	@Autowired
	private PostMapper postMapper;
	
	@GetMapping("/test1")
	@ResponseBody
	public String test1() {
		return "<h2>스트링 테스트</h2>";
	}
	
	@GetMapping("/test2")
	@ResponseBody
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<>();
		map.put("aaa", 111);
 		map.put("bbb", 222);
		map.put("ccc", 333);
		return map;
	}
	
	@GetMapping("/test3")
	public String test3() {
		return "test/test";
	}
	
	
	@GetMapping("/test4")
	@ResponseBody
	public List<Map<String, Object>> test4() {
		return postMapper.selectPostList();
	}
	
}
