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

    Web.include(SpringRepositoryServiceImpl.class);
    Web.include(Cache.class);

    Web.scanAutoConf();
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
