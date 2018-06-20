package com.cinemas_theaters.cinemas_theaters.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SCALE")
public class Scale implements Serializable {

    private Long id;
    private int milestoneSilver;
    private int milestoneGold;

    private double bronzeDiscount;
    private double silverDiscount;
    private double goldDiscount;


    public Scale(){}

    public Scale(int milestoneSilver, int milestoneGold, double bronzeDiscount, double silverDiscount, double goldDiscount){
        this.milestoneGold = milestoneGold;
        this.milestoneSilver = milestoneSilver;
        this.bronzeDiscount = bronzeDiscount;
        this.silverDiscount = silverDiscount;
        this.goldDiscount = goldDiscount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId(){return this.id;}
    public void setId(Long id){this.id = id;}

    @Column
    public int getMilestoneSilver(){return this.milestoneSilver;}
    public void setMilestoneSilver(int milestoneSilver){this.milestoneSilver = milestoneSilver;}

    @Column
    public int getMilestoneGold(){return this.milestoneGold;}
    public void setMilestoneGold(int milestoneGold){this.milestoneGold = milestoneGold;}

    @Column
    public double getBronzeDiscount() {
        return bronzeDiscount;
    }

    public void setBronzeDiscount(double bronzeDiscount) {
        this.bronzeDiscount = bronzeDiscount;
    }

    @Column
    public double getSilverDiscount() {
        return silverDiscount;
    }

    public void setSilverDiscount(double silverDiscount) {
        this.silverDiscount = silverDiscount;
    }

    @Column
    public double getGoldDiscount() {
        return goldDiscount;
    }

    public void setGoldDiscount(double goldDiscount) {
        this.goldDiscount = goldDiscount;
    }
}
