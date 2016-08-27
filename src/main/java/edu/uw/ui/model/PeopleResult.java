package edu.uw.ui.model;

import java.util.List;

public class PeopleResult {

	private List<People> people;
	long total;
	int page;
	int size;

	public PeopleResult() {
		// TODO Auto-generated constructor stub
	}

	public PeopleResult(List<People> people, long total, int page, int size) {
		super();
		this.people = people;
		this.total = total;
		this.page = page;
		this.size = size;
	}

	public List<People> getPeople() {
		return people;
	}

	public void setPeople(List<People> people) {
		this.people = people;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
