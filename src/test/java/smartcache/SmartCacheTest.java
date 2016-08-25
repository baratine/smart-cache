package smartcache;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.caucho.junit.HttpClient;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SmartCacheTest
{

  @Before
  public void setup() throws SQLException
  {
    try {
      Connection conn
        = DriverManager.getConnection("jdbc:hsqldb:file:db/sample");

      conn.createStatement().executeUpdate(
        "insert into DATAITEM (id, value) values(12, 'Hello World!')");

      conn.commit();
    } catch (SQLIntegrityConstraintViolationException e) {

    }
  }

  @Test
  public void testSpringData() throws IOException, SQLException
  {
    CacheServer cache = new CacheServer();

    cache.start("--conf", "src/test/resources/conf-spring-data.yml");

    HttpClient client = new HttpClient(8080);

    String response = client.get("/CachedItem/12").go().body();

    Assert.assertEquals("{\"id\":12,\"value\":\"Hello World!\"}", response);

    System.err.println("SmartCacheTest.test " + response);

    cache.stop();
  }

  @Test
  public void testJdbc() throws IOException, SQLException
  {
    CacheServer cache = new CacheServer();

    cache.start("--conf", "src/test/resources/conf-jdbc.yml");

    HttpClient client = new HttpClient(8080);

    String response = client.get("/CachedItem/12").go().body();

    Assert.assertEquals("{\"id\":12,\"value\":\"Hello World!\"}", response);

    System.err.println("SmartCacheTest.test " + response);

    cache.stop();
  }

  @Test
  public void testJpa() throws IOException, SQLException
  {
    CacheServer cache = new CacheServer();

    cache.start("--conf", "src/test/resources/conf-jpa.yml");

    HttpClient client = new HttpClient(8080);

    String response = client.get("/CachedItem/12").go().body();

    Assert.assertEquals("{\"id\":12,\"value\":\"Hello World!\"}", response);

    System.err.println("SmartCacheTest.test " + response);

    cache.stop();
  }

}
