package br.com.ada.locatecar.services;

import br.com.ada.locatecar.models.Devolution;
import br.com.ada.locatecar.models.Rent;
import br.com.ada.locatecar.repositories.DevolutionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DevolutionServiceTest {

    @InjectMocks
    private DevolutionService devolutionService;

    @Mock
    private DevolutionRepository devolutionRepository;

    @Mock
    private Devolution devolution;

    @Test
    public void shouldSaveDevolveCar(){
        //given
        when(devolutionRepository.save(Mockito.any(Devolution.class))).thenReturn(devolution);

        //when
        devolutionService.devolveCar(new Rent(), LocalDateTime.now(), new BigDecimal(1000));

        //then
        verify(devolutionRepository).save(any(Devolution.class));
    }

    @Test
    public void shouldReturnADevolutionOfRent(){
        //given - dado
        when(devolutionRepository.save(Mockito.any(Devolution.class))).thenReturn(devolution);

        //when
        Devolution newDevolution = devolutionService.devolveCar(new Rent(), LocalDateTime.now(), new BigDecimal(1000));

        //then
        verify(devolutionRepository).save(any(Devolution.class));
        Assertions.assertThat(newDevolution).isEqualTo(devolution);
    }


}