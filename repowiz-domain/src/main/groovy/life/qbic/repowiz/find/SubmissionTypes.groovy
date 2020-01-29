package life.qbic.repowiz.find


class SubmissionTypes {

    enum Organism{
        HUMAN, ENVIRONMENTAL_COMMUNITIES, PLANTS, OTHER
    }

    enum AccessType{
        OPEN, RESTRICTED
    }

    enum DataType{
        VARIANTS, EXPRESSION_DATA, DNA_RNA, PROTEIN
    }

    enum ExperimentType{
        CLINICAL_VARIANTS, CANCER_VARIANTS, OTHER, RAW_READS, GENOMICS, STRUCTURAL_VARIANTS, GENOMIC_VARIANTS,
    }
}