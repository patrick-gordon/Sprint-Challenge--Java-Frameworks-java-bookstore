package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Authors;
import com.lambdaschool.starthere.models.ErrorDetail;
import com.lambdaschool.starthere.services.AuthorService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
    @RequestMapping("/authors")
    public class AuthorController {
        private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

        @Autowired
        private AuthorService authorService;

        @ApiOperation(value = "Get all authors", response = Authors.class)
        @ApiImplicitParams({
                @ApiImplicitParam(name = "page", dataType = "integr", paramType = "query",
                        value = "Results page you want to retrieve (0..N)"),
                @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                        value = "Number of records per page."),
                @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                        value = "Sorting criteria in the format: property(,asc|desc). " +
                                "Default sort order is ascending. " +
                                "Multiple sort criteria are supported.")})
        @ApiResponses(value = {@ApiResponse(code = 200, message = "Authors Found", response = Authors.class), @ApiResponse(code = 404, message = "Authors Not Found", response = ErrorDetail.class)})
        @GetMapping(value = "/authors", produces = {"application/json"})
        public ResponseEntity<?> listAllAuthors(HttpServletRequest request, @PageableDefault(page = 0, size = 10)
                Pageable pageable) {
            logger.warn(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed!");
            List<Authors> myAuthors = authorService.findAll(pageable);
            return new ResponseEntity<>(myAuthors, HttpStatus.OK);
        }
}
