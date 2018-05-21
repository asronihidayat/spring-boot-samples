package com.superboot.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener::class)
class Product(
        var sku: String,
        var name: String,
        var thumbail: String,
        var price: Int,
        var stockAvailable: Int
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date = Date()

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Date = Date()

    constructor():this(
             "","","", 0, 0
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (sku != other.sku) return false
        if (name != other.name) return false
        if (thumbail != other.thumbail) return false
        if (price != other.price) return false
        if (stockAvailable != other.stockAvailable) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false
        if (id != other.id) return false

        return true
    }
}

