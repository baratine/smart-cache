package data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DataItem
{
  @Id
  private long id;

  private String value;

  public DataItem()
  {
  }

  public DataItem(long id, String value)
  {
    this.id = id;
    this.value = value;
  }

  public long getId()
  {
    return id;
  }

  public String getValue()
  {
    return value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }
}
