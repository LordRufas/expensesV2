package com.kaiga.expenses.utilities;

import tools.jackson.databind.ObjectMapper;

import java.util.*;

public class Utilities {

    private Utilities(){

    }

    public static String createJsonResponse(String response, String statusCode, Map<String, Object> data) {

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> root = new LinkedHashMap<>();
        root.put("response", response);
        root.put("statusCode", statusCode);

       if(data != null){
            for (String i : data.keySet()) {
                root.put(i, data.get(i));
            }

        }


        return mapper.writeValueAsString(root);
    }
}
