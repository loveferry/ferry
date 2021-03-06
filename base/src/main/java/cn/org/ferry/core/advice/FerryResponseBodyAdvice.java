package cn.org.ferry.core.advice;

import cn.org.ferry.core.dto.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class FerryResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private static final Logger logger = LoggerFactory.getLogger(FerryResponseBodyAdvice.class);
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        if(methodParameter.getMethod().getReturnType().equals(ResponseEntity.class)){
            return false;
        }
        if(methodParameter.getMethod().getAnnotation(ResponseBody.class) == null
                && methodParameter.getDeclaringClass().getAnnotation(RestController.class) == null){
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(o instanceof ResponseData){
            ResponseData responseData = (ResponseData)o;
            if(null == responseData.getCode()){
                responseData.setCode(((ServletServerHttpResponse) serverHttpResponse).getServletResponse().getStatus());
            }
            if(!responseData.getSuccess()){
                logger.error(responseData.getMessage());
            }
            return responseData;
        }else {
            return o;
        }

    }
}
