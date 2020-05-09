### Developer Information

## Extent RepoWiz
RepoWiz is an extensible tool and allows adding the submission preparation for new repositories.
Furthermore, the local DMS from which the user data is being loaded can also be exchanged.
The following describes how to do that. 

#### Internal representation
In the module **repowiz-infrastructure** search for ``resources/repositories/repository.schema.json```.
This file determines how a repository is represent within RepoWiz. 
Each accepted field is described by the schema. In order to be accepted by the core the schema must be followed. 

The description is important for the internal representation of the repository. In several use cases
RepoWiz depends on this information such as for the specification of the upload type or the validation
of the data with the selected repository. 


### Plugin implementation details
#### Adding new Target Repositories

When adding a new TargetRepository:

1. The module for the repository needs to implement the interfaces __SubmissionManager__ and __TargetRepositoryProvider__

2. Add the classpath of the TargetRepositoryProvider implementation class to the 
   file ``life.qbic.repowiz.spiimpl.TargetRepositoryProvider`` in the **META_INF/services** directory 
   of the **repowiz-application** module.

3. The implementation has dependencies on the domain and the infrastructure modules. 
   It should be aware of the metadata model of RepoWiz and that the submissionTypes need to be provided by each added plugin.


4. In order to access the plugin code from RepoWiz, add your module as dependency into the 
   **repowiz-infrastructure** POM.

5. The ```repository.schema.json``` located in the **repowiz-infrastructure** module defines how the repository needs to be defined.
   The JSON object based on this schema must be part of the plugin and translated in the a RepoWiz Repository object
   as defined in the **TargetRepositoryProvider** interface.
   
The **clinvar-plugin-manager** is a minimal example for a repository plugin.


#### Exchanging the Local Database

For an example the implementations of the domain and infrastructure modules in the package **preparation**

1. The **ProjectSearcher** class inside the **repowiz-infrastructure** module needs to be extended by the class which handles the project loading for a given DMS.
   In order to be used in RepoWiz the new **ProjectSearcher** it must be exchanged in the **SubmissionController** (within **repowiz-infrastructure**) 
   and the **RepoWizTool** class (**repowiz-application**).
   
   Check out the **OpenBisSearcher** in the **repowiz-infrastructure** to see an example.

2. It is adviced to move further implementations like session connections and mapper into separate classes.
   See the exemplary implementation infrastructure module in the directory ``prepare/openBis``.

3. A JSON object describing the mapping of the metadata terms can be used to facilitate the mapping of the metadata 
   (see **resources/metadataMapping/openbisMapping.json** in the **repowiz-infrastructure** module)

## Implementing the RepoWiz Metadata Structure

The RepoWiz metadata model is represented by the **SubmissionModel** object in the **repowiz-domain** module.
It consists of one **RepoWizProject** object which describes the project information and multiple **RepoWizSamples** 
and further information on the submission (the upload type and what the missing metadata terms in the model).

In schemata under **resources/metadataMapping** define how the samples and projects should look like. These are used to
verify the submission objects that are transferred into RepoWiz.



