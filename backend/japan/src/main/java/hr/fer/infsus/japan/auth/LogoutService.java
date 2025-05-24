package hr.fer.infsus.japan.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName()) || "accessToken".equals(cookie.getName())) {
                    Cookie expiredCookie;
                    if (cookie.getName().equals("accessToken")) {
                        expiredCookie = new Cookie("accessToken", null);
                        expiredCookie.setPath("/");
                    } else {
                        expiredCookie = new Cookie("refreshToken", null);
                        expiredCookie.setPath("/auth");
                    }

                    expiredCookie.setHttpOnly(true);
                    expiredCookie.setSecure(true);
                    expiredCookie.setMaxAge(0);

                    response.addCookie(expiredCookie);
                }
            }
        }

        SecurityContextHolder.clearContext();
    }

}
