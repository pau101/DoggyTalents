package doggytalents.entity;

import java.util.List;

import doggytalents.entity.ModeUtil.EnumMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

/**
 * @author ProPercivalalb
 */
public abstract class EntityDoggyBeam extends EntityThrowable {
	
	public EntityDoggyBeam(World worldIn) {
		super(worldIn);
	}
	
    public EntityDoggyBeam(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }
    
    public void onImpactGENERAL(Entity entityHit) {
    	if(entityHit instanceof EntityLivingBase) {
 
    		List<Entity> nearEnts = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(100D, 10D, 100D));
    		for(Object o : nearEnts) {
    			if(o instanceof EntityDog) {
                 	EntityDog dog = (EntityDog)o;
                 	if(!dog.isSitting() && entityHit != dog && dog.shouldAttackEntity((EntityLivingBase)entityHit, dog.getOwner()) && this.getThrower() instanceof EntityPlayer && dog.canInteract((EntityPlayer)this.getThrower())) {
                 		if(dog.getDistanceToEntity(entityHit) < this.getTargetDistance(dog) && (dog.mode.isMode(EnumMode.AGGRESIVE) || dog.mode.isMode(EnumMode.TACTICAL))) {
                 			dog.setAttackTarget((EntityLivingBase)entityHit);
                 		}
                 	}
                 }
             }
        }

        for(int j = 0; j < 8; ++j)
            this.world.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);

        if(!this.world.isRemote)
            this.setDead();
    }
    
    protected double getTargetDistance(EntityDog dog) {
        IAttributeInstance iattributeinstance = dog.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
    }
}