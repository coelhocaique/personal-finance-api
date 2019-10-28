package com.coelhocaique.finance.api.handler

data class FetchCriteria (
        val referenceCode: String? = null,
        val referenceDate: String? = null,
        val dateFrom: String? = null,
        val dateTo: String? = null
){
    enum class SearchType{
        REFERENCE_CODE,
        REFERENCE_DATE,
        RANGE_DATE
    }

    fun searchType(): SearchType? {
        if (referenceCode != null)
            return SearchType.REFERENCE_CODE

        if (referenceDate != null)
            return SearchType.REFERENCE_DATE

        if (dateFrom != null && dateTo != null)
            return SearchType.RANGE_DATE

        return null
    }

}