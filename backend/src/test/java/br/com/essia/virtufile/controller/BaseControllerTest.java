package br.com.essia.virtufile.controller;

import br.com.essia.virtufile.BaseTest;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
class BaseControllerTest extends BaseTest {

    private static final String TEST_DATABASE_IMAGE = "mysql:8.0.26";
    private static final String TEST_DATABASE_NAME = "essia";
    private static final String TEST_DATABASE_USER = "root";
    private static final String TEST_DATABASE_PWD = "password";

    @Autowired
    protected MockMvc mockMvc;


    @Container
    private static MySQLContainer<?> mysqlContainer = new MySQLContainer<>(TEST_DATABASE_IMAGE)
            .withDatabaseName(TEST_DATABASE_NAME)
            .withUsername("root")
            .withPassword("password")
            .withExposedPorts(3306);

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        String jdbcUrl = String.format("jdbc:mysql://localhost:%d/%s", mysqlContainer.getFirstMappedPort(), TEST_DATABASE_NAME);
        registry.add("spring.datasource.url", () -> jdbcUrl);
        registry.add("spring.datasource.username", () -> TEST_DATABASE_USER);
        registry.add("spring.datasource.password", () -> TEST_DATABASE_PWD);
    }

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/sql/init.sql"));
        }
    }
}
