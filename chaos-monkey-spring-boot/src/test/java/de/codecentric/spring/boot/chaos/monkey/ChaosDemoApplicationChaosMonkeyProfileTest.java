package de.codecentric.spring.boot.chaos.monkey;

import de.codecentric.spring.boot.chaos.monkey.configuration.ChaosMonkeySettings;
import de.codecentric.spring.boot.demo.chaos.monkey.ChaosDemoApplication;
import de.codecentric.spring.boot.chaos.monkey.component.ChaosMonkey;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Benjamin Wilms
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChaosDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"chaos.monkey" +
        ".watcher.controller=true","chaos.monkey.assaults.level=1","chaos.monkey.assaults.latencyRangeStart=10","chaos.monkey.assaults" +
        ".latencyRangeEnd=50", "chaos.monkey.assaults" +
        ".killApplicationActive=true","spring.profiles" +
        ".active=chaos-monkey"})
public class ChaosDemoApplicationChaosMonkeyProfileTest {

    @Autowired
    private ChaosMonkey chaosMonkey;

    @Autowired
    private ChaosMonkeySettings monkeySettings;

    @Autowired
    private Environment env;

    @Before
    public void setUp() {
        chaosMonkey = new ChaosMonkey(monkeySettings.getChaosMonkeyProperties(), monkeySettings.getAssaultProperties());
    }

    @Test
    public void contextLoads() {
        assertNotNull(chaosMonkey);
    }


    @Test
    public void checkChaosSettingsObject() {
        assertNotNull(monkeySettings);
    }

    @Test
    public void checkChaosSettingsValues() {
        assertThat(monkeySettings.getChaosMonkeyProperties().isEnabled(), is(false));
        assertThat(monkeySettings.getAssaultProperties().getLatencyRangeEnd(), is(50));
        assertThat(monkeySettings.getAssaultProperties().getLatencyRangeStart(), is(10));
        assertThat(monkeySettings.getAssaultProperties().getLevel(), is(1));
        assertThat(monkeySettings.getAssaultProperties().isLatencyActive(), is(true));
        assertThat(monkeySettings.getAssaultProperties().isExceptionsActive(), is(false));
        assertThat(monkeySettings.getAssaultProperties().isKillApplicationActive(), is(true));
        assertThat(monkeySettings.getWatcherProperties().isController(), is(true));
        assertThat(monkeySettings.getWatcherProperties().isRepository(), is(false));
        assertThat(monkeySettings.getWatcherProperties().isRestController(), is(false));
        assertThat(monkeySettings.getWatcherProperties().isService(), is(true));
    }
}