package com.sangodan.sangoplugin.utils;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import com.sangodan.sangoplugin.main.SangoPlugin;

public class ConfigMaker extends YamlConfiguration {
    private SangoPlugin plugin = SangoPlugin.instance();
    private String fileName;
    private String dir;
    public ConfigMaker(String fileName, String dir){
        this.fileName = fileName + (fileName.endsWith(".yml") ? "" : ".yml");
        this.dir = dir;
        createFile();
    }
    private void createFile() {
        try {
            File file = new File(plugin.getDataFolder() + dir, fileName);
            if (!file.exists()){
                if (plugin.getResource(fileName) != null){
                    plugin.saveResource(fileName, false);
                }else{
                    save(file);
                }
            }else{
                load(file);
                save(file);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void save(){
        try {
            save(new File(plugin.getDataFolder() + dir, fileName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
