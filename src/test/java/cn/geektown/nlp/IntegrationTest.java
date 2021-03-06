package cn.geektown.nlp;

import cn.geektown.nlp.api.Saying;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest {

    private static final String TMP_FILE = createTempFile();
    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-example.yml");

    @ClassRule
    public static final DropwizardAppRule<HelloWorldConfiguration> RULE = new DropwizardAppRule<>(
            HelloWorldApplication.class, CONFIG_PATH);

    private Client client;


    @Before
    public void setUp() throws Exception {
        client = ClientBuilder.newClient();
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }

    private static String createTempFile() {
        try {
            return File.createTempFile("test-example", null).getAbsolutePath();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void testHelloWorld() throws Exception {
        final Optional<String> name = Optional.of("Dr. IntegrationTest");
        final Saying saying = client.target("http://localhost:" + RULE.getLocalPort() + "/hanlp/hello-world")
                .queryParam("name", name.get())
                .request()
                .get(Saying.class);
        assertThat(saying.getContent()).isEqualTo(RULE.getConfiguration().buildTemplate().render(name));
    }

    @Test
    public void testKeyword() throws Exception {
        final Optional<String> name = Optional.of("英国在哪里？");
        final List<Map> reg = client.target("http://localhost:" + RULE.getLocalPort() + "/hanlp/keyword")
                .queryParam("corpus", name.get())
                .request()
                .get(List.class);
        System.out.println(reg.get(0));
        //assertThat(reg.get(0).getWord()).isEqualTo("英国");
    }

    @Test
    public void testPinyin() throws Exception {
        final Optional<String> name = Optional.of("王昌龄-出塞");
        final String pinyin = client.target("http://localhost:" + RULE.getLocalPort() + "/hanlp/pinyinconverter")
                .queryParam("corpus", name.get())
                .request()
                .get(String.class);
        System.out.println(pinyin);
    }

}