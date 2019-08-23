package com.coelhocaique.finance.api.mapper

import com.coelhocaique.finance.api.dto.IncomeRequestDTO
import com.coelhocaique.finance.api.dto.IncomeResponseDTO
import com.coelhocaique.finance.core.dto.IncomeDTO
import com.coelhocaique.finance.core.enums.Owner
import reactor.core.publisher.Mono
import java.time.LocalDateTime

object IncomeMapper {

    fun toIncomeDTO(incomeRequestDTO: IncomeRequestDTO) =
            Mono.create<IncomeDTO> { IncomeDTO(
                    grossAmount = incomeRequestDTO.grossAmount(),
                    netAmount = incomeRequestDTO.netAmount(),
                    extraAmount = incomeRequestDTO.totalExtraAmount(),
                    description = incomeRequestDTO.description!!,
                    owner = incomeRequestDTO.owner,
                    date = incomeRequestDTO.date!!,
                    referenceMonth = incomeRequestDTO.referenceMonth!!,
                    referenceYear = incomeRequestDTO.referenceYear!!,
                    discountAmount = incomeRequestDTO.totalDiscount(),
                    companyName = incomeRequestDTO.companyName!!
            ) }


    fun toIncomeResponseDTO(incomeDTO: IncomeDTO) =
            Mono.create<IncomeResponseDTO> {
                IncomeResponseDTO(id = incomeDTO.id!!,
                        grossAmount = incomeDTO.grossAmount,
                        netAmount = incomeDTO.netAmount,
                        extraAmount = incomeDTO.extraAmount,
                        description = incomeDTO.description,
                        owner = incomeDTO.owner,
                        date = incomeDTO.date,
                        referenceMonth = incomeDTO.referenceMonth,
                        referenceYear = incomeDTO.referenceYear,
                        discountAmount = incomeDTO.discountAmount,
                        companyName = incomeDTO.companyName,
                        creationDate = incomeDTO.creationDate!!)
            }
}