import de.jackwhite20.cobra.server.impl.CobraConfig;
import resources.TestResource;

/**
 * Created by JackWhite20 on 30.01.2016.
 */
public class TestConfig extends CobraConfig {

    public TestConfig() {

        host("localhost");
        port(8080);
        backLog(25);

        filter(TestFilter.class);

        register(TestResource.class);
    }
}
