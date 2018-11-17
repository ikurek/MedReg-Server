package com.ikurek.medreg.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
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
                mappedBy = "appUserModel",
                cascade = [CascadeType.MERGE, CascadeType.ALL],
                fetch = FetchType.EAGER)
        @field:JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        var entries: List<EntryModel>
)