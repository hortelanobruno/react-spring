package com.callistech.analytics.frontend.domains.tacacs;

import com.callistech.analytics.frontend.domains.tacacs.entity.TacacsServer;

import net.javasource.net.tacacs.Tacacs;

public class TACACsAuthImpl implements Runnable {

	private TacacsServer server;
	private String userName;
	private String password;
	private Boolean result;

	public TACACsAuthImpl(TacacsServer server, String userName, String password) {
		this.server = server;
		this.userName = userName;
		this.password = password;
	}

	@Override
	public void run() {
		Tacacs tac;
		try {
			tac = new Tacacs();
			tac.setHostname(server.getIpAddress());
			tac.setPortNumber(server.getPort());
			tac.setKey(server.getSecret());
			result = tac.isAuthenticated(userName, password);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Boolean getResult() {
		return result;
	}
}
