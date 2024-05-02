package keystrokesmod.script.packets.serverbound;

import keystrokesmod.script.packets.clientbound.S12;
import keystrokesmod.script.packets.clientbound.S27;
import keystrokesmod.script.packets.clientbound.SPacket;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class PacketHandler {
    public static CPacket convertServerBound(net.minecraft.network.Packet packet) {
        CPacket newPacket;
        try {
            if (packet instanceof C0APacketAnimation) {
                newPacket = new C0A(packet);
            }
            else if (packet instanceof C0BPacketEntityAction) {
                newPacket = new C0B((C0BPacketEntityAction) packet);
            }
            else if (packet instanceof C01PacketChatMessage) {
                newPacket = new C01((C01PacketChatMessage)packet);
            }
            else if (packet instanceof C02PacketUseEntity) {
                newPacket = new C02((C02PacketUseEntity)packet);
            }
            else if (packet instanceof C0FPacketConfirmTransaction) {
                newPacket = new C0F((C0FPacketConfirmTransaction) packet);
            }
            else if (packet instanceof C0EPacketClickWindow) {
                newPacket = new C0E((C0EPacketClickWindow) packet);
            }
            else if (packet instanceof C03PacketPlayer) {
                newPacket = new C03((C03PacketPlayer)packet, "", "", "", "", "");
            }
            else if (packet instanceof C07PacketPlayerDigging) {
                newPacket = new C07((C07PacketPlayerDigging)packet);
            }
            else if (packet instanceof C08PacketPlayerBlockPlacement) {
                newPacket = new C08((C08PacketPlayerBlockPlacement)packet);
            }
            else if (packet instanceof C09PacketHeldItemChange) {
                newPacket = new C09(((C09PacketHeldItemChange)packet), true);
            }
            else {
                newPacket = new CPacket(packet);
            }
        }
        catch (Exception ex) {
            newPacket = null;
        }
        return newPacket;
    }

    public static SPacket convertClientBound(Packet packet) {
        SPacket sPacket;
        try {
            if (packet instanceof S12PacketEntityVelocity) {
                sPacket = new S12((S12PacketEntityVelocity)packet);
            }
            else if (packet instanceof S27PacketExplosion) {
                sPacket = new S27((S27PacketExplosion)packet);
            }
            else {
                sPacket = new SPacket(packet);
            }
        }
        catch (Exception ex) {
            sPacket = null;
        }
        return sPacket;
    }

    public static Packet convertCPacket(CPacket cPacket) {
        if (cPacket instanceof C0A) {
            return new C0APacketAnimation();
        }
        else if (cPacket instanceof C0B) {
            return ((C0B) cPacket).convert();
        }
        else if (cPacket instanceof C09) {
            return ((C09) cPacket).convert();
        }
        else if (cPacket instanceof C0E) {
            return ((C0E) cPacket).convert();
        }
        else if (cPacket instanceof C0F) {
            return ((C0F) cPacket).convert();
        }
        else if (cPacket instanceof C08) {
            return ((C08) cPacket).convert();
        }
        else if (cPacket instanceof C07) {
            return ((C07) cPacket).convert();
        }
        else if (cPacket instanceof C01) {
            return ((C01) cPacket).convert();
        }
        else if (cPacket instanceof C02) {
            return ((C02) cPacket).convert();
        }
        else if (cPacket instanceof C03) {
            return ((C03) cPacket).convert();
        }
        return cPacket.packet;
    }
}
