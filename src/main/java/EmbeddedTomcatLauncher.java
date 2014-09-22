/**
 * Created by Hicham B.I. on 22/09/14.
 */

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextEnvironment;
import org.apache.tomcat.util.descriptor.web.ContextResource;

import javax.servlet.ServletException;
import java.io.File;

public class EmbeddedTomcatLauncher {
    public static void main(String[] args) throws Exception {
        String webappDirLocation = "src/main/webapp/";

        Tomcat tomcat = new Tomcat();
        TomcatCommandLine commandLine = new TomcatCommandLine(args);
        commandLine.parse();

        configureTomcat(tomcat, webappDirLocation, commandLine.getPort());
        launchTomcat(tomcat, commandLine.isDaemon());
    }

    private static void configureTomcat(Tomcat tomcat, String webappDirLocation, Integer port) throws ServletException {
        Context rootContext = tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());

        tomcat.setPort(port);
        rootContext.setParentClassLoader(Thread.currentThread().getContextClassLoader());

        tomcat.enableNaming();

        ContextResource contextResource = getContextResource();
        rootContext.getNamingResources().addResource(contextResource);

        ContextEnvironment environment = getContextEnvironment();
        rootContext.getNamingResources().addEnvironment(environment);
    }

    private static void launchTomcat(final Tomcat tomcat, boolean daemon) {
        Thread tomcatThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tomcat.start();
                    tomcat.getServer().await();
                } catch (LifecycleException e) {
                    e.printStackTrace();
                }
            }
        });

        tomcatThread.setDaemon(daemon);
        tomcatThread.start();
    }

    private static ContextEnvironment getContextEnvironment() {
        ContextEnvironment environment = new ContextEnvironment();
        environment.setType("java.lang.String");
        environment.setName("app/exportDir");
        environment.setValue("c:/exportdir");

        return environment;
    }

    private static ContextResource getContextResource() {
        ContextResource contextResource = new ContextResource();
        contextResource.setName("jdbc/mydb");
        contextResource.setType("javax.sql.DataSource");
        contextResource.setAuth("Container");

        contextResource.setProperty("username", "dbuser");
        contextResource.setProperty("password", "dbpassword");
        contextResource.setProperty("driverClassName", "com.mysql.jdbc.Driver");

        contextResource.setProperty("url", "jdbc:mysql://localhost:3306/mydb?characterEncoding=UTF-8");
        contextResource.setProperty("maxActive", "10");
        contextResource.setProperty("maxIdle", "3");
        contextResource.setProperty("maxWait", "10000");
        contextResource.setProperty("defaultAutoCommit", "false");
        return contextResource;
    }
}
