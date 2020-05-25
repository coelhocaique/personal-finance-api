package com.coelhocaique.finance.api.handler

import java.util.*

data class FetchCriteria (
        val referenceCode: UUID? = null,
        val referenceDate: String? = null,
        val dateFrom: String? = null,
        val dateTo: String? = null,
        val id: UUID? = null,
        val parameterName: String? = null,
        val propertyName: String? = null,
        val accountId: UUID
){
    enum class SearchType {
        BY_ID,
        REFERENCE_CODE,
        REFERENCE_DATE,
        RANGE_DATE,
        PROPERTY_NAME,
        PARAMETER_NAME,
        PARAMETER_NAME_REF_DATE,
        PARAMETER_NAME_RANGE_DATE
    }

    fun searchType(): SearchType? {
        if (id != null)
            return SearchType.BY_ID

        if(referenceDate != null && parameterName != null)
            return SearchType.PARAMETER_NAME_REF_DATE

        if(dateFrom != null && dateTo != null && parameterName != null)
            return SearchType.PARAMETER_NAME_RANGE_DATE

        if (referenceCode != null)
            return SearchType.REFERENCE_CODE

        if (referenceDate != null)
            return SearchType.REFERENCE_DATE

        if (dateFrom != null && dateTo != null)
            return SearchType.RANGE_DATE

        if (propertyName != null)
            return SearchType.PROPERTY_NAME

        if(parameterName != null)
            return SearchType.PARAMETER_NAME

        return null
    }

    override fun toString(): String =
            "accountId=".plus(accountId)
                    .plus(",searchType=").plus(searchType())
                    .plus(",id=").plus(id)
                    .plus(",referenceCode=").plus(referenceCode)
                    .plus(",referenceDate=").plus(referenceDate)
                    .plus(",dateFrom=").plus(dateFrom)
                    .plus(",dateTo=").plus(dateTo)
                    .plus(",parameterName=").plus(parameterName)
                    .plus(",propertyName=").plus(propertyName)

}