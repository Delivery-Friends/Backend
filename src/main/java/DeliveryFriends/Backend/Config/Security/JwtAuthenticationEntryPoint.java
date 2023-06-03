package DeliveryFriends.Backend.Config.Security;

import DeliveryFriends.Backend.Controller.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        PrintWriter writer = response.getWriter();
        ExceptionResponse exceptionResponse = new ExceptionResponse("잘못된 접근입니다. UNAUTHORIZEDDDDDD");
        try{
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            writer.write(exceptionResponse.getMessage());
        }catch(NullPointerException e){
            log.error("응답 메시지 작성 에러", e);
        }finally{
            if(writer != null) {
                writer.flush();
                writer.close();
            }
        }
        response.getWriter().write(exceptionResponse.getMessage());
    }
}
