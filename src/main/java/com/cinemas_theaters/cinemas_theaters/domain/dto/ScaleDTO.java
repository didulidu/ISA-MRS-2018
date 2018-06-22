package com.cinemas_theaters.cinemas_theaters.domain.dto;

public class ScaleDTO {

    private int milestoneSilver;
    private int milestoneGold;

    private double bronzeDiscount;
    private double silverDiscount;
    private double goldDiscount;

    public ScaleDTO(){}

    public ScaleDTO(int milestoneSilver, int milestoneGold, double bronzeDiscount, double silverDiscount, double goldDiscount){
        this.milestoneSilver = milestoneSilver;
        this.milestoneGold = milestoneGold;
        this.bronzeDiscount = bronzeDiscount;
        this.silverDiscount = silverDiscount;
        this.goldDiscount = goldDiscount;
    }

    public int getMilestoneSilver(){return this.milestoneSilver;}
    public void setMilestoneSilver(int milestoneSilver){this.milestoneSilver = milestoneSilver;}

    public int getMilestoneGold(){return this.milestoneGold;}
    public void setMilestoneGold(int milestoneGold){this.milestoneGold = milestoneGold;}

    public double getBronzeDiscount() {
        return bronzeDiscount;
    }

    public void setBronzeDiscount(double bronzeDiscount) {
        this.bronzeDiscount = bronzeDiscount;
    }

    public double getSilverDiscount() {
        return silverDiscount;
    }

    public void setSilverDiscount(double silverDiscount) {
        this.silverDiscount = silverDiscount;
    }

    public double getGoldDiscount() {
        return goldDiscount;
    }

    public void setGoldDiscount(double goldDiscount) {
        this.goldDiscount = goldDiscount;
    }
}
