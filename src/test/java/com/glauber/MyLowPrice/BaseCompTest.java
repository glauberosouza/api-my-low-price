package com.glauber.MyLowPrice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MyLowPriceApplication.class)
@ExtendWith({SpringExtension.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/db/sql/reset.sql")
public class BaseCompTest {
    @Autowired
    private DataSource dataSource;

    @BeforeAll
    void initDB() {
        var databaseSeeder = new ResourceDatabasePopulator();
        databaseSeeder
                .addScript(new ClassPathResource("db/sql/insert_into_product.sql"));
        databaseSeeder.execute(dataSource);
    }

}
