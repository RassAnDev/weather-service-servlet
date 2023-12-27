package org.weatherserviceservlet;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.weatherserviceservlet.servlet.WeatherServlet;

import java.io.File;

public class App {

    private static int getPort() {
        String port = System.getenv("PORT");
        if (port != null) {
            return Integer.valueOf(port);
        }
        return 8000;
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

    public static void main(String[] args) throws LifecycleException {
            Tomcat app = getApp(getPort());
            app.start();
            app.getServer().await();
    }
}
