package com.ikurek.medreg.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name = "APP_USERS")
data class AppUserModel(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "APP_USER_ID")
        var id: Long?,

        @Column(name = "EMAIL")
        var email: String,

        @Column(name = "PASSWORD")
        @field:JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        var password: String,

        @ElementCollection(targetClass = EntryModel::class)
        @OneToMany(
                cascade = [CascadeType.ALL],
                orphanRemoval = true,
                mappedBy = "appUserModel")
        @field:JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        var entries: List<EntryModel>
)