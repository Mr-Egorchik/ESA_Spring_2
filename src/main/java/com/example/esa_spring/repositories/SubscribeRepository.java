package com.example.esa_spring.repositories;

import com.example.esa_spring.entity.Subscribe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SubscribeRepository extends CrudRepository<Subscribe, UUID> {

    @Query(value = "select subscribe.mail from subscribe where subscribe.notify = :notify", nativeQuery = true)
    List<String> getEmailsByNotify(@Param("notify") String notify);

}
