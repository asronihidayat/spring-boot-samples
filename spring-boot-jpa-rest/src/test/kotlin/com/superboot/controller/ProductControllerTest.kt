package com.superboot.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.superboot.SuperbootApplication
import com.superboot.dao.ProductRepository
import com.superboot.dto.ProductDto
import com.superboot.dto.ProductListDto
import com.superboot.dto.ResponseDto
import com.superboot.entity.Product
import com.superboot.service.ProductService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [SuperbootApplication::class])
@AutoConfigureMockMvc
@TestPropertySource(locations = arrayOf("classpath:application.properties"))
class ProductControllerTest
{
    @MockBean
    lateinit var productService: ProductService

    @MockBean
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var product: Product

    @Test
    fun `get all products`()
    {
        product = Product("SKU123", "Product Sample 1", "1.jpg", 10000, 10)
        var products: List<Product> = emptyList()
        products += product
        given(productService.getAllProducts()).willReturn(products)

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(ProductListDto("Success", products))))
    }

    @Test
    fun `create product`()
    {
        var product = Product("SKU123", "Product Sample 1", "1.jpg", 10000, 10)
        mockMvc.perform(MockMvcRequestBuilders
                .post("/products")
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(ProductDto("Success", product))))
        verify(productService, times(1)).save(product)
    }

    @Test
    fun `update product`()
    {
        var product = Product("SKU123", "Product Sample 1", "1.jpg", 10000, 10)
        var productWillUpdate: Product = Product("SKU345", "Product Sample 1", "1.jpg", 10000, 10)
        product.id = 10
        productWillUpdate.id = 10
        var products = Optional.of(product)
        given(productService.getOne(10)).willReturn(products)

        mockMvc.perform(MockMvcRequestBuilders
                .put("/products/10")
                .content(objectMapper.writeValueAsString(productWillUpdate))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(ProductDto("Success", productWillUpdate))))
        verify(productService, times(1)).getOne(10)
        verify(productService, times(1)).save(product)
    }

    @Test
    fun `show product when product is exist`()
    {

        var product = Product("SKU123", "Product Sample 1", "1.jpg", 10000, 10)
        product.id = 10
        var products = Optional.of(product)
        given(productService.getOne(10)).willReturn(products)
        mockMvc.perform(MockMvcRequestBuilders
                .get("/products/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(ProductDto("Success", product))))
        verify(productService, times(1)).getOne(10)
    }

    @Test
    fun `show product when invalid product id`()
    {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/products/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(ResponseDto("Product not found"))))
        verify(productService, times(1)).getOne(123)
    }

    @Test
    fun `delete product with id`()
    {
        var product = Product("SKU123", "Product Sample 1", "1.jpg", 10000, 10)
        product.id = 10
        var products = Optional.of(product)
        given(productService.getOne(10)).willReturn(products)
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/products/10"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(ResponseDto("Success"))))
        verify(productService, times(1)).getOne(10)
        verify(productService, times(1)).remove(product)
    }
}