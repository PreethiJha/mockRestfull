package com.custom.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Parser {

    public static List<OutputPojo> parse(String json){
        List<OutputPojo> outputPojos = new ArrayList();
        String[] jsonDocs = json.split("}\\s*+,");
        for (String jsonDoc: jsonDocs) {
            OutputPojo outputPojo = new OutputPojo();
            outputPojo.keyValue = new LinkedHashMap();
            outputPojo.numbers = new ArrayList();
            String jsonDocBody = jsonDoc.split("\\{")[1];
            String[] splits = jsonDocBody.split(",");
            for(String split : splits) {
                String element = split.trim();
                String[] keyVal = element.split(":");
                if(keyVal.length == 2) {
                    String key = keyVal[0].trim();
                    String value = keyVal[1].trim();
                    key = key.substring(1, key.length()-1);
                    if(key.toLowerCase().equals("numbers")) {
                        String val = keyVal[1].trim().split("\\[")[1].trim();
                        outputPojo.numbers.add(Integer.parseInt(val));
                    } else {
                        if(value.startsWith("\"")) {
                            outputPojo.keyValue.put(key, value.substring(1, value.length() - 1));
                        }
                        else {
                            outputPojo.keyValue.put(key, value);
                        }
                    }
                } else { //keyVal.length is 1
                    String numStr = keyVal[0].trim();
                    if(numStr.endsWith("]")) {
                        String num = numStr.split("]")[0].trim();
                        outputPojo.numbers.add(Integer.parseInt(num));
                    } else {
                        outputPojo.numbers.add(Integer.parseInt(numStr));
                    }
                }
            }
            outputPojos.add(outputPojo);
        }
        return outputPojos;
    }

    public static void main(String[] args) {
        String json = "[{" +
                "  \"numbers\": [" +
                "    1," +
                "    2," +
                "    3" +
                "  ]," +
                "  \"boolean\": true," +
                "  \"string\": \"Hello World\"" +
                "}," +
                "{" +
                "  \"numbers\": [" +
                "    1," +
                "    2," +
                "    3" +
                "  ]," +
                "  \"boolean\": true," +
                "  \"string\": \"Hello World\"" +
                "}," +
                "{" +
                "  \"numbers\": [" +
                "    1," +
                "    2," +
                "    3" +
                "  ]," +
                "  \"boolean\": true," +
                "  \"string\": \"Hello World\"" +
                "}]";
        long startTime = System.nanoTime();
        List<OutputPojo>  outputPojos = parse(json);
        System.out.println(outputPojos.size());
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
    }
}

