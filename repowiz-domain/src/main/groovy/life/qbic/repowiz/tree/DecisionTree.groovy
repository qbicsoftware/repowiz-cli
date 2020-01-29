package life.qbic.repowiz.tree

class DecisionTree {

    Node<String> root

    void buildTree(){
        root = new Node<String>("Root");

        //organism type
        Node<String> human = new Node<String>("human")
        Node<String> env = new Node<String>("environmental community")
        Node<String> plant = new Node<String>("plants")
        Node<String> other = new Node<String>("other")


        //access type:
        Node<String> controlled = new Node<String>("controlled")
        Node<String> open = new Node<String>("open")

        //data type:
        Node<String> dna_rna_human = new Node<String>("dna_rna")
        Node<String> dna_rna_env = new Node<String>("dna_rna")
        Node<String> dna_rna_other = new Node<String>("dna_rna")
        Node<String> dna_rna_plants = new Node<String>("dna_rna")

        Node<String> variants_open_human = new Node<String>("variants")
        Node<String> variants_controlled_human = new Node<String>("variants")
        Node<String> variants_plants = new Node<String>("variants")
        Node<String> variants_other = new Node<String>("variants")

        Node<String> other_data_human = new Node<String>("other")

        Node<String> expression_data_human = new Node<String>("expression_data")
        Node<String> expression_data_env = new Node<String>("expression_data")
        Node<String> expression_data_other = new Node<String>("expression_data")
        Node<String> expression_data_plants = new Node<String>("expression_data")

        Node<String> protein_human = new Node<String>("protein")
        Node<String> protein_env = new Node<String>("protein")
        Node<String> protein_plants = new Node<String>("protein")
        Node<String> protein_other = new Node<String>("protein")


        //experiment type:
        Node<String> clinical_var = new Node<String>("clinical_variants")
        Node<String> cancer_var = new Node<String>("cancer_variants")
        Node<String> other_var = new Node<String>("other_variants")

        Node<String> raw_reads_human = new Node<String>("raw_reads")
        Node<String> raw_reads_env = new Node<String>("raw_reads")
        Node<String> raw_reads_other = new Node<String>("raw_reads")
        Node<String> raw_reads_plants = new Node<String>("raw_reads")

        Node<String> genomic_seq_human = new Node<String>("genomic_sequence")
        Node<String> genomic_seq_env = new Node<String>("genomic_sequence")
        Node<String> genomic_seq_other = new Node<String>("genomic_sequence")
        Node<String> genomic_seq_plants = new Node<String>("genomic_sequence")

        Node<String> structural_var_other = new Node<String>("structural_variants")
        Node<String> structural_var_plants = new Node<String>("structural_variants")

        Node<String> genomic_var_other = new Node<String>("genomic_variants")
        Node<String> genomic_var_plants = new Node<String>("genomic_variants")

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
        dna_rna_human.addChild(genomic_seq_human)
        genomic_seq_human.addChild("ena")
        genomic_seq_human.addChild("genbank")

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
        dna_rna_env.addChild(genomic_seq_env)
        genomic_seq_env.addChild("genbank")
        genomic_seq_env.addChild("ena")

        env.addChild(protein_env)
        protein_env.addChild("pride")

        //plant tree
        root.addChild(plant)

        plant.addChild(variants_plants)
        variants_plants.addChild(genomic_var_plants)
        genomic_var_plants.addChild("dbsnp")
        genomic_var_plants.addChild("eva")
        genomic_var_plants.addChild("tair")
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
        dna_rna_plants.addChild(genomic_seq_plants)
        genomic_seq_plants.addChild("ena")
        genomic_seq_plants.addChild("genbank")

        plant.addChild(protein_plants)
        protein_plants.addChild("pride")

        //other tree
        root.addChild(other)

        other.addChild(variants_other)
        variants_other.addChild(genomic_var_other)
        genomic_var_other.addChild("dbsnp")
        genomic_var_other.addChild("eva")
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
        dna_rna_other.addChild(genomic_seq_other)
        genomic_seq_other.addChild("ena")
        genomic_seq_other.addChild("genbank")

        other.addChild(protein_other)
        protein_other.addChild("pride")

    }

    def getRepository(HashMap<String,String> submissionType){

        String org = submissionType.get("organism")
        String access = submissionType.get("access_type")
        String data = submissionType.get("data_type")
        String experiment = submissionType.get("experiment_type")

        try{
            Node<String> org_ = findNode(root,org)
            Node<String> access_ = findNode(org_,access)
            Node<String> data_ = findNode(access_,data)
            Node<String> exp_ = findNode(data_,experiment)

            exp_.data
        }catch(NullPointerException e){
            System.err.println "No repository found for required submission type"
            e.printStackTrace()
        }

    }

    Node<String> findNode(Node<String> start, String type) throws NullPointerException{
        Node<String> match = null

        List<Node<String>> childrenNodes = start.children

        childrenNodes.each {
            if(it.data == type){
                print "return "+it.data
                match = it
            }
        }
        match
    }

}
