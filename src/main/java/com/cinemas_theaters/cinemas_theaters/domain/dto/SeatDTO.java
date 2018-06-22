package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class SeatDTO {

    private Integer row;
    private Integer num;

    private Long id;

    public SeatDTO(Integer row, Integer num, Long id) {
        this.row = row;
        this.num = num;
        this.id = id;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
