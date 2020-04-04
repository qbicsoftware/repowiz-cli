# RepoWiz

[![Build Status](https://travis-ci.com/qbicsoftware/repowiz.svg?branch=development)](https://travis-ci.com/qbicsoftware/repowiz)[![Code Coverage]( https://codecov.io/gh/qbicsoftware/repowiz/branch/development/graph/badge.svg)](https://codecov.io/gh/qbicsoftware/repowiz)

RepoWiz, version 1.0.0-SNAPSHOT - RepoWiz helps you to find a suitable repository for your data and prepares your submission.

![wizard](./docs/logo/wizard_repo.png)

## Author
Created by Jennifer Bödker (jennifer.boedker@student.uni-tuebingen.de)
 
## Description

The **__Fair Data Principle__** demands the submission of data of funded research in order to make it publicly accessible. 
Since there are a lot of repositories for different data types or even multiple repositories for the same data types but with different requirements for the submission, 
in order to facilitate FAIR data RepoWiz supports the upload from any local management systems to different repositories.

## How to Install
In order to execute the code you need to clone the repository

```git clone https://github.com/qbicsoftware/RepoWiz```

and then package the jar with Maven

```mvn clean package```

## Run

RepoWiz offers different subcommands:

### Config
For two of the three subcommands require to supply the path to a config file.
This is a json file that can look like this:
```
"server_url" : "openbis-serverurl",
"user": "username",
"password": "password"
``` 
The example config shows all required fields for the connection with the openBIS system.
For data from another local database instance the config may vary. 
#### Guide
For inexperienced users RepoWiz offers a __guide__ that suggest a suitable repository for your project

```java -jar RepoWiz-1.0.0.jar guide -p QFSVI -conf credentials.properties```

#### Select
If you are already familiar with repositories and just want to upload your data to a repository use the __select__ command:

```java -jar RepoWiz-1.0.0.jar select -r geo -p QFSVI -conf credentials.properties```

#### List
If you just want to know which repositories are already supported use the __list__ command:

```java -jar RepoWiz-1.0.0.jar list```

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
