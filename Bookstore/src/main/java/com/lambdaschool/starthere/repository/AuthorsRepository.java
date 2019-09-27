package com.lambdaschool.starthere.repository;

import com.lambdaschool.starthere.models.Authors;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorsRepository extends PagingAndSortingRepository<Authors, Long> {

}
