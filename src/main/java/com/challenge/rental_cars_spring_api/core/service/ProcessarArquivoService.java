package com.challenge.rental_cars_spring_api.core.service;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.domain.Carro;
import com.challenge.rental_cars_spring_api.core.domain.Cliente;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProcessarArquivoService {

    private static final Logger log = LoggerFactory.getLogger(ProcessarArquivoService.class);
    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;
    private final AluguelRepository aluguelRepository;

    public void processarArquivoRTN(File file) throws IOException {
        List<String> linhas = Files.readAllLines(file.toPath());
        for (String linha : linhas) {
            int carroId = Integer.parseInt(linha.substring(0, 2).trim());
            int clienteId = Integer.parseInt(linha.substring(2, 4).trim());
            LocalDate dataAluguel = LocalDate.parse(linha.substring(4, 12), DateTimeFormatter.ofPattern("yyyyMMdd"));
            LocalDate dataDevolucao = LocalDate.parse(linha.substring(12, 20), DateTimeFormatter.ofPattern("yyyyMMdd"));

            Optional<Carro> carroOpt = carroRepository.findById((long) carroId);
            Optional<Cliente> clienteOpt = clienteRepository.findById((long) clienteId);

            if (carroOpt.isPresent() && clienteOpt.isPresent()) {
                Carro carro = carroOpt.get();
                Cliente cliente = clienteOpt.get();
                long diasAlugados = ChronoUnit.DAYS.between(dataAluguel, dataDevolucao);
                BigDecimal valor = carro.getVlrDiaria().multiply(BigDecimal.valueOf(diasAlugados));

                Aluguel aluguel = new Aluguel();
                aluguel.setCarro(carro);
                aluguel.setCliente(cliente);
                aluguel.setDataAluguel(dataAluguel);
                aluguel.setDataDevolucao(dataDevolucao);
                aluguel.setValor(valor);
                aluguel.setPago(false);

                aluguelRepository.save(aluguel);
            } else {
                if(!carroOpt.isPresent()) {
                    log.warn("Carro ID {} não encontrado no banco de dados", carroId);
                }
                if(!clienteOpt.isPresent()) {
                    log.warn("Cliente ID {} encontrado no banco de dados", clienteId);
                }
            }
        }
    }
}