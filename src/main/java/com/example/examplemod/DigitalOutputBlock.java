package com.example.examplemod;

import com.example.examplemod.tileentity.TileEntityDigitalOutput;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DigitalOutputBlock extends Block {
	ParticleAPI api = new ParticleAPI();
	private boolean isOn;
	public String pin = "D0";
	
	protected DigitalOutputBlock(Material mat) {
		super(Material.redstoneLight);
		this.isOn = false;
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setRegistryName("digitalOutputBlock");
	}
    
    public boolean isNormalCube() {
    	return true;
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
    	TileEntity tileentity = worldIn.getTileEntity(pos);
    	System.out.println("Ok, cool");
    	this.pin = "D1";
    	
    	if (tileentity instanceof TileEntityDigitalOutput) {
    		TileEntityDigitalOutput tileentitydigitaloutput = (TileEntityDigitalOutput)tileentity;
            tileentitydigitaloutput.incrementPin();
        }
    	
        return true;
    }
	
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!world.isRemote) {
            if (world.isBlockPowered(pos)) {	
            	api.setDigitalPin(this.pin, "on");
            	this.setLightLevel(1.0F);
            	this.isOn = true;
            } else {
            	api.setDigitalPin(this.pin, "off");
            	this.setLightLevel(0.0F);
            	this.isOn = false;
            }
        }
    }
	
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityDigitalOutput();
    }
}
