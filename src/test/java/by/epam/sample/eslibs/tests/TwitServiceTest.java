package by.epam.sample.eslibs.tests;

import org.apache.log4j.BasicConfigurator;
import org.junit.*;
import by.epam.sample.eslibs.domain.Twit;
import by.epam.sample.eslibs.service.TwitService;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class TwitServiceTest {

    private TwitService twitService = new TwitService();
    private Twit t1;
    private Twit t2;
    private Twit t3;

    @BeforeClass
    public static void setUpClass() {
        BasicConfigurator.configure();
    }

    @Before
    public void setup() throws IOException {
        t1 = new Twit(UUID.randomUUID().toString(), "t1", "u1");
        t2 = new Twit(UUID.randomUUID().toString(), "t2", "u2");
        t3 = new Twit(UUID.randomUUID().toString(), "t2", "u2");

        twitService.save(Arrays.asList(t1, t2, t3));
    }

    @After
    public void cleanUp() throws IOException {
        twitService.deleteAll();
    }

    @Test
    public void findAll() throws IOException {
        Assert.assertEquals(3, twitService.findAll().size());
    }

    @Test
    public void testFindById() throws IOException {
        Assert.assertNotNull(twitService.findById(t1.getGuid()));
    }

    @Test
    public void testFindByText() throws IOException {
        Assert.assertEquals(2, twitService.findByText(t2.getText()).size());
    }

    @Test
    public void testFindByUserId() throws IOException {
        Assert.assertEquals(1, twitService.findByUserId(t1.getUid()).size());
    }


}
