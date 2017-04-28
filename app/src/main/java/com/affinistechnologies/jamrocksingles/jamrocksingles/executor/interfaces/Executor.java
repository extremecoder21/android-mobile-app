package com.affinistechnologies.jamrocksingles.jamrocksingles.executor.interfaces;


/**
 * Created by clayt on 9/27/2016.
 */

import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.base.AbstractManager;

/**
 * This executor is responsible for running interactors on background threads.
 * <p/>
 */
public interface Executor {

    /**
     * This method should call the interactor's run method and thus start the interactor. This should be called
     * on a background thread as interactors might do lengthy operations.
     *
     * @param interactor The interactor to run.
     */
    void execute(final AbstractManager interactor);
}
