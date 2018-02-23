package manticore;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import java.util.List;

public class Manticore {
    public static boolean gpsActive = false;
    public static int gpsX = 0;
    public static int gpsZ = 0;
    public static boolean breadcrumbActive = false;
    public static boolean displayCoords = true;
    public static boolean lightActive = false;
    public static long lightTimer = 0;
    public static boolean chestESPActive = false;
    public static boolean xrayActive = false;
    public static int[] xrayBlocks = new int[]{54, 57};
    public static boolean xrayDiamondOre = true;
    public static boolean xrayCoalOre = true;
    public static boolean xrayIronOre = true;
    public static boolean xrayGoldOre = true;
    public static boolean xrayBedrock = true;

    public static float getPct(float min, float max, float val) {
        float pct = (val - min) / max;
        if (pct < 0F) pct = 0F;
        if (pct > 1F) pct = 1F;
        return pct;
    }

    public static float interpolate(float from, float to, float pct) {
        return from + (to - from) * pct;
    }

    public static double getDistanceSq(Vector3d v1, Vector3d v2)
    {
        double d0 = v1.x + 0.5D - v2.x;
        double d1 = v1.y + 0.5D - v2.y;
        double d2 = v1.z + 0.5D - v2.z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public static List<Vector3d> breadcrumbData = Lists.newArrayList();

    private static Vector3d lastPlayerPosition = null;

    public static void updateBreadcrumb() {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (lastPlayerPosition == null) {
            lastPlayerPosition = new Vector3d();
            lastPlayerPosition.x = player.posX;
            lastPlayerPosition.y = player.posY;
            lastPlayerPosition.z = player.posZ;
        } else {
            Vector3d v2 = new Vector3d();
            v2.x = player.posX;
            v2.y = player.posY;
            v2.z = player.posZ;
            double dist = getDistanceSq(lastPlayerPosition, v2);
            if (dist >= 1) {
                lastPlayerPosition.x = player.posX;
                lastPlayerPosition.y = player.posY;
                lastPlayerPosition.z = player.posZ;
                Vector3d v3 = new Vector3d();
                v3.x = player.posX;
                v3.y = player.posY + 1;
                v3.z = player.posZ;
                breadcrumbData.add(v3);
            }
        }
    }

    public static void renderBreadcrumb(Entity renderViewEntity, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(true);

        GL11.glLineWidth(2F);

        double d0 = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * (double)partialTicks;
        double d1 = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * (double)partialTicks;
        double d2 = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * (double)partialTicks;

        GL11.glColor4f(1F, 0F, 0F, 1F);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (Vector3d v : breadcrumbData) {
            GL11.glVertex3d(v.x-(float)d0, v.y-(float)d1, v.z-(float)d2);
        }
        GL11.glEnd();

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void renderArrow(Entity renderViewEntity, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);

        double d0 = renderViewEntity.prevPosX + (renderViewEntity.posX - renderViewEntity.prevPosX) * (double)partialTicks;
        double d1 = renderViewEntity.prevPosY + (renderViewEntity.posY - renderViewEntity.prevPosY) * (double)partialTicks;
        double d2 = renderViewEntity.prevPosZ + (renderViewEntity.posZ - renderViewEntity.prevPosZ) * (double)partialTicks;

        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, 0);
        double angle = Math.atan2(Manticore.gpsX - d0, Manticore.gpsZ - d2);
        GL11.glRotated(angle * 180 / Math.PI, 0, 1, 0);
        GL11.glTranslatef(0, 0, 3);
        GL11.glColor4f(1F, 0F, 0F, 0.15F);

        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glVertex3d(0, 0.2,1);
        GL11.glVertex3d(1, 0.2,-0.2f);
        GL11.glVertex3d(0.4, 0.2,-0.2f);
        GL11.glVertex3d(0.4, 0.2,-1);
        GL11.glVertex3d(-0.4, 0.2,-1);
        GL11.glVertex3d(-0.4, 0.2,-0.2f);
        GL11.glVertex3d(-1, 0.2,-0.2f);
        GL11.glEnd();

        GL11.glLineWidth(2F);
        GL11.glColor4f(1F, 0F, 0F, 0.5F);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex3d(0, 0.2,1);
        GL11.glVertex3d(1, 0.2,-0.2f);
        GL11.glVertex3d(0.4, 0.2,-0.2f);
        GL11.glVertex3d(0.4, 0.2,-1);
        GL11.glVertex3d(-0.4, 0.2,-1);
        GL11.glVertex3d(-0.4, 0.2,-0.2f);
        GL11.glVertex3d(-1, 0.2,-0.2f);
        GL11.glEnd();

        GL11.glPopMatrix();

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}