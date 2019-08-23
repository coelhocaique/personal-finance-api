package com.coelhocaique.finance.api.resource

import com.coelhocaique.finance.api.dto.IncomeRequestDTO
import com.coelhocaique.finance.api.dto.IncomeResponseDTO
import com.coelhocaique.finance.api.handler.IncomeHandler
import io.swagger.annotations.ApiOperation
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.switchIfEmpty
import javax.validation.Valid

@RestController
@RequestMapping("/v1/income")
class IncomeResource(private val incomeHandler: IncomeHandler) {

    @PostMapping
    fun create(@Valid @RequestBody incomeRequestDTO: IncomeRequestDTO): Mono<ResponseEntity<IncomeResponseDTO>> {
        return incomeHandler.create(Mono.just(incomeRequestDTO))
                .flatMap { Mono.just(ResponseEntity.ok(it)) }
                .doOnError { ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build<IncomeResponseDTO>()}
    }

    @GetMapping
    fun findAll(): ResponseEntity<Flux<IncomeResponseDTO>> {
        return ResponseEntity.ok(Flux.create {   })
    }

    @GetMapping("/{id}")
    fun find(@PathVariable("id") id: String): Mono<ResponseEntity<IncomeResponseDTO>> {
        return incomeHandler.findById(Mono.just(id))
                .flatMap { Mono.just(ResponseEntity.ok(it)) }
                .switchIfEmpty { Mono.just(ResponseEntity.notFound().build()) }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String): Mono<ResponseEntity<IncomeResponseDTO>> {
        return incomeHandler.findById(Mono.just(id))
                .flatMap { Mono.just(ResponseEntity.ok(it)) }
                .switchIfEmpty { Mono.just(ResponseEntity.notFound().build()) }
    }
}