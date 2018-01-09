package data.streaming.dto;

public class ConferenceRating {
	
	private String conference1;
	private String conference2;
	private Integer rating;
	
	public ConferenceRating(String idConference1, String idConference2, Integer rating) {
		super();
		this.conference1 = idConference1;
		this.conference2 = idConference2;
		this.rating = rating;
	}

	public String getConference1() {
		return conference1;
	}

	public void setConference1(String conference1) {
		this.conference1 = conference1;
	}

	public String getConference2() {
		return conference2;
	}

	public void setConference2(String conference2) {
		this.conference2 = conference2;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "ConferenceRating [conference1=" + conference1 + ", conference2=" + conference2 + ", rating=" + rating + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conference1 == null) ? 0 : conference1.hashCode());
		result = prime * result + ((conference2 == null) ? 0 : conference2.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
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
		ConferenceRating other = (ConferenceRating) obj;
		if (conference1 == null) {
			if (other.conference1 != null)
				return false;
		} else if (!conference1.equals(other.conference1))
			return false;
		if (conference2 == null) {
			if (other.conference2 != null)
				return false;
		} else if (!conference2.equals(other.conference2))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		return true;
	}


}