package p1xel.nobuildplus.listener.version.feature;

import p1xel.nobuildplus.listener.version.FeatureListener;

public class SpearLungeListener implements FeatureListener {

    @Override
    public String getName() {
        return "SpearLungeListener 26.1+";
    }

    @Override
    public boolean matchRequirement(int[] version) {
        return version[0] == 26 && version[1] >= 1;
    }

    // Spigot API怎麽沒有EntityLungeEvent???
    // 虧我看半天Paper javadocs才發現原來是Paper的API才有這個事件


}
