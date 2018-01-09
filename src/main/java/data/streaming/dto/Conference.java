package data.streaming.dto;

public class Conference {
	
	private String conference;
	private String acronym;
	private Integer edition;
	private String city;
	private String country;
	private String idConference;

	public Conference(String title, String date, String idConference, String conference, String acronym, Integer edition, String city, String country) {
		super();
		this.conference = conference;
		this.acronym = acronym;
		this.edition = edition;
		this.city = city;
		this.country = country;
		this.idConference = idConference;
	}

	public String getConference() {
		return conference;
	}

	public void setConference(String conference) {
		this.conference = conference;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	
	public Integer getEdition() {
		return edition;
	}

	public void setEdition(Integer edition) {
		this.edition = edition;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIdConference() {
		return idConference;
	}

	public void setIdConference(String idConference) {
		this.idConference = idConference;
	}

	@Override
	public String toString() {
		return "Conference [conference=" + conference + ", acronym=" + acronym + ", edition=" + edition + ", city=" + city + ", country=" + country + ", idConference=" + idConference + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conference == null) ? 0 : conference.hashCode());
		result = prime * result + ((idConference == null) ? 0 : idConference.hashCode());
		result = prime * result + ((acronym == null) ? 0 : acronym.hashCode());
		result = prime * result + ((edition == null) ? 0 : edition.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conference other = (Conference) obj;
		if (conference == null) {
			if (other.conference != null)
				return false;
		} else if (!conference.equals(other.conference))
			return false;
		if (idConference == null) {
			if (other.idConference != null)
				return false;
		} else if (!idConference.equals(other.idConference))
			return false;
		if (acronym == null) {
			if (other.acronym != null)
				return false;
		} else if (!acronym.equals(other.acronym))
			return false;
		if (edition == null) {
			if (other.edition != null)
				return false;
		} else if (!edition.equals(other.edition))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		return true;
	}


}
