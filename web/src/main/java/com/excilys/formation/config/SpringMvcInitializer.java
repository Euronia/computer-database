package com.excilys.formation.config;

import javax.servlet.ServletRegistration;

import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { SpringWebConfig.class,
                SpringPersistenceConfig.class,
                SpringServiceConfig.class,
                SpringSecurityConfig.class,
                SpringCliConfig.class,
                RepositoryRestMvcConfiguration.class
        };
    }
    
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
    
    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
    
    @Override
    protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        DispatcherServlet serv = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        serv.setThrowExceptionIfNoHandlerFound(true);
        return serv;
    }
    
    @Override
    public void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }
}