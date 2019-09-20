package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Authors;
import com.lambdaschool.starthere.repository.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "AuthorService")
public class AuthorServiceimpl implements AuthorService {

    @Autowired
    private AuthorsRepository authorsrepos;

    @Override
    public ArrayList<Authors> findAll(Pageable pageable) {
        ArrayList<Authors> list = new ArrayList<>();
        authorsrepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }
}
