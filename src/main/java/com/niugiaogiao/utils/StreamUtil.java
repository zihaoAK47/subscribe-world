package com.niugiaogiao.utils;

import com.niugiaogiao.conf.exception.SystemException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public final class StreamUtil {

    private StreamUtil() {
    }

    public static String getHttpRequestContent(final HttpServletRequest request) {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            SystemException.exception(e.getMessage());
        }

        return stringBuilder.toString();
    }
}
