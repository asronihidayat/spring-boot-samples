package com.superboot.controller

import com.superboot.dao.ProductRepository
import com.superboot.dto.ProductDto
import com.superboot.dto.ProductListDto
import com.superboot.dto.ResponseDto
import com.superboot.entity.Product
import com.superboot.exception.ResourceNotFoundException
import com.superboot.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ProductController
{
    @Autowired
    lateinit var productService: ProductService
    @Autowired
    lateinit var productRepository: ProductRepository

    @GetMapping(value = "/products")
    fun index(): ResponseEntity<ProductListDto>
    {
        return ResponseEntity<ProductListDto>(ProductListDto("Success", productService.getAllProducts()), HttpStatus.OK)
    }

    @PostMapping(value = "/products")
    fun create(@RequestBody product: Product): ResponseEntity<ProductDto>
    {
        try {
            var product = productService.save(product)
        }catch (ex: Exception)
        {
            return ResponseEntity.ok(ProductDto(ex.message.toString()))
        }
        return ResponseEntity.ok(ProductDto("Success", product))
    }

    @PutMapping(value = "/products/{id}")
    fun update(@RequestBody product: Product, @PathVariable id: Long): ResponseEntity<ProductDto>
    {
        var productOnDatabase =getOne(id)
        try {
            productOnDatabase.sku = product.sku
            productOnDatabase.name = product.name
            productOnDatabase.thumbail = product.thumbail
            productOnDatabase.price = product.price
            productOnDatabase.stockAvailable = product.stockAvailable
            productService.save(productOnDatabase)
        }catch (ex: Exception)
        {
            return ResponseEntity.badRequest().body(ProductDto(ex.message.toString()))
        }
        return ResponseEntity.ok(ProductDto("Success", productOnDatabase))
    }

    @GetMapping(value = "/products/{id}")
    fun show(@PathVariable id: Long): ResponseEntity<ProductDto>
    {
        return ResponseEntity.ok(ProductDto("Success", getOne(id)))
    }

    @DeleteMapping(value = "/products/{id}")
    fun delete(@PathVariable id: Long) : ResponseEntity<ResponseDto>
    {
        productService.remove(getOne(id))
        return ResponseEntity<ResponseDto>(ResponseDto("Success"), HttpStatus.OK)
    }

    private fun getOne(id: Long): Product
    {
        try {
            var product = productService.getOne(id)
            return product.get()
        }catch (ex: NoSuchElementException){
            throw  ResourceNotFoundException("Product not found")
        }
    }
}
