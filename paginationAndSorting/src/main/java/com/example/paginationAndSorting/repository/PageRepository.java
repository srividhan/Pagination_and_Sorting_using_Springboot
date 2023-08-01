package com.example.paginationAndSorting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.paginationAndSorting.model.Pager;

public interface PageRepository extends JpaRepository<Pager, Long>{
    
}
