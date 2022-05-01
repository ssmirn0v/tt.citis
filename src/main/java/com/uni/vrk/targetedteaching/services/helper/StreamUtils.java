package com.uni.vrk.targetedteaching.services.helper;

import java.util.Collection;
import java.util.stream.Stream;

public class StreamUtils {

    public static <T> Stream<T> toStream(final Collection<T> collection) {
        return collection.isEmpty() ? Stream.empty() : collection.stream();
    }
}
