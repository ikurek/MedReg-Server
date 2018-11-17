package com.ikurek.medreg.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "ENTRIES")
data class EntryModel (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ENTRY_ID")
        var id: Long?,

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "USER_ID")
        @field:JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        var appUserModel: AppUserModel?,

        @field:JsonProperty("disease_name")
        var diseaseName: String,

        @field:JsonProperty("disease_condition")
        var diseaseCondition: String,

        @field:JsonProperty("drugs")
        var drugs: String,

        @field:JsonProperty("start_date")
        @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        var startDate: Date,

        @field:JsonProperty("finish_date")
        @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        var finishDate: Date
)