package trip.planner

class Trip {
	String name
	String city
	Date startDate
	Date endDate
	String purpose
	String notes
	Airline airline
	
	static constraints = {
		name()
		city()
		purpose()
		airline()
		startDate()
		endDate()
		notes()
	}
	
	String toString(){
		return name
	}
}
