package smartcache;

import io.baratine.web.Web;
import io.baratine.web.WebServer;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class CacheServer
{
  private WebServer _server;

  public CacheServer()
  {
    GenericApplicationContext ctx = new GenericApplicationContext();

    XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);

    xmlReader.loadBeanDefinitions(new ClassPathResource("spring.xml"));

    ctx.refresh();

    Web.bean(ctx).to(ApplicationContext.class);

    //useSpring();

    //useJdbc();

    useJpa();

    Web.include(Cache.class);

    Web.scanAutoConf();
  }

  private void useSpring()
  {
    Web.include(SpringRepositoryService.class);
  }

  private void useJdbc()
  {
    Web.property("jdbc:///items.url", "jdbc:hsqldb:file:db/sample");
    Web.property("jdbc:///items.poolSize", "4");
    Web.include(JdbcRepositoryService.class);
  }

  private void useJpa()
  {
    Web.include(JpaRepositoryService.class);
  }

  public void start(String... args)
  {
    _server = Web.start(args);
  }

  public void stop()
  {
    _server.close();
  }

  public static void main(String[] args)
  {
    CacheServer cache = new CacheServer();

    cache.start(args);
  }
}
