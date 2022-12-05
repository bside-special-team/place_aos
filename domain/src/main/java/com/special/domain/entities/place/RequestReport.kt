package com.special.domain.entities.place

import com.special.domain.entities.ReportType

data class RequestReport(
    val targetId: String,
    val type: ReportType
)
