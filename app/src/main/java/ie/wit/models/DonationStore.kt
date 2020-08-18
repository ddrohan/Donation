package ie.wit.models;

interface DonationStore {

    fun findAll() : List<DonationModel>
    fun findById(id: String) : DonationModel?
    fun create(donation: DonationModel)
    fun update(donation: DonationModel)
    fun delete(donation: DonationModel)
}