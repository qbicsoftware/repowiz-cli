package life.qbic.repowiz.prepare

abstract class TemplateSection {
    //all fields
    List optionalFields
    List requiredFields
    //map with at least the required fields + 0 or at most all optional fields
    HashMap data
    //fields that are only valid when restricted vocabulary is used
    HashMap restrictedFields
    //all fields that can occur multiple times
    List multiFields

    TemplateSection(List optional, List required, List multi){

    }

    //method to fill data map --> map with at least the required fields + 0 or at most all optional fields


    //methods for fields with restricted vocabulary

}
