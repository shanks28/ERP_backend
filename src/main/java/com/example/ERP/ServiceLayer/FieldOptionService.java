package com.example.ERP.ServiceLayer;

import org.springframework.stereotype.Service;
import org.springframework.http.*;
import com.example.ERP.Models.*;
import java.util.*;
import com.example.ERP.Repository.FieldOptionRepository;

import jakarta.transaction.Transactional;
@Service
public class FieldOptionService {
    private final FieldOptionRepository fieldOptionRepository;

    FieldOptionService(FieldOptionRepository fieldOptionRepository) {
        this.fieldOptionRepository = fieldOptionRepository;
    }
    public List<?> getFieldValues(String fieldName){
        List<String> response=new ArrayList<>();
        List<FieldOptions> fieldOptions=fieldOptionRepository.findByFieldName(fieldName);
        for(FieldOptions op:fieldOptions){
            response.add(op.getFieldValue());
        }
        return response;

    }
    @Transactional
    public ResponseEntity<?> addFieldOption(Map<String,String> request){
        try{
            String fieldName=request.get("fieldName").toLowerCase();
            String fieldValue=request.get("fieldValue").toLowerCase();
            FieldOptions option=new FieldOptions();
            option.setFieldName(fieldName);
            option.setFieldValue(fieldValue);
            fieldOptionRepository.save(option);
            return new ResponseEntity<>("Added Category",HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Failed to add Category",HttpStatus.BAD_REQUEST);
        }

    }
}
