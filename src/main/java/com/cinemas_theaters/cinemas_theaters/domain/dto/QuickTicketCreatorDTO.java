package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class QuickTicketCreatorDTO {

    private Long projectionId;
    private Integer seatRow;
    private Integer seatNum;
    private Integer discount;

    public QuickTicketCreatorDTO(String projectionId, String seatRow, String seatNum, String discount) {
        this.projectionId = Long.parseLong(projectionId);
        this.seatRow = Integer.parseInt(seatRow);
        this.seatNum = Integer.parseInt(seatNum);
        this.discount = Integer.parseInt(discount);
    }

    public QuickTicketCreatorDTO(){

    }

    public Long getProjectionId() {
        return projectionId;
    }

    public void setProjectionId(Long projectionId) {
        this.projectionId = projectionId;
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
    }

    public Integer getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(Integer seatNum) {
        this.seatNum = seatNum;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}

