package DeliveryFriends.Backend.Config.Security;

import DeliveryFriends.Backend.Controller.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ExceptionResponse exceptionResponse = new ExceptionResponse("잘못된 접근입니다. UNAUTHORIZEDDDDDD");
        try{
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            JSONObject responseJson = new JSONObject();
            responseJson.put("message", "JWT가 만료되었습니다.");
            responseJson.put("statusCode", 10003);
            response.getWriter().print(responseJson);
        } catch (JSONException e) {
            log.error("JSON 생성 에러 {}", e);
        }
    }
}
