package com.superboot.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "cart_items")
@EntityListeners(AuditingEntityListener::class)
class CartItem()
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    lateinit var cart: Cart

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    lateinit var product: Product

    var quantity: Int = 1

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date = Date()

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date = Date()

    constructor(cart: Cart, product: Product, quantity: Int):this(){
        this.cart = cart
        this.product = product
        this.quantity = quantity
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CartItem

        if (id != other.id) return false
        if (cart != other.cart) return false
        if (product != other.product) return false
        if (quantity != other.quantity) return false

        return true
    }
}