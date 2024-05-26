package br.com.ada.locatecar.services;

import br.com.ada.locatecar.dtos.CarDto;
import br.com.ada.locatecar.models.CarStatus;
import br.com.ada.locatecar.models.Rent;
import br.com.ada.locatecar.repositories.CarRepository;
import br.com.ada.locatecar.repositories.CarStatusRepository;
import br.com.ada.locatecar.repositories.RentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentServiceTest {

    @InjectMocks
    private RentService rentService;

    @Mock
    private CarDto carDto;

    @Mock
    private Rent rent;

    @Mock
    private CarRepository carRepository;

    @Mock
    private RentRepository rentRepository;

    @Mock
    private CarStatusRepository carStatusRepository;

    @Mock
    private CarStatus carStatus;

    @Test
    public void shouldSaveRentACar() {
        //given - dado
        given(carStatusRepository.findById(any(Long.class))).willReturn(Optional.of(carStatus));
        given(carRepository.findById(any(Long.class))).willReturn(Optional.of(carDto));
        given(rentRepository.save(any(Rent.class))).willReturn(rent);

        //when
        rentService.rentCar(1L, 1L, LocalDateTime.now());

        //then
        then(this.rentRepository).should().save(any(Rent.class));
    }

    @Test
    public void shouldReturnARentedACar() {
        //given
        when(carStatusRepository.findById(any(Long.class))).thenReturn(Optional.of(carStatus));
        when(carRepository.findById(any(Long.class))).thenReturn(Optional.of(carDto));
        when(rentRepository.save(any(Rent.class))).thenReturn(rent);

        //when
        Rent rentedCar = rentService.rentCar(1L, 1L, LocalDateTime.now());

        //then
        verify(rentRepository).save(any(Rent.class));
        Assertions.assertThat(rentedCar).isEqualTo(rent);
    }

    @Test
    public void shouldGetRent(){
        //given
        Rent expectedRent = new Rent();
        when(rentRepository.findById(1L)).thenReturn(Optional.of(expectedRent));

        //when
        Optional<Rent> actualRent = rentService.getRent(1L);

        //then
        assertTrue(actualRent.isPresent());
        assertEquals(expectedRent, actualRent.get());
    }
}