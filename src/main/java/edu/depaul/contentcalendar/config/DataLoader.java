package edu.depaul.contentcalendar.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.h2.tools.RunScript;
import org.h2.tools.Script;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DataLoader {
    private final DataSource dataSource;
    private final String h2dbFile;

    public DataLoader(DataSource dataSource,
                      @Value("${content.h2db.file}") String h2dbFile) {
        this.dataSource = dataSource;
        this.h2dbFile = h2dbFile;
    }

    @PostConstruct
    private void postConstruct() {
        // load the database from an H2 DB export file
        try (Connection conn = dataSource.getConnection()) {
            RunScript.execute(conn, new FileReader(h2dbFile));
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void preDestroy() {
        // dump the database to an H2 DB export file
        try (Connection conn = dataSource.getConnection()) {
            Script.process(conn, h2dbFile, "", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
