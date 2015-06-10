package com.expensetracker.gui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import com.expensetracker.constants.Constants;
import com.expensetracker.gui.pojo.Job;

public class PersistenceUtil {

	private static FileOutputStream fOut = null;
	private static FileInputStream fIn = null;
	private static ObjectOutputStream oOut = null;
	private static ObjectInputStream oIn = null;
	private static File dataDir = null;
	private static File dataFile = null;

	static {
		dataDir = new File(Constants.PERSIST_DIRECTORY_PATH);
		if (!dataDir.exists()) {
			dataDir.mkdir();
			dataDir = null;
		}
		dataFile = new File(Constants.PERSIST_DIRECTORY_PATH + "\\"
				+ Constants.PERSIST_FILE_NAME);
	}

	public static void saveObject(Object object) {

		try {
			List<Job> obj = (List<Job>) object;
			if (obj.size() > 0) {
				fOut = new FileOutputStream(dataFile);

				oOut = new ObjectOutputStream(fOut);
				oOut.writeObject(object);
				oOut.flush();
				oOut.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Object readObject() {

		List<Job> retVal = null;
		try {

			if (dataFile.exists()) {
				fIn = new FileInputStream(dataFile);
				oIn = new ObjectInputStream(fIn);
				retVal = (List<Job>) oIn.readObject();
				oIn.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;
	}
}
