package smartcache;

import javax.inject.Inject;

import io.baratine.service.OnLoad;
import io.baratine.service.Result;
import io.baratine.vault.Asset;
import io.baratine.vault.Id;
import io.baratine.web.Get;

import data.DataItem;
import data.DataItemRepository;

@Asset
public class CachedItem
{
  @Id
  private long _id;

  private DataItem _data;

  @Inject
  private DataItemRepository _repository;

  @OnLoad
  public void load(Result<Boolean> result)
  {
    try {
      _data = _repository.getOne(_id);
    } catch (Throwable e) {
      e.printStackTrace();
    }

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
      return new JsonDataItem(item.getId(), item.getValue());
    }
  }
}
