package smartcache;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.caucho.junit.HttpClient;

import org.junit.Assert;
import org.junit.Test;

public class SmartCacheTest
{
  @Test
  public void test() throws IOException, SQLException
  {
    CacheServer cache = new CacheServer();

    cache.start();

    Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:plugins-qa");

    conn.createStatement().executeUpdate(
      "insert into DATAITEM (id, value) values(12, 'Hello World!')");

    conn.commit();

    HttpClient client = new HttpClient(8080);

    String response = client.get("/CachedItem/12").go().body();

    Assert.assertEquals("{\"id\":12,\"value\":\"Hello World!\"}", response);

    System.err.println("SmartCacheTest.test " + response);

    cache.stop();
  }
}
