package smartcache;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.caucho.junit.HttpClient;

import org.junit.Assert;
import org.junit.Test;

public class SmartCacheTest
{
  @Test
  public void test() throws IOException, SQLException
  {
    Logger logger = Logger.getLogger("");
    logger.setLevel(Level.OFF);

    logger = Logger.getLogger("com.caucho.v5.subsystem");
    logger.setLevel(Level.OFF);

    CacheServer cache = new CacheServer();

    cache.start();

    try {
      Connection conn
        = DriverManager.getConnection("jdbc:hsqldb:file:db/sample");

      conn.createStatement().executeUpdate(
        "insert into DATAITEM (id, value) values(12, 'Hello World!')");

      conn.commit();
    } catch (SQLIntegrityConstraintViolationException e) {

    }

    HttpClient client = new HttpClient(8080);

    String response = client.get("/CachedItem/12").go().body();

    Assert.assertEquals("{\"id\":12,\"value\":\"Hello World!\"}", response);

    System.err.println("SmartCacheTest.test " + response);

    cache.stop();
  }
}
