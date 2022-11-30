package com.shortloop.agent;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class HttpRequest<T, V> {
    String url;
    T requestBody;
    Map<String, String> queryParams;
    Map<String, String> requestHeaders;
    TypeReference<V> responseType;
}
