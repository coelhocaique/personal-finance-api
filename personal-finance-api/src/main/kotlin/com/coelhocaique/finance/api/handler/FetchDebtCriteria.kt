package com.coelhocaique.finance.api.handler

data class FetchDebtCriteria (
        val referenceCode: String? = null,
        val referenceDate: String? = null,
        val dateFrom: String? = null,
        val dateTo: String? = null
){
    enum class SearchType{
        REFERENCE_CODE,
        REFERENCE_DATE,
        RANGE_DATE,
        INVALID
    }

    fun searchType(): SearchType {
        if (referenceCode != null)
            return SearchType.REFERENCE_CODE

        if (referenceDate != null)
            return SearchType.REFERENCE_DATE

        if (dateFrom != null && dateTo != null)
            return SearchType.RANGE_DATE

        return SearchType.INVALID
    }

}