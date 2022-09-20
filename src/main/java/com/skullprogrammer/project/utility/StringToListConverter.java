package com.skullprogrammer.project.utility;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringToListConverter implements AttributeConverter<List<String>, String> {

    private String delimiter = ",";
    @Override
    public String convertToDatabaseColumn(List<String> list) {
        if(list == null) return "";
        return String.join(delimiter, list);
    }

    @Override
    public List<String> convertToEntityAttribute(String joined) {
        if(joined == null) return new ArrayList<>();
        return new ArrayList<>(Arrays.asList(joined.split(delimiter)));
    }
}