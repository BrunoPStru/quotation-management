package br.inatel.quotationmanagement.controller.dto;

public class WebClientDto {

    private String id;

    private String description;

    public WebClientDto() {
    }

    public WebClientDto(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
