package config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GameConfigurationTest {
    @InjectMocks
    private GameConfiguration subject;

    @Mock
    @SuppressWarnings("unused")
    private InputStream inputStream;

    @Test
    public void  getGameConfiguration_CorrectFile_Properties() throws IOException {
        final Properties properties = subject.getGameConfiguration();

        assertThat(properties ,is(notNullValue()));
    }
}
