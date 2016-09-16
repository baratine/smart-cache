package smartcache;

import java.util.Iterator;

import javax.inject.Inject;

import io.baratine.jdbc.JdbcRow;
import io.baratine.jdbc.JdbcRowSet;
import io.baratine.jdbc.JdbcService;
import io.baratine.service.Result;
import io.baratine.service.Service;
import io.baratine.service.Workers;

import data.DataItem;

@Service
@Workers(4)
public class JdbcRepositoryService implements RepositoryService
{
  @Inject
  @Service("jdbc:///items")
  private JdbcService _jdbc;

  @Override
  public void findOne(long id, Result<DataItem> result)
  {
    _jdbc.query(result.then((rs, r) -> get(id, rs, r)),
                "SELECT VALUE FROM DATAITEM WHERE ID=?",
                id);
  }

  private void get(long id, JdbcRowSet rs, Result<DataItem> result)
  {
    Iterator<JdbcRow> iterator = rs.iterator();
    if (iterator.hasNext()) {
      JdbcRow row = iterator.next();
      result.ok(new DataItem(id, row.getString(1)));
    }
    else {
      result.ok(null);
    }
  }
}
