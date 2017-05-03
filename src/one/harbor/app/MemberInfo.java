/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package one.harbor.app;

import java.io.Serializable;
/**
 *
 * @author eimi_
 */
public class MemberInfo implements Serializable{
   
// instance variables
    private String fullName;
    private String communityGroup;
    private String hospitality;
    private String kidsMinistry;
    private String youth;
    private String musicAndMedia;
    private String benevolence;
    private String oversightPastor;
    private String community;
    private String serving;
    private String partner;
    private String site;
    private String firstStepsDate;
    private String partnershipClassDate;
        
    public MemberInfo(){
        this.fullName = "";
        this.communityGroup = "None";
        this.hospitality = "No";
        this.kidsMinistry = "No";
        this.youth = "No";
        this.musicAndMedia = "No";
        this.benevolence = "No";
        this.oversightPastor = "None";
        this.community = "No";
        this.serving = "No";
        this.partner = "";
        this.site = "";
        this.firstStepsDate = "-";
        this.partnershipClassDate = "-";
    }
    
    //public String getFullName() {return this.fullName;}
    //public void setFullName(String fullName){this.fullName = fullName;}
    
    public String getFullName(){return fullName;}
    public MemberInfo setFullName(String fullName){
        this.fullName = fullName;
        return this;
    }
    
    public String getCommunityGroup(){return communityGroup;}
    public MemberInfo setCommunityGroup(String communityGroup){
        this.communityGroup = communityGroup;
        return this;
    }
    
    public String getHospitality(){return hospitality;}
    public MemberInfo setHospitality(String hospitality){
        this.hospitality = hospitality;
        return this;
    }
    
    public String getKidsMinistry(){return kidsMinistry;}
    public MemberInfo setKidsMinistry(String kidsMinistry){
        this.kidsMinistry = kidsMinistry;
        return this;
    }
    
    public String getYouth(){return youth;}
    public MemberInfo setYouth(String youth){
        this.youth = youth;
        return this;
    }
    
    public String getMusicAndMedia(){return musicAndMedia;}
    public MemberInfo setMusicAndMedia(String musicAndMedia){
        this.musicAndMedia = musicAndMedia;
        return this;
    }
    
    public String getBenevolence(){return benevolence;}
    public MemberInfo setBenevolence(String benevolence){
        this.benevolence = benevolence;
        return this;
    }
    
    public String getOversightPastor(){return oversightPastor;}
    public MemberInfo setOversightPastor(String oversightPastor){
        this.oversightPastor = oversightPastor;
        return this;
    }
    
    public String getCommunity(){return community;}
    public MemberInfo setCommunity(String community){
        this.community = community;
        return this;
    }
    
    public String getServing(){return serving;}
    public MemberInfo setServing(String serving){
        this.serving = serving;
        return this;
    }
    
    public String getPartner(){return partner;}
    public MemberInfo setPartner(String partner){
        this.partner = partner;
        return this;
    }
    
    public String getSite(){return site;}
    public MemberInfo setSite(String site){
        this.site = site;
        return this;
    }
    
    public String getFirstStepsDate(){return firstStepsDate;}
    public MemberInfo setFirstStepsDate(String firstStepsDate){
        this.firstStepsDate = firstStepsDate;
        return this;
    }
    
    public String getPartnershipClassDate(){return partnershipClassDate;}
    public MemberInfo setPartnershipClassDate(String partnershipClassDate){
        this.partnershipClassDate = partnershipClassDate;
        return this;
    }
}