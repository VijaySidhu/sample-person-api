package edu.uw.service;

import java.io.InputStream;

public interface DataLoader {

	public void process(InputStream inputStream) throws Exception;

}
