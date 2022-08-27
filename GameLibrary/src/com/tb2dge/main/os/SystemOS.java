package com.tb2dge.main.os;

public class SystemOS {
	public static OperatingSystem getOS() throws UnsupportedOSException {
		String os = System.getProperty("os.name").toLowerCase();
		if(os.contains("win")) 
			return OperatingSystem.WINDOWS;
		if(os.contains("mac")) 
			return OperatingSystem.MAC;
		if(os.contains("nix") || os.contains("nux") || os.contains("aix"))
			return OperatingSystem.UNIX;
		if(os.contains("sunos"))
			return OperatingSystem.SOLARIS;
		throw new UnsupportedOSException(os);
	}
}
