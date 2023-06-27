package com.airhubmaster.airhubmaster.adapter;

import java.util.List;

/**
 * Interface implementing the on click method in departure adapters
 */
public interface OnSendPlaneListener {

    void onFlightClick(int planeId, List<Integer> crew, int routeId);
}
