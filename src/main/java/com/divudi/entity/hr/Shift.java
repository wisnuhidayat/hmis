/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.divudi.entity.hr;

import com.divudi.data.hr.DayType;
import com.divudi.entity.WebUser;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author safrin
 */
@Entity
public class Shift implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne
    private Roster roster;
    @OneToOne
    private ShiftPreference shiftPreference;
    private int shiftOrder;
    @OneToMany(mappedBy = "shift", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StaffShift> staffShifts = new ArrayList<>();
    @ManyToOne
    private Shift shift;
    @Enumerated(EnumType.STRING)
    private DayType dayType;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date startingTime;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date endingTime;
    private boolean grouped;
    private int repeatedDay;
    private boolean dayOff;
//    private int count;
    @Transient
    private int durationHour;
    @Transient
    private int durationMin;

    //Created Properties
    @ManyToOne
    private WebUser creater;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdAt;
    //Retairing properties
    private boolean retired;
    @ManyToOne
    private WebUser retirer;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date retiredAt;
    private String retireComments;
    @ManyToOne
    Shift previousShift;
    @ManyToOne
    Shift nextShift;
    
    private boolean hideShift;

    public Shift getPreviousShift() {
        return previousShift;
    }

    public void setPreviousShift(Shift previousShift) {
        this.previousShift = previousShift;
    }

    public Shift getNextShift() {
        return nextShift;
    }

    public void setNextShift(Shift nextShift) {
        this.nextShift = nextShift;
    }
    
    

    public Shift() {

    }

    public int getDurationHour() {
        if (getStartingTime() == null && getEndingTime() == null) {
            return 0;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(getStartingTime());
        int sHour = cal.get(Calendar.HOUR_OF_DAY);
        cal.setTime(getEndingTime());
        int eHour = cal.get(Calendar.HOUR_OF_DAY);

        
        if (sHour < eHour) {
            durationHour = eHour - sHour;
        } else {
            durationHour = sHour - 12;
            durationHour += 12 - eHour;
        }

        return durationHour;
    }

    public int getDurationMin() {

        if (getStartingTime() == null && getEndingTime() == null) {
            return 0;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(getStartingTime());
        int sMin = cal.get(Calendar.MINUTE);
        cal.setTime(getEndingTime());
        int eMin = cal.get(Calendar.MINUTE);

        if (sMin < eMin) {
            durationMin = eMin - sMin;
        } else if (sMin == eMin) {
            durationMin = 0;
        } else {
            durationMin = sMin - eMin;
        }

        return durationMin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Shift)) {
            return false;
        }
        Shift other = (Shift) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.divudi.entity.hr.Shift[ id=" + id + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Roster getRoster() {
        return roster;
    }

    public void setRoster(Roster roster) {
        this.roster = roster;
    }

    public ShiftPreference getShiftPreference() {
        return shiftPreference;
    }

    public void setShiftPreference(ShiftPreference shiftPreference) {
        this.shiftPreference = shiftPreference;
    }

    public WebUser getCreater() {
        return creater;
    }

    public void setCreater(WebUser creater) {
        this.creater = creater;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public WebUser getRetirer() {
        return retirer;
    }

    public void setRetirer(WebUser retirer) {
        this.retirer = retirer;
    }

    public Date getRetiredAt() {
        return retiredAt;
    }

    public void setRetiredAt(Date retiredAt) {
        this.retiredAt = retiredAt;
    }

    public String getRetireComments() {
        return retireComments;
    }

    public void setRetireComments(String retireComments) {
        this.retireComments = retireComments;
    }

    public List<StaffShift> getStaffShifts() {
        return staffShifts;
    }

    public void setStaffShifts(List<StaffShift> staffShifts) {
        this.staffShifts = staffShifts;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    public Date getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Date startingTime) {
        this.startingTime = startingTime;
    }

    public Date getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(Date endingTime) {
        this.endingTime = endingTime;
    }

    public boolean isGrouped() {
        return grouped;
    }

    public void setGrouped(boolean grouped) {
        this.grouped = grouped;
    }

    public int getRepeatedDay() {
        return repeatedDay;
    }

    public void setRepeatedDay(int repeatedDay) {
        this.repeatedDay = repeatedDay;
    }

    public boolean isDayOff() {
        return dayOff;
    }

    public void setDayOff(boolean dayOff) {
        this.dayOff = dayOff;
    }

//    public int getCount() {
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//    }

    public int getShiftOrder() {
        return shiftOrder;
    }

    public void setShiftOrder(int shiftOrder) {
        this.shiftOrder = shiftOrder;
    }

    public boolean isHideShift() {
        return hideShift;
    }

    public void setHideShift(boolean hideShift) {
        this.hideShift = hideShift;
    }
    
}
