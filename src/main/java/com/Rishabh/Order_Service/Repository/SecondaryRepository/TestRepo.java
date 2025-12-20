package com.Rishabh.Order_Service.Repository.SecondaryRepository;

import com.Rishabh.Order_Service.Entity.SecondaryDb.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepo extends JpaRepository<TestEntity, Long> {
}
