package com.challenge.rental_cars_spring_api.access;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.queries.dtos.AluguelDTO;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/alugueis")
@RequiredArgsConstructor
public class AluguelRestController {

    private final AluguelRepository aluguelRepository;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista com os alugueis encontrados.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AluguelDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
    public ResponseEntity<List<AluguelDTO>> listarAlugueis() {
        List<Aluguel> aluguels = aluguelRepository.findAll();
        BigDecimal totalNaoPago = BigDecimal.ZERO;
        List<AluguelDTO> dtos = new ArrayList<>();

        for (Aluguel aluguel : aluguels) {
            AluguelDTO dto = new AluguelDTO();
            dto.setDataAluguel(aluguel.getDataAluguel());
            dto.setModeloCarro(aluguel.getCarro().getModelo());
            dto.setKmCarro(aluguel.getCarro().getKm());
            dto.setNomeCliente(aluguel.getCliente().getNome());
            dto.setTelefoneCliente(formatarTelefone(aluguel.getCliente().getTelefone()));
            dto.setDataDevolucao(aluguel.getDataDevolucao());
            dto.setValor(aluguel.getValor());
            dto.setPago(aluguel.isPago() ? "SIM" : "NÃO");

            if (!aluguel.isPago()) {
                totalNaoPago = totalNaoPago.add(aluguel.getValor());
            }

            dtos.add(dto);
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    private String formatarTelefone(String telefone) {
        return telefone.replaceFirst("(\\d{2})(\\d{2})(\\d{5})(\\d{4})", "+$1($2)$3-$4");
    }
}