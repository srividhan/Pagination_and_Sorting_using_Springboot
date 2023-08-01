package com.example.paginationAndSorting.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.paginationAndSorting.model.Pager;
import com.example.paginationAndSorting.repository.PageRepository;

@RestController
@RequestMapping("/paging")
public class PageController {
    
    @Autowired
    PageRepository pageRepository;

    @GetMapping("/show")
    public List<Pager> getAllDetails(){
        return (List<Pager>) pageRepository.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Pager> createDetails(@RequestBody Pager page){
        Pager _page=pageRepository
        .save(new Pager(page.getName(),page.getAge(),page.getPassword()));
        return new ResponseEntity<>(_page, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteAllDetails(){
        pageRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/insert/{id}")
    public ResponseEntity<Pager> updateDetails(@PathVariable("id") Long id,@RequestBody Pager page){
        Optional<Pager> __page=pageRepository.findById(id);
        if(__page.isPresent()){
            Pager _page = __page.get();
            _page.setName(page.getName());
            _page.setAge(page.getAge());
            _page.setPassword(page.getPassword());
            return new ResponseEntity<>(pageRepository.save(_page), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private Sort.Direction getSortDirection(String direction){
        if(direction.equals("desc")){
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }
    @GetMapping("/paginationandsorting")
    public ResponseEntity<Map<String, Object>> getAllDetailsPage(
        @RequestParam(required = false) String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size,
        @RequestParam(defaultValue = "id,asc") String[] sort) {
            List<Order> orders = new ArrayList<Order>();
            if (sort[0].contains(",")){
                for(String sortOrder : sort){
                    String[] _sort=sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]),_sort[0]));
                }
            }
            else{
                orders.add(new Order(getSortDirection(sort[1]),sort[0]));
            }
            List<Pager> pager = new ArrayList<Pager>();
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
  
            Page<Pager> pageTuts;
            pageTuts = pageRepository.findAll(pagingSort);
            pager = pageTuts.getContent();
  
            Map<String, Object> response = new HashMap<>();
            response.put("page", page);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
  
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
}