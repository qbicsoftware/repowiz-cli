# Plugin implementation details
## Adding new Target Repositories

When adding a new TargetRepository add the classpath of the TargetRepositoryProvider implementation class to the 
file life.qbic.repowiz.spiimpl.GeoTargetRepositoryProvider in the META_INF/services directory

the module for the repository needs to implement the interfaces TargetRepository and TargetRepositoryProvider,
furthermore the implementation has dependencies on the domain and the infrastructure modules. It should be aware of the 
metadata model of repowiz and that the mapping needs to be provided by each added plugin

In order to access the plugin code from repowiz add your modul as dependency into the 
repowiz-infrastructure POM

## Exchanging new Local Database