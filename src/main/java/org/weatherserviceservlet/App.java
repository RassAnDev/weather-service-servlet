package org.weatherserviceservlet;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.weatherserviceservlet.servlet.WeatherServlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

    private static int getPort() {
        String port = System.getenv("PORT");
        if (port != null) {
            return Integer.valueOf(port);
        }
        return 8000;
    }

    private static String getFileContent(String fileName) throws IOException {
        Path pathToSolution = Paths.get(fileName).toAbsolutePath();
        return Files.readString(pathToSolution).trim();
    }

    public static Tomcat getApp(int port) {
        Tomcat tomcat = new Tomcat();

        tomcat.setBaseDir(System.getProperty("java.io.tmpdir"));
        tomcat.setPort(port);

        // Добавляем в контекст шаблоны
        Context ctx = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());

        tomcat.addServlet(ctx, WeatherServlet.class.getSimpleName(), new WeatherServlet());
        ctx.addServletMappingDecoded("", WeatherServlet.class.getSimpleName());

        return tomcat;
    }

    public static void main(String[] args) throws LifecycleException, SQLException, IOException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/weatherservice",
                "rassandev", "password");
        Statement statement = connection.createStatement();
        String initSql = getFileContent("init.sql");
        statement.execute(initSql);

        Tomcat app = getApp(getPort());
        app.start();
        app.getServer().await();
    }
}
