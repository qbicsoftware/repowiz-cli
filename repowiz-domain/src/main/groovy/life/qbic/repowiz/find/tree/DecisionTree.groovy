package life.qbic.repowiz.find.tree

/**
 * This class creates a decision tree
 *
 * This class should be used whenever a decision tree is required. A decision tree is a data structure which allows to guide
 * the user to a suitable repository for his data. The nodes of the tree represent characteristics of his data. The leaf nodes
 * are repositories which implement the traits of the previously selected nodes.
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class DecisionTree {

    private Node<String> root

    /**
     * Constructs the decision tree
     */
    DecisionTree() {
        buildTree()
    }

    /**
     * Creates the decision tree
     */
    void buildTree() {
        root = new Node<String>("Root");

        //organism type
        Node<String> human = new Node<String>("human")
        Node<String> env = new Node<String>("environmental community")
        Node<String> plant = new Node<String>("plants")
        Node<String> other = new Node<String>("other")


        //access type:
        Node<String> controlled = new Node<String>("controlled access")
        Node<String> open = new Node<String>("open access")

        //data type:
        Node<String> dna_rna_human = new Node<String>("dna/rna")
        Node<String> dna_rna_env = new Node<String>("dna/rna")
        Node<String> dna_rna_other = new Node<String>("dna/rna")
        Node<String> dna_rna_plants = new Node<String>("dna/rna")

        Node<String> variants_open_human = new Node<String>("variants")
        Node<String> variants_controlled_human = new Node<String>("variants")
        Node<String> variants_plants = new Node<String>("variants")
        Node<String> variants_other = new Node<String>("variants")

        Node<String> other_data_human = new Node<String>("other")

        Node<String> expression_data_human = new Node<String>("expression data")
        Node<String> expression_data_env = new Node<String>("expression data")
        Node<String> expression_data_other = new Node<String>("expression data")
        Node<String> expression_data_plants = new Node<String>("expression data")

        Node<String> protein_human = new Node<String>("protein")
        Node<String> protein_env = new Node<String>("protein")
        Node<String> protein_plants = new Node<String>("protein")
        Node<String> protein_other = new Node<String>("protein")


        //experiment type:
        Node<String> clinical_var = new Node<String>("clinical variants")
        Node<String> cancer_var = new Node<String>("cancer variants")
        Node<String> other_var = new Node<String>("other variants")

        Node<String> raw_reads_human = new Node<String>("raw reads")
        Node<String> raw_reads_env = new Node<String>("raw reads")
        Node<String> raw_reads_other = new Node<String>("raw reads")
        Node<String> raw_reads_plants = new Node<String>("raw reads")

        Node<String> genetic_seq_human = new Node<String>("genetic sequence")
        Node<String> genetic_seq_env = new Node<String>("genetic sequence")
        Node<String> genetic_seq_other = new Node<String>("genetic sequence")
        Node<String> genetic_seq_plants = new Node<String>("genetic sequence")

        Node<String> structural_var_other = new Node<String>("structural variants")
        Node<String> structural_var_plants = new Node<String>("structural variants")

        Node<String> genetic_var_other = new Node<String>("genetic variants")
        Node<String> genetic_var_plants = new Node<String>("genetic variants")

        //repository node created with .addChild("reponame")
        //human tree
        root.addChild(human)

        //controlled branch
        human.addChild(controlled)

        controlled.addChild(variants_controlled_human)

        variants_controlled_human.addChild(clinical_var)
        clinical_var.addChild("clinvar")
        variants_controlled_human.addChild(cancer_var)
        cancer_var.addChild("icgc")
        variants_controlled_human.addChild(other_var)
        other_var.addChild("ega")
        other_var.addChild("dbgap")

        controlled.addChild(other_data_human)
        other_data_human.addChild("ega")

        //open branch
        human.addChild(open)

        open.addChild(variants_open_human)
        variants_open_human.addChild("eva")

        open.addChild(expression_data_human)
        expression_data_human.addChild("arrayexpress")
        expression_data_human.addChild("geo")

        open.addChild(dna_rna_human)
        dna_rna_human.addChild(raw_reads_human)
        raw_reads_human.addChild("ena")
        raw_reads_human.addChild("sra")
        dna_rna_human.addChild(genetic_seq_human)
        genetic_seq_human.addChild("ena")
        genetic_seq_human.addChild("genbank")

        open.addChild(protein_human)
        protein_human.addChild("pride")

        //env tree
        root.addChild(env)

        env.addChild(expression_data_env)
        expression_data_env.addChild("geo")
        expression_data_env.addChild("ena") //todo +mgnify how to add that

        env.addChild(dna_rna_env)
        dna_rna_env.addChild(raw_reads_env)
        raw_reads_env.addChild("ena")
        raw_reads_env.addChild("sra")
        dna_rna_env.addChild(genetic_seq_env)
        genetic_seq_env.addChild("genbank")
        genetic_seq_env.addChild("ena")

        env.addChild(protein_env)
        protein_env.addChild("pride")

        //plant tree
        root.addChild(plant)

        plant.addChild(variants_plants)
        variants_plants.addChild(genetic_var_plants)
        genetic_var_plants.addChild("dbsnp")
        genetic_var_plants.addChild("eva")
        genetic_var_plants.addChild("tair")
        variants_plants.addChild(structural_var_plants)
        structural_var_plants.addChild("dbvar")
        structural_var_plants.addChild("eva")
        structural_var_plants.addChild("tair")

        plant.addChild(expression_data_plants)
        expression_data_plants.addChild("arrayexpress")
        expression_data_plants.addChild("geo")

        plant.addChild(dna_rna_plants)
        dna_rna_plants.addChild(raw_reads_plants)
        raw_reads_plants.addChild("ena")
        raw_reads_plants.addChild("sra")
        dna_rna_plants.addChild(genetic_seq_plants)
        genetic_seq_plants.addChild("ena")
        genetic_seq_plants.addChild("genbank")

        plant.addChild(protein_plants)
        protein_plants.addChild("pride")

        //other tree
        root.addChild(other)

        other.addChild(variants_other)
        variants_other.addChild(genetic_var_other)
        genetic_var_other.addChild("dbsnp")
        genetic_var_other.addChild("eva")
        variants_other.addChild(structural_var_other)
        structural_var_other.addChild("dbvar")
        structural_var_other.addChild("eva")

        other.addChild(expression_data_other)
        expression_data_other.addChild("arrayexpress")
        expression_data_other.addChild("geo")


        other.addChild(dna_rna_other)
        dna_rna_other.addChild(raw_reads_other)
        raw_reads_other.addChild("ena")
        raw_reads_other.addChild("sra")
        dna_rna_other.addChild(genetic_seq_other)
        genetic_seq_other.addChild("ena")
        genetic_seq_other.addChild("genbank")

        other.addChild(protein_other)
        protein_other.addChild("pride")

    }

    /**
     * Returns the all children of the root
     * @return a list of all nodes of the first level of the decision tree
     */
    List<Node> getFirstDecisionLevel() {
        root.children
    }

    /**
     * Returns the root node of the tree
     * @return the root node
     */
    Node getRoot() {
        return root
    }

    /**
     * Collects the data that is stored in the children of a parent node
     * @param parent of the children for which the data needs to be collected
     * @return list of data stored in the child nodes
     */
    List<String> getChildrenData(Node<String> parent) {
        List<String> childrenData = []

        parent.children.each {
            childrenData << it.data
        }
        return childrenData
    }

}
