package com.kittopmall.conn;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
@DisplayName("DB 연결 테스트")
public class DBConnTest {
    
    @Autowired
    private DataSource dataSource;

    @DisplayName("DB 연결 테스트 연결 되면 테스트 성공")
    @Test
    void givenDataSource_whenGettingConnection_thenReturnsConnection() {
        //Given
        Assertions.assertThat(dataSource).isNotNull();

        //When
        try (Connection conn = dataSource.getConnection()) {
            if (dataSource instanceof HikariDataSource) {
                HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
                System.out.println("JDBC URL: " + hikariDataSource.getJdbcUrl());
                System.out.println("DB User: " + hikariDataSource.getUsername());
                System.out.println("Driver Class: " + hikariDataSource.getDriverClassName());
            }
            //Then
            Assertions.assertThat(conn).isNotNull();
        } catch (SQLException e) {
            Assertions.fail("DB 연결에 실패했습니다.", e);
        }
    }
}
