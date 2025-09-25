package com.cab302.teachscope.models;

import com.cab302.teachscope.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {
    @Test
    public void testConnection() {
        Connection conn = DatabaseConnection.getInstance();
        assertNotNull(conn);
    }
}
