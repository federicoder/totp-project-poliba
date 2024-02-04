package com.secuirty.demo.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.secuirty.demo.requests.VerifyCodeRequest;
import com.secuirty.demo.service.TotpManager;
import com.secuirty.demo.service.dto.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.secuirty.demo.model.User;
import com.secuirty.demo.service.JWTService;
import com.secuirty.demo.service.RegistrationService;
import com.secuirty.demo.filters.AuthFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class RegistrationController {

	@Autowired
	private AuthFilter filter;
	@Autowired
	private RegistrationService service;
	@Autowired
	private TotpManager otp;

	@RequestMapping("/")
	public String home() {
		return "Hello World!";
	}

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
	Integer userID;
	String userName = "";

	@PostMapping("/new")
	@CrossOrigin(origins = "http://localhost:4200")
	public String register(@RequestBody User user) throws Exception {
		String tempEmail = user.getmail();
		if (tempEmail != null && !tempEmail.equals("")) {
			User userObj = service.FetchUserByEmailId(tempEmail);

			if (userObj != null) {
				throw new Exception("User With " + tempEmail + " is already Registered");
			}
		}
		String encodedPWD = encoder.encode(user.getPassword());
		user.setPassword(encodedPWD);
		User userObj = new User();
		user.setSecret(otp.generateSecret());
		userObj = service.saveUser(user);
		System.out.println();
		return "OK";
	}

	@PostMapping("/login")
	@CrossOrigin(origins = "http://localhost:4200")
	public String loginUser(@RequestBody User user, HttpServletResponse response) throws Exception {
		String tempMail = user.getmail();
		String tempPass = user.getPassword();
		System.out.println("email " + tempMail);
		System.out.println("pass " + tempPass);

		User test = service.FetchUserByEmailId(tempMail);
		if (encoder.matches(tempPass, test.getPassword())) {
			this.setCookie(response, test.getId());
			this.userID = test.getId();
			this.userName = test.getUsername();
			return "OK";
		} else
			throw new Exception("Credentials invalid");
	}

	@GetMapping("/2fa")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> getQRCode(@RequestParam(name = "digits", required = false) Optional<String> nrOfDigits,
			@RequestParam(name = "algorithm", required = false) Optional<String> typeAlgorithm,
			@RequestParam(name = "period", required = false) Optional<String> periodOfOTP) throws Exception {
		int userId = this.filter.getUserID();
		// String username = this.filter.getUserName();
		// findById su utente
		Optional<User> let = this.service.FetchUserName(userId);
		User user = let.get();
		URI location = ServletUriComponentsBuilder
				.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(userName).toUri();
		if (user.firstime) {
			System.out.println(location);
			System.out.println(" Log in " + otp.getUriForImage(user.secret, nrOfDigits, typeAlgorithm, periodOfOTP));
			return ResponseEntity.created(location).body(otp.getUriForImage(user.secret, nrOfDigits, typeAlgorithm, periodOfOTP));
		} else {
			return ResponseEntity.created(location).body(null);
		}
	}
	// chiamata utilizzata per verificare che il codice qr immesso sia valido:
	@PostMapping("/verify")
	@CrossOrigin(origins = "http://localhost:4200")
	public String verify(@RequestBody VerifyCodeRequest verifyCodeRequest, HttpServletResponse response) {
		System.out.println(" get code " + verifyCodeRequest.getCode());
		User user = this.service.findByUsername(this.filter.getUserName());
		// in questo punto viene verificato che il codice sia valido:
		if(!user.firstime){
			Parameter parameter = new Parameter();
			parameter.setNumberOfCode(user.getDigits());
			parameter.setAlgorithm(user.getAlgorithm());
			if (!otp.verifyCode(verifyCodeRequest.getCode(), user.secret, parameter)) {
				return "false";
			} else {
				this.filter.setTwoFa(true);
				return "true";
			}
		}
		else{
			if (!otp.verifyCode(verifyCodeRequest.getCode(), user.secret,verifyCodeRequest.getData())) {
				return "false";
			} else {
				this.filter.setTwoFa(true);
				user.firstime = false;
				user.setDigits(verifyCodeRequest.getCode().length());
				user.setAlgorithm(verifyCodeRequest.getData().getAlgorithm().toString());
				this.service.updateUser(user);
				return "true";
			}
		}
	

	}

	@PostMapping("/logout")
	@CrossOrigin(origins = "http://localhost:4200")
	public String logoutUser(HttpServletResponse response) {
		this.userID = filter.getUserID();
		System.out.println("Logout " + this.userID);
		this.deleteCookie(response, this.userID);
		System.out.println("delete " + this.userName);
		return "\"OK\"";
	}

	@GetMapping("/name")
	@CrossOrigin(origins = "http://localhost:4200")
	public String getUserName() {

		this.userName = filter.getUserName();
		System.out.println("username" + this.userName);
		return this.userName;
	}

	@GetMapping("/userdetails")
	@CrossOrigin(origins = "http://localhost:4200")
	public User getUserDetails() {
		Optional<User> let = this.service.FetchUserName(this.filter.getUserID());
		User user = let.get();
		user.setPassword("");
		return user;

	}

	@GetMapping("/firstime")
	@CrossOrigin(origins = "http://localhost:4200")
	public boolean getFirstTime(@RequestParam(name = "email") String email) {
		User user = this.service.FetchUserByEmailId(email);
		return user.isFirstime();

	}
	public Boolean setCookie(HttpServletResponse response, Integer userId) {
		// create a cookie
		Cookie cookie = new Cookie("token", new JWTService().generateToken(userId));
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(100 * 60 * 60);
		// cookie.s ;
		// add cookie to response
		response.addCookie(cookie);

		return true;
	}

	public Boolean deleteCookie(HttpServletResponse response, Integer userId) {
		// create a cookie
		Cookie cookie = new Cookie("token", new JWTService().generateToken(userId));
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		this.filter.setTwoFa(false);
		// cookie.s ;
		// add cookie to response
		response.addCookie(cookie);

		return true;
	}
}
