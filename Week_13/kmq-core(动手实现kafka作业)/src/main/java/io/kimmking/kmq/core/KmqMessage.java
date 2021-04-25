package io.kimmking.kmq.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

/**
 * 消息
 * @param <T>
 */
@AllArgsConstructor
@Data
public class KmqMessage<T> {

    private HashMap<String,Object> headers;

    private T body;

}
