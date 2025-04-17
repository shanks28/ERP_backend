package com.example.ERP.Repository;
import com.example.ERP.Models.FieldOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface FieldOptionRepository extends JpaRepository<FieldOptions, Integer> {
    // List<FieldOptions> findByFieldName(String fieldName);
    @Query("select o.fieldValue from FieldOptions o where o.fieldName=:fieldName")
    List<String> findByFieldName(@Param("fieldName") String fieldName);
}
