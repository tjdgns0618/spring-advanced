package org.example.expert.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class AdminApiLoggingAspect {

    private final ObjectMapper objectMapper;

    // Slf4j 어노테이션 사용하지 않고 Logger 클래스 사용
    private static final Logger log = LoggerFactory.getLogger(AdminApiLoggingAspect.class);

    @Around("execution(* org.example.expert.domain.comment.controller.CommentAdminController.deleteComment(..)) || " +
            "execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole(..))")
    public Object logAdminApi(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();

        Long userId = (Long) request.getAttribute("userId");
        String requestUrl = request.getRequestURI();
        LocalDateTime requestTime = LocalDateTime.now();

        // API 메서드 실행 전 요청 데이터 로깅
        String requestBody = objectMapper.writeValueAsString(joinPoint.getArgs());

        log.info("[Admin API] userId={}, requestTime={}, url={}, requestBody={}",
                userId, requestTime, requestUrl, requestBody);

        // 메서드 실행
        Object result = joinPoint.proceed();

        // API 메서드 실행 후 응답 데이터 로깅
        String responseBody = objectMapper.writeValueAsString(result);
        log.info("[Admin API] userId={}, url={}, responseBody={}", userId, requestUrl, responseBody);

        return result;
    }
}