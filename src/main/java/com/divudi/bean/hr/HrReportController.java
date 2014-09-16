/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.divudi.bean.hr;

import com.divudi.data.hr.FingerPrintRecordType;
import com.divudi.data.hr.ReportKeyWord;
import com.divudi.ejb.CommonFunctions;
import com.divudi.entity.hr.FingerPrintRecord;
import com.divudi.entity.hr.StaffShift;
import com.divudi.facade.FingerPrintRecordFacade;
import com.divudi.facade.StaffShiftFacade;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.TemporalType;

/**
 *
 * @author safrin
 */
@Named
@SessionScoped
public class HrReportController implements Serializable {

    ReportKeyWord reportKeyWord;
    Date fromDate;
    Date toDate;
    @EJB
    CommonFunctions commonFunctions;
    List<StaffShift> staffShifts;
    List<FingerPrintRecord> fingerPrintRecords;
    @EJB
    StaffShiftFacade staffShiftFacade;
    @EJB
    FingerPrintRecordFacade fingerPrintRecordFacade;

    public void createFingerPrintRecordLogged() {
        String sql = "";
        HashMap hm = new HashMap();
        createStaffShiftQuary(sql, hm);
        sql += " and ss.fingerPrintRecordType=:ftp";
        hm.put("ftp", FingerPrintRecordType.Logged);
        sql += " order by ss.staff,ss.recordTimeStamp";
        fingerPrintRecords = fingerPrintRecordFacade.findBySQL(sql, hm, TemporalType.DATE);
    }

    public void createFingerPrintRecordVarified() {
        String sql = "";
        HashMap hm = new HashMap();
        createStaffShiftQuary(sql, hm);
        sql += " and ss.fingerPrintRecordType=:ftp";
        hm.put("ftp", FingerPrintRecordType.Varified);
        sql += " order by ss.staff,ss.recordTimeStamp";
        fingerPrintRecords = fingerPrintRecordFacade.findBySQL(sql, hm, TemporalType.DATE);
    }

    public void createFingerPrintQuary(String sql, HashMap hm) {
        sql = "select ss from FingerPrintRecord ss"
                + " where ss.retired=false"
                + " and ss.recordTimeStamp between :frm  and :to ";
        hm.put("frm", fromDate);
        hm.put("to", toDate);

        if (getReportKeyWord().getStaff() != null) {
            sql += " and ss.staff=:stf";
            hm.put("stf", getReportKeyWord().getStaff());
        }

        if (getReportKeyWord().getDepartment() != null) {
            sql += " and ss.staff.department=:dep";
            hm.put("dep", getReportKeyWord().getDepartment());
        }

        if (getReportKeyWord().getStaffCategory() != null) {
            sql += " and ss.staff.staffCategory=:stfCat";
            hm.put("stfCat", getReportKeyWord().getStaffCategory());
        }

        if (getReportKeyWord().getDesignation() != null) {
            sql += " and ss.staff.designation=:des";
            hm.put("des", getReportKeyWord().getDesignation());
        }

        if (getReportKeyWord().getRoster() != null) {
            sql += " and ss.staff.roster=:rs";
            hm.put("rs", getReportKeyWord().getRoster());
        }

        if (getReportKeyWord().getShift() != null) {
            sql += " and ss.staffShift.shift=:sh";
            hm.put("sh", getReportKeyWord().getShift());
        }

    }

    public void createStaffShiftQuary(String sql, HashMap hm) {
        sql = "select ss from StaffShift ss"
                + " where ss.retired=false"
                + " and ss.leaveType is null "
                + " and ss.shiftDate between :frm  and :to ";
        hm.put("frm", fromDate);
        hm.put("to", toDate);

        if (getReportKeyWord().getStaff() != null) {
            sql += " and ss.staff=:stf";
            hm.put("stf", getReportKeyWord().getStaff());
        }

        if (getReportKeyWord().getDepartment() != null) {
            sql += " and ss.staff.department=:dep";
            hm.put("dep", getReportKeyWord().getDepartment());
        }

        if (getReportKeyWord().getStaffCategory() != null) {
            sql += " and ss.staff.staffCategory=:stfCat";
            hm.put("stfCat", getReportKeyWord().getStaffCategory());
        }

        if (getReportKeyWord().getDesignation() != null) {
            sql += " and ss.staff.designation=:des";
            hm.put("des", getReportKeyWord().getDesignation());
        }

        if (getReportKeyWord().getRoster() != null) {
            sql += " and ss.staff.roster=:rs";
            hm.put("rs", getReportKeyWord().getRoster());
        }

        if (getReportKeyWord().getShift() != null) {
            sql += " and ss.shift=:sh";
            hm.put("sh", getReportKeyWord().getShift());
        }

    }

    public void createStaffShift() {
        String sql = "";
        HashMap hm = new HashMap();
        createStaffShiftQuary(sql, hm);
        sql += " order by ss.shift,ss.shiftDate";
        staffShifts = staffShiftFacade.findBySQL(sql, hm, TemporalType.DATE);
    }

    public void createStaffShiftEarlyIn() {
        String sql = "";
        HashMap hm = new HashMap();
        createStaffShiftQuary(sql, hm);
        sql += " and ss.shiftStartTime  > ss.startRecord.recordTimeStamp";
        sql += " order by ss.shift,ss.shiftDate";
        staffShifts = staffShiftFacade.findBySQL(sql, hm, TemporalType.DATE);

    }

    public void createStaffShiftLateIn() {
        String sql = "";
        HashMap hm = new HashMap();
        createStaffShiftQuary(sql, hm);
        sql += " and ss.shiftStartTime  < ss.startRecord.recordTimeStamp";
        sql += " order by ss.shift,ss.shiftDate";
        staffShifts = staffShiftFacade.findBySQL(sql, hm, TemporalType.DATE);

    }

    public void createStaffShiftEarlyOut() {
        String sql = "";
        HashMap hm = new HashMap();
        createStaffShiftQuary(sql, hm);
        sql += " and ss.shiftEndTime > ss.endRecord.recordTimeStamp";
        sql += " order by ss.shift,ss.shiftDate";
        staffShifts = staffShiftFacade.findBySQL(sql, hm, TemporalType.DATE);

    }

    public void createStaffShiftLateOut() {
        String sql = "";
        HashMap hm = new HashMap();
        createStaffShiftQuary(sql, hm);
        sql += " and ss.shiftEndTime < ss.endRecord.recordTimeStamp";
        sql += " order by ss.shift,ss.shiftDate";
        staffShifts = staffShiftFacade.findBySQL(sql, hm, TemporalType.DATE);

    }

    /**
     * Creates a new instance of HrReport
     */
    public HrReportController() {
    }

    public void makeNull() {
        reportKeyWord = null;
        staffShifts = null;
        fingerPrintRecords = null;
    }

    public ReportKeyWord getReportKeyWord() {
        if (reportKeyWord == null) {
            reportKeyWord = new ReportKeyWord();
        }

        return reportKeyWord;
    }

    public void setReportKeyWord(ReportKeyWord reportKeyWord) {
        this.reportKeyWord = reportKeyWord;
    }

    public Date getFromDate() {
        if (fromDate == null) {
            fromDate = commonFunctions.getStartOfMonth(new Date());
        }
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        if (toDate == null) {
            toDate = commonFunctions.getEndOfMonth(new Date());
        }
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public CommonFunctions getCommonFunctions() {
        return commonFunctions;
    }

    public void setCommonFunctions(CommonFunctions commonFunctions) {
        this.commonFunctions = commonFunctions;
    }

    public List<StaffShift> getStaffShifts() {
        return staffShifts;
    }

    public void setStaffShifts(List<StaffShift> staffShifts) {
        this.staffShifts = staffShifts;
    }

    public List<FingerPrintRecord> getFingerPrintRecords() {
        return fingerPrintRecords;
    }

    public void setFingerPrintRecords(List<FingerPrintRecord> fingerPrintRecords) {
        this.fingerPrintRecords = fingerPrintRecords;
    }

    public StaffShiftFacade getStaffShiftFacade() {
        return staffShiftFacade;
    }

    public void setStaffShiftFacade(StaffShiftFacade staffShiftFacade) {
        this.staffShiftFacade = staffShiftFacade;
    }

}