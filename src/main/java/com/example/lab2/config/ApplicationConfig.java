package com.example.lab2.config;


import com.example.lab2.math.MyFunction;
import com.example.lab2.math.log.Ln10Function;
import com.example.lab2.math.log.Ln2Function;
import com.example.lab2.math.log.Ln3Function;
import com.example.lab2.math.log.LnFunction;
import com.example.lab2.math.trig.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AspectConfig.class)
public class ApplicationConfig {

    @Bean
    public SinFunction sinFunction() {
        return new SinFunction();
    }

    @Bean
    public CosFunction cosFunction() {
        return new CosFunction(sinFunction());
    }

    @Bean
    public TgFunction tgFunction() {
        return new TgFunction(ctgFunction());
    }

    @Bean
    public CtgFunction ctgFunction() {
        return new CtgFunction(sinFunction(), cosFunction());
    }

    @Bean
    public SecFunction secFunction() {
        return new SecFunction(cosFunction());
    }

    @Bean
    public CscFunction cscFunction() {
        return new CscFunction(sinFunction());
    }

    @Bean
    public LnFunction lnFunction() {
        return new LnFunction();
    }

    @Bean
    public Ln2Function ln2Function() {
        return new Ln2Function(lnFunction());
    }

    @Bean
    public Ln3Function ln3Function() {
        return new Ln3Function(lnFunction());
    }

    @Bean
    public Ln10Function ln10Function() {
        return new Ln10Function(lnFunction());
    }

    @Bean
    public MyFunction myFunction() {
        return new MyFunction(
                sinFunction(),
                cosFunction(),
                tgFunction(),
                ctgFunction(),
                secFunction(),
                cscFunction(),
                ln2Function(),
                ln3Function(),
                ln10Function()
        );
    }

}
