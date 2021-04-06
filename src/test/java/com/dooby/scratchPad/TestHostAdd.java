package com.dooby.scratchPad;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.dooby.utilities.MonitoringMail;
import com.dooby.utilities.TestConfig;

public class TestHostAdd {

	public static void main(String[] args) throws UnknownHostException, AddressException, MessagingException {
		
		
		MonitoringMail mail = new MonitoringMail();

		String msgBody = "http://" + InetAddress.getLocalHost().getHostAddress()
				+ ":8080/job/DataDrivenLiveProject/Extent_20Report/";

		System.out.println(msgBody);

		mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, msgBody);
		
		//Testing the EGIT options adding few lines to check it again ---------
	

	}

}
