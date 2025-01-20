package com.inditex.infrastructure.config;

import com.inditex.domain.port.in.ValidateCredential;
import com.inditex.domain.port.in.GetSinglePrice;
import com.inditex.domain.port.out.PriceRepositoryPort;
import com.inditex.application.service.AuthService;
import com.inditex.infrastructure.repository.PriceRepository;
import com.inditex.infrastructure.repository.PriceRepositoryAdapter;
import com.inditex.infrastructure.security.JwtRequestFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ComponentScan(basePackages = "com.inditex.infrastructure.config")
public class AppConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PriceRepository priceRepository;

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @InjectMocks
    private AppConfig appConfig;

    @Test
    public void testPriceRepositoryPortBean() {
        // Verificar si el bean PriceRepositoryPort se crea correctamente
        PriceRepositoryPort priceRepositoryPortBean = applicationContext.getBean(PriceRepositoryPort.class);
        assertThat(priceRepositoryPortBean).isNotNull();
        assertThat(priceRepositoryPortBean).isInstanceOf(PriceRepositoryAdapter.class);
    }

    @Test
    public void testPriceServicePortBean() {
        // Verificar si el bean PriceServicePort se crea correctamente
        GetSinglePrice priceServiceBean = applicationContext.getBean(GetSinglePrice.class);
        assertThat(priceServiceBean).isNotNull();
        assertThat(priceServiceBean).isInstanceOf(com.inditex.application.service.PriceService.class);
    }

    @Test
    public void testAuthServicePortBean() {
        // Verificar si el bean AuthServicePort se crea correctamente
        ValidateCredential validateCredentialBean = applicationContext.getBean(ValidateCredential.class);
        assertThat(validateCredentialBean).isNotNull();
        assertThat(validateCredentialBean).isInstanceOf(AuthService.class);
    }

    @Test
    public void testJwtRequestFilterBean() {
        // Verificar si el bean JwtRequestFilter se crea correctamente
        JwtRequestFilter jwtRequestFilterBean = applicationContext.getBean(JwtRequestFilter.class);
        assertThat(jwtRequestFilterBean).isNotNull();
    }

    @Test
    public void testModelMapperBean() {
        // Verificar si el bean ModelMapper se crea correctamente
        ModelMapper modelMapperBean = applicationContext.getBean(ModelMapper.class);
        assertThat(modelMapperBean).isNotNull();
    }

    @Test
    public void testOpenApiConfigBean() {
        // Verificar si el bean OpenApiConfig se crea correctamente
        OpenApiConfig openApiConfigBean = applicationContext.getBean(OpenApiConfig.class);
        assertThat(openApiConfigBean).isNotNull();
    }

    @Test
    public void testSecurityConfigBean() {
        // Verificar si el bean SecurityConfig se crea correctamente
        SecurityConfig securityConfigBean = applicationContext.getBean(SecurityConfig.class);
        assertThat(securityConfigBean).isNotNull();
    }


    @BeforeEach
    public void setUp() {
        // Configura el PriceRepository mock antes de cada prueba, si es necesario
        priceRepositoryPort = mock(PriceRepositoryPort.class);
    }
}
