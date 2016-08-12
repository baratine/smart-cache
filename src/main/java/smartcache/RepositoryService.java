package smartcache;

import io.baratine.service.Api;
import io.baratine.service.Result;

import data.DataItem;

@Api
public interface RepositoryService
{
  void findOne(long id, Result<DataItem> result);
}
