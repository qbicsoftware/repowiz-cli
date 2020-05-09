#Find Matching Repositories

### Use Case Description

The system suggests suitable repositories based on the characteristics of a project.
This is implemented with a decision tree. the first level is based on the source organism of the data. In further levels 
information about data and study types are considered.

Part of the decision tree considering human data, it needs to be considered if should be publicly accessible.

![human](decisionTree_human.png)

Part of the decision tree considering data from plants:

![plant](decisionTree_plant.png)

Part of the decision tree considering data from environmental samples:

![env](decisionTree_env.png)

Part of the decision tree considering data from any other organism like microorganisms:

![other](decisionTree_other.png)


### Input:
1. Project details from the user
2. Repository descriptions

### Output:
1. Level information from the decision tree
2. Repository objects

### Architecture
![architecture](architecture-FingMatchingRepositories.png)
