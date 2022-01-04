package com.fnvls.logservice.impl.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnvls.logservice.data.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Util {
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
