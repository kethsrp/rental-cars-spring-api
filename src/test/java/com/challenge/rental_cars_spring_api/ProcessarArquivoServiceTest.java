package com.challenge.rental_cars_spring_api;

import com.challenge.rental_cars_spring_api.core.domain.Aluguel;
import com.challenge.rental_cars_spring_api.core.service.ProcessarArquivoService;
import com.challenge.rental_cars_spring_api.infrastructure.repositories.AluguelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessarArquivoServiceTest {

    @Autowired
    private ProcessarArquivoService processarArquivoService;

    @Autowired
    private AluguelRepository aluguelRepository;

    @Test
    public void testProcessarArquivoRTN() throws IOException {
        File file = new File("src/main/resources/RentReport.rtn");
        processarArquivoService.processarArquivoRTN(file);

        List<Aluguel> alugueis = aluguelRepository.findAll();
        assertFalse(alugueis.isEmpty());
    }
}
