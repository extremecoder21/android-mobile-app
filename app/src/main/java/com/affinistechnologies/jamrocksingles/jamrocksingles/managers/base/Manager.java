package com.affinistechnologies.jamrocksingles.jamrocksingles.managers.base;

/**
 * Created by clayt on 9/27/2016.
 */

/**
 * This is the main interface of an interactor. Each interactor serves a specific use case.
 */
public interface Manager {
    /**
     * This is the main method that starts an interactor. It will make sure that the interactor operation is done on a
     * background thread.
     */
    void execute();
}
