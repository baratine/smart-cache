# Smart Cache

Smart cache is an application utilizing Baratine technology. It provides REST 
accessible cache for values stored in a database. The data is represented by 
the DataItem class. 

Classes CachedItem and Cache implement Baratine's Vault. Vault hosts url addressable
assets and allows fast serving of data contained in the Assets. In this model
CachedItem is used to hold an instance of DataItem. 

To make this work CachedItem and DataItem must share the key. When the CachedItem 
is requested for the first time it will load the DataItem identified by that key. 

The Cache can be configured to use either Spring-Data or JPA or AsynJDBC to access 
the data in the backing database. 

The three ways of accessing data is abstracted with a RepositoryService. 

Classes SpringRepositoryService, JdbcRepositoryService and JpaRepositoryService
provide the implementations for Spring, AsyncJDBC and JPA respectively.

Configuration parameter "persistence" in smart-cache.yml chooses implementation of
RepositoryService. Acceptable values for the parameter are "spring-data", "jdbc" 
and "jpa".

Configuration can be supplied as an argument to the main class with --conf <file>
and is required.

e.g `java -cp $CP smartcache.CacheServer --conf src/main/resources/smart-cache.yml`

CachedItems are made accessible at /CachedItem/{id} REST URL via Baratine Vault.

e.g. `curl http://localhost:8080/CachedItem/13`    
{"id":13,"value":"Hello World 13!"}

Application can be run with `gradle run` from the smart-cache directory.

Sample data provided in ./db directory has data for keys 12, 13, 14
