package com.alta.hello.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class User {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String password;
}
