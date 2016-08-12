package smartcache;

import javax.inject.Inject;

import io.baratine.service.Result;
import io.baratine.service.Service;
import io.baratine.service.Workers;

import data.DataItem;
import data.DataItemRepository;

@Service()
@Workers(4)
public class SpringRepositoryService implements RepositoryService
{
  @Inject
  private DataItemRepository _repository;

  @Override
  public void getOne(long id, Result<DataItem> result)
  {
    result.ok(_repository.getOne(id));
  }
}
