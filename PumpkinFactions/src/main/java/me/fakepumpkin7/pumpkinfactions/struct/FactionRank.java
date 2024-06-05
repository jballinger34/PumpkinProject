package me.fakepumpkin7.pumpkinfactions.struct;

public enum FactionRank {
    MEMBER,
    MODERATOR,
    COLEADER,
    LEADER;


    //permissions that need to be sorted
    //invite/kick
    //build/break
    //claim/unclaim
    //

    //leader
    //

    //coleader

    //mod

    //member

    public String getPrefix(){
        if(this == MEMBER){
            return "";
        }
        if(this == MODERATOR){
            return "*";
        }
        if(this == COLEADER){
            return "**";
        }
        if(this == LEADER){
            return "***";
        }
        return "";
    }

    @Override
    public String toString() {
        if(this == MEMBER){
            return "Member";
        }
        if(this == MODERATOR){
            return "Moderator";
        }
        if(this == COLEADER){
            return "Co-Leader";
        }
        if(this == LEADER){
            return "Leader";
        }
        return super.toString();
    }
}
