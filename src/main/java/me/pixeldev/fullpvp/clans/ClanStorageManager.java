package me.pixeldev.fullpvp.clans;

import me.pixeldev.fullpvp.Storage;
import me.pixeldev.fullpvp.files.FileCreator;

import team.unnamed.inject.Inject;
import team.unnamed.inject.name.Named;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ClanStorageManager implements Storage<String, Clan> {

    @Inject
    @Named("clans")
    private FileCreator clanFile;

    private final Map<String, Clan> clans = new ConcurrentHashMap<>();

    @Override
    public Map<String, Clan> get() {
        return clans;
    }

    @Override
    public Optional<Clan> find(String key) {
        return Optional.ofNullable(clans.get(key));
    }

    @Override
    public Optional<Clan> findFromData(String key) {
        if (!clanFile.contains("clans." + key)) {
            return Optional.empty();
        }

        Map<String, Object> clanSerialize = new HashMap<>();

        clanSerialize.put("creator", clanFile.getString("clans." + key + ".creator"));
        clanSerialize.put("alias", clanFile.getString("clans." + key + ".alias"));

        List<UUID> members = new ArrayList<>();

        clanFile.getStringList("clans." + key + ".members").forEach(member -> members.add(UUID.fromString(member)));

        clanSerialize.put("members", members);
        clanSerialize.put("color", clanFile.getString("clans." + key + ".properties.color"));
        clanSerialize.put("allowedDamage", clanFile.getBoolean("clans." + key + ".properties.allowed-damage"));
        clanSerialize.put("messages", clanFile.getStringList("clans." + key + ".properties.messages"));
        clanSerialize.put("deaths", clanFile.getInt("clans." + key + ".statistics.deaths"));
        clanSerialize.put("kills", clanFile.getInt("clans." + key + ".statistics.kills"));

        return Optional.of(new DefaultClan(clanSerialize));
    }

    @Override
    public void save(String key) {
        find(key).ifPresent(clan -> clanFile.set("clans." + key, clan.serialize()));

        remove(key);
    }

    @Override
    public void saveObject(String key, Clan value) { }

    @Override
    public void remove(String key) {
        clans.remove(key);
    }

    @Override
    public void add(String key, Clan value) {
        clans.put(key, value);
    }

    @Override
    public void saveAll() {
        clans.keySet().forEach(this::save);

        clanFile.save();
    }

    @Override
    public void loadAll() {
        if (!clanFile.contains("clans")) {
            return;
        }

        clanFile.getConfigurationSection("clans").getKeys(false).forEach(name -> findFromData(name).ifPresent(clan -> add(name, clan)));
    }

}