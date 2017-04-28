package com.affinistechnologies.jamrocksingles.jamrocksingles.managers.interfaces;

import com.affinistechnologies.jamrocksingles.jamrocksingles.managers.base.Manager;
import com.affinistechnologies.jamrocksingles.jamrocksingles.models.Config;

public interface ConfigurationManager extends Manager {
    interface Callback {
        void OnAsyncResult(Config config);

        void OnAsyncResultError(String message);
    }
}