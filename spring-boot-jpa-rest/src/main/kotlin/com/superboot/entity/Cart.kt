package com.superboot.entity

import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name="cart")
class Cart(
        var userId: Int?,
        var code: String,
        var total: Int = 0

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(mappedBy = "cart", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY, orphanRemoval = true)
    var cartItems: Set<CartItem> = emptySet()

    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date = Date()

    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date = Date()

    constructor():this(null, "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cart

        if (userId != other.userId) return false
        if (code != other.code) return false
        if (total != other.total) return false
        if (id != other.id) return false
        if (cartItems != other.cartItems) return false

        return true
    }
}