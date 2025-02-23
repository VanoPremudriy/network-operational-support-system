package ru.mirea.network.operational.support.system.back.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.mirea.network.operational.support.system.common.api.BodyDTO;
import ru.mirea.network.operational.support.system.common.api.ErrorDTO;
import ru.mirea.network.operational.support.system.route.api.route.calculate.CalculateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRq;
import ru.mirea.network.operational.support.system.route.api.route.create.CreateRouteRs;

import java.io.IOException;
import java.net.URL;
import java.util.stream.Stream;

import static ru.mirea.network.operational.support.system.back.dictionary.Constant.CREATE_ROUTE_ENDPOINT;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateRouteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    private static MockWebServer mockWebServer;

    @BeforeAll
    public static void beforeAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    public static void afterAll() throws IOException {
        mockWebServer.close();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("calculate.route.url", () ->
        {
            try {
                return new URL("http", mockWebServer.getHostName(), mockWebServer.getPort(), "").toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void calculateSuccess() throws Exception {
        String rs = jsonMapper.writeValueAsString(CreateRouteRs.builder()
                .success(true)
                .body(BodyDTO.builder().build())
                .build());

        mockWebServer.setDispatcher(new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) throws InterruptedException {
                return new MockResponse()
                        .setHeader("content-type", MediaType.APPLICATION_JSON_VALUE)
                        .setResponseCode(HttpStatus.OK.value())
                        .setBody(rs);
            }
        });

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ROUTE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(CreateRouteRq.builder()
                                .startingPoint("start")
                                .destinationPoint("destination")
                                .build())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(rs));
    }

    @ParameterizedTest
    @MethodSource("errorSatus")
    void calculateError(HttpStatus status) throws Exception {
        mockWebServer.setDispatcher(new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) throws InterruptedException {
                return new MockResponse()
                        .setHeader("content-type", MediaType.APPLICATION_JSON_VALUE)
                        .setResponseCode(status.value());
            }
        });

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ROUTE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonMapper.writeValueAsString(CreateRouteRq.builder()
                                .startingPoint("start")
                                .destinationPoint("destination")
                                .build())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonMapper.writeValueAsString(CreateRouteRs.builder()
                        .success(false)
                        .error(ErrorDTO.builder()
                                .title("Непредвиденная ошибка")
                                .build())
                        .build())));
    }

    private static Stream<HttpStatus> errorSatus() {
        return Stream.of(HttpStatus.BAD_REQUEST, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
