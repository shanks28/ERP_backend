package com.example.ERP.Controller;
import org.springframework.data.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import com.example.ERP.ServiceLayer.FieldOptionService;
import java.util.List;
import java.util.*;
@RestController
@RequestMapping("/fieldOption")
public class FieldOptionController {
    private final FieldOptionService fieldOptionService;
    
    FieldOptionController(FieldOptionService fieldOptionService){
        this.fieldOptionService=fieldOptionService;
    }

    @GetMapping("/{fieldName}")
    public List<?> getFieldOptions(@PathVariable String fieldName){
        return fieldOptionService.getFieldValues(fieldName);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addFieldOption(@RequestBody Map<String,String> request){
        return fieldOptionService.addFieldOption(request);
    }
    @PostMapping("/delete")
    public ResponseEntity<?> deleteFieldOptino(@RequestBody Map<String,String> request){
        return fieldOptionService.deleteField(request);
    }

}
