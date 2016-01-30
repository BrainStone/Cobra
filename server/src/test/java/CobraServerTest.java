import de.jackwhite20.cobra.server.CobraServer;
import de.jackwhite20.cobra.server.CobraServerFactory;

/**
 * Created by JackWhite20 on 30.01.2016.
 */
public class CobraServerTest {

    public static void main(String[] args) {

        CobraServer cobraServer = CobraServerFactory.create(new TestConfig());
        cobraServer.start();
    }
}
