package me.fakepumpkin7.pumpkinframework.factions;

import java.util.HashMap;
import java.util.UUID;

public interface FactionAPI {

    String getFactionName(UUID uuid);

    HashMap<UUID, String> getMembersAndRankPrefix(UUID uuid);
    boolean isSameFac(UUID uuid1, UUID uuid2);
    boolean isAlly(UUID uuid1, UUID uuid2);



}
