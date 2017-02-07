package ooby.contents.entity;

import java.sql.Blob;

public interface FileIF {
	public Blob getFile();
	public String getExtension();
}
