package smartcache;

import io.baratine.service.Service;
import io.baratine.vault.Vault;

@Service
public interface Cache extends Vault<Long,CachedItem>
{
}
