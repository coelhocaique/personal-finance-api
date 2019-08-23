package com.coelhocaique.finance.api.resource

import com.coelhocaique.finance.api.dto.DiscountResponseDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v2/income/{incomeId}/discount")
class DiscountResource {

    @GetMapping("/{id}")
    fun find(@PathVariable("id") id: String): Mono<ResponseEntity<DiscountResponseDTO>> {
        return Mono.create {  ResponseEntity.ok(DiscountResponseDTO()) }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String): Mono<ResponseEntity<DiscountResponseDTO>> {
        return Mono.create {  ResponseEntity.ok(DiscountResponseDTO()) }
    }
}