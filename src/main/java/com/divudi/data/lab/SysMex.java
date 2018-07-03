/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.divudi.data.lab;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Dr. M H B Ariyaratne <buddhika.ari@gmail.com>
 */
public class SysMex {

    private String inputString;
    private List<Byte> bytes;

    private int lengthOfMessage = 444;
    private int instrumentId1Start = 4;
    private int instrumentId1End = 19;
    private int sampleId1Start = 33;
    private int sampleId1End = 47;
    private int year1Start = 48;
    private int yearEnd = 51;
    private int month1Start = 52;
    private int month1End = 53;
    private int date1Start = 54;
    private int date1End = 55;
    private int hour1Start = 56;
    private int hour1End = 57;
    private int min1Start = 58;
    private int min1End = 59;
    private int sampleId2Start = 222;
    private int sampleId2End = 236;
    private int wbcStart = 237;
    private int wbcEnd = 242;
    private int rbcStart = 243;
    private int rbcEnd = 247;
    private int hgbStart = 248;
    private int hgbEnd = 252;
    private int hctStart = 253;
    private int hctEnd = 257;
    private int mcvStart = 258;
    private int mcvEnd = 262;
    private int mchStart = 263;
    private int mchEnd = 267;
    private int mchcStart = 268;
    private int mchcEnd = 272;
    private int pltStart = 273;
    private int pltEnd = 277;
    private int lymphPercentStart = 278;
    private int lymphPercentEnd = 282;
    private int monoPercentStart = 283;
    private int monoPercentEnd = 287;
    private int neuPercentStart = 288;
    private int neuPercentEnd = 292;
    private int eoPercentStart = 293;
    private int eoPercentEnd = 297;
    private int basoPercentStart = 298;
    private int basoPercentEnd = 302;

    private long sampleId;
    private String wbc;
    private String rbc;
    private String hgb;
    private String hct;
    private String mcv;
    private String mch;
    private String mchc;
    private String plt;
    private String lymphPercentage;
    private String monoPercentage;
    private String neutPercentage;
    private String eoPercentage;
    private String basoPercentage;

    public boolean isCorrectReport() {
        boolean flag = true;
        if (bytes == null || bytes.isEmpty()) {
            return false;
        }
        if (bytes.size() < 300) {
            return false;
        }
        double id1 = findValue(sampleId1Start, sampleId1End, 0);
        System.out.println("id1 = " + id1);
        double id2 = findValue(sampleId2Start, sampleId2End, 0);
        System.out.println("id2 = " + id2);
        if (id1 != id2) {
            return false;
        }
        double thb = findValue(hgbStart, hgbEnd, 2);
        System.out.println("thb = " + thb);
        if (thb < 2 || thb > 25) {
            return false;
        }
        double tpcv = findValue(hctStart, hctEnd, 2);
        if (tpcv < 5 || tpcv > 95) {
            return false;
        }
        return true;
    }

    private void textToByteArray() {
        bytes = new ArrayList<>();
        String strInput = inputString;
        String[] strByte;
        if (inputString.contains(Pattern.quote("+"))) {
            strByte = strInput.split(Pattern.quote("+"));
        } else {
            strByte = strInput.split("\\s+");
        }
        for (String s : strByte) {
            System.out.println("s = " + s);
            s = s.replaceAll("\n", "");
            s = s.replaceAll("\r", "");
            try {
                Byte b = Byte.parseByte(s);
                System.out.println("b = " + b);
                bytes.add(b);
            } catch (Exception e) {
                System.out.println("e = " + e);
                bytes.add(null);
            }
        }
    }

    public String addDecimalSeperator(String val) {
        String formatString = "#,###";
        Double dbl = Double.parseDouble(val);
        DecimalFormat formatter = new DecimalFormat(formatString);
        return formatter.format(dbl);
    }

    private String round(double value, int places) {
        String returnVal = "";
        if (places == 0) {
            returnVal = ((long) value) + "";
        } else if (places < 0) {
            long tn = (long) value;
            long pow = (long) Math.pow(10, Math.abs(places));
            tn = (tn / pow) * pow;
            returnVal = tn + "";
        } else {
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            returnVal = bd.doubleValue() + "";
        }
        return returnVal;
    }

    private Double findValue(int from, int to, int decimals) {
        Double val = null;
        String display = "";
        for (int i = from; i < to + 1; i++) {
            System.out.println("i = " + i);
            int temN = bytes.get(i);
            display += (char) temN + "";
        }
        if (decimals > 0) {
            String wn = display.substring(0, display.length() - decimals);
            String fn = display.substring(display.length() - decimals, display.length());
            display = wn + "." + fn;
            try {
                val = Double.parseDouble(display);
            } catch (Exception e) {
                val = null;
            }
        } else if (decimals > 0) {
            try {
                val = Double.parseDouble(display);
            } catch (Exception e) {
                val = null;
            }
        } else {
            try {
                val = Double.parseDouble(display);
                val = val * Math.pow(10, Math.abs(decimals));
            } catch (Exception e) {
                val = null;
            }
        }
        return val;
    }

    public int getLengthOfMessage() {
        return lengthOfMessage;
    }

    public void setLengthOfMessage(int lengthOfMessage) {
        this.lengthOfMessage = lengthOfMessage;
    }

    public int getInstrumentId1Start() {
        return instrumentId1Start;
    }

    public void setInstrumentId1Start(int instrumentId1Start) {
        this.instrumentId1Start = instrumentId1Start;
    }

    public int getInstrumentId1End() {
        return instrumentId1End;
    }

    public void setInstrumentId1End(int instrumentId1End) {
        this.instrumentId1End = instrumentId1End;
    }

    public int getSampleId1Start() {
        return sampleId1Start;
    }

    public void setSampleId1Start(int sampleId1Start) {
        this.sampleId1Start = sampleId1Start;
    }

    public int getSampleId1End() {
        return sampleId1End;
    }

    public void setSampleId1End(int sampleId1End) {
        this.sampleId1End = sampleId1End;
    }

    public int getYear1Start() {
        return year1Start;
    }

    public void setYear1Start(int year1Start) {
        this.year1Start = year1Start;
    }

    public int getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(int yearEnd) {
        this.yearEnd = yearEnd;
    }

    public int getMonth1Start() {
        return month1Start;
    }

    public void setMonth1Start(int month1Start) {
        this.month1Start = month1Start;
    }

    public int getMonth1End() {
        return month1End;
    }

    public void setMonth1End(int month1End) {
        this.month1End = month1End;
    }

    public int getDate1Start() {
        return date1Start;
    }

    public void setDate1Start(int date1Start) {
        this.date1Start = date1Start;
    }

    public int getDate1End() {
        return date1End;
    }

    public void setDate1End(int date1End) {
        this.date1End = date1End;
    }

    public int getHour1Start() {
        return hour1Start;
    }

    public void setHour1Start(int hour1Start) {
        this.hour1Start = hour1Start;
    }

    public int getHour1End() {
        return hour1End;
    }

    public void setHour1End(int hour1End) {
        this.hour1End = hour1End;
    }

    public int getMin1Start() {
        return min1Start;
    }

    public void setMin1Start(int min1Start) {
        this.min1Start = min1Start;
    }

    public int getMin1End() {
        return min1End;
    }

    public void setMin1End(int min1End) {
        this.min1End = min1End;
    }

    public int getSampleId2Start() {
        return sampleId2Start;
    }

    public void setSampleId2Start(int sampleId2Start) {
        this.sampleId2Start = sampleId2Start;
    }

    public int getSampleId2End() {
        return sampleId2End;
    }

    public void setSampleId2End(int sampleId2End) {
        this.sampleId2End = sampleId2End;
    }

    public int getWbcStart() {
        return wbcStart;
    }

    public void setWbcStart(int wbcStart) {
        this.wbcStart = wbcStart;
    }

    public int getWbcEnd() {
        return wbcEnd;
    }

    public void setWbcEnd(int wbcEnd) {
        this.wbcEnd = wbcEnd;
    }

    public int getRbcStart() {
        return rbcStart;
    }

    public void setRbcStart(int rbcStart) {
        this.rbcStart = rbcStart;
    }

    public int getRbcEnd() {
        return rbcEnd;
    }

    public void setRbcEnd(int rbcEnd) {
        this.rbcEnd = rbcEnd;
    }

    public int getHgbStart() {
        return hgbStart;
    }

    public void setHgbStart(int hgbStart) {
        this.hgbStart = hgbStart;
    }

    public int getHgbEnd() {
        return hgbEnd;
    }

    public void setHgbEnd(int hgbEnd) {
        this.hgbEnd = hgbEnd;
    }

    public int getHctStart() {
        return hctStart;
    }

    public void setHctStart(int hctStart) {
        this.hctStart = hctStart;
    }

    public int getHctEnd() {
        return hctEnd;
    }

    public void setHctEnd(int hctEnd) {
        this.hctEnd = hctEnd;
    }

    public int getMcvStart() {
        return mcvStart;
    }

    public void setMcvStart(int mcvStart) {
        this.mcvStart = mcvStart;
    }

    public int getMcvEnd() {
        return mcvEnd;
    }

    public void setMcvEnd(int mcvEnd) {
        this.mcvEnd = mcvEnd;
    }

    public int getMchStart() {
        return mchStart;
    }

    public void setMchStart(int mchStart) {
        this.mchStart = mchStart;
    }

    public int getMchEnd() {
        return mchEnd;
    }

    public void setMchEnd(int mchEnd) {
        this.mchEnd = mchEnd;
    }

    public int getMchcStart() {
        return mchcStart;
    }

    public void setMchcStart(int mchcStart) {
        this.mchcStart = mchcStart;
    }

    public int getMchcEnd() {
        return mchcEnd;
    }

    public void setMchcEnd(int mchcEnd) {
        this.mchcEnd = mchcEnd;
    }

    public int getPltStart() {
        return pltStart;
    }

    public void setPltStart(int pltStart) {
        this.pltStart = pltStart;
    }

    public int getPltEnd() {
        return pltEnd;
    }

    public void setPltEnd(int pltEnd) {
        this.pltEnd = pltEnd;
    }

    public int getLymphPercentStart() {
        return lymphPercentStart;
    }

    public void setLymphPercentStart(int lymphPercentStart) {
        this.lymphPercentStart = lymphPercentStart;
    }

    public int getLymphPercentEnd() {
        return lymphPercentEnd;
    }

    public void setLymphPercentEnd(int lymphPercentEnd) {
        this.lymphPercentEnd = lymphPercentEnd;
    }

    public int getMonoPercentStart() {
        return monoPercentStart;
    }

    public void setMonoPercentStart(int monoPercentStart) {
        this.monoPercentStart = monoPercentStart;
    }

    public int getMonoPercentEnd() {
        return monoPercentEnd;
    }

    public void setMonoPercentEnd(int monoPercentEnd) {
        this.monoPercentEnd = monoPercentEnd;
    }

    public int getNeuPercentStart() {
        return neuPercentStart;
    }

    public void setNeuPercentStart(int neuPercentStart) {
        this.neuPercentStart = neuPercentStart;
    }

    public int getNeuPercentEnd() {
        return neuPercentEnd;
    }

    public void setNeuPercentEnd(int neuPercentEnd) {
        this.neuPercentEnd = neuPercentEnd;
    }

    public int getEoPercentStart() {
        return eoPercentStart;
    }

    public void setEoPercentStart(int eoPercentStart) {
        this.eoPercentStart = eoPercentStart;
    }

    public int getEoPercentEnd() {
        return eoPercentEnd;
    }

    public void setEoPercentEnd(int eoPercentEnd) {
        this.eoPercentEnd = eoPercentEnd;
    }

    public int getBasoPercentStart() {
        return basoPercentStart;
    }

    public void setBasoPercentStart(int basoPercentStart) {
        this.basoPercentStart = basoPercentStart;
    }

    public int getBasoPercentEnd() {
        return basoPercentEnd;
    }

    public void setBasoPercentEnd(int basoPercentEnd) {
        this.basoPercentEnd = basoPercentEnd;
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
        textToByteArray();
    }

    public List<Byte> getBytes() {
        return bytes;
    }

    public void setBytes(List<Byte> bytes) {
        this.bytes = bytes;
    }

    public long getSampleId() {
        sampleId = (findValue(sampleId1Start, sampleId1End, 0)).longValue();
        return sampleId;
    }

    public void setSampleId(long sampleId) {
        this.sampleId = sampleId;
    }

    public String getWbc() {
        double w = findValue(wbcStart, wbcEnd, 0);
        wbc = round(w, -2);
        wbc = addDecimalSeperator(wbc);
        return wbc;
    }

    public void setWbc(String wbc) {
        this.wbc = wbc;
    }

    public String getRbc() {
        double r = findValue(rbcStart, rbcEnd, 3);
        rbc = round(r, 2);
        return rbc;
    }

    public void setRbc(String rbc) {
        this.rbc = rbc;
    }

    public String getHgb() {
        double h = findValue(hgbStart, hgbEnd, 2);
        hgb = round(h, 1);
        return hgb;
    }

    public void setHgb(String hgb) {
        this.hgb = hgb;
    }

    public String getHct() {
        double h = findValue(hctStart, hctEnd, 2);
        hct = round(h, 1);
        return hct;
    }

    public void setHct(String hct) {
        this.hct = hct;
    }

    public String getMcv() {
        double m = findValue(mcvStart, mcvEnd, 2);
        mcv = round(m, 1);
        return mcv;
    }

    public void setMcv(String mcv) {
        this.mcv = mcv;
    }

    public String getMch() {
        double m = findValue(mchStart, mchEnd, 2);
        mch = round(m, 1);
        return mch;
    }

    public void setMch(String mch) {
        this.mch = mch;
    }

    public String getMchc() {
        double m = findValue(mchcStart, mchcEnd, 2);
        mchc = round(m, 1);
        return mchc;
    }

    public void setMchc(String mchc) {
        this.mchc = mchc;
    }

    public String getPlt() {
        double p = findValue(pltStart, pltEnd, -2);
        plt = round(p, -3);
        plt = addDecimalSeperator(plt);
        return plt;
    }

    public void setPlt(String plt) {
        this.plt = plt;
    }

    public String getLymphPercentage() {
        double l = findValue(lymphPercentStart, lymphPercentEnd, 2);
        lymphPercentage = round(l, 0);
        return lymphPercentage;
    }

    public void setLymphPercentage(String lymphPercentage) {
        this.lymphPercentage = lymphPercentage;
    }

    public String getMonoPercentage() {
        double m = findValue(monoPercentStart, monoPercentEnd, 2);
        monoPercentage = round(m, 0);
        return monoPercentage;
    }

    public void setMonoPercentage(String monoPercentage) {
        this.monoPercentage = monoPercentage;
    }

    public String getNeutPercentage() {
        double n = findValue(neuPercentStart, neuPercentEnd, 2);
        neutPercentage = round(n, 0);
        return neutPercentage;
    }

    public void setNeutPercentage(String neutPercentage) {
        this.neutPercentage = neutPercentage;
    }

    public String getEoPercentage() {
        double e = findValue(eoPercentStart, eoPercentEnd, 2);
        eoPercentage = round(e, 0);
        return eoPercentage;
    }

    public void setEoPercentage(String eoPercentage) {
        this.eoPercentage = eoPercentage;
    }

    public String getBasoPercentage() {
        double b = findValue(basoPercentStart, basoPercentEnd, 2);
        basoPercentage = round(b, 0);
        return basoPercentage;
    }

    public void setBasoPercentage(String basoPercentage) {
        this.basoPercentage = basoPercentage;
    }

}
