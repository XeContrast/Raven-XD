package keystrokesmod.utility;

import keystrokesmod.event.PostUpdateEvent;
import keystrokesmod.event.ReceivePacketEvent;
import keystrokesmod.event.SendPacketEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class BadPacketsHandler { // ensures you don't get banned
    private boolean C08;
    private boolean C07;
    private boolean C02;
    private int attacks = 0;
    public int serverSlot = -1;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent ev) {
        if (!Utils.nullCheck()) {
            if (ev.phase == TickEvent.Phase.END) {
                this.serverSlot = -1;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onSendPacket(SendPacketEvent e) {
        if (e.isCanceled()) {
            return;
        }
        if (e.getPacket() instanceof C02PacketUseEntity) { // sending a C07 on the same tick as C02 can ban, this usually happens when you unblock and attack on the same tick
            if (C07) {
                e.setCanceled(true);
                return;
            }
            if (((C02PacketUseEntity) e.getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK) {
                attacks++;
            }
            C02 = true;
        }
        else if (e.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            C08 = true;
        }
        else if (e.getPacket() instanceof C07PacketPlayerDigging) {
            C07 = true;
        }
        else if (e.getPacket() instanceof C09PacketHeldItemChange) {
            if (((C09PacketHeldItemChange) e.getPacket()).getSlotId() == serverSlot) {
                e.setCanceled(true);
                return;
            }
            serverSlot = ((C09PacketHeldItemChange) e.getPacket()).getSlotId();
        }
    }

    @SubscribeEvent
    public void onReceivePacket(ReceivePacketEvent e) {
        if (e.getPacket() instanceof S09PacketHeldItemChange) {
            S09PacketHeldItemChange packet = (S09PacketHeldItemChange) e.getPacket();
            if (packet.getHeldItemHotbarIndex() >= 0 && packet.getHeldItemHotbarIndex() < InventoryPlayer.getHotbarSize()) {
                serverSlot = packet.getHeldItemHotbarIndex();
            }
        }
        else if (e.getPacket() instanceof S0CPacketSpawnPlayer && Minecraft.getMinecraft().thePlayer != null) {
            if (((S0CPacketSpawnPlayer) e.getPacket()).getEntityID() != Minecraft.getMinecraft().thePlayer.getEntityId()) {
                return;
            }
            serverSlot = ((S0CPacketSpawnPlayer) e.getPacket()).getCurrentItemID();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPostUpdate(PostUpdateEvent e) {
        C08 = C07 = C02 = false;
        attacks = 0;
    }
}
