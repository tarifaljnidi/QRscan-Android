package com.demo.MyServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductServiceImpl implements ProductService{
	
	@Autowired
private ProductRepository productRepository;
	@Override
	public Product find(String id) {
		return productRepository.findById(id).get();
	}

}
