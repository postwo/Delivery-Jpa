package com.delivery.api.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

//클라이언트에서 보낸 요청 데이터와 서버가 클라이언트에게 보낸 응답 데이터를 로그로 기록하는 필터
@Slf4j
@Component
public class LoggerFilter implements Filter {
     /* 이 필터는 클라이언트가 보낸 요청과 서버가 응답한 내용을 로그로 기록하는 기능을 수행합니다. 구체적으로, 이 필터는 다음과 같은 작업을 합니다:

        클라이언트 요청에 대한 정보 로그:
        헤더 정보: 클라이언트가 요청을 보낼 때 포함한 헤더 정보를 로그로 기록합니다.
        본문 정보: 클라이언트가 요청 본문에 포함한 내용을 로그로 기록합니다.
        URI와 메소드: 요청이 들어온 URI와 HTTP 메소드(GET, POST 등)를 로그로 기록합니다.

        서버 응답에 대한 정보 로그:
        헤더 정보: 서버가 클라이언트에게 응답할 때 설정한 응답 헤더 정보를 로그로 기록합니다.
        본문 정보: 서버가 클라이언트에게 응답 본문으로 보내는 내용을 로그로 기록합니다.
        응답 내용의 복사: res.copyBodyToResponse()를 호출하여 응답 본문을 클라이언트에게 실제로 전달합니다.
        */

    // 캐싱(Caching)이란 데이터를 임시로 저장해서 이후에 같은 데이터에 접근할 때 더 빠르게 처리할 수 있도록 하는 기술입니다. 즉, 한 번 저장한 데이터를 재사용함으로써 성능을 높이는 방법이에요.

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // Request를 캐싱 가능한 ContentCachingRequestWrapper로 감싸서 읽을 수 있도록 설정
        var req = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        // Response를 캐싱 가능한 ContentCachingResponseWrapper로 감싸서 읽을 수 있도록 설정
        var res = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        // 요청 초기 URI 로그 출력 (filterChain 호출 전이라 먼저 로그로 찍힘)
        log.info("INIT URI{}",req.getRequestURI());

        // 필터 체인을 통해 다음 필터로 이동 (여기까지 필터를 거쳐간 후 아래 코드가 실행됨)
        filterChain.doFilter(req,res); //여기를 기준으로 로그를 찍어준다 밑에부분은 dofilter를 나가기전에 찍어서 늦게 찍히는거고 위에는 dofilter보다 앞에있어서 먼저 찍힌다

        // --- Request 정보 ---
        // 클라이언트가 보낸 요청 헤더의 이름들을 가져옴
        // 지역변수들이다
        var headerNames = req.getHeaderNames();
        var headerValues = new StringBuilder();

        // 각 헤더 정보를 key-value 형식으로 읽어 로그를 출력하기 위한 문자열로 구성
        headerNames.asIterator().forEachRemaining(headerKey ->{
            var headerValue = req.getHeader(headerKey); // 클라이언트가 보낸 요청 헤더 값

            //authorization-token :??? , user-agent :???
            headerValues.append("[").append(headerKey).append(" : ").append(headerValue).append("] ");
        });

        // 요청 바디 내용을 읽어서 문자열로 변환
        var requestBody = new String(req.getContentAsByteArray());
        // 요청 URI
        var uri = req.getRequestURI();
        // 요청 메서드 (GET, POST 등)
        var method = req.getMethod();

        //>>>> uri: /api/account/me, method: GET ,header : [host : localhost:8080] [connection : keep-alive] [sec-ch-ua-platform : "Windows"]
        log.info(">>>> uri: {}, method: {} ,header : {}, body : {}",uri,method,headerValues.toString(),requestBody);


        //response 정보
        // 서버가 응답으로 보낼 헤더 정보를 가져옴
        var responseHeaderValues = new StringBuilder();

        // 각 응답 헤더의 값을 읽어 로그를 출력하기 위한 문자열로 구성
        res.getHeaderNames().forEach(headerKey ->{
            var headerValue = res.getHeader(headerKey); // 서버가 응답으로 보낼 헤더 값

            responseHeaderValues.append("[").append(headerValue).append(" : ").append(headerValue).append("] ");
        });

        // 응답 바디 내용을 읽어서 문자열로 변환
        var responseBody = new String(res.getContentAsByteArray());

        log.info("<<<<< uri: {}, method: {} , header :{} , body : {}",uri,method,responseHeaderValues.toString(),responseBody);

        // ContentCachingResponseWrapper가 응답 데이터를 캐싱하기 때문에 이를 실제 클라이언트에 전달
        //이거는 반드시 넣어줘야한다 responsebody가 깨져서 간다
        res.copyBodyToResponse();
    }
}
