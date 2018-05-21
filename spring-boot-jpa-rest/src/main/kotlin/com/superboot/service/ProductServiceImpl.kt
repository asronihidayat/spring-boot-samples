package com.superboot.service

import com.superboot.dao.ProductRepository
import com.superboot.entity.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductServiceImpl : ProductService
{
    @Autowired
    lateinit var productRepository: ProductRepository

    override fun getAllProducts(): List<Product> {
        return productRepository.findAll().toList()
    }

    override fun save(product: Product): Product {
        productRepository.save(product)
        return product
    }

    override fun getOne(id: Long): Optional<Product> {
       return productRepository.findById(id)
    }

    override fun remove(product: Product) {
        productRepository.delete(product)
    }

}

