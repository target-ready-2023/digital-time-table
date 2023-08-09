package com.target_india.digitize_time_table.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Data
@Component
public class DbSettings {

    String url,username,password;

    public List<String> getDbInfo() {
        List<String> list = new ArrayList<>();
        list.add(this.getUrl());
        list.add(this.getUsername());
        list.add(this.getPassword());
        return list;
    }

}
