package ca.mcgill.ecse321.petadoptionapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Donation {
	private Integer amount;

	public void setAmount(Integer value) {
		this.amount = value;
	}

	public Integer getAmount() {
		return this.amount;
	}

	private GeneralUser donatedTo;

	@ManyToOne(optional = false)
	public GeneralUser getDonatedTo() {
		return this.donatedTo;
	}

	public void setDonatedTo(GeneralUser donatedTo) {
		this.donatedTo = donatedTo;
	}

	private GeneralUser donatedFrom;

	@ManyToOne(optional = false)
	public GeneralUser getDonatedFrom() {
		return this.donatedFrom;
	}

	public void setDonatedFrom(GeneralUser donatedFrom) {
		this.donatedFrom = donatedFrom;
	}

	private Integer id;

	public void setId(Integer value) {
		this.id = value;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}
}
