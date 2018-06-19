package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class QuickTicketCreatorDTO {

    private Long projectionId;
    private Integer seatRow;
    private Integer seatNum;
    private Integer discount;

    public QuickTicketCreatorDTO(Long projectionId, Integer seatRow, Integer seatNum, Integer discount) {
        this.projectionId = projectionId;
        this.seatRow = seatRow;
        this.seatNum = seatNum;
        this.discount = discount;
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

