package com.callistech.analytics.frontend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.callistech.analytics.frontend.domains.tacacs.entity.TacacsServer;
import com.callistech.analytics.frontend.domains.tacacs.entity.TacacsServerAuthType;
import com.callistech.analytics.frontend.domains.tacacs.repository.TacacsServerAuthRepository;
import com.callistech.analytics.frontend.domains.tacacs.repository.TacacsServerRepository;

@Component
public class TACACsService {

	@Autowired
	private TacacsServerAuthRepository tacacsServerAuthRepository;
	@Autowired
	private TacacsServerRepository tacacsServerRepository;

	public void createNewServer(String name, String ip, String port, String secret) {
		TacacsServer server = new TacacsServer();
		server.setName(name);
		server.setIpAddress(ip);
		server.setPort(Integer.valueOf(port));
		server.setSecret(secret);
		tacacsServerRepository.save(server);
	}

	public List<TacacsServer> getTACACsServers() {
		return tacacsServerRepository.findAll();
	}

	public TacacsServerAuthType getTACACsAuthType() {
		List<TacacsServerAuthType> auths = tacacsServerAuthRepository.findAll();
		if (auths != null) {
			return auths.get(0);
		}
		return null;
	}

	public TacacsServer getTACACsServerById(String id) {
		return tacacsServerRepository.findOne(id);
	}

	public void removeServer(String id) {
		tacacsServerRepository.delete(id);
	}

	public void setAuthTypeTACACS(String authtype, boolean fallbackAuth) {
		TacacsServerAuthType auth = getTACACsAuthType();
		if (auth == null) {
			auth = new TacacsServerAuthType();
		}
		auth.setAuthType(authtype);
		auth.setFallbackAuthenthication(fallbackAuth);
		tacacsServerAuthRepository.save(auth);
	}

}
