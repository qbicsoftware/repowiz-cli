What a function do
What the function's parameters or arguments are
What a function returns


# Plugin implementation details
## Adding new Target Repositories

When adding a new TargetRepository:

1. add the classpath of the TargetRepositoryProvider implementation class to the 
file life.qbic.repowiz.spiimpl.GeoTargetRepositoryProvider in the META_INF/services directory

2. the module for the repository needs to implement the interfaces __TargetRepository__ and __TargetRepositoryProvider__

3. the implementation has dependencies on the domain and the infrastructure modules. It should be aware of the 
metadata model of RepoWiz and that the submissionTypes needs to be provided by each added plugin

4. In order to access the plugin code from RepoWiz add your module as dependency into the 
repowiz-infrastructure POM

5. give a description about the repository in order to be included into the guide and to handel the
upload type

## Exchanging the Local Database

Check the implementations of the domain and infrastructure modules in the package preparation

1. The Interfaces ProjectSearchInput needs to be implemented by a ProjectSearcher
check this class for an exemplary implementation. This class needs to use the ProjectOutput Interface
to transfer the loaded data back into the domain.

2. It is adviced to move further implementations like session connections and mapper into separate classes.
See the exemplary implementation for openbis in the infrastructure module prepare/openbis.

## Implementing the RepoWiz Metadata Structure