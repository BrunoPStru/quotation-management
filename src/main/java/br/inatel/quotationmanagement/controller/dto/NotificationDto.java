package br.inatel.quotationmanagement.controller.dto;

public class NotificationDto {

	private String host;

	private Integer port;

	public NotificationDto() {
	}

	public NotificationDto(String host, Integer port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

}
