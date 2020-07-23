package me.perfectpixel.fullpvp.user;

import me.perfectpixel.fullpvp.Storage;
import me.perfectpixel.fullpvp.files.FileManager;

import me.yushust.inject.Inject;
import me.yushust.inject.name.Named;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class UserStorageManager implements Storage<User, UUID> {

    @Inject
    @Named("data")
    private FileManager data;

    private final Set<User> users = new HashSet<>();

    @Override
    public Set<User> users() {
        return users;
    }

    @Override
    public Optional<User> find(UUID uuid) {
        User user = null;

        for (User findingUser : users) {
            if (findingUser.getID().equals(uuid)) {
                user = findingUser;
            }
        }

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findFromData(UUID uuid) {
        return Optional.of(new SimpleUser((UUID) data.get("users." + uuid.toString())));
    }

    @Override
    public void save(UUID uuid) {
        find(uuid).ifPresent(user -> data.set("users." + uuid.toString(), user.serialize()));
        
        remove(uuid);
    }

    @Override
    public void remove(UUID uuid) {
        find(uuid).ifPresent(users::remove);
    }

    @Override
    public void add(User user) {
        users.add(user);
    }

}