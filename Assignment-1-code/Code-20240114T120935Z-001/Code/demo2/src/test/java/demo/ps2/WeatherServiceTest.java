package demo.ps2;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {
	
	@Mock
	WeatherApiClient mockWeatherApiClient; 
	
	@Spy
	Logger mockLogger; 

	@Spy
	List<String> mockHistory = new ArrayList<String>();

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

    	@Test
    	public void givenSuccessfulFetchForBerlin_whenGetWeatherForecast_ThenReturnRainy() {
		// 1. Arrange
		// 1.1. Create an instance of WeatherService with mocks
		WeatherService weatherService = new WeatherService(mockWeatherApiClient, mockLogger, mockHistory);

		// 1.2. Stubbing - Define behavior for mockApiClient
		when(mockWeatherApiClient.fetchWeather("Berlin")).thenReturn("Rainy");
		
		// 2. Action
		// 2.1. Call the method under test
		String forecast = weatherService.getWeatherForecast("Berlin");
		
		// 3. Assertion
		// 3.1. Verify interactions
		verify(mockWeatherApiClient).fetchWeather("Berlin");
		verify(mockLogger).log("Weather data retrieved for Berlin");
		verify(mockLogger, never()).error(anyString());
		verify(mockHistory).add("Berlin");

		// 3.2. Assert the result
		assertEquals("Rainy", forecast);
    }

    @Test
    public void givenFailedFetchForBerlin_whenGetWeatherForecast_ThenReturnNull() {
	    // 1. Arrange
	    // 1.1. Create an instance of WeatherService with mocks
	    WeatherService weatherService = new WeatherService(mockWeatherApiClient, mockLogger, mockHistory);

	    // 1.2. Stubbing - Define failed behavior for mockApiClient
	    when(mockWeatherApiClient.fetchWeather("Berlin")).thenReturn("");

	    // 2. Action
	    // 2.1. Call the method under test
	    String forecast = weatherService.getWeatherForecast("Berlin");

	    // 3. Assertion
	    // 3.1. Verify interactions
	    verify(mockWeatherApiClient).fetchWeather("Berlin");
	    verify(mockLogger, never()).log(anyString());
	    verify(mockLogger).error("Weather data not found for Berlin");
	    verify(mockHistory, never()).add("Berlin");

	    // 3.2. Asserts the result
	    assertNull(forecast);
    }


}

