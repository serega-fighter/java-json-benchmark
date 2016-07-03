package com.github.fabienrenaud.jjb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.github.fabienrenaud.jjb.model.UserCollection;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Created by frenaud on 7/3/16.
 */
public class JsonSourceTest {

    private static final int MARGIN_ERROR = 10; // in percent
    private static final ObjectMapper JACKSON = new ObjectMapper();

    static {
        JACKSON.registerModule(new AfterburnerModule());
    }

    @Test
    public void populateUserCollection_1k() throws IOException {
        populateUserCollection(1000);
    }

    @Test
    public void populateUserCollection_10k() throws IOException {
        populateUserCollection(10000);
    }

    @Test
    public void populateUserCollection_100k() throws IOException {
        populateUserCollection(100000);
    }

    @Test
    public void populateUserCollection_1m() throws IOException {
        populateUserCollection(1000000);
    }

    private void populateUserCollection(int size) throws IOException {
        UserCollection uc = new UserCollection();
        int approxSize = JsonSource.populateUserCollection(uc, size);
        String v = JACKSON.writeValueAsString(uc);
        int realSize = v.getBytes().length;
        int maxAllowedSIze = realSize + (realSize / MARGIN_ERROR);
        int minAllowedSIze = realSize - (realSize / MARGIN_ERROR);

        assertTrue("Generated payload is bigger than the maximum expected. Got: " + approxSize + ". Max expected: " + maxAllowedSIze, approxSize <= maxAllowedSIze);
        assertTrue("Generated payload is smaller than the minimum expected. Got: " + approxSize + ". Min expected: " + minAllowedSIze, approxSize >= minAllowedSIze);
    }
}
