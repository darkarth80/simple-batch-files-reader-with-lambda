package com.darkarth.demo.util;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.darkarth.demo.exception.UncheckedException;
import com.darkarth.demo.model.dto.SimpleDTO;
import com.darkarth.demo.util.enumeration.Type;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LineUtil {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LineUtil.class);

    private final String ID_KEY = "id";
    private final String NAME_KEY = "name";
    private final String DATE_KEY = "date";
    private final String TYPE_KEY = "type";
    private final String KEY_VALUE_SEPARATOR = "=";
    private final String BASE_MSG = "The line format is incorrect";

    private final String DATE_REGEX = "^(?=\\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\\x20|$))|(?:2[0-8]|1\\d|0?[1-9]))([-./])(?:1[012]|0?[1-9])\\1(?:1[6-9]|[2-9]\\d)?\\d\\d(?:(?=\\x20\\d)\\x20|$))?(((0?[1-9]|1[012])(:[0-5]\\d){0,2}(\\x20[AP]M))|([01]\\d|2[0-3])(:[0-5]\\d){1,2})?$";

    @Value("${app.conf.line.line-token-separator}")
    private String tokenSeparator;

    public SimpleDTO transform(String line/*, Consumer<Integer> consumer*/) {
        // try {
            return createObject(getMap(line.split(Pattern.quote(tokenSeparator))));
        // } catch(Exception e) {
        //     return null;
        // }
    }

    private SimpleDTO createObject(Map<String, String> map) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, SimpleDTO.class);
    }

    private Map<String, String> getMap(String[] content) {

        validateLength(content.length);

        Map<String, String> map = Arrays.stream(content)
        .map(
            s -> s.split(KEY_VALUE_SEPARATOR)
        ).collect(
            Collectors.toMap(
                a -> validateKey(a[0]), 
                a -> validateValue(a[0], a[1])
            )
        );

        validateLength(map.keySet().size());

        return map;
    }

    private void validateLength(int size) {
        if (size != 4) {
            throw new UncheckedException("EX01", BASE_MSG, "The line fields should be exactly 4.", LOGGER);
        }
    }

    private String validateKey(String key) {
        if (
            !key.equals(ID_KEY) && 
            !key.equals(NAME_KEY) && 
            !key.equals(DATE_KEY) && 
            !key.equals(TYPE_KEY)
        ) {
            throw new UncheckedException("EX02", BASE_MSG, String.format("The key %s is not a valid key.", key), LOGGER);
        }

        return key;
    }

    private String validateValue(String key, String value) {
        
        switch(key) {
            case ID_KEY:
                validateNullOrEmpty(key, value);
            break;
            case NAME_KEY:
                validateNullOrEmpty(key, value);
                if (value.length() > 15) {
                    throw new UncheckedException("EX03", BASE_MSG, String.format("The field %s's length can't be higher than 15. Actual value: %s", key, value.length()), LOGGER);
                }
            break;
            case DATE_KEY:
                validateNullOrEmpty(key, value);
                if (!value.matches(DATE_REGEX)) {
                    throw new UncheckedException("EX03", BASE_MSG, String.format("The field %s's format should be dd/mm/yyyy hh:mm:ss. Actual value: %s", key, value), LOGGER);
                }
            break;
            case TYPE_KEY:
                try {
                    Type.valueOf(value);
                } catch (IllegalArgumentException e) {
                    throw new UncheckedException("EX03", BASE_MSG, String.format("The field %s is not valid, must be one of the following: Simple, Complex. Actual value: %s", key, value), LOGGER);
                }
            break;
            default:
                throw new UncheckedException("EX03", BASE_MSG, String.format("The value %s is invalid.", value), LOGGER);
        }

        return value;
    }

    private void validateNullOrEmpty(String key, String value){
        if (StringUtils.isBlank(value)) {
            throw new UncheckedException("EX03", BASE_MSG, String.format("The field %s can't be null or empty.", key), LOGGER);
        }
    }

}