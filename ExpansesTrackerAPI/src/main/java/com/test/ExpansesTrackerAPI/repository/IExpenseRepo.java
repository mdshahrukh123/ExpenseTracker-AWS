package com.test.ExpansesTrackerAPI.repository;

import com.test.ExpansesTrackerAPI.model.ExpenseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExpenseRepo extends JpaRepository<ExpenseProduct,Integer> {

    @Query(value = "select * from expenses where fk_user_id = :existUserId",nativeQuery = true)
    List<ExpenseProduct> getExpenseByUserFkId(Integer existUserId);


}
