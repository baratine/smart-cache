package smartcache;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.baratine.service.OnInit;
import io.baratine.service.Result;
import io.baratine.service.Service;

import data.DataItem;

@Service
public class JpaRepositoryService implements RepositoryService
{
  private EntityManager _manager;

  @OnInit
  public void init()
  {
    EntityManagerFactory factory
      = Persistence.createEntityManagerFactory("data");

    _manager = factory.createEntityManager();
  }

  @Override
  public void findOne(long id, Result<DataItem> result)
  {
    DataItem data = _manager.find(DataItem.class, id);

    result.ok(data);
  }
}
