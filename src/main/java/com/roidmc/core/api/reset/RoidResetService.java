package com.roidmc.core.api.reset;

import com.roidmc.core.RoidCore;
import com.roidmc.core.RoidPlugin;
import com.roidmc.core.util.Progress;
import com.roidmc.core.util.java.ZipCompress;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class RoidResetService extends Thread{

    private Progress progress;

    public RoidResetService() {
        this.progress = new Progress(RoidCore.getPlugins().size());
    }

    @Override
    public void run() {
        this.run(true);
    }

    private void run(boolean make){
        File backupFolder = new File(RoidCore.getInstance().getDataFolder().getParentFile(),String.format("reset-backup_%d",System.currentTimeMillis()));
        for(RoidPlugin plugin : RoidCore.getPlugins()){
            RoidReset roidReset = plugin.reset();
            if(roidReset==null){
                this.progress.next();
                continue;
            }
            try {
                Progress progress = new Progress(roidReset.maxInteraction(make));
                File folder = new File(backupFolder, plugin.getName());
                folder.mkdirs();
                try {
                    onReset(plugin.getName(), progress);
                    if(make){
                        roidReset.make(folder,progress);
                    }else {
                        roidReset.rollback(folder,progress);
                    }
                }catch (Exception e){
                    progress.error(e);
                    throw e;
                }
                progress.then();
                this.progress.next();
            }catch (Exception e){
                this.progress.error(e);
                if(!make)return;
                run(false);
                return;
            }

        }
        ZipCompress.zip(backupFolder);

        try {
            FileUtils.deleteDirectory(backupFolder);
        } catch (IOException e) {
            System.out.println("Não foi possivel deletar a pasta de backup");
        }
        this.progress.then();
    }

    public Progress getRootProgress() {
        return progress;
    }

    public abstract void onReset(String title, Progress runtimeProgress);
}
