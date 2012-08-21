package com.tmm.enterprise.microblog.util;

import org.springframework.transaction.annotation.Transactional;

public class IndexObjects extends BatchProcess {

	@Transactional
	public void execute() throws Exception {
		getSearchService().indexAllObjects();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		new IndexObjects().execute();
		System.out.println("Done!");
	}
}
