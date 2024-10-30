package net.midget807.trapsntrickery.render;

import net.midget807.trapsntrickery.TrapsAndTrickeryMain;
import net.midget807.trapsntrickery.entity.trapsntrickery.MagmaProjectileEntity;
import net.midget807.trapsntrickery.entity.trapsntrickery.SlimeProjectileEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class SlimeProjectileEntityRenderer extends ProjectileEntityRenderer<SlimeProjectileEntity> {
    public static final Identifier SLIME = TrapsAndTrickeryMain.id("textures/entity/projectile/slime.png");
    public static final Identifier MAGMA = TrapsAndTrickeryMain.id("textures/entity/projectile/magma.png");
    public SlimeProjectileEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(SlimeProjectileEntity entity) {
        if (entity instanceof MagmaProjectileEntity) {
            return MAGMA;
        } else {
            return SLIME;
        }
    }
}
