package com.roidmc.core.api.reset;

import com.roidmc.core.util.Progress;

import java.io.File;

public interface RoidReset {

    int maxInteraction(boolean make);

    void make(File backupFolder, Progress progress);

    void rollback(File backupFolder, Progress progress);

}
