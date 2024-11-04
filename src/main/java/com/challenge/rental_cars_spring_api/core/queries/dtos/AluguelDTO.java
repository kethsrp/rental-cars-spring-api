package com.challenge.rental_cars_spring_api.core.queries.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class AluguelDTO {
    private LocalDate dataAluguel;
    private String modeloCarro;
    private int kmCarro;
    private String nomeCliente;
    private String telefoneCliente;
    private LocalDate dataDevolucao;
    private BigDecimal valor;
    private String pago;
}