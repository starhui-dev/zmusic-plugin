package me.zhenxin.zmusic.proto.packet.impl.paper;

import io.papermc.paper.adventure.PaperAdventure;
import me.zhenxin.zmusic.proto.packet.AdvancementPacket;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minecraft.advancements.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket.PositionedAdvancement;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Optional;

/**
 * 26.3 起进度包改为携带坐标的 PositionedAdvancement 列表
 *
 * @author Lumine1909
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class AdvancementPacket_26_3 extends AdvancementPacket {

    private static final AdvancementRequirements SIMPLE_REQUIREMENT = new AdvancementRequirements(Collections.singletonList(Collections.singletonList("1")));

    private final Identifier resourceLocation = Identifier.fromNamespaceAndPath(namespaced, key);
    private final Component messageComponent;
    private final Component descriptionComponent;

    public AdvancementPacket_26_3(Player player, String message) {
        super(player, message);
        messageComponent = PaperAdventure.asVanilla(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
        descriptionComponent = PaperAdventure.asVanilla(LegacyComponentSerializer.legacyAmpersand().deserialize(desc));
    }

    @Override
    protected void sent(boolean add) {
        ClientboundUpdateAdvancementsPacket packet;

        if (add) {
            Advancement advancement = new Advancement(
                Optional.empty(),
                Optional.of(new DisplayInfo(
                    ItemStackTemplate.fromNonEmptyStack(CraftItemStack.asNMSCopy(new ItemStack(icon))),
                    messageComponent,
                    descriptionComponent,
                    Optional.empty(),
                    AdvancementType.TASK,
                    true,
                    false,
                    true
                )),
                AdvancementRewards.EMPTY,
                Collections.emptyMap(),
                SIMPLE_REQUIREMENT,
                true
            );

            AdvancementProgress progress = new AdvancementProgress();
            progress.update(SIMPLE_REQUIREMENT);
            progress.grantProgress("1");
            packet = new ClientboundUpdateAdvancementsPacket(
                false,
                // Toast 不涉及进度界面布局，坐标与旧版 DisplayInfo 默认值一致取 0
                Collections.singletonList(new PositionedAdvancement(new AdvancementHolder(resourceLocation, advancement), 0F, 0F)),
                Collections.emptySet(),
                Collections.singletonMap(resourceLocation, progress),
                true
            );
        } else {
            packet = new ClientboundUpdateAdvancementsPacket(
                false,
                Collections.emptyList(),
                Collections.singleton(resourceLocation),
                Collections.emptyMap(),
                false
            );
        }
        CraftPlayer player = (CraftPlayer) this.player;
        player.getHandle().connection.send(packet);
    }
}
