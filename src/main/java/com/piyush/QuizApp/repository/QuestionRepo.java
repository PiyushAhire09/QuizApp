package com.piyush.QuizApp.repository;

import com.piyush.QuizApp.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Questions,Integer> {

    public List<Questions> findByCategory(String category);


    @Query(value = "SELECT * FROM questions q WHERE q.category = :category ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
    List<Questions> findRandomQuestionByCategory(@Param("category") String category, @Param("numQ") int numQ);


}
