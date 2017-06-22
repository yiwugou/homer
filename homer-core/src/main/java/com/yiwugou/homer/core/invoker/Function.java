package com.yiwugou.homer.core.invoker;

public interface Function<ARG, RETURN> {
    RETURN apply(ARG arg) throws Exception;
}
