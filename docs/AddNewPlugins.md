# Plugin implementation details
## Adding new Target Repositories

When adding a new TargetRepository:

1. add the classpath of the TargetRepositoryProvider implementation class to the 
file life.qbic.repowiz.spiimpl.GeoTargetRepositoryProvider in the META_INF/services directory

2. the module for the repository needs to implement the interfaces TargetRepository and TargetRepositoryProvider

3. the implementation has dependencies on the domain and the infrastructure modules. It should be aware of the 
metadata model of repowiz and that the mapping needs to be provided by each added plugin

4. In order to access the plugin code from repowiz add your module as dependency into the 
repowiz-infrastructure POM

5. give a description about the repository in order to be included into the guide and to handel the
upload type

## Exchanging new Local Database