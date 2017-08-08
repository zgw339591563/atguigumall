package com.atguigu.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.pojo.User;
import com.atguigu.sso.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	@RequestMapping(value="check/{param}/{type}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Boolean> check(@PathVariable("param")String param,@PathVariable("type") Integer type){
		
		try {
			Boolean check = userService.check(param, type);
			return ResponseEntity.ok(check);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
	}
	
	@RequestMapping(value="login",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> login(@RequestParam("username") String username,@RequestParam("password") String password){
		
		try {
			String ticket = this.userService.login(username,password);
			if (ticket==null) {
				//登录失败
				return ResponseEntity.ok(null);
			}else{
				//登录成功
				return ResponseEntity.ok(ticket);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
	}
	
	@RequestMapping(value="register",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> register(User user){
		
		try {
			Boolean b = this.userService.register(user);
			if (b) {
				
				return ResponseEntity.status(HttpStatus.CREATED).body(null);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
	}
	
	@RequestMapping(value="{ticket}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<User> getUserByTicket(@PathVariable("ticket")String ticket){
		
		try {
			User user=userService.getUserByTicket(ticket);
			if(user!=null){
				return ResponseEntity.status(HttpStatus.OK).body(user);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
	}
	
}
