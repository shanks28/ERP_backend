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
        try{
        List<String> response=new ArrayList<>();
        List<String> fieldOptions=fieldOptionRepository.findByFieldName(fieldName);
        // for(FieldOptions op:fieldOptions){
        //     response.add(op.getFieldValue());
        // }
        return fieldOptions;
        }catch(Exception e){
            return new ArrayList<>(Arrays.asList(e));
        }

    }
    @Transactional
    public ResponseEntity<?> addFieldOption(Map<String,String> request){
        try{
            String fieldName=request.get("fieldName").toLowerCase();
            String fieldValue=request.get("fieldValue").toLowerCase();
            List<String> existing_options=fieldOptionRepository.findByFieldName(fieldName);
            if(existing_options.contains(fieldValue)){
                return new ResponseEntity<>("Field Value already exists",HttpStatus.BAD_REQUEST);
            }
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
