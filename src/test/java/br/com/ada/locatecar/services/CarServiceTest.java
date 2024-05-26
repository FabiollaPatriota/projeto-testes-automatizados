package br.com.ada.locatecar.services;

import br.com.ada.locatecar.dtos.CarDto;
import br.com.ada.locatecar.repositories.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private WebClient webClientMock;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpecMock;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpecMock;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    @Test
    public void shouldGetAllCars() {
        // given
        CarDto[] carArray = {new CarDto(), new CarDto()};
        doReturn(requestHeadersUriSpecMock).when(webClientMock.get());
        doReturn(requestHeadersSpecMock).when(requestHeadersUriSpecMock).uri("/api/v1/cars");
        doReturn(responseSpecMock).when(requestHeadersSpecMock).retrieve();
        when(responseSpecMock.bodyToMono(CarDto[].class)).thenReturn(Mono.just(carArray));

        try (MockedStatic<WebClient> webClientMockedStatic = mockStatic(WebClient.class)) {
            webClientMockedStatic.when(WebClient::create).thenReturn(webClientMock);

            // when
            List<CarDto> result = carService.getAllCar();

            // then
            assertThat(result).hasSize(2);
        }
    }

    @Test
    public void shouldGetCarById() {
        // given
        CarDto carDto = new CarDto();
        String carId = "1";
        doReturn(requestHeadersUriSpecMock).when(webClientMock.get());
        doReturn(requestHeadersSpecMock).when(requestHeadersUriSpecMock.uri("/api/v1/cars/{idCar}", carId));
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(CarDto.class)).thenReturn(Mono.just(carDto));

        // when
        CarDto result = carService.getCarById(carId);

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldSugarCars() {
        // given
        List<CarDto> carList = new ArrayList<>();
        doReturn(requestHeadersUriSpecMock).when(webClientMock.get());
        doReturn(requestHeadersSpecMock).when(requestHeadersUriSpecMock.uri("/api/v1/cars"));
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(CarDto[].class)).thenReturn(Mono.just(new CarDto[0]));
        when(carRepository.saveAll(anyList())).thenReturn(carList);

        // when
        List<CarDto> result = carService.sugarCars();

        // then
        assertThat(result).isEqualTo(carList);
    }

    @Test
    public void shouldSugarCar() {
        // given
        CarDto carDto = new CarDto();
        String carId = "1";
        doReturn(requestHeadersUriSpecMock).when(webClientMock.get());
        doReturn(requestHeadersSpecMock).when(requestHeadersUriSpecMock.uri("/api/v1/cars/{idCar}", carId));
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(CarDto.class)).thenReturn(Mono.just(carDto));
        when(carRepository.save(any(CarDto.class))).thenReturn(carDto);

        // when
        CarDto result = carService.sugarCar(carId);

        // then
        assertThat(result).isEqualTo(carDto);
    }

    @Test
    public void shouldGetAllAvailableCars() {
        // given
        List<CarDto> carList = new ArrayList<>();
        when(carRepository.findAllAvailableCars()).thenReturn(carList);

        // when
        List<CarDto> result = carService.getAllAvailableCars();

        // then
        assertThat(result).isEqualTo(carList);
    }

    @Test
    public void shouldCheckIfCarIsAvailable() {
        // given
        Long carId = 1L;
        when(carRepository.isCarAvailable(carId)).thenReturn(true);

        // when
        boolean result = carService.checkIfCarIsAvailable(carId);

        // then
        assertThat(result).isTrue();
    }

}
