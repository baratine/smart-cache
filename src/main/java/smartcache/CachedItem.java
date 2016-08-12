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

  private DataItem _data;

  @Inject
  @Service
  private SpringRepositoryService _repository;

  @OnLoad
  public void load(Result<Boolean> result)
  {
    _repository.getOne(_id, result.then((d, r) -> loadImpl(d, r)));
  }

  public void loadImpl(DataItem data, Result<Boolean> result)
  {
    _data = data;
    result.ok(true);
  }

  @Get("/")
  public void getSelf(Result<JsonDataItem> result)
  {
    result.ok(JsonDataItem.of(_data));
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
      try {
        return new JsonDataItem(item.getId(), item.getValue());
      } catch (Throwable t) {
        return null;
      }
    }
  }
}
