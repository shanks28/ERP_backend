package com.example.ERP.Repository;
import com.example.ERP.Models.FieldOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FieldOptionRepository extends JpaRepository<FieldOptions, Integer> {
    List<FieldOptions> findByFieldName(String fieldName);
}
