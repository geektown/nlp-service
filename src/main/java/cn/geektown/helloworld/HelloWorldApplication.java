package cn.geektown.helloworld;

import cn.geektown.helloworld.resources.KeywordResource;
import cn.geektown.helloworld.resources.NLPRecognitionResource;
import cn.geektown.helloworld.resources.TuringResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import cn.geektown.helloworld.resources.HelloWorldResource;
import cn.geektown.helloworld.health.TemplateHealthCheck;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(HelloWorldConfiguration configuration,
                    Environment environment) {
        final HelloWorldResource helloWorldResource = new HelloWorldResource(
            configuration.getTemplate(),
            configuration.getDefaultName()
        );

        final NLPRecognitionResource recognitionResource = new NLPRecognitionResource();
        final TemplateHealthCheck healthCheck =
            new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        environment.jersey().register(helloWorldResource);
        environment.jersey().register(recognitionResource);
        environment.jersey().register(new KeywordResource());
        environment.jersey().register(new TuringResource());

    }

}
