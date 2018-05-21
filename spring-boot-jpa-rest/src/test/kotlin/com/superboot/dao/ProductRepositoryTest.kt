package com.superboot.dao

import com.superboot.entity.Product
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner
import org.assertj.core.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest

@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var productRepository: ProductRepository

    @Test
    fun testFindAllProducts()
    {
        var product: Product = Product("SKU123", "Product Sample 1", "1.jpg", 10000, 10)
        entityManager.persist(product)
        entityManager.flush()

        val products = productRepository.findAll()
        val productFirst =  products.first()

        assert(products.count() == 1)
        assertThat(productFirst.sku).isEqualTo(product.sku)
        assertThat(productFirst.name).isEqualTo(product.name)
        assertThat(productFirst.thumbail).isEqualTo(product.thumbail)
        assertThat(productFirst.price).isEqualTo(product.price)
        assertThat(productFirst.createdAt).isEqualTo(product.updatedAt)
    }


    @Test
    fun testUpdateProduct()
    {
        val productSample: Product = Product("SKU123", "Product Sample 1", "1.jpg", 10000, 10)
        entityManager.persist(productSample)
        entityManager.flush()

        val newName = "Product sample 2"
        val product = productRepository.findAll().first()
        product.name = newName
        productRepository.save(product)

        assertThat(productRepository.findAll().first().name).isEqualTo(newName)
    }

}