package com.security.api.util;

import lombok.Data;
import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Data
public class ValidSet<E> {
    @Valid
    @Delegate
    private Set<E> list = new HashSet<>();
}
