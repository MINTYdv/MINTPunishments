package xyz.mintydev.punishment.util;

import java.util.ArrayList;
import java.util.List;

public class PaginationUtil<T> {

	private List<T> items;
	private int itemsPerPage;

	public PaginationUtil(List<T> items, int itemsPerPage) {
		this.items = items;
		this.itemsPerPage = itemsPerPage;
	}

	/**
	 * Automatically retrieves content for the current page
	 * 
	 * @param the current Page number
	 * @return the list of the items to show on this page
	 */
	public List<T> getContents(int pageNumber) {
		List<T> res = new ArrayList<>();

		final int lastIndex = (pageNumber - 1) * (itemsPerPage);

		int loopLimit = lastIndex + itemsPerPage;
		if (loopLimit > items.size())
			loopLimit = items.size();

		// cette liste doit faire max itemsPerPage
		for (int a = lastIndex; a < loopLimit; a++) {
			T item = items.get(a);

			res.add(item);
		}

		return res;
	}

	public int getPagesAmount() {
		double res = (double) items.size() / itemsPerPage;
		return (int) Math.ceil(res);
	}

	/**
	 * Check if the current list can have a next shown page
	 * 
	 * @param the current page int
	 * @return true if the list can have at least one next page
	 */
	public boolean hasNextPage(int currentPage) {
		return getContents(currentPage + 1) != null && getContents(currentPage + 1).size() > 0;
	}

	/**
	 * Check if the currnet list can have a previous shown page
	 * 
	 * @param the current page int
	 * @return true if the list can have at least one previous page
	 */
	public boolean hasPreviousPage(int currentPage) {
		return currentPage > 1;
	}

	/*
	 * Getters & Setters
	 */

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public List<T> getItems() {
		return items;
	}

}