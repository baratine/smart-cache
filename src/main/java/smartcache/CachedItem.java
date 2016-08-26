package smartcache;

import javax.inject.Inject;

import io.baratine.service.OnLoad;
import io.baratine.service.Result;
import io.baratine.service.Service;
import io.baratine.vault.Asset;
import io.baratine.vault.Id;
import io.baratine.web.Get;

import data.DataItem;

@Asset
public class CachedItem
{
  @Id
  private long _id;

  private JsonDataItem _data;

  @Inject
  @Service
  private RepositoryService _repository;

  @OnLoad
  public void load(Result<Boolean> result)
  {
    _repository.findOne(_id, result.then((d, r) -> loadImpl(d, r)));
  }

  public void loadImpl(DataItem data, Result<Boolean> result)
  {
    if (data != null) {
      _data = JsonDataItem.of(data);
    }

    result.ok(data != null);
  }

  @Get("/")
  public void getData(Result<JsonDataItem> result)
  {
    result.ok(_data);
  }

  public static class JsonDataItem
  {
    private long id;
    private String value;

    public JsonDataItem()
    {
    }

    public JsonDataItem(long id, String value)
    {
      this.id = id;
      this.value = value;
    }

    public static JsonDataItem of(DataItem item)
    {
      if (item == null)
        return null;
      else
        return new JsonDataItem(item.getId(), item.getValue());
    }
  }
}
