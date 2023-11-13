package com.base.entity;

import com.platform.annotations.ClassMetaProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Muhil
 *
 */
@Entity
@Table(name = "FILESTORE")
@ClassMetaProperty(code = "FS")
public class FileStore extends FileBlob {

	private static final long serialVersionUID = 1L;

}
