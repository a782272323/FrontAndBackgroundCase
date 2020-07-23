package learn.lhb.oauth2.vue01.admin.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @Description  自定义异常 OAuth2ExceptionHandler类的序列化类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/7/23
 * @time 15:16
 */
public class OAuth2ExceptionJacksonSerializerHandler extends StdSerializer<OAuth2ExceptionHandler> {

    protected OAuth2ExceptionJacksonSerializerHandler() {
        super(OAuth2ExceptionHandler.class);
    }


    @Override
    public void serialize(OAuth2ExceptionHandler value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        // 开始序列化
        gen.writeStartObject();
        // 异常状态码
        gen.writeStringField("code", String.valueOf(value.getHttpErrorCode()));
        // 异常响应信息
        String errorMessage = value.getMessage();
        if (StringUtils.isNotBlank(errorMessage)) {
            errorMessage = HtmlUtils.htmlEscape(errorMessage);
        }
        gen.writeStringField("message", errorMessage);
        gen.writeStringField("timestamp", DateTimeFormatter.ofPattern("yy-MM-dd hh:MM:ss").format(LocalDateTime.now()));
        // 如果还有其他异常信息
        if (value.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = (String) entry.getKey();
                String genValue = (String) entry.getValue();
                gen.writeStringField(key,genValue);
            }
        }
        // 结束序列化
        gen.writeEndObject();
    }
}
