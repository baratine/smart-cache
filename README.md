# Smart Cache

Smart cache is an application utilizing Baratine technology. It provides REST 
accessible cache for values stored in the database. The data is represented by 
the DataItem class. As written it only contains one data field but can be 
extended to have more. 

Classes CachedItem and Cache implement Baratine's Vault. Vault hosts addressable
assets and allows fast serving of data contained in the Assets. In this model
CachedItem is used to hold an instance of DataItem. 

To make this work CachedItem and DataItem must share the key. When the CachedItem 
is requested for the first time it will load the DataItem identified by that key. 

CachedItems become accessible at /CachedItem/{id} REST URL.
 
e.g. curl http://localhost:8080/CachedItem/13
will return Json representation of the item as so: {"id":13,"value":"Hello World 13!"}

Application can be run with `gradle run` from the smart-cache directory.

Sample data provided in ./db directory has data for keys 12, 13, 14


