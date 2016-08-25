package smartcache;

import javax.inject.Inject;

import io.baratine.config.Config;
import io.baratine.web.IncludeWeb;
import io.baratine.web.Web;
import io.baratine.web.WebBuilder;
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
    Web.include(RepositoryConfigurator.class);

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

class RepositoryConfigurator implements IncludeWeb
{
  @Inject
  Config _conf;

  @Override
  public void build(WebBuilder builder)
  {
    final String repository = _conf.get("repository");

    if ("spring-data".equals(repository)) {
      useSpring(builder);
    }
    else if ("jdbc".equals(repository)) {
      useJdbc(builder);
    }
    else if ("jpa".equals(repository)) {
      useJpa(builder);
    }
    else {
      throw new IllegalStateException();
    }
  }

  private void useSpring(WebBuilder builder)
  {
    GenericApplicationContext ctx = new GenericApplicationContext();

    XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(ctx);

    xmlReader.loadBeanDefinitions(new ClassPathResource("spring.xml"));

    ctx.refresh();

    builder.bean(ctx).to(ApplicationContext.class);

    builder.service(SpringRepositoryService.class);
  }

  private void useJdbc(WebBuilder builder)
  {
    builder.service(JdbcRepositoryService.class);
  }

  private void useJpa(WebBuilder builder)
  {
    builder.service(JpaRepositoryService.class);
  }
}
