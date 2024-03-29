package com.secuirty.demo.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.secuirty.demo.model.User;
import com.secuirty.demo.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.secuirty.demo.service.JWTService;

@Component
public class AuthFilter implements Filter {
   @Override
   public void destroy() {}
   private Integer userID = null ;
   private  String userName = null ;
   private Boolean twoFa = false;

    public void setTwoFa(Boolean twoFa) {
        this.twoFa = twoFa;
    }

    @Autowired
    private RegistrationService service ;


    @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain) 
      throws IOException, ServletException {
              HttpServletRequest req = (HttpServletRequest) request;
       HttpServletResponse  res = (HttpServletResponse) response;

       res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
       res.setHeader("Access-Control-Allow-Credentials", "true");
       res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
       res.setHeader("Access-Control-Max-Age", "3600");
       res.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

      System.out.println("Remote Host:"+request.getRemoteHost());
      System.out.println("Remote Address:"+request.getRemoteAddr());
      
      Cookie[] cookies = req.getCookies();
      boolean authenticated = false;
      if (cookies != null){
        for (Cookie ck : cookies) {
          if ("token".equals(ck.getName())) {
              request.setAttribute("userId",  new JWTService().validateToken(ck.getValue()));
              System.out.println("User id in filter " + request.getAttribute("userId"));
              userID = Integer.parseInt(request.getAttribute("userId").toString()) ;
              authenticated = true;
              Optional<User> options = service.FetchUserName(userID) ;

              options.ifPresent(user -> userName =  user.getUsername());

          }
        }
      }
      
      if (!(req.getMethod().equalsIgnoreCase("OPTIONS"))) {
          try {
	        	  String path = req.getRequestURI();
	              System.out.println("Is authenticated : " + authenticated + " 2 factor " + twoFa);
	
	              ArrayList<String> excludeList = new ArrayList<>(Arrays.asList("/login", "/new", "/2fa", "/verify", "/firstime"));
	            		  
	              if (excludeList.contains(path) || (authenticated && twoFa)) {
	            	  filterchain.doFilter(request, response);
	              } else {
                      res.setStatus(HttpStatus.UNAUTHORIZED.value());
                      res.getOutputStream().print("{\"error\": \"Not authorised\"}");
	              }
              	} catch(Exception e) {
              e.printStackTrace();
          }
      } else {
          System.out.println("Pre-flight");
          res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
          res.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,PUT");
          res.setHeader("Access-Control-Max-Age", "3600");
          res.setHeader("Access-Control-Allow-Headers", "Access-Control-Expose-Headers"+"Authorization, content-type," +
          "USERID" + "ROLE"+
                  "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with,responseType,observe");
          res.setStatus(HttpServletResponse.SC_OK);
      }
      
   }

    public Integer getUserID() {
        System.out.println("userId "+ userID);
        return userID;
    }

    public  String getUserName() {
        return userName ;
    }

    @Override
   public void init(FilterConfig filterconfig) throws ServletException {}
}
