package com.dooby.scratchPad;

import java.util.Date;

public class TestTimeStamp {
	
	public static void main(String[] args) {
		
		
		Date d = new Date();
		
		String fdate = d.toString().replace(" ", "_").replace(":", "_");
		
		System.out.println(fdate);
	}
	
	

}
