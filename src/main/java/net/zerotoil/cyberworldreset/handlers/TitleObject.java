package net.zerotoil.cyberworldreset.handlers;

import net.zerotoil.cyberworldreset.interfaces.Title;
import org.bukkit.entity.Player;

public class TitleObject implements Title {

    @Override
    public void send(Player player, String title, String subtitle, int in, int stay, int out) {
        player.sendTitle(title, subtitle, in, stay, out);
    }

}
