package com.expensetracker.gui.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ImageUtil {
	
	public static String getAppIconPath() {
		URL url = ImageUtil.class.getResource("appicon1.png");
		return url.toExternalForm();
	}
	
	public static URL getAppIconURL() {
		URL url = ImageUtil.class.getResource("appicon.jpg");
		return url;
	}
	
	public static String getTrayIconPath() {
		URL url = ImageUtil.class.getResource("trayIcon.jpg");
		return url.toExternalForm();
	}
	
	public static URL getTrayIconURL() {
		URL url = ImageUtil.class.getResource("trayicon1.png");
		return url;
	}
	
	public static String getLoginBtnPath() {
		URL url = ImageUtil.class.getResource("login1.jpg");
		return url.toExternalForm();
	}

	public static String getLogoutBtnPath() {
		// TODO Auto-generated method stub
		URL url = ImageUtil.class.getResource("logout1.jpg");
		return url.toExternalForm();
	}
	

	public static String getDefaultUserPath() {
		// TODO Auto-generated method stub
		URL url = ImageUtil.class.getResource("default_user.jpg");
		return url.toExternalForm();
	}
	
	public static BufferedImage getScaledImage(java.awt.Image srcImg, int w, int h) {
	    BufferedImage resizedImg = new BufferedImage(w, h, Transparency.TRANSLUCENT);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    return resizedImg;
	} 
}
