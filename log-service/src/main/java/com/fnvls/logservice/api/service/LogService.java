package com.fnvls.logservice.api.service;

import com.fnvls.logservice.data.Log;

import java.util.List;

public interface LogService {
    void listenAndSave(String message);

    List<Log> getLogs();
}
