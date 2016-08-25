package smartcache;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import io.baratine.service.Result;
import io.baratine.service.Service;
import io.baratine.service.Workers;

import com.caucho.v5.jdbc.JdbcService;

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

  private void get(long id, ResultSet rs, Result<DataItem> result)
  {
    try {
      if (rs.next()) {
        result.ok(new DataItem(id, rs.getString(1)));
      }
      else {
        result.ok(null);
      }
    } catch (SQLException e) {
      result.fail(e);
    }
  }
}
