package com.challenge.rental_cars_spring_api.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "aluguel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aluguel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carro_id")
    private Carro carro;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "data_aluguel")
    private LocalDate dataAluguel;

    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "pago")
    private boolean pago;
}

