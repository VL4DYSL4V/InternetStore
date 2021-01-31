package config;

import formatter.StringToLocalDateFormatter;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@Configuration
@ComponentScan(value = {"config", "dao", "service"})
//@EnableWebMvc
@EnableAspectJAutoProxy
@Import(value = {
        DatabaseConfig.class,
        SecurityConfig.class
})
public class InternetStoreConfiguration{

//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.ALL);
//    }
//
//    @Bean
//    public ContentNegotiatingViewResolver contentNegotiatingViewResolver(
//            ContentNegotiationManager contentNegotiationManager){
//        ContentNegotiatingViewResolver contentNegotiatingViewResolver =
//                new ContentNegotiatingViewResolver();
//        contentNegotiatingViewResolver.setContentNegotiationManager(contentNegotiationManager);
//        List<ViewResolver> viewResolvers = new ArrayList<>(5);
//        viewResolvers.add(new BeanNameViewResolver());
//        viewResolvers.add(internalResourceViewResolver());
//        contentNegotiatingViewResolver.setViewResolvers(viewResolvers);
//        return contentNegotiatingViewResolver;
//    }

//    @Bean
//    public InternalResourceViewResolver internalResourceViewResolver(){
//        InternalResourceViewResolver internalResourceViewResolver =
//                new InternalResourceViewResolver();
//        internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
//        internalResourceViewResolver.setSuffix(".jsp");
//        return internalResourceViewResolver;
//    }
//
//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor(){
//        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//        localeChangeInterceptor.setParamName("lang");
//        return localeChangeInterceptor;
//    }
//
//    @Bean
//    public LocaleResolver localeResolver(){
//        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
//        localeResolver.setDefaultLocale(new Locale("en"));
//        return localeResolver;
//    }
//
//    @Bean
//    public MessageSource messageSource(){
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("messages_en.properties");
//        return messageSource;
//    }


//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new StringToLocalDateFormatter());
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor());
//    }
}
