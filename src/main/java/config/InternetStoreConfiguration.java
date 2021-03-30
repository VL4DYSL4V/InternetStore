package config;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(value = {"config", "dao", "service"})
//@EnableWebMvc
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
