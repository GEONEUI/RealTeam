package com.study.youtubeteam.controller;

import java.util.List;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.youtubeteam.emtity.youtubeList;
import com.study.youtubeteam.emtity.youtubeUserList;
import com.study.youtubeteam.mapper.YoutubeListMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;





@Controller
public class MyController {
	@Autowired
	YoutubeListMapper mapper;

	//************************* 건의 *************************
	
	//유투브 전체 리스트 불러오기

	// 건의
	@RequestMapping("/")
	public String index(@RequestParam(value="category",required=false,defaultValue="1") int category, @RequestParam(value="search",required=false,defaultValue="") String search, Model model, HttpSession session){
		String id = (String)session.getAttribute("id");
		
		//아이디를 알고있을때 해당 아이디의
		
		if(id == null) {
			id = "손님";
		}
		
		List<youtubeList> list = null;
		
		
		youtubeUserList userInfo = mapper.getOneUser(id);
		

		if(category==1) {
			list = mapper.selectAll();
		}
		if(category==2) {
			list = mapper.selectCate(category);
		}
		if(category==3) {
			list = mapper.selectCate(category);
		}
		if(category==4) {
			list = mapper.selectCate(category);
		}
		if(category==5) {
			list = mapper.selectCate(category);
		}
		if(category==6) {
			list = mapper.selectCate(category);
		}
		if(category == 7) {
			list = mapper.selectCate(category);
		}
		if(category == 8) {
			list = mapper.selectAll();
		}
			
		
		if(search.equals("")) {
			
		}else {
			list = mapper.dataSearch(search);
		}
		
		
		
		model.addAttribute("list", list);
		model.addAttribute("id", id);
		model.addAttribute("search", search);
		model.addAttribute("category", category);
		model.addAttribute("userInfo", userInfo);
		
		return "index";
	}
	
	//회원가입
	@RequestMapping("/join.do")
	public String join() {
		

		return "join";
	}
	
	//회원가입 데이터 받는곳
	@PostMapping("/joinProc.do")
	public String joinProc(youtubeUserList vo) {
		mapper.userInsert(vo);
		return "redirect:/joinMessage.do";
	}
	
	//회원가입 성공후 메세지화면
	@RequestMapping("/joinMessage.do")
	public String joinMessage() {
		return "joinMessage";
	}
	
	//로그인
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request, Model model) {
		
		Cookie[] cookies = request.getCookies();
		
		String CookieID = "";
		String CookiePW = "";
		
		if(cookies != null) {
			for(int i=0; i<cookies.length ;i++) {
				if(cookies[i].getName().equals("cookieID")) {
					CookieID = cookies[i].getValue();
					break;
				}
			}
		}
		
		if(cookies != null) {
			for(int i=0; i<cookies.length ;i++) {
				if(cookies[i].getName().equals("cookiePW")) {
					CookiePW = cookies[i].getValue();
					break;
				}
			}
		}
		
		
		model.addAttribute("CookieID", CookieID);
		model.addAttribute("CookieID", CookiePW);
		
		return "login";
	}
	
	//로그인 처리하는곳
	@PostMapping("/loginProc.do")
	public String loginProc(@RequestParam("user_id") String id, @RequestParam("user_pw") String pw, @RequestParam(value="checkbox", required = false, defaultValue = "0") int check, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		int result = mapper.userCheck(id, pw);
		
		
		//세션생성
		session.setAttribute("id", id);
		session.setMaxInactiveInterval(60*10);
		model.addAttribute("result", result);
		
		//쿠키생성
		if(check == 1) {
			Cookie cookie = new Cookie("cookieID", id);
			cookie.setMaxAge(60*1);
			response.addCookie(cookie);
			
			Cookie cookiepw = new Cookie("cookiePW", pw);
			cookiepw.setMaxAge(60*1);
			response.addCookie(cookie);
		}else {
			
		}
		
		return "loginMessage";
	}
	
	//로그아웃 처리하는곳
	@RequestMapping("/logoutProc.do")
	public String logoutProc(HttpSession session) {
		session.setAttribute("id", null);
		session.setMaxInactiveInterval(0);
		
		return "redirect:/";
	}
	//로그인 중복체크
	@RequestMapping("/loginCheck.do")
	public @ResponseBody int loginCheck(@RequestParam("user_id") String userid, Model model) {
		int result = mapper.searchId(userid);
		return result;
	}
	
	

	//예준

	@RequestMapping("/play")
	public String play() {
		return "play";
	}
	// 준호
	@RequestMapping("/channel")
	public String channel() {
		return "channel";
	}
	
	//준호
	@RequestMapping("/channelBoard")
	public String channelBoard() {
		return "channelBoard";
	}
	
	//준호
	@RequestMapping("/channelIndex")
	public String channelIndex() {
		return "channelIndex";
	}
	
	//유진
	@RequestMapping("/mypage")
	public String mypage() {
		return "mypage";
	}

	// 유진-보관함
	@RequestMapping("/videos")
	public String videos() {
		return "videos";
	}

	// 유진-시청기록
	@RequestMapping("/watchtime")
	public String watchtime() {
		return "watchtime";
	}

	// 유진-구독
	@RequestMapping("/subscribe")
	public String subscribe() {
		return "subscribe";
	}

	// 유진-댓글
	@RequestMapping("/comment")
	public String comment() {
		return "comment";
	}
}
