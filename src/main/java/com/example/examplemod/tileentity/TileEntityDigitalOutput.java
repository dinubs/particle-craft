package com.example.examplemod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class TileEntityDigitalOutput extends TileEntity {
	public byte pin = 0;
	public boolean previousRedstoneState;
	
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setByte("note", this.pin);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.pin = compound.getByte("note");
        this.pin = (byte)MathHelper.clamp_int(this.pin, 0, 24);
    }
	
	public void incrementPin() {
		this.pin = (byte)((this.pin + 1) % 8);
		System.out.println(this.pin + "");
		this.markDirty();
	}
	
	public String getPin() {
		return "D" + this.pin;
	}
}
