## Extent RepoWiz
RepoWiz is an extensible tool and allows to add new submissino preparaition for new repositories.
Furthermore, the local database from which the user data is loaded can also be exchanged.
The following describes how to do that. 

### Add new Repositories
In order to add a new repository it should be usable for internal representation and for preparing an upload for it.

#### Internal representation
In the module repowiz-infrastructure search for resources/repositories/repository.schema.json.
This file determines how a repository is represent within RepoWiz. Each field
is described by the schema. 
The description is important for the internal representation of the repository. In serveral use cases
RepoWiz depends on this information such as for the specification of the upload type or the validation
of the data with the selected repository. 

#### Submission Preparation
For preparing a submission for a repository it is required to supply a maven module 
that follows the guidelines of docs/AddNewPlugins.md. 

### Change the local Database
Also see docs/AddNewPlugins.md
