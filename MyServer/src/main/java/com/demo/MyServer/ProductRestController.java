package com.demo.MyServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/product")
//@ComponentScan(basePackages={"com.demo"})
//@EnableAutoConfiguration
public class ProductRestController {
	
	@Autowired
	private ProductService productService ;
	
	@RequestMapping(value="find/{id}",method=RequestMethod.GET,produces={
			MimeTypeUtils.APPLICATION_JSON_VALUE
	})
	public ResponseEntity<Product> find(@PathVariable("id") String id){
		
		
		try {
			return new ResponseEntity<Product>(productService.find(id),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
		}
	}
	




}
