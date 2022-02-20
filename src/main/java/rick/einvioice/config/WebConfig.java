package rick.einvioice.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan("rick.einvioice.controller")
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /*資源處理器*/
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/img/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Bean
    public ITemplateResolver templateResolver() {
        // 透過此實例進行相關設定，後續用來建立模版引擎物件
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);

        // 開發階段可設定為不快取模版內容，修改模版才能即時反應變更
        resolver.setCacheable(false);
        // 搭配控制器傳回值的前置名稱
        resolver.setPrefix("classpath:/templates/");
        // 搭配控制器傳回值的後置名稱
        resolver.setSuffix(".html");
        // HTML 頁面編碼
        resolver.setCharacterEncoding("UTF-8");
        // 這是一份 HTML 文件
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }


    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        // 建立與設定模版引擎
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver);
        return engine;
    }

    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine engine) {
        // 建立ViewResolver實作物件並設置模版引擎實例
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(engine);
        // 回應內容編碼
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCache(false);
        return resolver;
    }
}